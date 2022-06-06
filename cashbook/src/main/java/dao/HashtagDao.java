package dao;

import java.util.*;

import util.DBUtil;

import java.sql.*;

public class HashtagDao {
	public List<Map<String, Object>> selectTagRankList(String memberId) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		conn = DBUtil.getConnection();
		String sql = "SELECT b.tag tag, COUNT(*) cnt, RANK() over(ORDER BY cnt DESC) rank"
					+ " FROM (SELECT c.cashbook_no cashbookNo, c.member_id memberId"
					+ "		FROM cashbook c INNER JOIN member m"
					+ "			ON m.member_id=c.member_id) a"
					+ "				INNER JOIN (SELECT cashbook_no cashbookNo, tag"
					+ "								FROM hashtag) b"
					+ "					ON a.cashbookNo=b.cashbookNo"
					+ " WHERE a.memberId=?"
					+ " GROUP BY tag"
					+ " ORDER BY RANK";
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, memberId);
			rs = stmt.executeQuery();
	         while(rs.next()) {
	             Map<String, Object> map = new HashMap<>();
	             map.put("tag", rs.getString("tag"));
	             map.put("cnt", rs.getInt("cnt"));
	             map.put("rank", rs.getInt("rank"));
	             list.add(map);
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
		return list;
	}
	public List<Map<String, Object>> tagKindSearchList(String kind, String memberId) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		conn = DBUtil.getConnection();
		String sql = "SELECT RANK() over(ORDER BY t.cnt DESC) rank, t.tag tag, t.cnt cnt"
					+ " FROM (SELECT t.tag tag, COUNT(*) cnt"
					+ "		  FROM hashtag t INNER JOIN cashbook c"
					+ "		  ON t.cashbook_no = c.cashbook_no"
					+ "		  WHERE c.kind = ? AND c.member_id=?"
					+ "		  GROUP BY t.tag) t";
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, kind);
			stmt.setString(2, memberId);
			rs = stmt.executeQuery();
	         while(rs.next()) {
	             Map<String, Object> map = new HashMap<>();
	             map.put("rank", rs.getInt("rank"));
	             map.put("tag", rs.getString("tag"));
	             map.put("cnt", rs.getInt("cnt"));
	             list.add(map);
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
		return list;
	}
	public List<Map<String, Object>> tagDateSearchList(String startDate, String endDate, String memberId) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		conn = DBUtil.getConnection();
		String sql = "SELECT h.tag tag, COUNT(*) cnt, RANK() over(ORDER BY cnt DESC) ranking"
					+ " FROM hashtag h INNER JOIN cashbook c"
					+ "		ON h.cashbook_no = c.cashbook_no"
					+ " WHERE c.cash_date BETWEEN ? AND ? AND c.member_id=?"
					+ " GROUP BY tag";
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, startDate);
			stmt.setString(2, endDate);
			stmt.setString(3, memberId);
			rs = stmt.executeQuery();
	         while(rs.next()) {
	             Map<String, Object> map = new HashMap<>();
	             map.put("tag", rs.getString("tag"));
	             map.put("cnt", rs.getInt("cnt"));
	             map.put("ranking", rs.getInt("ranking"));
	             list.add(map);
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
		return list;
	}
	public List<Map<String, Object>> selectTagOne(String tag, String memberId) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		conn = DBUtil.getConnection();
		String sql = "SELECT h.tag tag, c.cash_date cashDate, c.kind kind, c.cash cash, c.memo memo"
					+ " FROM cashbook c INNER JOIN hashtag h"
					+ "		ON c.cashbook_no=h.cashbook_no"
					+ " WHERE h.tag=? AND c.member_id=?"
					+ " ORDER BY c.cash_date";
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, tag);
			stmt.setString(2, memberId);
			rs = stmt.executeQuery();
	         while(rs.next()) {
	             Map<String, Object> map = new HashMap<>();
	             map.put("tag", rs.getString("tag"));
	             map.put("cashDate", rs.getString("cashDate"));
	             map.put("kind", rs.getString("kind"));
	             map.put("memo", rs.getString("memo"));
	             list.add(map);
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
		return list;
	}
}
