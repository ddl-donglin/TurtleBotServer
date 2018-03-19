package com.turtlebot.operation.utils;

import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

/**
 * 描述:
 * 数据库连接工具类
 *
 * @outhor didonglin
 * @create 2018-01-30 11:59
 */
public class DBUtil {

    public DBUtil(){}

    public Connection openConnection(){
        Properties prop = new Properties();
        String username;
        String url;
        String driver;
        String password;
        try{
            prop.load(this.getClass().getClassLoader().getResourceAsStream("jdbc.properties"));
            driver = prop.getProperty("jdbc.driver");
            url = prop.getProperty("jdbc.url");
            username = prop.getProperty("jdbc.username");
            password = prop.getProperty("jdbc.password");
            Class.forName(driver);
            return DriverManager.getConnection(url, username, password);
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }


    public void closeConnection(Connection conn){
        try{
            conn.close();
        }catch(SQLException e)
        {
            e.printStackTrace();
        }
    }

    public static String DBMonitorSQL(String sql, String table) throws SQLException {

        ResultSet result;// 创建一个结果集对象

        String resultdata = ""; //
        //1. 连接数据库
        DBUtil dbUtil = new DBUtil();

        Connection connection = dbUtil.openConnection();

        String[] labels;
        if(sql.charAt(7) == '*')
            labels = dbUtil.getallLabel(table);
        else{
            if(sql.toUpperCase().contains("FROM"))
                labels = sql.substring(7,sql.toUpperCase().indexOf("FROM")).trim().split(",");
            else if(sql.toUpperCase().contains("SET"))
                labels = sql.substring(7,sql.toUpperCase().indexOf("SET")).trim().split(",");
            else
                labels = new String[]{"id"};
        }
        //2. 执行sql查询语句,得到结果后关闭连接
        try {
            PreparedStatement preparedstatement = connection.prepareStatement(sql); //实例化预编译语句

            if(sql.contains("select") || sql.contains("SELECT")){
                result = preparedstatement.executeQuery();// 执行查询，注意括号中不需要再加参数
                if (!result.next())
                    resultdata = "error! 啥都没有搜索到！";
                else{
                    result.beforeFirst();
                    int line = 0;
                    while (result.next()) {
                        resultdata += line + ":  ";
                        for (String s : labels)
                            resultdata += "\""+result.getString(s)+"\", ";
                        resultdata = resultdata.substring(0,resultdata.length()-2);
                        resultdata += ";\n";
                        line++;
                    }
                }
            }
            if(sql.contains("update") || sql.contains("UPDATE") || sql.contains("insert") || sql.contains("INSERT") || sql.contains("delete") || sql.contains("DELETE")){
                int updateres = preparedstatement.executeUpdate();
                resultdata += updateres > 0 ? "更新成功！有"+updateres+"列发生变化！" : "error! 更新失败！";
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            dbUtil.closeConnection(connection);
        }


        //3. 返回查询结果
        return resultdata;
    }

    public String[] getallLabel(String table) throws SQLException {

        Connection connection = this.openConnection();
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM "+table);
        ResultSetMetaData rsmd = rs.getMetaData();

        int count=rsmd.getColumnCount();

        String[] name=new String[count];

        for(int i=0;i<count;i++)
            name[i]=rsmd.getColumnName(i+1);
        return name;
    }
}