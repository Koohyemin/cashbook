package controller;

import java.io.IOException;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.CashbookDao;
import vo.Cashbook;


@WebServlet("/UpdateCashbookController")
public class UpdateCashbookController extends HttpServlet {
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
		
		int cashbookNo = Integer.parseInt(request.getParameter("cashbookNo"));
		CashbookDao cashbookDao = new CashbookDao();
		Cashbook cashbook = new Cashbook();
		cashbook = cashbookDao.selectCashBookOne(cashbookNo); 
		
		System.out.println("[cashbookNo UpdateCashbookController] : " + cashbookNo);	
		
		request.setAttribute("cashbookNo", cashbookNo);
		request.setAttribute("cashbook", cashbook);
		
		request.getRequestDispatcher("/WEB-INF/view/UpdateCashbook.jsp").forward(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");

		// 세션 확인
		HttpSession session = request.getSession();
		String memberId = (String)session.getAttribute("sessionMemberId");
		if(memberId == null) {
			// 로그인이 되어있지 않은 상태 -> 로그인 폼으로 돌아가기
			response.sendRedirect(request.getContextPath() + "/LoginController");
			return;
		}
		
		Cashbook cashbook = new Cashbook();
		cashbook.setKind(request.getParameter("kind"));
		cashbook.setCash(Integer.parseInt(request.getParameter("cash")));
		cashbook.setMemo(request.getParameter("memo"));
		cashbook.setCashbookNo(Integer.parseInt(request.getParameter("cashbookNo")));
		
		List<String> hashtag = new ArrayList<String>();
		String memo = request.getParameter("memo").replace("#", " #");
		String[] arr = memo.split(" ");
		for(String s : arr) {
			if(s.startsWith("#")) {
				String temp = s.replace("#", "");
				if(temp.length()>0) {
					hashtag.add(temp);
				}
			}
		}
		
		CashbookDao cashbookDao = new CashbookDao();
		cashbookDao.updateCashbook(cashbook, hashtag);
		
		// 수정 후 리스트로 돌아가기
		response.sendRedirect(request.getContextPath()+"/CashbookListByMonthController");
	}
}
