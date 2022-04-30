<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>InsertCashbookForm</title>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css">
</head>
<body>
<div class="container">
	<br>
	<h2 class="text-center">가계부 입력</h2>
	<div style="float:right">
		<a href="<%=request.getContextPath()%>/SelectMemberOneController?memberId=<%=session.getAttribute("sessionMemberId")%>">[<%=session.getAttribute("sessionMemberId")%>]</a>님 반갑습니다.
		<a href="<%=request.getContextPath()%>/LogoutController" class="btn btn-outline-info btn-sm">로그아웃</a>
	</div>
	<a href="<%=request.getContextPath()%>/CashbookListByMonthController" class="btn btn-link">가계부</a>
	<%
		String msg = "";
		if((String)request.getAttribute("msg")!=null) {
			msg = request.getParameter("msg");
		}
	%>
	<div class="text-danger"><%=msg%></div>
	<form method="post" action="<%=request.getContextPath()%>/InsertCashbookController">
		<table class="table">
			<tr>
				<th class="table-info text-center">날짜</th>
				<td>
					<input type="text" name="cashDate" value="<%=(String)request.getAttribute("cashDate")%>" readonly="readonly" class="form-control">
				</td>
			</tr>
			<tr>
				<th class="table-info text-center">수입/지출</th>
				<td>
					<input type="radio" name="kind" value="수입" checked="checked"> 수입
					<input type="radio" name="kind" value="지출"> 지출
				</td>
			</tr>
			<tr>
				<th class="table-info text-center">금액</th>
				<td>
					<input type="number" name="cash" class="form-control" placeholder="₩">
				</td>
			</tr>
			<tr>
				<th class="table-info text-center">메모</th>
				<td>
					<textarea rows="4" name=memo cols="50" class="form-control" placeholder="메모와 함께 #해시태그 방식으로 본인만의 해시태그를 기록해보세요!"></textarea>
				</td>
			</tr>
		</table>
		<button type="submit" class="btn btn-info">등록</button>
	</form>
</div>
</body>
</html>