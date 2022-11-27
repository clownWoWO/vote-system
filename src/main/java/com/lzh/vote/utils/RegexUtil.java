package com.lzh.vote.utils;

import java.util.regex.Pattern;

public class RegexUtil {
    private final static String ID_CARD_PATTERN = "^[A-Z]\\d{6}\\(\\d\\)$";
    private final static String EMAIL_PATTERN = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";

    public static boolean verifyIdCard(String idCard) {
        return Pattern.matches(ID_CARD_PATTERN, idCard);
    }

    public static boolean verifyEmail(String email){
        return Pattern.matches(EMAIL_PATTERN, email);
    }
}
