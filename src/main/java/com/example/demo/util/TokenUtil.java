package com.example.demo.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.InvalidClaimException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.io.UnsupportedEncodingException;
import java.util.Date;

public class TokenUtil {

    // 自定义密钥,可以自己随便写,越复杂越好
    public static final String SECRET_KEY = "53g2jkj2be215k1b1";

    public static String createToken(String Username) throws UnsupportedEncodingException {
        // 创建token
        String token = JWT.create()
                // 在负载中添加自定义的数据
                //.withClaim("userId", "1001")
                .withClaim("Username", Username)
                // 发布时间,也就是生成时间,可以稍微记录一下上次的登录时间
                .withIssuedAt(new Date())
                // 设置该token的过期时间
                // 10分钟后该token过期
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 10))
                // sign要在最后调用,使用Algorithm选择加密方式,常用HMAC256
                .sign(Algorithm.HMAC256(SECRET_KEY));
        System.out.println(token);
        return token;
        // 生成后的token: eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE1NDEwNDEzNDksInVzZXJJZCI6MTAwMSwiaWF0IjoxNTQxMDQxMzM5LCJ1c2VybmFtZSI6Imt0bG42NjYifQ.tU_kElllOPZq4A67VEJCODlwD3OFi6ntC4V8ARO60Q0
    }

    public static DecodedJWT verify(String token) throws UnsupportedEncodingException,
            AlgorithmMismatchException, SignatureVerificationException, TokenExpiredException, InvalidClaimException {
        // 根据校验规则HMAC256生成校验对象
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SECRET_KEY)).build();
        // 校验前端发送过来的token是否合法
        // 如果合法会返回一个解码后的jwt对象
        // 如果不合法,会抛出xxx异常
        DecodedJWT jwt = verifier.verify(token);
        return jwt;
    }

}
