package com.turtlebot.operation.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * @Auther: davidddl
 * @Date: 2018/5/16 22:12
 * @Description:
 */
@WebServlet(name = "FunctionServlet")
public class FunctionServlet extends HttpServlet {

    public static final String prefixPath = "/home/suheng/caffe/examples/HWDB_AD/control";
    static String repairBash = prefixPath + "/picService.sh";
    static String identityBash = prefixPath + "/picService.sh";
    static String imitateBash = prefixPath + "/picService.sh";
    static String manufactureBash = prefixPath + "/picService.sh";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String function = request.getParameter("function");
        String args1 = request.getParameter("args1");
        String args2 = request.getParameter("args2");
        String args3 = request.getParameter("args3");
        String args4 = request.getParameter("args4");

        System.out.println("function: " + function + ", args1: " + args1 + ", args2: " + args2 + ", args3: " + args3 + ", arg4: " + args4);
        System.out.println(new String(args1.getBytes("iso8859-1"), "UTF-8"));
//        System.out.println(new String("哈哈哈".getBytes("ISO-8859-1"), "utf-8"));
        String result = "nothing!!";
        
        switch (function){
            case "repair":
                //残损修复
                try {
                    result = repair(args1, args2, args3);  // 风格，选择的字, 手动输入
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                break;
            case "identify":
                //笔迹鉴定
                identify();
                break;
            case "imitate":
                //风格模仿
                imitate();
                break;
            case "manufacture":
                //手写识别
                manufacture();
                break;
        }

        response.getWriter().print(result);

    }

    private String repair(String style, String resId, String input) throws IOException, InterruptedException {
        Runtime runtime = Runtime.getRuntime();
        Process pro = runtime.exec("bash " + repairBash);
        int status = pro.waitFor();
        if (status != 0){
            //脚本执行出错
            System.out.println("Failed to call shell's command ");
            return "error";
        }else{
            //脚本执行成功
            BufferedReader br = new BufferedReader(new InputStreamReader(pro.getInputStream()));
            StringBuffer strbr = new StringBuffer();
            String line;
            while ((line = br.readLine())!= null)
            {
                strbr.append(line).append("\n");
            }

            String result = strbr.toString();
            return result;
        }
    }
    
    private void identify(){
        
    }
    
    private void imitate(){
        
    }
    
    private void manufacture(){
        
    }
}
