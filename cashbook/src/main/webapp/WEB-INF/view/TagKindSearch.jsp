<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import = "java.util.*" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>TagKindSearch</title>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css">
</head>
<body>
<div class="container">
	<h1 class="text-center">수입/지출별 검색</h1>
	<a href="<%=request.getContextPath()%>/TagController" class="btn btn-outline-info">tags</a><br><br>
	<form method="get" action="<%=request.getContextPath()%>/TagKindSearchController">
		<table class="table text-center">
			<tr>
				<td>선택</td>
				<td>
					<input type="radio" name="kind" value="수입"> 수입
					<input type="radio" name="kind" value="지출"> 지출
				</td>
				<td>
					<button type="submit" class="btn btn-info">검색</button>
				</td>
			</tr>
		</table>
	</form>
	<%
		if(request.getAttribute("kind")!=null) {
			String kind = (String)request.getAttribute("kind");
			List<Map<String, Object>> list = (List<Map<String, Object>>)request.getAttribute("list");
	%>
			<h2 class="text-center"><%=kind%></h2>
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
						for(Map<String, Object> map : list) {
					%>
							<tr>
								<td><%=map.get("rank")%></td>
								<td><%=map.get("tag")%></td>
								<td><%=map.get("cnt")%></td>
							</tr>
					<%
						}
					%>
				</tbody>
			</table>
	<%
		}
	%>
</div>
</body>
</html>