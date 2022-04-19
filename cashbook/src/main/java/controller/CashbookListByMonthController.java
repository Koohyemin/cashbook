package controller;

import java.io.IOException;
import java.util.Calendar;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.CashbookDao;


@WebServlet("/CashbookListByMonthController")
public class CashbookListByMonthController extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 1) 월별 가계부 리스트 요청 분석
		Calendar now = Calendar.getInstance();
		int y = now.get(Calendar.YEAR);
		int m = now.get(Calendar.MONTH) + 1; // 0->1월, 1->2월,....11->12월
		
		
		if(request.getParameter("y") != null) {
			y = Integer.parseInt(request.getParameter("y"));
		}
		if(request.getParameter("m") != null) {
			m = Integer.parseInt(request.getParameter("m"));
		}
		
		if(m==0) { // 0이 넘어올 시, 작년(y-1) 12월
			m = 12;
			y = y-1;
		}
		if(m==13) { // 13이 넘어올 시, 올해(y+1) 1월
			m = 1;
			y = y+1;
		}
		
		System.out.println("[연도 / 월] : " + y + "년 / " + m + "월");
		
		/*
		 	1) startBlank
		 	2) endDay
		 	3) endBlank
		 	4) totalBlank
		 */
		
		// 1) startBlank -> 시작시 필요한 공백 <td> 개수를 구하는 알고리즘
		// firstDay는 오늘날짜에서 날짜만 1일로 변경
		Calendar firstDay = Calendar.getInstance(); // ex) 2022.04.19
		firstDay.set(Calendar.YEAR, y);
		firstDay.set(Calendar.MONTH, m-1); // 자바 달력API MONTH는 0부터 시작하기 때문에 1을 빼줌
		firstDay.set(Calendar.DATE, 1);  // ex) 2022.04.01
		// dayOfWeek = DAY_OF_WEEK API가 반환하는 숫자 : 일1, ... , 금6, 토7
		int dayOfWeek = firstDay.get(Calendar.DAY_OF_WEEK);
		// startBlank = 일 0, 월1, 화2, ... 토6 -> dayOfWeekK-1
		int startBlank = dayOfWeek - 1;
		
		// 2) endDay -> 마지막 날짜 : 자바 달력 api를 이용
		int endDay = firstDay.getActualMaximum(Calendar.DATE); // firstDay달의 제일 마지막 날짜
		
		// 3) endBlank -> startBlank와 endDay를 합의 결과에 endBlank를 더해서 7의 배수가 되도록
		int endBlank = 0;
		if((startBlank+endDay)%7 != 0) {
			endBlank = 7 - (startBlank+endDay)%7;
		}
		
		// 4) totalTd
		int totalTd = startBlank + endDay + endBlank;
		
		
		// 2) 모델값(월별 가계부 리스트)을 반환하는 비지니스로직(모델) 호출
		CashbookDao cashbookDao = new CashbookDao();
		List<Map<String, Object>> list = cashbookDao.selectCashbookListByMonth(y, m);
		
		
		// 달력출력에 필요한 모델값(1),2),3),4)) + 데이터베이스에서 반환된 모델값(list, y출력 연도, m출력 월)
		
		// 달력출력에 필요한 모델값(startBlank, endDay, endBlank, totalTd)
		request.setAttribute("startBlank", startBlank);
		request.setAttribute("endDay", endDay);
		request.setAttribute("endBlank", endBlank);
		request.setAttribute("totalTd", totalTd);
		// 데이터베이스에서 반환된 모델값(list, y출력 연도, m출력 월)
		request.setAttribute("list", list);
		request.setAttribute("y", y);
		request.setAttribute("m", m);
		
		// 3) 뷰 포워딩
		request.getRequestDispatcher("/WEB-INF/view/CashbookListByMonth.jsp").forward(request, response);
	}
}
