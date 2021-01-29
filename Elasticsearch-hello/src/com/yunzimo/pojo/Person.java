package com.yunzimo.pojo;


import java.util.List;

/**
 * @author Administrator
 */
public class Person {


    private int id;
    private String name;
    private int age;
    private List<String> skills;

    public Person() {
    }

    public Person(String name, int age, List<String> skills) {
        this.name = name;
        this.age = age;
        this.skills = skills;
    }

    public Person(int id, String name, int age, List<String> skills) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.skills = skills;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public List<String> getSkills() {
        return skills;
    }

    public void setSkills(List<String> skills) {
        this.skills = skills;
    }
}
