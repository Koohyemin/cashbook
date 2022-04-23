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
	<br>
	<h1 class="text-center">해시태그</h1>
	<div style="float:right">
		<a href="<%=request.getContextPath()%>/SelectMemberOneController?memberId=<%=session.getAttribute("sessionMemberId")%>">[<%=session.getAttribute("sessionMemberId")%>]</a>님 반갑습니다.
		<a href="<%=request.getContextPath()%>/LogoutController" class="btn btn-outline-info btn-sm">로그아웃</a>
	</div> <br><br>
	<div style="float:left">
		 <a href="<%=request.getContextPath()%>/CashbookListByMonthController" class="btn btn-link text-info">가계부 돌아가기</a>
	</div>
	<!-- 수입/지출 검색 -->
	<form method="get" action="<%=request.getContextPath()%>/TagKindSearchController" style="float:left">
		<select name="kind" onchange="this.form.submit()" class="custom-select col-lg-12">
			<option>수입/지출 선택</option>
			<option value="수입">수입</option>
			<option value="지출">지출</option>
		</select>
	</form>
	<!-- 기간별 해시태그 검색 -->
	<form method="get" action="<%=request.getContextPath()%>/TagDateSearchController">
		<div style="float:right;">
			<button type="submit" class="btn btn-outline-info">검색</button>
		</div>
		<div class="input-group-prepend" style="float:right;">
			<input type=date name="startDate" class="form-control"> ~
			<input type=date name="endDate" class="form-control"> &nbsp;
		</div>
	</form>
	<br><br>
	<!-- 해시태그 목록 -->
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