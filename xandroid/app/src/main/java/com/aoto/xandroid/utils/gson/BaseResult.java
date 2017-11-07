package com.aoto.xandroid.utils.gson;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/9/30.
 */

public class BaseResult<T> implements Serializable{
    private String state="1";//默认请求成功
    private String message="执行成功";
    private T result;

    public BaseResult(T result) {
        this.result = result;
    }

    public BaseResult(String state, String message, T result) {
        this.state = state;
        this.message = message;
        this.result = result;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}
