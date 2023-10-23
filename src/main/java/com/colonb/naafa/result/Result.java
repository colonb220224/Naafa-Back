package com.colonb.naafa.result;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class Result {
    private final String message;
    private final HttpStatus status;
    private final Object data;
    private final boolean success;

    public Result(String message, HttpStatus status, Object data, boolean success) {
        this.message = message;
        this.status = status;
        this.data = data;
        this.success = success;
    }

    public Result(String message, int code, Object data, boolean success) {
        this.message = message;
        this.status = HttpStatus.valueOf(code);
        this.data = data;
        this.success = success;
    }

    public Result(String message, HttpStatus status, boolean success) {
        this.message = message;
        this.status = status;
        this.data = null;
        this.success = success;
    }

    public Result(String message, int code, boolean success) {
        this.message = message;
        this.status = HttpStatus.valueOf(code);
        this.data = null;
        this.success = success;
    }


    public Result(HttpStatus status, Object data, boolean success) {
        if (success) {
            this.message = "요청에 성공했습니다.";
        } else {
            this.message = "요청에 실패했습니다.";
        }
        this.status = status;
        this.data = data;
        this.success = success;
    }


    public Result(HttpStatus status, boolean success) {
        if (success) {
            this.message = "요청에 성공했습니다.";
        } else {
            this.message = "요청에 실패했습니다.";
        }
        this.status = status;
        this.data = null;
        this.success = success;
    }

    public HttpStatus status() {
        return this.status;
    }
}
