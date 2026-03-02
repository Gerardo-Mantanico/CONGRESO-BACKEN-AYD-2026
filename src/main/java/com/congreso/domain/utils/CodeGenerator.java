package com.congreso.domain.utils;

import java.security.SecureRandom;

public class CodeGenerator {
    private static final SecureRandom secureRandom = new SecureRandom();
    private static final String DIGITS = "0123456789";

    public static String generateNumericCode(int length) {
        StringBuilder code = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int index = secureRandom.nextInt(DIGITS.length());
            code.append(DIGITS.charAt(index));
        }
        return code.toString();
    }
}
