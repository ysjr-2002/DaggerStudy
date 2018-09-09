package com.visitor.obria.yourapplication.model;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * created by yangshaojie  on 2018/9/6
 * email: ysjr-2002@163.com
 */

@Singleton
public class Student {

    public Student() {

        this.name = "shit shit shit shit shit shit shit shit";
    }

    private String name;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
