package com.visitor.obria.yourapplication.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

public class DeviceUtil {

    public static boolean writeSysFileValue(String path, String val) {

        boolean result = false;
        File file;
        file = new File(path);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            val += "\n";
            byte[] data = val.getBytes();
            fos.write(data);
            result = true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            result = false;
        } catch (IOException e) {
            e.printStackTrace();
            result = false;
        } finally {

            if (fos != null) {
                try {
                    fos.flush();
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    public static String getPathInputStream(String path) {

        String value = " ";
        File file;
        BufferedReader reader = null;
        file = new File(path);
        try {
            reader = new BufferedReader(new FileReader(file));
            StringBuffer stringBuffer = new StringBuffer();
            String stringappend = reader.readLine();
            if (stringappend != null && stringappend.length() != 0) {
                stringBuffer.append(stringappend);
            }
            value = stringBuffer.toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            value = "";
        } catch (IOException e) {
            e.printStackTrace();
            value = "";
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return value;
    }
}
