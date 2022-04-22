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

@WebServlet("/SelectMemberOneController")
public class SelectMemberOneController extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8"); // 인코딩
		
		// 로그인 상태 확인
		String memberId = request.getParameter("sessionMemberId");
		if(memberId == null) {
			// 로그인이 되어있지 않다면
			response.sendRedirect(request.getContextPath() + "/LoginController"); // 로그인 페이지로 돌아가기
			return;
		}
		
		System.out.println("[memberId SelectMemberOneController] : " + memberId);
		
		MemberDao memberDao = new MemberDao();
		Member member = memberDao.selectMemberOne(memberId);
		
		request.setAttribute("member", member);
		request.getRequestDispatcher("/WEB-INF/view/MemberOne.jsp").forward(request, response);
	}

}
