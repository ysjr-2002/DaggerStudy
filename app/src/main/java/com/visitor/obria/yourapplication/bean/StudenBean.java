package com.visitor.obria.yourapplication.bean;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class StudenBean {

    public StudenBean() {
        this.name ="加油加油";
    }

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
