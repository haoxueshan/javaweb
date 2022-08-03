package druid;

import java.io.FileInputStream;
import java.sql.Connection;
import java.util.Properties;

import javax.sql.DataSource;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;

public class DruidDemo {

	public static void main(String[] args) throws Exception {
		
		Properties prop=new Properties();
		
		prop.load(new FileInputStream("src/druid.properties"));
		//获取连接池
		DataSource dataSource=DruidDataSourceFactory.createDataSource(prop);
		
		Connection connection= dataSource.getConnection();
		System.out.println(connection);
//		System.out.println(System.getProperty("user.dir"));
	}
}
