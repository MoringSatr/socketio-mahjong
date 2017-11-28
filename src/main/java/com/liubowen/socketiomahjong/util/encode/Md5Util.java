package com.liubowen.socketiomahjong.util.encode;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * @author liubowen
 * @date 2017/11/10 4:15
 * @description
 */
public class Md5Util {

    public static String MD5(String param) {
        return DigestUtils.md5Hex(param);
    }
}
