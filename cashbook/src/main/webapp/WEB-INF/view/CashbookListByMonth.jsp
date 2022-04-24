<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import = "java.util.*" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>CashbookListByMonth</title>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css">
</head>
<body>
	<div class="container-fluid">
	<%
		List<Map<String, Object>> list = (List<Map<String, Object>>)request.getAttribute("list");
		int y = (Integer)request.getAttribute("y");
		int m = (Integer)request.getAttribute("m");
		
		int startBlank = (Integer)request.getAttribute("startBlank");
		int endDay = (Integer)request.getAttribute("endDay");
		int endBlank = (Integer)request.getAttribute("endBlank");
		int totalTd = (Integer)request.getAttribute("totalTd");
		/* 		
			-- 디버깅
			System.out.println("[list.size() CashBookListByMonth.jsp] : " + list.size());
			System.out.println("[y CashBookListByMonth.jsp] : " + y);
			System.out.println("[m CashBookListByMonth.jsp] : " + m);
			
			System.out.println("[startBlank CashBookListByMonth.jsp] : " + startBlank);
			System.out.println("[endDay CashBookListByMonth.jsp] : " + endDay);
			System.out.println("[endBlank CashBookListByMonth.jsp] : " + endBlank);
			System.out.println("[totalTd CashBookListByMonth.jsp] : " + totalTd); 
		*/
	%>
	<br>
	<h2 class="text-center"><%=y%>년 <%=m%>월 가계부</h2>
	<div style="float:right">
		<a href="<%=request.getContextPath()%>/SelectMemberOneController?memberId=<%=session.getAttribute("sessionMemberId")%>">[<%=session.getAttribute("sessionMemberId")%>]</a>님 반갑습니다.
		<a href="<%=request.getContextPath()%>/LogoutController" class="btn btn-outline-info btn-sm">로그아웃</a>
	</div>
	<div style="float:left">
		<a href="<%=request.getContextPath()%>/TagController" class="btn btn-info">해시태그</a>
	</div>
	<br><br>
		<table class="table table-bordered">
			<colgroup>
				<col style="width:14%">
				<col style="width:14%">
				<col style="width:14%">
				<col style="width:14%">
				<col style="width:14%">
				<col style="width:14%">
				<col style="width:14%">
			</colgroup>
		 	<thead class="table-info text-center">
		 		<tr>
		 			<th class="text-danger">일</th>
		 			<th>월</th>
		 			<th>화</th>
		 			<th>수</th>
		 			<th>목</th>
		 			<th>금</th>
		 			<th class="text-primary">토</th>
		 		</tr>
		 	</thead>
		 	<tbody>
	<!-- 
		1) 이번날 1일의 요일 firstDayYoil
		2) 요일 -> startBlank -> 일 0, 월 1, 화 2, .... 토6
		3) 이번달 마지막 날짜 endDay
		4) endBlank -> totalBlank
		5) td의 개수는 1 ~ totalBlank
				+
		6) 가계부 list
		7) 오늘 날짜
	 -->
		 		<tr>
			 		<%
			 			for(int i=1; i<=totalTd; i++) {
			 				if((i-startBlank) > 0 && (i-startBlank) <= endDay) {
			 					String c = "";
			 					if(i%7==0) {
			 						c = "text-primary";
			 					} else if(i%7==1) {
			 						c = "text-danger";
			 					}
			 		%>
			 						<td class="<%=c%>">
			 							<%=i-startBlank%>
			 							<a href="<%=request.getContextPath()%>/InsertCashbookController?y=<%=y%>&m=<%=m%>&d=<%=i-startBlank%>" class="btn btn-light text-info float-right">입력</a> <hr><br>
			 							<div>
			 									<%
			 										// 해당 날짜의 cashbook 목록 출력
													for(Map<String,Object> map : list) {
														if((Integer)map.get("cashDay") == (i-startBlank)) {
												%>
															<div>
																<a href="<%=request.getContextPath()%>/CashbookOneController?cashbookNo=<%=map.get("cashbookNo")%>" class="text-body" title="<%=map.get("fullMemo")%>">
																<%
																	if("수입".equals(map.get("kind"))) {
																%>
																			
																		<div class="text-success" style="display:inline;">[<%=map.get("kind")%>]</div>
																		+
																<%
																	} else {
																%>
																		<div class="text-danger" style="display:inline;">[<%=map.get("kind")%>]</div>
																		-
																<%
																	}
																%>
																		<%=map.get("cash")%>원
																		<%=map.get("shortMemo")%>...
																</a>
															</div>
												<%
														}
													}
												%>
			 							</div>
			 						</td>
		 						
			 		<%
			 				} else {
			 		%>
			 					<td>&nbsp;</td>
			 		<%
			 				}
			 				if(i<totalTd && i%7==0) {
			 		%>
			 					</tr><tr> <!-- 새로운 행을 추가시키기 위해 -->
			 		<%
			 				}
			 			}
			 		%>
		 		</tr>
		 	</tbody>
		 </table>
	<div class="text-center">
		<a href="<%=request.getContextPath()%>/CashbookListByMonthController?y=<%=y%>&m=<%=m-1%>" class="btn btn-outline-info">이전</a>
		<a href="<%=request.getContextPath()%>/CashbookListByMonthController?y=<%=y%>&m=<%=m+1%>" class="btn btn-outline-info">다음</a>
	</div>
	</div>
</body>
</html>