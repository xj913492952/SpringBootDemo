package com.example.demo.response;

public class DataHttpResponse extends BaseHttpResponse{

    public Object Data;
    public DataHttpResponse succeed(Object d) {
        this.Code = CODE_SUCCEED;
        this.Message = "请求成功";
        this.Data = d;
        return this;
    }
}
