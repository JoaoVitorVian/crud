package com.challenge.crud.util;

import org.springframework.stereotype.Component;

@Component
public class StringExtensions {

    public static String removeNonDigits(String str) {
        if (str != null && !str.isEmpty()) {
            return str.replaceAll("\\D", "");
        }
        return str;
    }
}
