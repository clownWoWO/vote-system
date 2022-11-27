/*
 * Copyright (c) 2012-2020 iSports Team. All Rights Reserved.
 * This software is the confidential and proprietary information of
 * iSports Team. You shall not disclose such Confidential Information
 * and shall use it only in accordance with the terms of the agreements
 * you entered into with iSports Team.
 */
package com.lzh.vote.exception;

/**
 * @ClassName GenericException
 * @Package com.isports.foundation.exception
 * @Author jkcheung
 * @Date 2020/6/11 2:46 下午
 * @Description
 * @Version 1.0
 **/
public class GenericException extends Exception {
	/**
	 * 异常代码
	 */
	private String errorCode;

	public GenericException(String errorCode) {
		this(errorCode, null, null);
	}

	public GenericException(String errorCode, String message) {
		this(errorCode, message, null);
	}

	public GenericException(String errorCode, String message, Throwable cause) {
		super(message, cause);
		this.errorCode = errorCode;
	}

}
