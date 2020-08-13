<!-- 
신고관리 페이지
작성자 : 정은진
수정자:
최종수정일 : 17.11.16
 -->
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width,initial-scale=1.0" />
  <title>신고 관리 페이지</title>
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
<jsp:include page="/mngr/mngrForm.jsp" />
<div id="declarationManage" class="content-wrapper">
	<div class="container-fluid"><br>
		<ul>
			<li>신고글 수 : ${count}</li>
		</ul>
		<table class='table table-striped' style='border: 1px solid #dddddd' height='100'>
			<tr>
				<th>게시글 번호</th>
				<th>사용자 아이디</th>
				<th>신고 내용</th>
				<th>게시글 삭제</th>
			</tr>
			<c:forEach var="declar" items="${declarList}">
				<tr>
					<td>${declar.board_num}</td>
					<td>${declar.user_id}</td>
					<td>${declar.declaration_content}</td>
					<td><a href="/Animal/Controller?action=MngrDeclarAction&mngrAction=boardDelete&board_num=${declar.board_num}">게시글 삭제</a></td>
				</tr>
			</c:forEach>
		</table>
	</div>
</div>
