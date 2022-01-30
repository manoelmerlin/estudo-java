package com.udemy.controllers.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class URL {

    public static String decodificarUrl(String s) {
        try {
            return URLDecoder.decode(s, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }

    public static List<Integer> converterStringParaLista(String s) {
        List<Integer> list = Arrays.asList(s.split("\\s*,\\s*")).stream().map(item -> Integer.parseInt(item)).collect(Collectors.toList());

        return list;
    }
}
