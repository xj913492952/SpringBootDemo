package com.example.demo.response;

public class BaseHttpResponse {
    //"status": 500, "error": "Internal Server Error",(代码逻辑未捕获到的异常直接返回到前端了：需要处理)
    public static final String CODE_SUCCEED = "000";
    public String Code;
    public String Message;

    public BaseHttpResponse succeed() {
        this.Code = CODE_SUCCEED;
        this.Message = "请求成功";
        return this;
    }

    public BaseHttpResponse error() {
        this.Code = "999";
        this.Message = "服务器错误";
        return this;
    }

    public BaseHttpResponse tokenError() {
        this.Code = "10000";
        this.Message = "账号异地登录";
        return this;
    }

    public BaseHttpResponse tokenExpired() {
        this.Code = "10001";
        this.Message = "token过期";
        return this;
    }

    public BaseHttpResponse error(String Code, String Message) {
        this.Code = Code;
        this.Message = Message;
        return this;
    }

    public BaseHttpResponse errorWithUserNotExit() {
        this.Code = Code;
        this.Message = Message;
        return this;
    }
}
