package jdbcDemo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Test;

import com.mysql.cj.protocol.Resultset;
import pojo.Account;

import java.util.ArrayList;
import java.util.List;

public class JDBCDemo4_UserLogin {

//	DML语句


	public void testResultSet() throws SQLException {
		String url="jdbc:mysql://127.0.0.1:3306/yg";
		String username="root";
		String password="123456";
			
		Connection conn=DriverManager.getConnection(url, username, password);
		
		String name="zs";
		String pwd="123";
		
		String sql="select * from tb_user where username='"+name+"' and psaaword='"+pwd+"'";
		
		Statement stmt=conn.createStatement();
		
		ResultSet rs=stmt.executeQuery(sql);
		
		if(rs.next()) {
			System.out.println("ok");
		}else {
			System.out.println("no");
		}
		
		rs.close();
		stmt.close();
		conn.close();

	}
    
    @Test
	public void testLog() throws SQLException {
		String url="jdbc:mysql://127.0.0.1:3306/yg";
		String username="root";
		String password="123456";
			
		Connection conn=DriverManager.getConnection(url, username, password);
		
		String name="zs";
		String pwd="'or '1'='1";
		
		String sql="select * from tb_user where username='"+name+"' and psaaword='"+pwd+"'";
		
		Statement stmt=conn.createStatement();
		
		ResultSet rs=stmt.executeQuery(sql);
		
		if(rs.next()) {
			System.out.println("ok");
		}else {
			System.out.println("no");
		}
		
		rs.close();
		stmt.close();
		conn.close();

	}

}
