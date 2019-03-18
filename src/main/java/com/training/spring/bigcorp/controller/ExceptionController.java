package com.training.spring.bigcorp.controller;

import com.training.spring.bigcorp.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(NotFoundException.class)
    public ModelAndView handle(NotFoundException e){
        String stringStackTrace = "";
        for (StackTraceElement trace:e.getStackTrace()){
            stringStackTrace += trace.toString() + "\n";
        }
        ModelAndView mv = new ModelAndView("/error/404")
                .addObject("status", 404)
                .addObject("error", "Not found exception")
                .addObject("trace", stringStackTrace)
                .addObject("timestamp", new Date())
                .addObject("message", e.getMessage());
        mv.setStatus(HttpStatus.NOT_FOUND);
        return mv;
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ModelAndView handle(AccessDeniedException e){
        String stringStackTrace = "";
        for (StackTraceElement trace:e.getStackTrace()){
            stringStackTrace += trace.toString() + "\n";
        }
        ModelAndView mv = new ModelAndView("/error/403")
                .addObject("status", 403)
                .addObject("error", "Access Forbidden")
                .addObject("trace", stringStackTrace)
                .addObject("timestamp", new Date())
                .addObject("message", e.getMessage());
        mv.setStatus(HttpStatus.FORBIDDEN);
        return mv;
    }
}
