<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Login</title>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css">
</head>
<body class="table-info">
<div class="container" style="margin-top:10%;">
	<br>
	<h1 class="text-center text-secondary">로그인</h1>
	<%
		String msg = (String)request.getAttribute("msg");	
		if(msg == null) {
			msg = "";
		}
	%>
	<div class="text-danger"><%=msg%></div>
	<form method="post" action="<%=request.getContextPath()%>/LoginController">
		<table class="table text-center">
			<tr>
				<th>ID</th>
				<td>
					<input type="text" name="memberId" class="form-control">
				</td>
			</tr>
			<tr>
				<th>비밀번호</th>
				<td>
					<input type="password" name="memberPw" class="form-control">
				</td>
			</tr>
		</table>
		<div class="btn-group float-right" style="margin-left:5px;">
			<button type="submit" class="btn btn-info btn">로그인</button>
		</div>
		<div class="btn-group float-right">
			<a href="<%=request.getContextPath()%>/InsertMemberController" class="btn btn-success">회원가입</a>
		</div>
	</form>
</div>
</body>
</html>