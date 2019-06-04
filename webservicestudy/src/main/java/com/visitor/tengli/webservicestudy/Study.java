package com.visitor.tengli.webservicestudy;

import android.text.TextUtils;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

/**
 * created by yangshaojie  on 2019/6/4
 * email: ysjr-2002@163.com
 */
public class Study {

    public void postForm() {

        FormBody.Builder builder = new FormBody.Builder();
        builder.add("", "");
        builder.add("", "");

        FormBody body = builder.build();
    }

    public void postJson() {
        OkHttpClient client = new OkHttpClient();
        String json = "";
        MediaType mediaType = MediaType.get("application/json;charset=utf-8");
        RequestBody requestBody = RequestBody.create(mediaType, json);
    }

    public void xmlParse() {
        XmlPullParser pullParser = Xml.newPullParser();
        StringReader reader = new StringReader("<?xml version=\"1.0\" encoding=\"utf-8\"?><int xmlns=\"http://tempuri.org/\">1</int>");
        try {
            pullParser.setInput(reader);

            int eventtype = pullParser.getEventType();
            while (eventtype != XmlPullParser.END_DOCUMENT) {

                switch (eventtype) {
                    case XmlPullParser.START_DOCUMENT:
                        break;
                    case XmlPullParser.START_TAG:
                        String name = pullParser.getName();
                        if (TextUtils.equals(name, "int")) {
                            try {
                                String intnamenodeval = pullParser.nextText();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        break;
                }
                try {
                    eventtype = pullParser.next();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
    }
}
