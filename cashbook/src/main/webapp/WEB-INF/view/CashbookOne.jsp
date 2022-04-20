<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import = "vo.*" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>CashbookOne</title>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css">
</head>
<body>
<%
	Cashbook c = (Cashbook)request.getAttribute("cashbook");
	int cashbookNo = (Integer)request.getAttribute("cashbookNo");
%>
	<div class="container">
	<h2 class="text-center">상세보기</h2>
	<a href="<%=request.getContextPath()%>/CashbookListByMonthController" class="btn btn-outline-info">가계부</a>
		<table class="table table-bordered text-center">
				<tr>
					<th class="table-info">날짜</th>
					<td><%=c.getCashDate()%></td>
				</tr>
				<tr>
					<th class="table-info">수입/지출</th>
					<td><%=c.getKind()%></td>
				</tr>
				<tr>
					<th class="table-info">수입/지출</th>
					<td><%=c.getCash()%></td>
				</tr>
				<tr>
					<th class="table-info">메모</th>
					<td><%=c.getMemo()%></td>
				</tr>
				<tr>
					<th class="table-info">등록일</th>
					<td><%=c.getCreateDate()%></td>
				</tr>
				<tr>
					<th class="table-info">수정일</th>
					<td><%=c.getUpdateDate()%></td>
				</tr>
		</table>
	<a href="<%=request.getContextPath()%>/UpdateCashbookController?cashbookNo=<%=cashbookNo%>" class="btn btn-outline-info">수정</a>
	<a href="<%=request.getContextPath()%>/DeleteCashbookController?cashbookNo=<%=cashbookNo%>" class="btn btn-outline-info">삭제</a>
	</div>
</body>
</html>