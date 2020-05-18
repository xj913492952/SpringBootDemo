package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/Global")
public class GlobalController {

    @GetMapping("/CheckNewVersion/{appName}")
    @ResponseBody
    public String checkNewVersion(@PathVariable String appName) {
        System.out.println("appName="+appName);
        return "has no new version";
    }
}
