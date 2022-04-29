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


@WebServlet("/LoginController")
public class LoginController extends HttpServlet {
	// 로그인 폼
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8"); // 인코딩
		
		HttpSession session = request.getSession();
		String sessionMemberId = (String)session.getAttribute("sessionMemberId");
		if(sessionMemberId != null) {
			// 이미 로그인이 되어있는 상태
			response.sendRedirect(request.getContextPath() + "/CashbookListByMonthController");
			return;
		}
		
		// 유효성 메세지
		String msg = "";
		
		if(!"".equals(request.getParameter("msg"))) {
			msg = request.getParameter("msg");
		}
		
		request.setAttribute("msg", msg);
		
		// 로그인 되어있는 멤버면 리다이렉트
		request.getRequestDispatcher("WEB-INF/view/Login.jsp").forward(request, response);
	}
	// 로그인 액션
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 로그인 정보 받아오기
		String memberId = request.getParameter("memberId");
		String memberPw = request.getParameter("memberPw");
		
		// 유효성
		if("".equals(memberId)) {
			response.sendRedirect(request.getContextPath()+"/LoginController?msg=Please input ID");
			return;
		} else if("".equals(memberPw)) {
			response.sendRedirect(request.getContextPath()+"/LoginController?msg=Please input PW");
			return;
		}
		
		Member member = new Member();
		member.setMemberId(memberId);
		member.setMemberPw(memberPw);
		
		// 모델 호출
		MemberDao memberDao = new MemberDao();
		String returnMemberId = memberDao.selectMemberByIdPw(member);
		System.out.println("[returnMemberId LoginController] : " + returnMemberId);
		if(returnMemberId == null) { // 로그인 실패
			System.out.println("[doPost LoginController] : 로그인 실패");
			response.sendRedirect(request.getContextPath()+"/LoginController");
			return;
		}
		// 로그인 성공
		HttpSession session = request.getSession(); // 현재 연결한 클라이언트(브라우저)에 대하 세션값을 받아옴
		session.setAttribute("sessionMemberId", returnMemberId);
		System.out.println("[MemberId LoginController] : " + returnMemberId + "님이 로그인하셨습니다");
		response.sendRedirect(request.getContextPath()+"/CashbookListByMonthController");
	}
}