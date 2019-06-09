package com.visitor.tengli.webservicestudy;

import android.os.Environment;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

/**
 * created by yangshaojie  on 2019/6/5
 * email: ysjr-2002@163.com
 */
public class Config {

    private static final String tag = Config.class.getSimpleName();

    String host = "";
    String brandcode = "";
    String clubId = "";
    String key = "";

    public void read() {

        File root = Environment.getExternalStorageDirectory();
        String mypath = "m5/m5.properties";
        File myfile = new File(root, mypath);
//        if (!myfile.exists()) {
//            Log.d(tag, "文件不存在");
//            try {
//                boolean create = myfile.createNewFile();
//                if (create) {
//
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            return;
//        }
//
//        try {
//            FileReader fileReader = new FileReader(myfile);
//            BufferedReader reader = new BufferedReader(fileReader);
//            String line = "";
//            while ((line = reader.readLine()) != null) {
//                Log.d(tag, line);
//            }
//            fileReader.close();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    final String cfgfilename = "m5/m5.properties";

    private void readByProperties() {
        File root = Environment.getExternalStorageDirectory();
        File myfile = new File(root, cfgfilename);
        FileReader fileReader = null;
        try {
            fileReader = new FileReader(myfile);
            Properties properties = new Properties();
            properties.load(fileReader);
            host = properties.getProperty("host");
            brandcode = properties.getProperty("brandcode");
            clubId = properties.getProperty("clubId");
            key = properties.getProperty("key");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
