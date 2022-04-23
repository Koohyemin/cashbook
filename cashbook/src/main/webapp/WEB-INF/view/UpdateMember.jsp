<%@page import="vo.Member"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Update Member</title>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css">
</head>
<body>
<%
	Member member = (Member)request.getAttribute("member");
%>
<div class="container">
	<br>
	<h1 class="text-center">회원정보 수정</h1>
	<a href="<%=request.getContextPath()%>/SelectMemberOneController?memberId=<%=member.getMemberId()%>" class="btn btn-link text-info">정보 상세보기</a>
	<form method="post" action="<%=request.getContextPath()%>/UpdateMemberController">
		<table class="table">
			<tr>
				<th class="table-info text-center">ID</th>
				<td>
					<input type="text" name="memberId" value="<%=member.getMemberId()%>" readonly="readonly" class="form-control">
				</td>
			</tr>
			<tr>
				<th class="table-info text-center">이름</th>
				<td>
					<input type="text" name="memberName" value="<%=member.getMemberName()%>" class="form-control">
				</td>
			</tr>
			<tr>
				<th class="table-info text-center">닉네임</th>
				<td>
					<input type="text" name="nickName" value="<%=member.getNickName()%>" class="form-control">
				</td>
			</tr>
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
		<div>
			<input type="password" name="originalCheckPw" placeholder="수정을 위해 기존비밀번호를 입력해주세요." class="form-control col-sm-4 float-right">
		</div>
	</form>
</div>
</body>
</html>