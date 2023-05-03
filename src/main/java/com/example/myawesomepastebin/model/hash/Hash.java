package com.example.myawesomepastebin.model.hash;

import java.util.Random;
public class Hash {
    private final static String source = "йцукенгшщзхъфывапролджэячсмитьбю!№;%:?*()_+QWERTYUIOPASDFGHJKLZXCVBNMqwertyuioasdfghjklzxcvbnmЙЦУКЕНГШЩЗХЪФЫВАПРОЛДЖЭЯЧСМИТЬБЮ";
    public static String getHash(){
        char[] chars = source.toCharArray();
        StringBuilder hash = new StringBuilder();
        Random random = new Random();
        for(int i = 0; i<7; i++){
            hash.append(chars[random.nextInt(chars.length)]);
        }
        return String.valueOf(hash);
    }
}
