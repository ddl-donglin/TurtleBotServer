package com.turtlebot.operation.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.Vector;

import com.jcraft.jsch.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 描述:
 * 通过SFTP远程连接主机
 *
 * @outhor didonglin
 * @create 2018-01-09 18:13
 */
public class SFTPUtil {
    private static Log log = LogFactory.getLog(SFTPUtil.class);

    /**
     * 连接sftp服务器
     * @param host 远程主机ip地址
     * @param port sftp连接端口，null 时为默认端口
     * @param user 用户名
     * @param password 密码
     * @return
     * @throws JSchException
     */
    public static Session connect(String host, Integer port, String user, String password) throws JSchException{
        Session session = null;
        try {
            JSch jsch = new JSch();
            if(port != null){
                session = jsch.getSession(user, host, port.intValue());
            }else{
                session = jsch.getSession(user, host);
            }
            session.setPassword(password);
            //设置第一次登陆的时候提示，可选值:(ask | yes | no)
            session.setConfig("StrictHostKeyChecking", "no");
            //30秒连接超时
            session.connect(30000);
        } catch (JSchException e) {
            //e.printStackTrace();
            System.out.println("SFTPUitl 获取连接发生错误");
            throw e;
        }
        return session;
    }

    /**
     * sftp上传文件(夹)
     * @param directory
     * @param uploadFile
     * @param sftp
     * @throws Exception
     */
    public static void uploadimpl(String directory, String uploadFile, ChannelSftp sftp) throws Exception{
        System.out.println("sftp upload file [directory] : "+directory);
        System.out.println("sftp upload file [uploadFile] : "+ uploadFile);
        File file = new File(uploadFile);
        if(file.exists()){
            //这里有点投机取巧，因为ChannelSftp无法去判读远程linux主机的文件路径,无奈之举
            try {
                Vector content = sftp.ls(directory);
                if(content == null){
                    sftp.mkdir(directory);
                }
            } catch (SftpException e) {
                sftp.mkdir(directory);
            }
            //进入目标路径
            sftp.cd(directory);
            if(file.isFile()){
                InputStream ins = new FileInputStream(file);
                //中文名称的
                sftp.put(ins, new String(file.getName().getBytes(),"UTF-8"));
                //sftp.setFilenameEncoding("UTF-8");
            }else{
                File[] files = file.listFiles();
                for (File file2 : files) {
                    String dir = file2.getAbsolutePath();
                    if(file2.isDirectory()){
                        String str = dir.substring(dir.lastIndexOf(file2.separator));
                        //directory = FileUtil.normalize(directory + str);
                        directory = "UPLOAD"+str;
                    }
                    uploadimpl(directory,dir,sftp);
                }
            }
        }
    }

    /**
     * sftp下载文件（夹）
     * @param srcFile 下载文件完全路径
     * @param saveFile 保存文件路径
     * @param sftp ChannelSftp
     * @throws UnsupportedEncodingException
     */
    public static void downloadimpl(String srcFile, String saveFile, ChannelSftp sftp) throws UnsupportedEncodingException {
        Vector conts = null;
        try{
            conts = sftp.ls(srcFile);
        } catch (SftpException e) {
            //e.printStackTrace();
            System.out.println("连接不到远程主机");
            log.debug("ChannelSftp sftp罗列文件发生错误",e);
        }
        File file = new File(saveFile);
        if(!file.exists()) file.mkdir();
        //文件
        if(srcFile.indexOf(".") > -1){
            try {
                sftp.get(srcFile, saveFile);
            } catch (SftpException e) {
                e.printStackTrace();
                log.debug("ChannelSftp sftp下载文件发生错误",e);
            }
        }else{
            //文件夹(路径)
            for (Iterator iterator = conts.iterator(); iterator.hasNext();) {
                ChannelSftp.LsEntry obj =  (ChannelSftp.LsEntry) iterator.next();
                String filename = new String(obj.getFilename().getBytes(),"UTF-8");
                if(!(filename.indexOf(".") > -1)){
                    //directory = FileUtil.normalize(directory + System.getProperty("file.separator") + filename);
                    srcFile = "DownLoad"+System.getProperty("file.separator")+"Step1";
                    //saveFile = FileUtil.normalize(saveFile + System.getProperty("file.separator") + filename);
                    saveFile = "Download" + System.getProperty("file.separator") + "Step2";
                }else{
                    //扫描到文件名为".."这样的直接跳过
                    String[] arrs = filename.split("\\.");
                    if((arrs.length > 0) && (arrs[0].length() > 0)){
                        //srcFile = FileUtil.normalize(directory + System.getProperty("file.separator") + filename);
                        srcFile = "Download" + System.getProperty("file.separator") + "Step3";
                    }else{
                        continue;
                    }
                }
                downloadimpl(srcFile, saveFile, sftp);
            }
        }
    }


    /**
     * 从远程主机下载指定文件至本机指定路径
     * @param host
     * @param port
     * @param username
     * @param password
     * @param srcfile  远程主机的文件路径
     * @param savefile  本机保存的路径
     * @return
     */
    public static boolean download(String host, Integer port, String username, String password, String srcfile, String savefile){

        ChannelSftp sftp = null;
        Session session = null;

        boolean result = false;
        try {
            session = SFTPUtil.connect(host, port, username, password);
            Channel channel = session.openChannel("sftp");
            channel.connect();
            sftp = (ChannelSftp) channel;
            //SFTPUtil.upload(destDir, srcfile, sftp);
            SFTPUtil.downloadimpl(srcfile,savefile,sftp);
            result = true;
        } catch (Exception e) {
            //e.printStackTrace();

            /*return UtilMisc.toMap("flag","failure","msg","备份文件到远程主机发生错误");*/
            System.out.println("从远程主机下载文件发生错误");

            result = false;
        }finally{
            if(sftp != null)sftp.disconnect();
            if(session != null)session.disconnect();
            return result;
        }

    }

    /**
     * 将本机文件上传至远程主机指定路径
     * @param host
     * @param port
     * @param username
     * @param password
     * @param srcfile
     * @param savefile
     * @return
     */
    public static boolean upload(String host, Integer port, String username, String password, String srcfile, String savefile){
        ChannelSftp sftp = null;
        Session session = null;

        boolean result = false;
        try {
            session = SFTPUtil.connect(host, port, username, password);
            Channel channel = session.openChannel("sftp");
            channel.connect();
            sftp = (ChannelSftp) channel;
            SFTPUtil.uploadimpl(savefile,srcfile,sftp);
            result = true;
        } catch (Exception e) {
            //e.printStackTrace();
            System.out.println("备份文件到远程主机发生错误");

            result = false;
        }finally{
            if(sftp != null)sftp.disconnect();
            if(session != null)session.disconnect();
            return result;
        }

    }


    //测试方法
    /*public static void main(String[] args) {

        String host = "123.206.70.190";
        int port = 22;
        String username = "root";
        String password = "ddl199729";

        String destDir = "/root/test";
        //String srcfile = "/Users/didonglin/Desktop/test.txt";
        String srcfile = "/root/test/test.txt";
        String savefile = "/Users/didonglin/Desktop";

        System.out.println(SFTPUtil.download(host,port,username,password,srcfile,savefile));

        System.out.println(SFTPUtil.upload(host,port,username,password,savefile,destDir));

    }*/
}