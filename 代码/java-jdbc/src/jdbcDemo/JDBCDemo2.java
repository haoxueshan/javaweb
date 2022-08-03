package jdbcDemo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Test;

public class JDBCDemo2 {
//	DML语句
	@Test
	public void testDML() throws SQLException {
		String url="jdbc:mysql://127.0.0.1:3306/yg";
		String username="root";
		String password="123456";
			
		Connection conn=DriverManager.getConnection(url, username, password);
		
		String sql="update account set money=202 where id=1";
		
		Statement stmt=conn.createStatement();
		
		try {
			conn.setAutoCommit(false);
			int cont=stmt.executeUpdate(sql);
			if(cont>0) {
				System.out.println("修改成功");
			}else {
				System.out.println("修改失败");
			}
			conn.commit();
		} catch (SQLException e) {

			System.out.println("修改失败");
			conn.rollback();
		}
		
		stmt.close();
		conn.close();
	}

}
