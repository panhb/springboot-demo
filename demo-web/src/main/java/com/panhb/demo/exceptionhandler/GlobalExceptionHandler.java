package com.panhb.demo.exceptionhandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.panhb.demo.model.result.Result;


@ControllerAdvice
public class GlobalExceptionHandler {

	public static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);
	
	@ExceptionHandler
	@ResponseBody
	public Result exceptionHandler(Exception e) {
		log.error("",e);
		return Result.error(e.getMessage());
	}

}

