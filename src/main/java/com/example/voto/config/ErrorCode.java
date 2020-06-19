package com.example.voto.config;

public enum ErrorCode {
    DUPLICATE_KEY(1001, "duplicate key");

    final int code;
    final String description;

    ErrorCode(int code, String description) {
        this.code = code;
        this.description = description;
    }
}
