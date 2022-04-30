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
		
		// 유효성 메세지
		String msg = "";
		
		if(!"".equals(request.getParameter("msg"))) {
			msg = request.getParameter("msg");
		}
		
		request.setAttribute("msg", msg);
		
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
		
		// 이미 있는 ID라면 -> 회원가입 실패
		MemberDao memberDao = new MemberDao();
		List<String> memberIdList = memberDao.memberIdList();
		for(String s : memberIdList) {
			if(s.equals(memberId)) {
				System.out.println("[InsertMemberController] : 회원가입 실패, 이미 존재하는 ID");
				response.sendRedirect(request.getContextPath() + "/InsertMemberController?msg=Member registration failed : duplicate ID");
				return;
			}
		}
		
		// 공백값이 있다면 -> 회원가입 실패
		for(String s : list) {
			if(s == null || "".equals(s)) {
				System.out.println("[InsertMemberController] : 회원가입 실패, 공백값");
				response.sendRedirect(request.getContextPath() + "/InsertMemberController?msg=Member registration failed : Required input value is empty");
				return;
			}
		}
		
		
		// memberPw와 memberPw가 같지않다면 -> 회원가입 실패
		if(!memberPw.equals(memberPwCheck)) {
			System.out.println("[InsertMemberController] : 회원가입 실패, 비밀번호 확인 불일치");
			response.sendRedirect(request.getContextPath() + "/InsertMemberController?msg=Member registration failed : password check mismatch");
			return;
		}
		
		
		// Member로 묶어주기
		Member member = new Member();
		member.setMemberId(memberId);
		member.setMemberPw(memberPw);
		member.setMemberName(memberName);
		member.setNickName(nickName);
		
		
		memberDao.insertMember(member);
		
		// 가입 완료 후 로그인 페이지로 돌아가기
		response.sendRedirect(request.getContextPath() + "/LoginController");
	}
}
