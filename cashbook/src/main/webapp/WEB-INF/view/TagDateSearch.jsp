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
	<h1 class="text-center">날짜별 검색</h1>
	<a href="<%=request.getContextPath()%>/TagController" class="btn btn-outline-info">tags</a><br><br>
	<form method="get" action="<%=request.getContextPath()%>/TagDateSearchController">
		<table class="table text-center">
			<tr>
				<td>날짜</td>
				<td>
					<input type="date" name="startDate" class="form-control col-sm-10">
				</td>
				<td>
					~
				</td>
				<td>
					<input type="date" name="endDate" class="form-control col-sm-10">
				</td>
				<td>
					<button type="submit" class="btn btn-info">검색</button>
				</td>
			</tr>
		</table>
	</form>
	<%
		if(null != request.getAttribute("startDate")) {
			String startDate = (String)request.getAttribute("startDate");
			String endDate = (String)request.getAttribute("endDate");
			List<Map<String, Object>> list = (List<Map<String, Object>>)request.getAttribute("list");
			if(list.size()==0) {
	%>
				<h2 class="text-center"><%=startDate%>~<%=endDate%></h2>
				<h4 class="text-danger text-center">등록된 해시태그가 존재하지 않습니다.</h4>
	<%
			} else {
	%>
				<h2 class="text-center"><%=startDate%>~<%=endDate%></h2>
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
		}
	%>
</div>
</body>
</html>