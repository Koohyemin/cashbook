<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import = "java.util.*" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>TagOne</title>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css">
</head>
<body>
<%
	int tagCount = (Integer)request.getAttribute("tagCount");
	List<Map<String, Object>> list = (List<Map<String, Object>>)request.getAttribute("list");
%>
<div class="container">
	<h1 class="text-center">카테고리별 상세보기</h1>
	<h3 class="text-center text-secondary">total : <%=tagCount%>개</h3>
	<a href="<%=request.getContextPath()%>/TagController" class="btn btn-outline-info">tags</a>
	<table class="table text-center">
		<thead class="table-info">
			<tr>
				<th>해시태그</th>
				<th>날짜</th>
				<th>수입/지출</th>
				<th>메모</th>
			</tr>
		</thead>
		<tbody>
			<%
				for(Map<String, Object> map : list) {
			%>
					<tr>
						<td><%=map.get("tag")%></td>
						<td><%=map.get("cashDate")%></td>
						<td><%=map.get("kind")%></td>
						<td><%=map.get("memo")%></td>
					</tr>
			<%
				}
			%>
		</tbody>
	</table>
</div>
</body>
</html>