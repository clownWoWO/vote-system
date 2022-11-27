package com.lzh.vote.utils;

import com.lzh.vote.exception.SysException;
import org.apache.commons.codec.binary.Base64;

/**
 * Base64加密解密 工具类
 *
 * @author lzh
 * @version 1.0
 * @since 1.0
 */
public abstract class Base64Util {

    /**
     * 字符编码
     */
    private final static String ENCODING = "UTF-8";

    /**
     * 方法描述: Base64编码
     *
     * @param data 待编码数据
     * @return java.lang.String
     */
    public static String encode(String data) throws Exception {

        // 执行编码
        byte[] b = Base64.encodeBase64(data.getBytes(ENCODING));

        return new String(b, ENCODING).trim();
    }


    /**
     * 方法描述: Base64安全编码<br>
     * 遵循RFC 2045实现
     *
     * @param data 待编码数据
     * @return java.lang.String
     */
    public static String encodeSafe(String data) throws Exception {

        // 执行编码
        byte[] b = Base64.encodeBase64(data.getBytes(ENCODING), true);

        return new String(b, ENCODING).trim();
    }

    /**
     * 方法描述: Base64解码
     *
     * @param data 待解码数据
     * @return java.lang.String
     */
    public static String decode(String data) throws Exception {
        // 执行解码
        byte[] b = Base64.decodeBase64(data.getBytes(ENCODING));
        return new String(b, ENCODING).trim();
    }
}