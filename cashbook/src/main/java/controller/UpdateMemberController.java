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

import java.util.*;

@WebServlet("/UpdateMemberController")
public class UpdateMemberController extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8"); // 인코딩
		
		// 세션 확인
		HttpSession session = request.getSession();
		String sessionMemberId = (String)session.getAttribute("sessionMemberId");
		if(sessionMemberId == null) { // 로그인상태가 아니라면 로그인 페이지로 이동
			response.sendRedirect(request.getContextPath() + "/LoginController");
			return;
		}
		
		// 정보수정 뷰 연결
		String memberId = request.getParameter("memberId");
		MemberDao memberDao = new MemberDao();
		Member member = memberDao.selectMemberOne(memberId);
		request.setAttribute("member", member);
		request.getRequestDispatcher("/WEB-INF/view/UpdateMember.jsp").forward(request, response);
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
		
		String memberId = request.getParameter("memberId");
		String memberName = request.getParameter("memberName");
		String nickName = request.getParameter("nickName");
		String memberPw = request.getParameter("memberPw"); // 변경할 비밀번호
		String memberPwCheck = request.getParameter("memberPwCheck"); // 변경할 비밀번호 확인
		
		String originalCheckPw = request.getParameter("originalCheckPw"); // 수정을 위한 기존 비밀번호
		
		// 수정값 하나로 묶기
		Member member =  new Member();
		member.setMemberId(memberId);
		member.setMemberName(memberName);
		member.setNickName(nickName);
		member.setMemberPw(memberPw);
	
		System.out.println("[UpdateMemberController]" + member);
		
		// 유효성 체크
		boolean nullcheck = false; 
		
		List<String> list = new ArrayList<String>();
		list.add(memberName);
		list.add(nickName);
		list.add(memberPw);
		list.add(memberPwCheck);
		list.add(originalCheckPw);
		
		for(String s : list) { // 비어있는 값이 있다면 true
			if(s==null || "".equals(s)) {
				System.out.println("[UpdateMemberController] : 정보 수정 실패, 공백값");
				nullcheck = true;
				break;
			}
		}
		if(!memberPw.equals(memberPwCheck)) { // memberPw와 memberPwCheck값이 다르다면 true
			System.out.println("[UpdateMemberController] : 정보 수정 실패, 수정비밀번호 확인 불일치");
			System.out.println("[memberPw UpdateMemberController]" + memberPw);
			System.out.println("[memberPwCheck UpdateMemberController]" + memberPwCheck);
			nullcheck = true;
		}
				
		// 빈값이 있거나, 기존 비밀번호 불일치시, 다시 정보수정폼으로 돌아가기
		if(nullcheck == true) {
			response.sendRedirect(request.getContextPath() + "/UpdateMemberController?memberId="+memberId);
			return;
		}
		
		// 수정 insert 메서드
		MemberDao memberDao = new MemberDao();
		int row = memberDao.updateMember(member, originalCheckPw); // originalCheckPw가 일치하지 않다면 sql문 WHERE절에서 조건불충족으로 INSERT 실패
		
		if(row == 1) {
			System.out.println("회원정보 수정 성공");
			response.sendRedirect(request.getContextPath()+"/SelectMemberOneController?memberId="+memberId);
		} else {
			System.out.println("회원정보 수정 실패"); // 기존 비밀번호 불일치 또는 다른 문제?
			response.sendRedirect(request.getContextPath()+"/UpdateMemberController?memberId="+memberId);
		}
	}
}
