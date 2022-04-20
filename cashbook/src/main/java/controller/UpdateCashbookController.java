package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.CashbookDao;
import vo.Cashbook;


@WebServlet("/UpdateCashbookController")
public class UpdateCashbookController extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
		
		Cashbook cashbook = new Cashbook();
		cashbook.setKind(request.getParameter("kind"));
		cashbook.setCash(Integer.parseInt(request.getParameter("cash")));
		cashbook.setMemo(request.getParameter("memo"));
		cashbook.setCashbookNo(Integer.parseInt(request.getParameter("cashbookNo")));
		
		CashbookDao cashbookDao = new CashbookDao();
		cashbookDao.updateCashbook(cashbook);
		
		// 수정 후 리스트로 돌아가기
		response.sendRedirect(request.getContextPath()+"/CashbookListByMonthController");
	}
}
