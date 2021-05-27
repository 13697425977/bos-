package com.itheima.bos.utils;

import java.util.UUID;

public class UUIDUtils {

    public static String getString16(){
        String id = UUID.randomUUID().toString().replaceAll("-", "");
        return id.substring(0,15);
    }

}
