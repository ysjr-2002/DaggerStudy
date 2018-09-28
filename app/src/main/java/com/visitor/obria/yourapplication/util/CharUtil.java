package com.visitor.obria.yourapplication.util;

import java.util.Arrays;

/**
 * created by yangshaojie  on 2018/9/28
 * email: ysjr-2002@163.com
 */
public class CharUtil {

    public static byte charToByte(char c) {

        byte b = (byte) c;
        return b;
    }

    public static char byteToChar(byte b) {
        char c = (char) b;
        return c;
    }

    public static void arrayCopy() {

//        System.arraycopy();
//        Arrays.copyOfRange()
    }

    public static String intToHex(int n) {

        String temp = "0x" + Integer.toHexString(n);
        return temp;
    }
}
