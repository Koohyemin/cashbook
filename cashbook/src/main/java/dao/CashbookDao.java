package dao;

import java.util.*;

import util.DBUtil;

import java.sql.*;
import vo.*;

public class CashbookDao {
	// 고객별 매월 수입, 지출 합계 메서드
	public List<Map<String,Object>> totalMonthCash(String memberId, int y, int m) {
		List<Map<String,Object>> list = new ArrayList<>();
		Map<String,Object> map = null;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		conn = DBUtil.getConnection();

		String sql = "		SELECT kind, SUM(cash) totalCash"
				+ "		FROM cashbook"
				+ "		WHERE member_id=? AND YEAR(cash_date)=? AND MONTH(cash_date)=?"
				+ "		GROUP BY kind";
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, memberId);
			stmt.setInt(2, y);
			stmt.setInt(3, m);
			rs = stmt.executeQuery();
			while(rs.next()) {
				map = new HashMap<>();
				map.put("kind", rs.getString("kind"));
				map.put("totalCash", rs.getInt("totalCash"));
				list.add(map);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public List<Map<String, Object>> selectCashbookListByMonth(int y, int m, String memberId) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		conn = DBUtil.getConnection();
		String sql = "SELECT "
				+ "		 cashbook_no cashbookNo"
				+ "		, DAY(cash_date) cashDay"
				+ "		, kind"
				+ "		, cash"
				+ "		, LEFT(memo,5) shortMemo"
				+ "		, memo fullMemo"
				+"		, member_id memberId"
				+ "	FROM cashbook"
				+ "	WHERE YEAR(cash_date)=? AND MONTH(cash_date)=? AND member_id=?"
				+ " ORDER BY DAY(cash_date) ASC, kind ASC";
		
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, y);
			stmt.setInt(2, m);
			stmt.setString(3, memberId);
			rs = stmt.executeQuery();
			while(rs.next()) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("cashbookNo", rs.getInt("cashbookNo"));
				map.put("cashDay", rs.getInt("cashDay"));
				map.put("kind", rs.getString("kind"));
				map.put("cash", rs.getInt("cash"));
				map.put("shortMemo", rs.getString("shortMemo"));
				map.put("fullMemo", rs.getString("fullMemo"));
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
		conn = DBUtil.getConnection();
		try {
			conn.setAutoCommit(false); // 자동커밋을 해제
			
			String insertSql = "INSERT INTO cashbook(cash_date, kind, cash, memo, member_id, update_date, create_date)"
								+ " VALUES(?,?,?,?,?,NOW(),NOW())";
			
			// insert + select 방금 생성된 행의 키값 ex)select 방금 입력한 cashbook_no from cashbook;
			stmt = conn.prepareStatement(insertSql, PreparedStatement.RETURN_GENERATED_KEYS);
			stmt.setString(1, cashbook.getCashDate());
			stmt.setString(2, cashbook.getKind());
			stmt.setInt(3, cashbook.getCash());
			stmt.setString(4, cashbook.getMemo());
			stmt.setString(5, cashbook.getMemberId());
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
		conn = DBUtil.getConnection();
		String sql = "SELECT cashbook_no cashbookNo, cash_date cashDate, kind, cash, memo, update_date updateDate, create_date createDate FROM cashbook WHERE cashbook_no=?";
		try {
			conn = DBUtil.getConnection();
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
		conn = DBUtil.getConnection();
		String hashtagSql = "DELETE FROM hashtag WHERE cashbook_no=?";
		String cashbooksql = "DELETE FROM cashbook WHERE cashbook_no=?";
		try {
			conn.setAutoCommit(false);
			// 해시태그 삭제
			stmt = conn.prepareStatement(hashtagSql);
			stmt.setInt(1, cashbookNo);
			stmt.executeUpdate();
			// 가계부 삭제
			stmt2 = conn.prepareStatement(cashbooksql);
			stmt2.setInt(1, cashbookNo);
			int row = stmt2.executeUpdate();
			
			if(row == 1) {
				System.out.println("가계부 삭제 성공");
				conn.commit();
			} else {
				System.out.println("가계부 삭제 실패");
			}
		} catch(Exception e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			try {
				stmt2.close();
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	public void updateCashbook(Cashbook cashbook, List<String> hashtag) {
		Connection conn = null;
		PreparedStatement stmt1 = null;
		PreparedStatement stmt2 = null;
		PreparedStatement stmt3 = null;
		conn = DBUtil.getConnection();
		String deleteHashtagSql = "DELETE FROM hashtag WHERE cashbook_no=?"; // 해시태그 삭제
		String updateSql = "UPDATE cashbook SET kind = ?, cash = ?, memo = ?, update_date = NOW() WHERE cashbook_no = ?"; // 업데이트 등록
		String hashtagSql = "INSERT INTO hashtag(cashbook_no,tag,create_date) VALUES(?,?,NOW())"; // 업데이트 된 해시태그 등록
		try {
			// 해시태그 삭제
			stmt1 = conn.prepareStatement(deleteHashtagSql);
			stmt1.setInt(1, cashbook.getCashbookNo());
			stmt1.executeUpdate();
			// 게시글 수정 적용
			stmt2 = conn.prepareStatement(updateSql);
			stmt2.setString(1, cashbook.getKind());
			stmt2.setInt(2, cashbook.getCash());
			stmt2.setString(3, cashbook.getMemo());
			stmt2.setInt(4, cashbook.getCashbookNo());
			int row = stmt2.executeUpdate();
			
			stmt3 = conn.prepareStatement(hashtagSql);
			// 수정 게시글 해시태그 등록
			for(String h : hashtag) {
				stmt3.setInt(1, cashbook.getCashbookNo());
				stmt3.setString(2, h);
				stmt3.executeUpdate();
			}
			
			if(row == 1) {
				System.out.println("가계부 수정 성공");
				conn.commit(); // 수정 성공시 커밋
			} else {
				System.out.println("가계부 수정 실패");
			}
		} catch (Exception e) {
			try {
				conn.rollback(); 
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			try {
				stmt3.close();
				stmt2.close();
				stmt1.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
