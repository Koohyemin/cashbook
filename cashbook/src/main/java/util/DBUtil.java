package util;

import java.sql.Connection;
import java.sql.DriverManager;

// 마리아db 접속 경로가 바뀌었을 때 수정에 용이하게 하기위해 페이지 분리
public class DBUtil {
	public static Connection getConnection () {
		Connection conn = null;	
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mariadb://3.38.213.210/cashbook","root","mariadb1234");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}
}