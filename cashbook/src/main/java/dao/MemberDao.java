package dao;

import java.sql.*;
import java.util.*;

import vo.*;

public class MemberDao {
	// 로그인
	public String selectMemberByIdPw(Member member) {
		String memberId = null;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql = "SELECT member_id memberId FROM member WHERE member_id=? AND member_pw=PASSWORD(?)";
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/cashbook","root","java1234");
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, member.getMemberId());
			stmt.setString(2, member.getMemberPw());
			rs = stmt.executeQuery();
	        if(rs.next()) {
	        	memberId = rs.getString("memberId");
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
		return memberId;
	}
	// 회원가입 정보 입력
	public void insertMember(Member member) {
		Connection conn = null;
		PreparedStatement stmt = null;
		String sql = "INSERT INTO member(member_id, member_pw, member_name, nick_name, create_date) VALUES(?,PASSWORD(?),?,?,NOW())";
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/cashbook","root","java1234");
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, member.getMemberId());
			stmt.setString(2, member.getMemberPw());
			stmt.setString(3, member.getMemberName());
			stmt.setString(4, member.getNickName());
			int row = stmt.executeUpdate();
			
			if(row == 1) {
				System.out.println("회원가입 완료");
			} else {
				System.out.println("회원가입 실패");
			}
		} catch (Exception e) {
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
	// 회원정보 상세보기, 업데이트시 기존 정보 입력처리
	public Member selectMemberOne(String memberId) {
		Member member = null;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql = "SELECT "
					+ "		member_id memberId"
					+ "		, member_name memberName"
					+ "		, nick_name nickName"
					+ "		, create_date createDate"
					+ "		, member_pw memberPw"
					+ " FROM member"
					+ " WHERE member_id=?";
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/cashbook","root","java1234");
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, memberId);
			rs = stmt.executeQuery();
			if(rs.next()) {
				member = new Member();
				member.setMemberId(rs.getString("memberId"));
				member.setMemberName(rs.getString("memberName"));
				member.setNickName(rs.getString("nickName"));
				member.setCreateDate(rs.getString("createDate"));
				member.setMemberPw(rs.getString("memberPw"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return member;
	}
	
	// 회원정보 수정
	public int updateMember(Member member, String originalCheckPw) {
		int row = 0;
		Connection conn = null;
		PreparedStatement stmt = null;
		// 기존 비밀번호랑 일치하지않다면 update되지않음
		String sql = "UPDATE member SET member_name=?, nick_name=?, member_pw=PASSWORD(?)"
					+ " WHERE member_id=? AND member_pw=PASSWORD(?)";
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/cashbook","root","java1234");
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, member.getMemberName());
			stmt.setString(2, member.getNickName());
			stmt.setString(3, member.getMemberPw());
			stmt.setString(4, member.getMemberId());
			stmt.setString(5, originalCheckPw);
			row = stmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return row;
	}
	// 회원정보 삭제
	public int deleteMember(String memberId, String memberPw) {
		int row = 0;
		Connection conn = null;
		PreparedStatement stmt1 = null;
		PreparedStatement stmt2 = null;
		PreparedStatement stmt3 = null;
		String hashtagSql = "DELETE h"
				+ " FROM hashtag h INNER JOIN cashbook c"
				+ "	ON h.cashbook_no=c.cashbook_no"
				+ " WHERE c.member_id=?"; // 고객관련 해시태그 삭제
		String cashbooksql = "DELETE FROM cashbook WHERE member_id=?"; // 고객 관련 캐시북 삭제
		String memberSql = "DELETE FROM member WHERE member_id=? AND member_pw=PASSWORD(?)"; // 고객 정보 삭제
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/cashbook","root","java1234");
			// 해시태그 삭제
			stmt1 = conn.prepareStatement(hashtagSql);
			stmt1.setString(1, memberId);
			stmt1.executeUpdate();
			// 가계부 삭제
			stmt2 = conn.prepareStatement(cashbooksql);
			stmt2.setString(1, memberId);
			stmt2.executeUpdate();
			// 고객정보 삭제
			stmt3 = conn.prepareStatement(memberSql);
			stmt3.setString(1, memberId);
			stmt3.setString(2, memberPw);
			row = stmt3.executeUpdate();
			
			if(row == 1) { // 고객정보가 삭제된다면 commit
				conn.commit();
			} else {
				try { // 고객정보가 삭제되지않았다면 rollback
					conn.rollback(); 
				} catch (SQLException e1) {
					e1.printStackTrace();
				} 
			}
		} catch (Exception e) {
			e.printStackTrace();
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
		return row;
	}
}
