package com.bean;

import java.sql.Connection;  
import java.sql.DriverManager;  
import java.sql.ResultSet;
import java.sql.SQLException;  
import java.sql.Statement;
import java.util.Properties; 


public class linkTest {
	public void getLinkTest() {  
        // ��ѯSQL���  
        String sql ="select cid,cname,size,language,parm,dependency,codeContent,author,description,createdTime from components";  
        // ���ݿ����ӹ�����  
        DBUtil util = new DBUtil();  
        // �������  
        Connection conn = util.openConnection();  
        try {  
            // ���Ԥ�������  
            Statement pstmt = conn.createStatement();  
            // ִ�в�ѯ  
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
