package com.liubowen.socketiomahjong.constant;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.RandomUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author liubowen
 * @date 2017/11/10 4:03
 * @description
 */
public class Constant {

    public static final String HALL_IP = "127.0.0.1";

    public static final int HALL_CLIENT_PORT = 9009;

    public static final int HALL_ROOM_PORT = 9009;

    public static final String ACCOUNT_PRI_KEY = "^&*#$%()@";

    public static final String ROOM_PRI_KEY = "~!@#$(*&^%$&";

    public static final String LOCAL_IP = "localhost";

    public static final int[] DI_FEN = { 1, 2, 5 };

    public static final int[] MAX_FAN = { 3, 4, 5 };

    public static final int[] JU_SHU = { 4, 8 };

    public static final int[] JU_SHU_COST = { 2, 3 };

    public static final String VERSION = "20161227";

    public static final String APP_WEB = "http://fir.im/2f17";

    public static final String HALL_ADDR = HALL_IP + ":" + HALL_CLIENT_PORT;

    public static final int ONE_TIME_CAN_BUY_GEMS_NUM = 100000;

    public static final List<String> RANDOM_HEAD_IMAGES = Lists.newArrayList("http://p1.wmpic.me/article/2017/11/29/1511925354_UUVzdxgJ.jpg",
            "http://p3.wmpic.me/article/2017/11/29/1511922068_lMiwpmgO.jpg", "http://p2.wmpic.me/article/2017/11/29/1511920600_IaWsKIOA.jpg",
            "http://img3.imgtn.bdimg.com/it/u=3239161958,3256667375&fm=27&gp=0.jpg", "http://img4.imgtn.bdimg.com/it/u=2413249460,1399330808&fm=27&gp=0.jpg",
            "http://img3.imgtn.bdimg.com/it/u=262763092,1396844267&fm=27&gp=0.jpg", "http://img0.imgtn.bdimg.com/it/u=3123548351,1977899723&fm=27&gp=0.jpg",
            "http://img3.imgtn.bdimg.com/it/u=1444563620,3645444401&fm=27&gp=0.jpg", "http://img1.imgtn.bdimg.com/it/u=470690947,494630454&fm=27&gp=0.jpg");

    public static final String randomHeadImage() {
        int index = RandomUtils.nextInt(0, RANDOM_HEAD_IMAGES.size() - 1);
        return RANDOM_HEAD_IMAGES.get(index);
    }

    public static final int randomSex() {
        return RandomUtils.nextInt(0, 1);
    }

    public static final String getIpByRequest(HttpServletRequest request) {
        String ip = request.getRemoteAddr();
        if (ip.indexOf("::ffff:") != -1) {
            ip = ip.substring(7);
        }
        return ip;
    }


    public static class RoomConstant {
        public static final String ROOM_SERVER_IP = "127.0.0.1";
        public static final int ROOM_SERVER_PORT = 10086;
    }
}
