package controller;

import java.io.IOException;
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
		HttpSession session = request.getSession();
		String sessionMemberId = (String)session.getAttribute("sessionMemberId");
		if(sessionMemberId != null) { // 로그인상태일때 회원가입 실행
			response.sendRedirect(request.getContextPath() + "/CashbookListByMonthController"); // 가계부 페이지로 돌아가기
			return;
		}
		request.getRequestDispatcher("WEB-INF/view/InsertMember.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8"); // 인코딩
		String memberId = request.getParameter("memberId");
		String memberPw = request.getParameter("memberPw");
		String memberPwCheck = request.getParameter("memberPwCheck");
		String memberName = request.getParameter("memberName");
		String nickName = request.getParameter("nickName");
		
		// 받은 값 중 하나라도 null이라면 다시 회원가입 창으로 돌아가기
		// 비밀번호(memberPw)와 비밀번호 확인값(memberPwCheck)이 같지않다면 다시 회원가입 창으로 돌아가기
		if(memberId == null || memberPw == null || memberPwCheck == null|| memberName == null || nickName == null || !memberPw.equals(memberPwCheck)) {
			System.out.println("[InsertMemberController] : 회원가입 실패");
			response.sendRedirect(request.getContextPath() + "/InsertMemberController");
			return;
		}
		
		Member member = new Member();
		member.setMemberId(memberId);
		member.setMemberPw(memberPw);
		member.setMemberName(memberName);
		member.setNickName(nickName);
		
		MemberDao memberDao = new MemberDao();
		memberDao.insertMember(member);
		
		// 로그인 페이지로 돌아가기
		response.sendRedirect(request.getContextPath() + "/LoginController");
	}
}
