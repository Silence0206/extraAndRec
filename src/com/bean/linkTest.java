package com.bean;

import java.sql.Connection;  
import java.sql.DriverManager;  
import java.sql.ResultSet;
import java.sql.SQLException;  
import java.sql.Statement;
import java.util.Properties; 


public class linkTest {
	public void getLinkTest() {  
        // 查询SQL语句  
        String sql ="select cid,cname,size,language,parm,dependency,codeContent,author,description,createdTime from components";  
        // 数据库连接工具类  
        DBUtil util = new DBUtil();  
        // 获得连接  
        Connection conn = util.openConnection();  
        try {  
            // 获得预定义语句  
            Statement pstmt = conn.createStatement();  
            // 执行查询  
            ResultSet rs = pstmt.executeQuery(sql);  
            while (rs.next()) {  
               System.out.println(rs.getInt(1)); 
            }  
        } catch (SQLException e) {  
            e.printStackTrace();  
        } finally {  
            util.closeConn(conn);  
        }   
	}
}
