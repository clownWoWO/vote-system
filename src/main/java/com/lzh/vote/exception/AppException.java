/*
 * Copyright (c) 2012-2020 iSports Team. All Rights Reserved.
 * This software is the confidential and proprietary information of
 * iSports Team. You shall not disclose such Confidential Information
 * and shall use it only in accordance with the terms of the agreements
 * you entered into with iSports Team.
 */
package com.lzh.vote.exception;


/**
 * @ClassName AppException
 * @Package com.isports.foundation.exception
 * @Author jkcheung
 * @Date 2020/6/11 2:51 下午
 * @Description
 * @Version 1.0
 **/
public class AppException extends RuntimeException {
	private int errorCode;
	private String errorMessage;

	public AppException(String message) {
		super("系统运行时异常:"+",message:"+message);
		this.errorCode=500;
		this.errorMessage = message;
	}

	public AppException(int errorCode, String message) {
		super("系统运行时异常：error:"+errorCode+",message:"+message);
		this.errorCode=errorCode;
		this.errorMessage = message;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}
}
