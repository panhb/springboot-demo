package com.panhb.demo.exceptionhandler;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.panhb.demo.model.result.Result;

/**
 * @author panhb
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

	@ExceptionHandler
	@ResponseBody
	public Result exceptionHandler(Exception e) {
		log.error("",e);
		return Result.error(e.getMessage());
	}

}

