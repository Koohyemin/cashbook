package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.MemberDao;
import vo.Member;

@WebServlet("/UpdateMemberController")
public class UpdateMemberController extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String memberId = request.getParameter("memberId");
		
		MemberDao memberDao = new MemberDao();
		Member member = memberDao.selectMemberOne(memberId);
		request.setAttribute("member", member);
		request.getRequestDispatcher("/WEB-INF/view/UpdateMember.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String memberId = request.getParameter("memberId");
		String memberName = request.getParameter("memberName");
		String nickName = request.getParameter("nickName");
		String memberPw = request.getParameter("memberPw");
		
		String checkPw = request.getParameter("checkPw");
		
		// 정보가 하나라도 입력되지 않았을시 다시 해당 수정폼으로 돌아가기
		if(memberName == null || nickName == null || checkPw == null|| memberPw == null) {
			System.out.println("[UpdateMemberController] : 정보수정 실패");
			response.sendRedirect(request.getContextPath() + "/UpdateMemberController?memberId="+memberId);
			return;
		}
		MemberDao memberDao = new MemberDao();
		Member member =  new Member();
		member.setMemberId(memberId);
		member.setMemberName(memberName);
		member.setNickName(nickName);
		member.setMemberPw(memberPw);
		
		int row = memberDao.updateMember(member, checkPw);
		
		if(row == 1) {
			System.out.println("회원정보 수정 성공");
			response.sendRedirect(request.getContextPath()+"/SelectMemberOneController?memberId="+memberId);
		} else {
			System.out.println("회원정보 수정 실패");
			response.sendRedirect(request.getContextPath()+"/UpdateMemberController?memberId="+memberId);
		}
	}
}
