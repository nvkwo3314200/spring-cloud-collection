package com.peak.common.util;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * 统一响应处理类
 * @param <T>
 */
@Data
public class Response<T> {
    private int code; // 响应code
    private String msg; // 响应消息
    private T result; // 结果
    private Map<String, Object> extend = new HashMap<String, Object>(); // 用于扩展

    // 私有化构造方法， 防止外不调用
    private Response () {}

    private Response (int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public interface CodeValue {
        int SUCCESS = 200;
        int ERROR = 400;
    }

    public static Response success() {
        return new Response(CodeValue.SUCCESS, null);
    }

    public static Response error() {
        return new Response(CodeValue.ERROR, null);
    }

    public static Response success(Object t) {
        Response response = new Response(CodeValue.SUCCESS, null);
        response.result = t;
        return response;
    }

    public static Response error(String msg) {
        return new Response(CodeValue.ERROR, msg);
    }

    public Response addExtend(String key, String value) {
        this.extend.put(key, value);
        return this;
    }
}
