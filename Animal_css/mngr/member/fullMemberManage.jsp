<!-- 
전체회원관리 페이지
작성자 : 정은진
수정자:
최종수정일 : 17.11.16
 -->
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width,initial-scale=1.0" />
  <title>회원관리 페이지</title>
  <!-- Bootstrap core CSS-->
  <link href="/Animal/Resources/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
  <link href="/Animal/Resources/vendor/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
  <link href="/Animal/Resources/vendor/datatables/dataTables.bootstrap4.css" rel="stylesheet">
  <link href="/Animal/Resources/css/sb-admin.css" rel="stylesheet">
  <%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
  <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
  <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
  <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
</head>
<jsp:include page="/mngr/mngrForm.jsp"/>

<div id="fullUserList" class="content-wrapper">
	<div class="container-fluid"><br>
		<ul>
			<li>가입한 회원수 : ${count}</li>
		</ul>
		<table class='table table-striped' style='border: 1px solid #dddddd' height='100'>
			<tr>
				<th>아이디</th>
				<th>이름</th>
				<th>가입 날짜</th>
				<th>활동 상태</th>
				<th>활동 정지</th>
				<th>스탭 임명</th>
			</tr>
			<c:forEach var="user" items="${userList}">
				<tr>
					<td>${user.user_id}</td>
					<td>${user.user_name}</td>
					<td>${user.user_date}</td>
					<c:choose>
						<c:when test="${user.user_available == '1'}">
							<td>활동</td>
						</c:when>
						<c:when test="${user.user_available == '2'}">
							<td>관리자</td>
						</c:when>
						<c:when test="${user.user_available >= '3'}">
							<td>활동(스탭)</td>
						</c:when>
						<c:otherwise>
							<td>정지</td>
						</c:otherwise>
					</c:choose>
					<c:choose>
						<c:when test="${user.user_available != '2'}">
							<td><a href="/Animal/Controller?action=MngrUserAction&mngrAction=banUser&user_id=${user.getUser_id()}">활동 정지</a></td>
							<c:if test="${my_available == '2' }">
								<td><a href="/Animal/Controller?action=MngrUserAction&mngrAction=appointStaff&user_id=${user.getUser_id()}">스탭 임명</a></td>
							</c:if>
						</c:when>
						<c:otherwise>
						<td>관리자</td><td>관리자</td> 
						</c:otherwise>
					</c:choose>
				</tr>
			</c:forEach>
		</table>
	</div>
</div>