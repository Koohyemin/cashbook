package controller;

import java.io.IOException;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.MemberDao;
import vo.Member;

@WebServlet("/InsertMemberController")
public class InsertMemberController extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8"); // 인코딩
		
		// 세션 확인
		HttpSession session = request.getSession();
		String sessionMemberId = (String)session.getAttribute("sessionMemberId");
		if(sessionMemberId != null) { // 로그인상태일때 가계부 페이지로 돌아가기
			response.sendRedirect(request.getContextPath() + "/CashbookListByMonthController");
			return;
		}
		request.getRequestDispatcher("WEB-INF/view/InsertMember.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8"); // 인코딩
		
		// 세션 확인
		HttpSession session = request.getSession();
		String sessionMemberId = (String)session.getAttribute("sessionMemberId");
		if(sessionMemberId != null) { // 로그인상태일때 가계부 페이지로 돌아가기
			response.sendRedirect(request.getContextPath() + "/CashbookListByMonthController");
			return;
		}
		
		String memberId = request.getParameter("memberId");
		String memberPw = request.getParameter("memberPw");
		String memberPwCheck = request.getParameter("memberPwCheck");
		String memberName = request.getParameter("memberName");
		String nickName = request.getParameter("nickName");
		
		
		// 유효성 체크
		List<String> list = new ArrayList<String>();
		list.add(memberId);
		list.add(memberPw);
		list.add(memberPwCheck);
		list.add(memberName);
		list.add(nickName);
		
		boolean nullcheck = false; // 공백값이 없다면 false
		
		// 공백값이 있다면 true로 변경 -> 회원가입 실패
		for(String s : list) {
			if(s == null || "".equals(s)) {
				nullcheck = true;
				System.out.println("[InsertMemberController] : 회원가입 실패, 공백값");
				break;
			}
		}
		// memberPw와 memberPw가 같지않다면 true로 변경 -> 회원가입 실패
		if(!memberPw.equals(memberPwCheck)) {
			nullcheck = true;
			System.out.println("[InsertMemberController] : 회원가입 실패, 비밀번호 확인 불일치");
		}
		
		// 회원가입 실패시 회원가입 페이지로 돌아가기
		if(nullcheck == true) {
			response.sendRedirect(request.getContextPath() + "/InsertMemberController");
			return;
		}
		
		// Member로 묶어주기
		Member member = new Member();
		member.setMemberId(memberId);
		member.setMemberPw(memberPw);
		member.setMemberName(memberName);
		member.setNickName(nickName);
		
		MemberDao memberDao = new MemberDao();
		memberDao.insertMember(member);
		
		// 가입 완료 후 로그인 페이지로 돌아가기
		response.sendRedirect(request.getContextPath() + "/LoginController");
	}
}
