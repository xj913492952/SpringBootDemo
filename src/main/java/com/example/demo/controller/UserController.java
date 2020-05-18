package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.response.BaseHttpResponse;
import com.example.demo.response.DataHttpResponse;
import com.example.demo.util.RedisUtil;
import com.example.demo.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/User")
public class UserController {

    @Resource
    private RedisUtil redisUtil;


    @PostMapping("/Login")
    @ResponseBody
    public Object userLogin(@RequestParam("Username") String Username, @RequestParam("Password") String Password) {
        try {
            String token = TokenUtil.createToken(Username);
            redisUtil.set(Username, token);
            Map<String, String> data = new HashMap<>();
            data.put("token", "Bearer " + token);
            return new DataHttpResponse().succeed(data);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return new BaseHttpResponse().error();
        }
    }

    @PostMapping("/Register")
    @ResponseBody
    public Object register(@RequestBody User u) {
        System.out.println("user=" + u.toString());
        return new BaseHttpResponse().succeed();
    }

    @GetMapping("/GetUserInfo")
    @ResponseBody
    public Object getUserInfo(@RequestParam("Username") String Username) {
        System.out.println("Username=" + Username);
        User u = new User();
        u.Username = Username;
        return new DataHttpResponse().succeed(u);
    }
}
