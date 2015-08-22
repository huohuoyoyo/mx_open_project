package com.guyc.sqlitehelper.module;

/**
 * person实体类
 * Created by MX on 2015/6/1.
 */
public class Person {

    private String name = "";
    private String sex = "";
    private String age = "";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "name: " + name + " ,sex: " + sex + " ,age: " + age;
    }
}
