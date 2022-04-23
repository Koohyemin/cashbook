<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Login</title>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css">
</head>
<body>
<div class="container">
	<br>
	<h1 class="text-center text-secondary">로그인</h1>
	<form method="post" action="<%=request.getContextPath()%>/LoginController" class="was-validated">
		<table class="table text-center">
			<tr>
				<th class="table-info">ID</th>
				<td>
					<input type="text" name="memberId" class="form-control" placeholder="Enter ID" required>
				</td>
			</tr>
			<tr>
				<th class="table-info">비밀번호</th>
				<td>
					<input type="password" name="memberPw" class="form-control" placeholder="Enter password" required>
				</td>
			</tr>
		</table>
		<div class="btn-group float-right">
			<a href="<%=request.getContextPath()%>/InsertMemberController" class="btn btn-success">회원가입</a>
			<button type="submit" class="btn btn-info btn">로그인</button>
		</div>
	</form>
</div>
</body>
</html>