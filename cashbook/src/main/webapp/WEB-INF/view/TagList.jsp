<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import = "java.util.*" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>TagList</title>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css">
</head>
<body>
<%
	List<Map<String,Object>> list = (List<Map<String,Object>>)request.getAttribute("list");
%>
<div class="container">
	<h1 class="text-center">해시태그</h1>
	<div>
		<a href="<%=request.getContextPath()%>/CashbookListByMonthController" class="btn btn-outline-info">가계부 돌아가기</a>
		<a href="<%=request.getContextPath()%>/TagKindSearchController" class="btn btn-outline-info">수입/지출별 검색</a>
		<a href="<%=request.getContextPath()%>/TagDateSearchController" class="btn btn-outline-info">날짜별 검색</a>
	</div> <br>
	<table class="table text-center">
		<thead class="table-info">
			<tr>
				<th>순위</th>
				<th>해시태그</th>
				<th>개수</th>
			</tr>
		</thead>
		<tbody>
			<%
				for(Map<String,Object> map : list) {
			%>
					<tr>
						<td><%=map.get("rank")%></td>
						<td><a href="<%=request.getContextPath()%>/TagOneController?tag=<%=map.get("tag")%>&tagCount=<%=map.get("cnt")%>"><%=map.get("tag")%></a></td>
						<td><%=map.get("cnt")%></td>
					</tr>
			<%
				}
			%>
		</tbody>
	</table>
</div>
</body>
</html>