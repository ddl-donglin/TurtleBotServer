package com.turtlebot.operation.utils;

import com.jcraft.jsch.*;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * 描述:
 * 执行shell脚本
 *
 * @outhor didonglin
 * @create 2018-01-09 18:32
 */
public class SSHshell {

    private String host;
    private String user;
    private Integer port = 22;
    private String psw;
    private String command;

    public SSHshell(String host, String user, String password) {
        this.host = host;
        this.user = user;
        this.psw = password;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public void setPsw(String psw) {
        this.psw = psw;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public SSHshell(){}

    public SSHshell(String host, String user, String psw, int port, String command){
        this.host = host;
        this.user = user;
        this.psw = psw;
        this.port = port;
        this.command = command;
    }

    public Session getSession(){

        Session session =null;
        try {
            JSch jsch=new JSch();

            session = jsch.getSession(user, host, port);
            Properties config = new Properties();


            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);
            session.setPassword(psw);
            //session.setTimeout(10000);
            session.connect();
            System.out.println("Session connected. session会话链接建立");

        } catch (JSchException e) {
            session = null;
            System.out.println("连接出错！");
            //e.printStackTrace();
        }finally{

        }
        return session;
    }

    public static Session getSession(String user,String host,String password,int port){

        Session session =null;
        try {
            JSch jsch=new JSch();
            session = jsch.getSession(user, host, port);
            Properties config = new Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);
            session.setPassword(password);
            session.connect();
            System.out.println("Session connected. session会话链接建立");
        } catch (JSchException e) {
            System.out.println("连接出错！");
            session = null;
            //e.printStackTrace();
        }finally{

        }
        return session;
    }

    public String exec(){

        ChannelExec ExecChannel =null;
        String result= "";
        String cmdlineresult = "";
        ChannelSftp sftp=null;
        Session session =null;
        int returnCode = 0;
        try {
            session = new SSHshell(host, user, psw, port, command).getSession();

            //if(session!=null) {
                ExecChannel = (ChannelExec) session.openChannel("exec");
                ExecChannel.setErrStream(System.err);
                ExecChannel.setCommand(command);

                System.out.println("正在执行脚本命令");

                //接收远程服务器执行命令的结果

                ExecChannel.setInputStream(null);
                ExecChannel.connect();
                BufferedReader input = new BufferedReader(new InputStreamReader(ExecChannel.getInputStream()));

                System.out.println("远程脚本命令是: " + command);

                InputStream in = ExecChannel.getInputStream();

                BufferedReader reader = new BufferedReader(new InputStreamReader(in, "utf8"));
                String buf;

                while ((buf = reader.readLine()) != null) {
                    //cmdlineresult += new String(buf.getBytes("gbk"),"UTF-8")+"    \r\n";
                    cmdlineresult += buf;
                }

                ExecChannel.disconnect();

                while (!ExecChannel.isClosed()) {

                }
                result = ExecChannel.getExitStatus() + "";
                //Thread.sleep(5000);

                /*Channel channel = session.openChannel("sftp");
                channel.connect();
                sftp = (ChannelSftp) channel;
                sftp.cd(directory);
                Thread.sleep(1000);
                sftp.get(downloadFile, saveFile);*/
            //}else {
            //    cmdlineresult += "\n\n-------连接出错，无法执行shell-------\n\n";
            //}

        }catch (Exception e) {
            cmdlineresult += e.getMessage()+"\n\n-------连接出错，无法执行shell-------\n\n";
            System.out.println("连接出错，无法执行shell");
        } finally{
            if(ExecChannel!=null&&!ExecChannel.isClosed()){
                ExecChannel.disconnect();
            }
            if(session!=null&&session.isConnected()){
                session.disconnect();
            }
        }
        return cmdlineresult;
    }

    //测试方法
    public static void main(String[] args) throws InterruptedException  {
        String host = "123.206.70.190";
        int port = 22;
        String user = "root";
        String pwd = "ddl199729";
        String command="echo -e `cd / && ls`";

        String exec = new SSHshell(host, user, pwd, port, command).exec();
        System.out.println(exec);
        System.out.println("finished");
    }
}