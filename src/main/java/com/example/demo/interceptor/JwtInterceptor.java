package com.example.demo.interceptor;

import com.alibaba.fastjson.JSON;
import com.auth0.jwt.exceptions.*;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.demo.response.BaseHttpResponse;
import com.example.demo.util.RedisUtil;
import com.example.demo.util.TokenUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import jdk.nashorn.internal.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.function.ServerResponse;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

@Component
public class JwtInterceptor extends HandlerInterceptorAdapter {

    /**
     * 简化获取token数据的代码编写（判断是否登录）
     * 1.通过request获取请求token信息
     * 2.从token中解析获取claims
     * 3.将claims绑定到request域中
     */

    @Resource
    private RedisUtil redisUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 1.通过request获取请求token信息
        String authorization = request.getHeader("Authorization");
        //判断请求头信息是否为空，或者是否已Bearer开头
        if (!StringUtils.isEmpty(authorization) && authorization.startsWith("Bearer")) {
            //获取token数据
            String token = authorization.replace("Bearer ", "");
            //先验证token合法性再对比缓存中的token
            try {
                DecodedJWT jwt = TokenUtil.verify(token);
                String Username = jwt.getClaim("Username").asString();
                //获取缓存中对应的的token
                String cacheToken = redisUtil.get(Username);
                if (StringUtils.isEmpty(cacheToken) || !token.equals(cacheToken)) {
                    onTokenError(response);
                    return false;
                }
                return true;
            } catch (JWTDecodeException | UnsupportedEncodingException | AlgorithmMismatchException | SignatureVerificationException | InvalidClaimException e) {
                onTokenError(response);
                return false;
            } catch (TokenExpiredException e) {
                onTokenExpired(response);
                return false;
            }
        }
        onTokenError(response);
        return false;
    }

    private void onTokenExpired(HttpServletResponse response) throws IOException {
        //重置response
        response.reset();
        //设置编码格式
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter pw = response.getWriter();
        pw.write(JSON.toJSONString(new BaseHttpResponse().tokenExpired()));
        pw.flush();
        pw.close();
    }

    private void onTokenError(HttpServletResponse response) throws IOException {
        //重置response
        response.reset();
        //设置编码格式
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter pw = response.getWriter();
        pw.write(JSON.toJSONString(new BaseHttpResponse().tokenError()));
        pw.flush();
        pw.close();
    }
}

