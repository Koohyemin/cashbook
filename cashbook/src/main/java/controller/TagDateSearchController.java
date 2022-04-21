package controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.HashtagDao;

@WebServlet("/TagDateSearchController")
public class TagDateSearchController extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		
		// endDate가 넘어오지 않았다면 현재날짜로 채워주기, 넘어왔다면 넘어온 날짜 받아주기
		if("".equals(endDate)) {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			Calendar now = Calendar.getInstance();
			Date today = now.getTime();
			endDate = format.format(today);
		}
		
		// startDate가 넘어오지 않았다면 endDate에 1일로 바꾼날짜로 채워주기, 넘어왔다면 넘어온 날자 받아주기
		if("".equals(startDate)) {
			startDate = endDate.substring(0,8) + "01";
		}
		
		HashtagDao hashbookDao = new HashtagDao();
		List<Map<String, Object>> list = hashbookDao.tagDateSearchList(startDate, endDate);
		System.out.println("[startDate TagDateSearchController] : " + startDate);
		System.out.println("[endDate TagDateSearchController] : " + endDate);
		request.setAttribute("startDate", startDate);
		request.setAttribute("endDate", endDate);
		request.setAttribute("list", list);
		
		request.getRequestDispatcher("/WEB-INF/view/TagDateSearch.jsp").forward(request, response);
	}
}
