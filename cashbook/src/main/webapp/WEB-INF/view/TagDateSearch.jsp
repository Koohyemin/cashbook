<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import = "java.util.*" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>TagDateSearch</title>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css">
</head>
<body>
<div class="container">
<%
	String startDate = (String)request.getAttribute("startDate");
	String endDate = (String)request.getAttribute("endDate");
	List<Map<String, Object>> list = (List<Map<String, Object>>)request.getAttribute("list");
%>
	<br>
	<h1 class="text-center"><%=startDate%>~<%=endDate%> 해시태그</h1>
	<a href="<%=request.getContextPath()%>/TagController" class="btn btn-outline-info">tags</a><br><br>
	<%
			if(list.size()==0) {
	%>
				<h4 class="text-danger text-center">등록된 해시태그가 존재하지 않습니다.</h4>
	<%
			} else {
	%>
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
									<td><%=map.get("ranking")%></td>
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