package com.colonb.naafa.handler;

import com.colonb.naafa.result.Result;
import org.apache.ibatis.exceptions.PersistenceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(PersistenceException.class)
    public ResponseEntity<Result> handlingMybatisException(PersistenceException e) {
        StackTraceElement[] stackTraceElements = e.getStackTrace();
        System.out.println("MyBatis 예외 발생");
        System.out.println(stackTraceElements[0]);
        System.out.println("----------------------");
        //추후 로그로 변경
        return ResponseEntity.internalServerError().body(new Result("요청을 처리중 문제가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR, false));
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<Result> handlingNPE(NullPointerException e) {
        StackTraceElement[] stackTraceElements = e.getStackTrace();
        System.out.println("NPE 발생");
        System.out.println(stackTraceElements[0]);
        System.out.println("----------------------");
        return ResponseEntity.internalServerError().body(new Result("요청을 처리중 문제가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR, false));
    }


}
