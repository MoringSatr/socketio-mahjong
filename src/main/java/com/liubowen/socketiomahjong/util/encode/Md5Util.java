package com.liubowen.socketiomahjong.util.encode;

import org.apache.tomcat.util.security.MD5Encoder;

/**
 * @author liubowen
 * @date 2017/11/10 4:15
 * @description
 */
public class Md5Util {

    public static String MD5(String param) {
        return MD5Encoder.encode(param.getBytes());
    }
}
