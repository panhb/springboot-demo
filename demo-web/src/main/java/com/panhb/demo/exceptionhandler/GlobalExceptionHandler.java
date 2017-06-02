package com.panhb.demo.exceptionhandler;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.panhb.demo.model.result.Result;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler
	@ResponseBody
	public Result exceptionHandler(Exception e) {
		return Result.error(e.getMessage());
	}

}

