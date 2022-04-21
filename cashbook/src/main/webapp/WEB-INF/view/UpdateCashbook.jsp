<%@page import="vo.Cashbook"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>UpdateCashbook</title>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css">
</head>
<body>
<%
	Cashbook c = (Cashbook)request.getAttribute("cashbook");
%>
<div class="container">
	<h2 class="text-center">가계부 수정</h2>
	<a href="<%=request.getContextPath()%>/CashbookOneController?cashbookNo=<%=c.getCashbookNo()%>" class="btn btn-link">이전으로</a>
	<form method="post" action="<%=request.getContextPath()%>/UpdateCashbookController">
		<table class="table">
			<tr>
				<th class="table-info text-center">번호</th>
				<td>
					<input type="number" name="cashbookNo" value="<%=c.getCashbookNo()%>" readonly="readonly" class="form-control">
				</td>
			</tr>
			<tr>
				<th class="table-info text-center">날짜</th>
				<td>
					<input type="text" name="cashDate" value="<%=c.getCashDate()%>" readonly="readonly" class="form-control">
				</td>
			</tr>
			<tr>
				<th class="table-info text-center">수입/지출</th>
				<td>
					<input type="radio" name="kind" value="수입" <%if(c.getKind().equals("수입")) {%>checked="checked"<%}%>> 수입
					<input type="radio" name="kind" value="지출" <%if(c.getKind().equals("지출")) {%>checked="checked"<%}%>> 지출
				</td>
			</tr>
			<tr>
				<th class="table-info text-center">금액</th>
				<td>
					<input type="number" name="cash"  value="<%=c.getCash()%>" class="form-control">
				</td>
			</tr>
			<tr>
				<th class="table-info text-center">메모</th>
				<td>
					<textarea rows="4" name=memo cols="50" class="form-control"><%=c.getMemo()%></textarea>
				</td>
			</tr>
		</table>
		<button type="submit" class="btn btn-info">수정</button>
	</form>
</div>
</body>
</html>