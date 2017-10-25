/*
 * Copyright (c) 2017 <l_iupeiyu@qq.com> All rights reserved.
 */

package com.geekcattle.util;

import java.util.UUID;

/**
 * UUID工具类
 * author geekcattle
 * date 2016/10/24 0024 上午 11:38
 */
public class UuidUtil {
    public UuidUtil(){

    }
    /**
     * 获得一个UUID
     *
     * @return String UUID
     */
    public static String getUUID() {
        return UUID.randomUUID().toString().replace("-", "").toLowerCase();
    }

    /**
     * 获得指定数目的UUID
     *
     * @param number int 需要获得的UUID数量
     * @return String[] UUID数组
     */
    public static String[] getUUID(int number) {
        if (number < 1) {
            return null;
        }
        String[] ss = new String[number];
        for (int i = 0; i < number; i++) {
            ss[i] = getUUID();
        }
        return ss;
    }
}
