package com.turtlebot.operation.service.recognize;

import jdk.nashorn.internal.codegen.CompilerConstants;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.concurrent.*;

/**
 * Created by kewenkang on 2018/6/26/0026.
 */

public class GoodsRecognizeThread implements Callable<String> {
    private String pythonFilePath = "D:\\workspace\\IdeaProjects\\TurtleBotServer\\src\\main\\python\\image.py ";

    public String imagePath;

    public static String[] res = new String[]{"123"};

    public GoodsRecognizeThread(String imagePath){
        this.imagePath = imagePath;
    }

    @Override
    public String call() throws Exception {
        Process proc = null;
        StringBuffer result = new StringBuffer();
        try {
            Runtime runtime = Runtime.getRuntime();
            proc = runtime.exec("python " + pythonFilePath + imagePath);
            proc.waitFor();
            BufferedReader reader = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            String line;
//            boolean flag = false;
            while ((line = reader.readLine()) != null) {
                result.append(line);
//                flag = true;
            }
            System.out.println(result.toString());

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        res[1] = result.toString();
        return result.toString();
    }


    public static void main(String[] args) {

        ExecutorService executorService= Executors.newFixedThreadPool(1);
        Callable<String> callable=new GoodsRecognizeThread("D:\\workspace\\IdeaProjects\\TurtleBotServer\\src\\main\\python\\cococola.jpg");
        Future future=executorService.submit(callable);
        System.out.println("submitted");
        try {
            if(future.isDone()){
                System.out.println(GoodsRecognizeThread.res[1]);
                System.out.println(future.get());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
