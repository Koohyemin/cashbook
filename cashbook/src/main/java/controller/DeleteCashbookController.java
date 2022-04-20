package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.CashbookDao;

@WebServlet("/DeleteCashbookController")
public class DeleteCashbookController extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int cashbookNo = Integer.parseInt(request.getParameter("cashbookNo")); // 삭제할 번호
		
		// CashbookDao.deleteCashbook()
		CashbookDao cashbookDao = new CashbookDao();
		cashbookDao.deleteCashbook(cashbookNo);
		
		System.out.println("[cashbookNo DeleteCashbookController] : " + cashbookNo);
		
		// 가계부 처음페이지로 돌아가기
		response.sendRedirect(request.getContextPath() + "/CashbookListByMonthController");
	}
}
