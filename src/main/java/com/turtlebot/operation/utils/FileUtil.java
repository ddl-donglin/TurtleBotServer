package com.turtlebot.operation.utils;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 描述:
 * 对文件操作的一些操作工具
 *
 * @outhor didonglin
 * @create 2018-01-05 下午6:21
 */


public class FileUtil {

    private String defaultFilePath = "";

    /**
     * 读取指定路径下的文件
     * @param filepath 文件路径
     * @return String类型的文件内容
     * @throws IOException
     */
    public static String readFile(String filepath) throws IOException {
        StringBuilder result = new StringBuilder();
        BufferedReader br = new BufferedReader(new FileReader(filepath));
        String s;
        while ((s = br.readLine()) != null)
            result.append(System.lineSeparator() + s);
        br.close();
        return result.toString();

    }

    /**
     * 清除字符串中的空格、空行等
     * @param str  需要处理的字符串
     * @return  String类型，清除空格、空行后的字符串
     */
    public static String replaceBlank(String str) {
        String dest = "";
        if (str != null) {
            Pattern p = Pattern.compile("\\s+");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }


    /**
     * 清除空行
     * @param str 需要处理的字符串
     * @return  清除空行后的字符串
     */
    public static String replaceBlankLine(String str) {
        String result = str.replaceAll("(?m)^\\s*$(\\n|\\r\\n)", "");
        ;
        for (int i = 0; i < 10; i++) {
            result = result.replaceAll("(?m)^\\s*$(\\n|\\r\\n)", "");
        }
        return result;
    }


    /**
     * 将字符串写入文件
     * @param args  待写入的数据
     * @param path  写入的文件路径
     * @return  操作成功返回true， 否则返回false
     */
    public static boolean writeFile(String args, String path) {
        try {
            File file = new File(path);
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(args);
            bw.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return true;
        }
    }


    /**
     * 清除文件
     * @param filepath  文件路径
     * @return  操作成功返回true，否则返回false
     */
    public static boolean clearFile(String filepath) {
        try {
            File file = new File(filepath);
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write("");
            bw.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return true;
        }
    }
}