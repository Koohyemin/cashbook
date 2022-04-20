package dao;

import java.util.*;
import java.sql.*;
import vo.*;

public class CashbookDao {
	public List<Map<String, Object>> selectCashbookListByMonth(int y, int m) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		
		/*
		 	SELECT cashbook_no cashbookNo,
		 		   DAY(cash_date) cashDay,
		 		   kind,
		 		   cash
		 	FROM cashbook
		 	WHERE YEAR(cash_date)=? AND MONTH(cash_date)=?
		 	ORDER BY DAY(cash_date) ASC
		 */
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql = "SELECT "
				+ "		 cashbook_no cashbookNo"
				+ "		, DAY(cash_date) cashDay"
				+ "		, kind"
				+ "		, cash"
				+ "		, LEFT(memo,5) memo"
				+ "	FROM cashbook"
				+ "	WHERE YEAR(cash_date)=? AND MONTH(cash_date)=?"
				+ " ORDER BY DAY(cash_date) ASC, kind ASC";
		
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/cashbook","root","java1234");
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, y);
			stmt.setInt(2, m);
			rs = stmt.executeQuery();
			while(rs.next()) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("cashbookNo", rs.getInt("cashbookNo"));
				map.put("cashDay", rs.getInt("cashDay"));
				map.put("kind", rs.getString("kind"));
				map.put("cash", rs.getInt("cash"));
				map.put("memo", rs.getString("memo"));
				list.add(map);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
	}
	
	public void insertCashbook(Cashbook cashbook, List<String> hashtag) {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/cashbook","root","java1234");
			conn.setAutoCommit(false); // 자동커밋을 해제
			
			String insertSql = "INSERT INTO cashbook(cash_date, kind, cash, memo, update_date, create_date)"
								+ " VALUES(?,?,?,?,NOW(),NOW())";
			
			// insert + select 방금 생성된 행의 키값 ex)select 방금 입력한 cashbook_no from cashbook;
			stmt = conn.prepareStatement(insertSql, PreparedStatement.RETURN_GENERATED_KEYS);
			stmt.setString(1, cashbook.getCashDate());
			stmt.setString(2, cashbook.getKind());
			stmt.setInt(3, cashbook.getCash());
			stmt.setString(4, cashbook.getMemo());
			stmt.executeUpdate(); // insert
			rs = stmt.getGeneratedKeys();  // select 방금입력한 cashbook_no from cashbook;
			int cashbookNo = 0;
			if(rs.next()) {
				cashbookNo = rs.getInt(1);
			}
			
			// hashtage를 저장하는 코드
			PreparedStatement stmt2 = null;
			String hashtagSql = "INSERT INTO hashtag(cashbook_no, tag, create_date)"
								+ " VALUES(?, ?, NOW())";
			for(String h : hashtag) {
				stmt2 = conn.prepareStatement(hashtagSql);
				stmt2.setInt(1, cashbookNo);
				stmt2.setString(2, h);
				stmt2.executeUpdate();
			}
			
			conn.commit();
		} catch(Exception e) {
			try {
				conn.rollback();
			} catch(SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	public Cashbook selectCashBookOne(int cashbookNo) {
		Cashbook cashbook = null;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql = "SELECT cashbook_no cashbookNo, cash_date cashDate, kind, cash, memo, update_date updateDate, create_date createDate FROM cashbook WHERE cashbook_no=?";
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/cashbook","root","java1234");
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, cashbookNo);
			rs = stmt.executeQuery();
			if(rs.next()) {
				cashbook = new Cashbook();
				cashbook.setCashbookNo(rs.getInt("cashbookNo"));
				cashbook.setCashDate(rs.getString("cashDate"));
				cashbook.setKind(rs.getString("kind"));
				cashbook.setCash(rs.getInt("cash"));
				cashbook.setMemo(rs.getString("memo"));
				cashbook.setUpdateDate(rs.getString("updateDate"));
				cashbook.setCreateDate(rs.getString("createDate"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return cashbook;
	}
	
	public void deleteCashbook(int cashbookNo) {
		Connection conn = null;
		PreparedStatement stmt = null;
		PreparedStatement stmt2 = null;
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/cashbook","root","java1234");
			// 해시태그 삭제
			String hashtagSql = "DELETE FROM hashtag WHERE cashbook_no=?";
			stmt2 = conn.prepareStatement(hashtagSql);
			stmt2.setInt(1, cashbookNo);
			stmt2.executeUpdate();
			// 가계부 삭제
			String sql = "DELETE FROM cashbook WHERE cashbook_no=?";
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, cashbookNo);
			int row = stmt.executeUpdate();
			if(row == 1) {
				System.out.println("가계부 삭제 성공");
			} else {
				System.out.println("가계부 삭제 실패");
			}
			conn.commit();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	public void updateCashbook(Cashbook cashbook) {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql = "UPDATE cashbook SET kind = ?, cash = ?, memo = ?, update_date = NOW() WHERE cashbook_no = ?";
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/cashbook","root","java1234");
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, cashbook.getKind());
			stmt.setInt(2, cashbook.getCash());
			stmt.setString(3, cashbook.getMemo());
			stmt.setInt(4, cashbook.getCashbookNo());
			int row = stmt.executeUpdate();
			if(row == 1) {
				System.out.println("가계부 입력 성공");
			} else {
				System.out.println("가계부 입력 실패");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
