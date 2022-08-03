package jdbcDemo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;





public class JDBCDemo {


	public static void main(String[] args) throws SQLException {
		// TODO Auto-generated method stub

//		Class.forName("com.mysql.cj.jdbc.Driver");
		
		String url="jdbc:mysql://127.0.0.1:3306/yg";
		String username="root";
		String password="123456";
			
		Connection conn=DriverManager.getConnection(url, username, password);
		
		String sql="update account set money=202 where id=1";
		
		Statement stmt=conn.createStatement();
		
		try {
			conn.setAutoCommit(false);
			int cont=stmt.executeUpdate(sql);
			System.out.println(cont);
			int i=3/0;
			conn.commit();
		} catch (SQLException e) {

			conn.rollback();
		}
		stmt.close();
		conn.close();
	}

}
