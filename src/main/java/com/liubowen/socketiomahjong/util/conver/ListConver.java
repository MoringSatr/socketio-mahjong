package com.liubowen.socketiomahjong.util.conver;

import com.google.common.collect.Lists;
import com.liubowen.socketiomahjong.util.Punctuation;
import org.apache.commons.lang3.StringUtils;
import java.util.List;

public class ListConver {

    public static List stringConverToList(String value) {
        if (StringUtils.isBlank(value)) {
            return Lists.newArrayList();
        }
        List list = Lists.newArrayList();
        String[] listStrs = StringUtils.split(value, Punctuation.COMMA);
        for (String listStr : listStrs) {
            list.add(listStr);
        }
        return list;
    }

    public static String listConverToString(List value) {
        StringBuilder builder = new StringBuilder();
        value.forEach(o -> {
            builder.append(o);
            builder.append(Punctuation.COMMA);
        });
        return builder.substring(0, (builder.length() - 1));
    }
}
