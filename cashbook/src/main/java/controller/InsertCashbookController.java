package controller;

import java.io.IOException;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.CashbookDao;
import vo.Cashbook;

@WebServlet("/InsertCashbookController")
public class InsertCashbookController extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String y = request.getParameter("y");
		String m = request.getParameter("m");
		String d = request.getParameter("d");
		String cashDate = y + "-" + m + "-" + d;
		request.setAttribute("cashDate", cashDate);
		request.getRequestDispatcher("/WEB-INF/view/InsertCashbookForm.jsp").forward(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 유효성검사 추가하기

		request.setCharacterEncoding("utf-8");
		
		String cashDate = request.getParameter("cashDate");
		String kind = request.getParameter("kind");
		int cash = Integer.parseInt(request.getParameter("cash"));
		String memo = request.getParameter("memo");
		
		Cashbook cashbook = new Cashbook();
		cashbook.setCashDate(cashDate);
		cashbook.setKind(kind);
		cashbook.setCash(cash);
		cashbook.setMemo(memo);
		
		// 디버깅
		System.out.println("[cashDate InsertCashbookController.doPost()] :" + cashDate);
		System.out.println("[kind InsertCashbookController.doPost()] :" + kind);
		System.out.println("[cash InsertCashbookController.doPost()] :" + cash);
		System.out.println("[memo InsertCashbookController.doPost()] :" + memo);
		
		List<String> hashtag = new ArrayList<String>();
		String memo2 = memo.replace("#", " #");
		String[] arr = memo2.split(" ");
		for(String s : arr) {
			if(s.startsWith("#")) {
				String temp = s.replace("#", "");
				if(temp.length()>0) {
					hashtag.add(temp);
				}
			}
		}
		System.out.println("[hashtag.size InsertCashbookController.doPost()] :" + hashtag.size());
		
		CashbookDao cashbookDao = new CashbookDao();
		cashbookDao.insertCashbook(cashbook, hashtag);
		System.out.println("[cashDate InsertCashbookController.doPost()] :" + cashDate);
		System.out.println("[kind InsertCashbookController.doPost()] :" + kind);
		System.out.println("[cash InsertCashbookController.doPost()] :" + cash);
		System.out.println("[memo InsertCashbookController.doPost()] :" + memo);
		
		for(String h : hashtag) {
			System.out.println("[hashtag InsertCashbookController.doPost()] :" + h);
		}
		
		response.sendRedirect(request.getContextPath()+"/CashbookListByMonthController");
	}
}
