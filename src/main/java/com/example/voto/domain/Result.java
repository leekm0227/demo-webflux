package com.example.voto.domain;

import com.example.voto.config.ErrorCode;

import java.util.HashMap;

public class Result {
    private ErrorCode errorCode;
    private String message = "";
    private int code = 0;
    private HashMap<String, Object> data = new HashMap<>();

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public HashMap<String, Object> getData() {
        return data;
    }

    public void setData(HashMap<String, Object> data) {
        this.data = data;
    }

    public void addData(String key, Object object) {
        data.put(key, object);
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}
