package com.turtlebot.operation.testJDK8;

import java.util.ArrayList;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        List<String> arr = new ArrayList<>();
        String str = "Count characters using callable";
        for (final String s: str.split(" ")){
        }
//        arr.forEach(String::toUpperCase);
        System.out.println(arr.toString());
    }
}
