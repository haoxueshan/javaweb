package jdbcDemo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Test;

import com.mysql.cj.jdbc.PreparedStatementWrapper;
import com.mysql.cj.protocol.Resultset;
import pojo.Account;

import java.util.ArrayList;
import java.util.List;

public class JDBCDemo5 {

//	DML语句

    

	public void testLog() throws SQLException {
		String url="jdbc:mysql://127.0.0.1:3306/yg";
		String username="root";
		String password="123456";
			
		Connection conn=DriverManager.getConnection(url, username, password);
		
		String name="zs";
		String pwd="123";
		
		String sql="select * from tb_user where username=? and psaaword=?";
		
		PreparedStatement pstmt=conn.prepareStatement(sql);
		
		pstmt.setString(1, name);
		pstmt.setString(2, pwd);
		
		ResultSet rs=pstmt.executeQuery();
		if(rs.next()) {
			System.out.println("ok");
		}else {
			System.out.println("no");
		}
		
		rs.close();
		pstmt.close();
		conn.close();

	}
    @Test
	public void testLogps() throws SQLException {
		String url="jdbc:mysql://127.0.0.1:3306/yg?useSSL=false&ServerPrepStmts=true";
		String username="root";
		String password="123456";
			
		Connection conn=DriverManager.getConnection(url, username, password);
		
		String name="zs";
		String pwd="123";
		
		String sql="select * from tb_user where username=? and psaaword=?";
		
		PreparedStatement pstmt=conn.prepareStatement(sql);
		
		pstmt.setString(1, name);
		pstmt.setString(2, pwd);
		
		ResultSet rs=pstmt.executeQuery();
		if(rs.next()) {
			System.out.println("ok");
		}else {
			System.out.println("no");
		}
		
		rs.close();
		pstmt.close();
		conn.close();

	}

}
