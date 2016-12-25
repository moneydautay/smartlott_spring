package com.smartlott.exceptions;

import org.springframework.validation.BindingResult;

/**
 * User: dbudunov
 * Date: 07.08.13
 * Time: 20:10
 */
public class PerfectMoneyException extends RuntimeException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private BindingResult msg;
	
	private String message;
	
	public PerfectMoneyException(BindingResult result){
        this.msg = result;
    }
	
	public PerfectMoneyException(String msg){
		message = msg;
	}

	public BindingResult getMsg() {
		return msg;
	}

	public String getMessage() {
		return message;
	}
	
	
}
