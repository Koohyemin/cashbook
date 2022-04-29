<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>InsertMember</title>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css">
</head>
<body  class="table-info">
	<div class="container" style="margin-top:8%;">
		<br>
		<h1 class="text-center text-secondary">회원가입</h1>
		<%
			String msg = (String)request.getAttribute("msg");	
			if(msg == null) {
				msg = "";
			}
		%>
		<div class="text-danger float-right"><%=msg%></div>
		<a href="<%=request.getContextPath()%>/LoginController" class="btn btn-link">로그인</a>
		<form method="post" action="<%=request.getContextPath()%>/InsertMemberController">
			<table class="table">
				<tr>
					<th class="table-info text-center">ID</th>
					<td>
						<input type="text" name="memberId" class="form-control">
					</td>
				</tr>
				<tr>
					<th class="table-info text-center">비밀번호</th>
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
				<tr>
					<th class="table-info text-center">이름</th>
					<td>
						<input type="text" name="memberName" class="form-control">
					</td>
				</tr>
				<tr>
					<th class="table-info text-center">닉네임</th>
					<td>
						<input type="text" name="nickName" class="form-control">
					</td>
				</tr>
			</table>
			<button type="submit" class="btn btn-info btn float-right">회원가입</button>
		</form>
	</div>
</body>
</html>