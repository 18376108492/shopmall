package com.itdan.shopmall.pojo;

/**
 * freemarker测试用类
 */
public class Student {

    private  Integer id;
    private  String name;
    private  String add;
    private  Integer age;

    public Student(Integer id, String name, String add, Integer age) {
        this.id = id;
        this.name = name;
        this.add = add;
        this.age = age;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAdd() {
        return add;
    }

    public void setAdd(String add) {
        this.add = add;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
