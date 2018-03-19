package com.turtlebot.operation.dataobject;

/**
 * 描述:
 *
 * @outhor didonglin
 * @create 2018-03-17 22:03
 */
public class User {

    private Integer id;
    private String name;
    private String gender;
    private Integer age;
    private String description;


    public User(Integer id, String name, String gender, Integer age, String description) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.description = description;
    }

    @Override
    public String toString(){
        return "User : " + name + "---" + gender + "---" + age + "---" + description;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
         return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
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