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

public class JDBCDemo3 {
private static final String List = null;
//	DML语句

	public void testResultSet() throws SQLException {
		String url="jdbc:mysql://127.0.0.1:3306/yg";
		String username="root";
		String password="123456";
			
		Connection conn=DriverManager.getConnection(url, username, password);
		String sql="select * from account";
		
		Statement stmt=conn.createStatement();
		
		ResultSet rs=stmt.executeQuery(sql);
		
//		while (rs.next()) {
//			int id=rs.getInt(1);
//			String name=rs.getString(2);
//			double money=rs.getDouble(3);
//			System.out.println(id);
//			System.out.println(name);
//			System.out.println(money);
//			System.out.println("---------");
//		}
//		
		while (rs.next()) {
			
			int id=rs.getInt("id");
			String name=rs.getString("name");
			double money=rs.getDouble("money");
			System.out.println(id);
			System.out.println(name);
			System.out.println(money);
			System.out.println("---------");
		}
		
		rs.close();
		stmt.close();
		conn.close();
	}
	@Test
	public void testResultSet2() throws SQLException {
		String url="jdbc:mysql://127.0.0.1:3306/yg";
		String username="root";
		String password="123456";
			
		Connection conn=DriverManager.getConnection(url, username, password);
		String sql="select * from account";
		
		Statement stmt=conn.createStatement();
		
		ResultSet rs=stmt.executeQuery(sql);
		List<Account> list=new ArrayList<>();
		
		while (rs.next()) {
			Account account=new Account();
			int id=rs.getInt("id");
			String name=rs.getString("name");
			double money=rs.getDouble("money");
			int balance=rs.getInt("balance");

			account.setId(id);
			account.setName(name);
			account.setMoney(money);
			account.setBalance(balance);
			list.add(account);
		}
		
		rs.close();
		stmt.close();
		conn.close();
		System.out.println(list);
	}

}
