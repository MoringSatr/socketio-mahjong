package com.liubowen.socketiomahjong.util.conver;

import com.google.common.collect.Maps;
import com.liubowen.socketiomahjong.util.Punctuation;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;

public class MapConver {

    public static String integerListMapConverToString(Map<Integer, List<Integer>> integerListMap) {
        if (integerListMap == null || integerListMap.isEmpty()) {
            return "";
        }
        StringBuilder builder = new StringBuilder();
        integerListMap.forEach((integer, integers) -> {
            builder.append(integer);
            builder.append(Punctuation.VERTICAL);
            builder.append(ListConver.listConverToString(integers));
            builder.append(Punctuation.SEMICOLON);
        });
        return builder.substring(0, (builder.length() - 1));
    }

    public static Map<Integer, List<Integer>> StringConverToIntegerListMap(String integerListMapString) {
        if (integerListMapString == null || StringUtils.isBlank(integerListMapString)) {
            return Maps.newHashMap();
        }
        String[] listMapStrings = StringUtils.split(integerListMapString, Punctuation.SEMICOLON);
        Map<Integer, List<Integer>> integerListMap = Maps.newHashMap();
        for (String listMapsString : listMapStrings) {
            String[] mapStrs = StringUtils.split(listMapsString, Punctuation.VERTICAL);
            if (mapStrs == null || mapStrs.length != 2) {
                continue;
            }
            int key = Integer.valueOf(mapStrs[0]);
            List<Integer> value = ListConver.stringConverToList(mapStrs[1]);
            integerListMap.put(key, value);
        }
        return integerListMap;
    }
}
