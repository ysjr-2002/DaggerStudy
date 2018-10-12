package com.visitor.obria.yourapplication.dao;


import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class PersonBean {

    @Id(autoincrement = true)
    private Long id;
    private String cardno;
    private String name;
    private String path;
    @Generated(hash = 1919810798)
    public PersonBean(Long id, String cardno, String name, String path) {
        this.id = id;
        this.cardno = cardno;
        this.name = name;
        this.path = path;
    }
    @Generated(hash = 836535228)
    public PersonBean() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getCardno() {
        return this.cardno;
    }
    public void setCardno(String cardno) {
        this.cardno = cardno;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPath() {
        return this.path;
    }
    public void setPath(String path) {
        this.path = path;
    }
}
