<%@page import="vo.Member"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>MemberOne</title>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css">
</head>
<body>
<div class="container">
<br>
<%
	Member member = (Member)request.getAttribute("member");
%>
	<h1 class="text-center">회원정보 상세보기</h1>
	<div style="float:right">
		<span class="text-primary">[<%=session.getAttribute("sessionMemberId")%>]</span>님 반갑습니다.
		<a href="<%=request.getContextPath()%>/LogoutController" class="btn btn-outline-info btn-sm">로그아웃</a>
	</div>
	<a href="<%=request.getContextPath()%>/CashbookListByMonthController" class="btn btn-link text-info">가계부 돌아가기</a>
	<table class="table">
		<tr>	
			<th class="table-info text-center">ID</th>
			<td><%=member.getMemberId()%></td>
		</tr>
		<tr>	
			<th class="table-info text-center">이름</th>
			<td><%=member.getMemberName()%></td>
		</tr>
		<tr>	
			<th class="table-info text-center">닉네임</th>
			<td><%=member.getNickName()%></td>
		</tr>
		<tr>	
			<th class="table-info text-center">가입일자</th>
			<td><%=member.getCreateDate()%></td>
		</tr>
	</table>
	<div class="float-right">
		<a href="<%=request.getContextPath()%>/DeleteMemberController?memberId=<%=member.getMemberId()%>" class="btn btn-secondary">회원탈퇴</a>
		<a href="<%=request.getContextPath()%>/UpdateMemberController?memberId=<%=member.getMemberId()%>" class="btn btn-info">정보수정</a>
		<a href="<%=request.getContextPath()%>/UpdateMemberPwController?memberId=<%=member.getMemberId()%>" class="btn btn-info">비밀번호 수정</a>
	</div>
</div>
</body>
</html>