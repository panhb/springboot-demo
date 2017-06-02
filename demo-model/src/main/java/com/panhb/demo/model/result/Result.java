package com.panhb.demo.model.result;

import com.panhb.demo.constants.Constants;

public class Result {
	
	public Result() {
	}

	public static Result success(String msg) {
		return new Result(Constants.SUCCESS, msg);
	}
	
	public static Result success(String msg, Object info){
		return new Result(Constants.SUCCESS, msg, info);
	}
	
	public static Result error(String msg){
		return new Result(Constants.ERROR, msg);
	}
	
	public static Result error(String msg, Object info){
		return new Result(Constants.ERROR, msg, info);
	}
	
	public Result(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}
	
	public Result(int code, String msg,Object info) {
		this.code = code;
		this.msg = msg;
		this.info = info;
	}

	// 编码
	public int code;

	// 详细信息
	public String msg;
	
	// 详细信息
	public Object info;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getInfo() {
		return info;
	}

	public void setInfo(Object info) {
		this.info = info;
	}
	
	
}
