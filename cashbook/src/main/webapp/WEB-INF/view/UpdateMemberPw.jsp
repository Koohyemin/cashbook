<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>UpdateMemberPw</title>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css">
</head>
<body>
<div class="container">
	<br>
	<h1 class="text-center">비밀번호 수정</h1>
	<div style="float:right">
		<a href="<%=request.getContextPath()%>/SelectMemberOneController?memberId=<%=session.getAttribute("sessionMemberId")%>">[<%=session.getAttribute("sessionMemberId")%>]</a>님 반갑습니다.
		<a href="<%=request.getContextPath()%>/LogoutController" class="btn btn-outline-info btn-sm">로그아웃</a>
	</div>
	<a href="<%=request.getContextPath()%>/CashbookListByMonthController" class="btn btn-link text-info">가계부 돌아가기</a>
	<form method="post" action="<%=request.getContextPath()%>/UpdateMemberPwController">
		<table class="table">
			<tr>
				<th class="table-info text-center">수정 비밀번호</th>
				<td>
					<input type="password" name="memberPw" class="form-control">
				</td>
			</tr>
			<tr>
				<th class="table-info text-center">비밀번호 확인</th>
				<td>
					<input type="password" name="memberPwCheck" class="form-control">
				</td>
			</tr>
		</table>
		<div>
			<button type="submit" class="btn btn-info float-right">수정</button>
		</div>
		<div class="was-validated">
			<input type="password" name="originalCheckPw" placeholder="수정을 위해 기존비밀번호를 입력해주세요." class="form-control col-sm-4 float-right" required>
		</div>
	</form>
</div>
</body>
</html>