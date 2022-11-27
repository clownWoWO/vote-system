/*
 * Copyright (c) 2012-2020 iSports Team. All Rights Reserved.
 * This software is the confidential and proprietary information of
 * iSports Team. You shall not disclose such Confidential Information
 * and shall use it only in accordance with the terms of the agreements
 * you entered into with iSports Team.
 */
package com.lzh.vote.exception;


/**
 * @ClassName SysException
 * @Package com.isports.foundation.exception
 * @Author jkcheung
 * @Date 2020/6/11 2:54 下午
 * @Description
 * @Version 1.0
 **/
public class SysException extends GenericException {
	public SysException(String errorCode, String message) {
		super(errorCode, message);
	}

	public SysException(String errorCode, String message, Throwable cause) {
		super(errorCode, message, cause);
	}
}
