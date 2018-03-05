package com.turtlebot.operation.dataobject;

public class Server {
    private String ip;
    private String userName;
    private String password;
    private String description;

    public String getIp() {
        return ip;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Server{" +
                "ip='" + ip + '\'' +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
