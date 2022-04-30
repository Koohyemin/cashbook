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

@WebServlet("/InsertCashbookController")
public class InsertCashbookController extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8"); // 인코딩
		
		// 세션 확인
		HttpSession session = request.getSession();
		String memberId = (String)session.getAttribute("sessionMemberId");
		if(memberId == null) {
			// 로그인이 되어있지 않은 상태 -> 로그인 폼으로 돌아가기
			response.sendRedirect(request.getContextPath() + "/LoginController");
			return;
		}
		
		String y = request.getParameter("y");
		String m = request.getParameter("m");
		String d = request.getParameter("d");
		String cashDate = y + "-" + m + "-" + d;
		
		// 유효성에 걸려서 돌아왔다면 걸린날짜 적용하여 InsertCashbook 보여주기
		if(request.getParameter("returnCashDate")!=null) {
			cashDate = request.getParameter("returnCashDate");
		}
		
		// 받아온 메시지 값 대입
		String msg = null;
		if(!"".equals(request.getParameter("msg")) || msg != null) {
			msg = request.getParameter("msg");
		}
		
		request.setAttribute("cashDate", cashDate);
		request.setAttribute("msg", msg);
		request.getRequestDispatcher("/WEB-INF/view/InsertCashbookForm.jsp").forward(request, response);
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
		
		// 유효성 검사 (kind는 checked로 기본값 속성 적용) -> 해당날짜 입력폼으로 돌아가기+실패메시지
		String[] nullcheck = {request.getParameter("cash"), request.getParameter("memo")};
		String[] nullMemo = {"cash","memo"};
		for(int i=0; i<nullcheck.length; i++) {
				if("".equals(nullcheck[i]) || nullcheck[i] == null) {
					System.out.println("[UpdateCashbookController] : 가계부 입력 실패, 공백값 존재");
					response.sendRedirect(request.getContextPath()+"/InsertCashbookController?returnCashDate="+request.getParameter("cashDate")+"&msg=fail insert : input "+nullMemo[i]);
					return;
				}
		}
		
		String cashDate = request.getParameter("cashDate");
		String kind = request.getParameter("kind");
		int cash = Integer.parseInt(request.getParameter("cash"));
		String memo = request.getParameter("memo");
		
		Cashbook cashbook = new Cashbook();
		cashbook.setCashDate(cashDate);
		cashbook.setKind(kind);
		cashbook.setCash(cash);
		cashbook.setMemo(memo);
		cashbook.setMemberId(memberId);
		
		// 디버깅
		System.out.println("[cashDate InsertCashbookController.doPost()] :" + cashDate);
		System.out.println("[kind InsertCashbookController.doPost()] :" + kind);
		System.out.println("[cash InsertCashbookController.doPost()] :" + cash);
		System.out.println("[memo InsertCashbookController.doPost()] :" + memo);
		
		List<String> hashtag = new ArrayList<String>();
		String memo2 = memo.replace("#", " #");  // 해시태그 사이 공백이 없을 경우 대비 ex) #테스트1#테스트2
		String[] arr = memo2.split(" ");  // " "문자를 기준으로 하나의 배열 구분
		for(String s : arr) {
			if(s.startsWith("#")) { // #으로 시작하는 문자일때
				String temp = s.replace("#", ""); // #을 ""로 바꿔줌 ex) #테스트 -> 테스트
				
				// 한 게시글에 중복 해시태그가 들어오는 경우 -> 해시태그 프라이머리키 중복으로 게시글 입력 되지않음 -> 입력실패 -> 하나의 해시태그로 처리
				boolean reviewHashtag = false; // 해시태그 중복여부, 글자수 확인 -> false일때추가
				if(temp.length()<1) { // 1글자 미만이라면
					reviewHashtag = true; // 조건 불만족 true로 변경
				}
				for(String h : hashtag) {
					if(temp.equals(h)) { // temp가 중복된 값을 가지고있다면
						reviewHashtag = true; // 조건 불만족 true로 변경
					}
				}
				if(reviewHashtag == false) { // reviewHashtag가 false일때, hashtag에 추가
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
