package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.MemberDao;

@WebServlet("/UpdateMemberPwController")
public class UpdateMemberPwController extends HttpServlet {
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
		// 유효성 메세지
		String msg = "";
		
		if(!"".equals(request.getParameter("msg"))) {
			msg = request.getParameter("msg");
		}
		
		request.setAttribute("msg", msg);
		request.setAttribute("memberId", memberId);
		
		request.getRequestDispatcher("/WEB-INF/view/UpdateMemberPw.jsp").forward(request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8"); // 인코딩
		
		// 세션 확인
		HttpSession session = request.getSession();
		String memberId = (String)session.getAttribute("sessionMemberId");
		if(memberId == null) { // 로그인상태가 아니라면 로그인 페이지로 이동
			response.sendRedirect(request.getContextPath() + "/LoginController");
			return;
		}
		
		String memberPw = request.getParameter("memberPw"); // 변경할 비밀번호
		String memberPwCheck = request.getParameter("memberPwCheck"); // 변경할 비밀번호 확인
		String originalCheckPw = request.getParameter("originalCheckPw"); // 수정을 위한 기존 비밀번호
		
		// 유효성 체크 -> 빈값이 있거나, 기존 비밀번호 불일치시, 다시 비밀번호 수정폼으로 돌아가기
		if(memberPw==null || "".equals(memberPw) || memberPwCheck==null || "".equals(memberPwCheck)) {
			System.out.println("[UpdateMemberController] : 정보 수정 실패, 공백값");
			response.sendRedirect(request.getContextPath() + "/UpdateMemberPwController?memberId="+ memberId+"&msg=fail update PW : Required input value is empty");
			return;
		}
		if(!memberPw.equals(memberPwCheck)) { // memberPw와 memberPwCheck값이 다르다면 true
			System.out.println("[UpdateMemberController] : 정보 수정 실패, 수정비밀번호 확인 불일치");
			System.out.println("[memberPw UpdateMemberController]" + memberPw);
			System.out.println("[memberPwCheck UpdateMemberController]" + memberPwCheck);
			response.sendRedirect(request.getContextPath() + "/UpdateMemberPwController?memberId="+ memberId+"&msg=fail update PW : PW check mismatch");
			return;
		}

		// 비밀번호 수정 update 메서드
		MemberDao memberDao = new MemberDao();
		int row = memberDao.updateMemberPw(memberPwCheck, memberId, originalCheckPw); // originalCheckPw가 일치하지 않다면 sql문 WHERE절에서 조건불충족으로 INSERT 실패
		
		if(row == 1) {
			System.out.println("비밀번호 수정 성공");
			response.sendRedirect(request.getContextPath()+"/SelectMemberOneController?memberId="+memberId);
		} else {
			System.out.println("비밀번호 수정 실패"); // 기존 비밀번호 불일치 또는 다른 문제?
			response.sendRedirect(request.getContextPath()+"/UpdateMemberPwController?memberId="+memberId+"&msg=fail update PW : PW mismatch");
		}
	}

}
