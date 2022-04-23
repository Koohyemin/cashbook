package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.CashbookDao;
import dao.MemberDao;


@WebServlet("/DeleteMemberController")
public class DeleteMemberController extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8"); // 인코딩
		
		// 세션 확인
		HttpSession session = request.getSession();
		String sessionMemberId = (String)session.getAttribute("sessionMemberId");
		if(sessionMemberId == null) { // 로그인상태가 아니라면 로그인 페이지로 이동
			response.sendRedirect(request.getContextPath() + "/LoginController");
			return;
		}
		
		String memberId = request.getParameter("memberId");
		request.setAttribute("memberId", memberId);
		request.getRequestDispatcher("/WEB-INF/view/DeleteMember.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8"); // 인코딩
		
		// 세션 확인
		HttpSession session = request.getSession();
		String sessionMemberId = (String)session.getAttribute("sessionMemberId");
		if(sessionMemberId == null) { // 로그인상태가 아니라면 로그인 페이지로 이동
			response.sendRedirect(request.getContextPath() + "/LoginController");
			return;
		}
		
		// 회원 정보, 데이터 삭제
		String memberId = request.getParameter("memberId");
		String memberPw = request.getParameter("memberPw");
		
		MemberDao memberDao = new MemberDao();
		int memberRow = memberDao.deleteMember(memberId, memberPw);
		
		if(memberRow == 1) {
			System.out.println("회원정보, 데이터 삭제 성공");
			response.sendRedirect(request.getContextPath()+"/LogoutController"); // 정보+데이터 삭제 후 로그아웃
			
		} else {
			System.out.println("회원정보, 데이터 삭제 실패"); // 비밀번호 불일치 또는 다른 문제?
			System.out.println("[memberPw DeleteMemberController] : " + memberPw);
			response.sendRedirect(request.getContextPath()+"/SelectMemberOneController?memberId="+memberId);
		}
	}
}
