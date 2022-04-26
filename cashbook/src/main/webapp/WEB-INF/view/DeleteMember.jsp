<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Delete Member</title>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css">
</head>
<body>
<%
	String memeberId = (String)request.getAttribute("memberId");
%>
<div class="container">
	<br>
	<h1 class="text-center">회원 탈퇴</h1>
	<div style="float:right">
		<a href="<%=request.getContextPath()%>/SelectMemberOneController?memberId=<%=session.getAttribute("sessionMemberId")%>">[<%=session.getAttribute("sessionMemberId")%>]</a>님 반갑습니다.
		<a href="<%=request.getContextPath()%>/LogoutController" class="btn btn-outline-info btn-sm">로그아웃</a>
	</div>
	<a href="<%=request.getContextPath()%>/SelectMemberOneController?memberId=<%=memeberId%>" class="btn btn-link text-info">정보 상세보기</a>
	<form method="post" action="<%=request.getContextPath()%>/DeleteMemberController" class="was-validated">
		<table class="table">
			<tr>
				<th class="table-info text-center">ID</th>
				<td>
					<input type="text" name="memberId" value="<%=memeberId%>" readonly="readonly" class="form-control">
				</td>
			</tr>
			<tr>
				<th class="table-info text-center">비밀번호</th>
				<td>
					<input type="password" name="memberPw" class="form-control" required>
				</td>
			</tr>
		</table>
		<div class="text-right">
			<span class="text-danger">회원 탈퇴시 고객님의 데이터 복구가 불가능하니 신중히 진행하세요</span>
			&nbsp;<button class="btn btn-info btn float-right">회원 탈퇴</button>
		</div>
	</form>
</div>	
</body>
</html>