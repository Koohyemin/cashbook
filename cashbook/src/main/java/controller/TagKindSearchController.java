package controller;

import java.io.IOException;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.HashtagDao;

@WebServlet("/TagKindSearchController")
public class TagKindSearchController extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		
		// 세션 확인
		HttpSession session = request.getSession();
		String memberId = (String)session.getAttribute("sessionMemberId");
		if(memberId == null) {
			// 로그인이 되어있지 않은 상태 -> 로그인 폼으로 돌아가기
			response.sendRedirect(request.getContextPath() + "/LoginController");
			return;
		}
		
		if(request.getParameter("kind") != null) {
			String kind = request.getParameter("kind");
			HashtagDao hashtagDao = new HashtagDao();
			List<Map<String, Object>> list = hashtagDao.tagKindSearchList(kind, memberId);
			request.setAttribute("kind", kind);
			request.setAttribute("list", list);
			
			System.out.println("[kind TagKindSearchController] : " + kind);
		}
		
		request.getRequestDispatcher("/WEB-INF/view/TagKindSearch.jsp").forward(request, response);
	}
}
