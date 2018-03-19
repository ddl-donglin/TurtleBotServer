package com.turtlebot.operation.dataobject;

/**
 * 描述:
 *
 * @outhor didonglin
 * @create 2018-03-17 22:03
 */
public class User {

    private String name;
    private Integer gender;
    private Integer age;
    private String description;

    @Override
    public String toString(){
        return "User : " + name + "---" + gender + "---" + age + "---" + description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}