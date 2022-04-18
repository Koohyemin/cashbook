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
	<div class="container">
	<%
		int y = (Integer)request.getAttribute("y");
		int m = (Integer)request.getAttribute("m");
	%>
	<h2 class="text-center"><%=y%>년 <%=m%>월 가계부</h2>
	<table class="table table-hover">
		<thead class="text-center bg-info text-light">
			<tr>
				<th>일</th>
				<th>수입/지출</th>
				<th>금액</th>
			</tr>
		</thead>
		<tbody class="text-center">
		<%
			List<Map<String, Object>> list = (List<Map<String, Object>>)request.getAttribute("list");
			for(Map map : list) {
		%>
				<tr>
					<td><%=map.get("cashDay")%></td>
					<td <%if(map.get("kind").equals("수입")) {%>class="text-primary"<%} else {%>class="text-danger"<%}%>>
						<%=map.get("kind")%> <!-- 수입: 파랑색, 지출: 빨간색 -->
					</td>
					<td><%=map.get("cash")%></td>
				</tr>
		<%
			}
		%>
		</tbody>
	</table>
	<div class="text-center">
		<a href="<%=request.getContextPath()%>/CashbookListByMonthController?y=<%=y%>&m=<%=m-1%>" class="btn btn-outline-info">이전</a>
		<a href="<%=request.getContextPath()%>/CashbookListByMonthController?y=<%=y%>&m=<%=m+1%>" class="btn btn-outline-info">다음</a>
	</div>
	</div>
</body>
</html>