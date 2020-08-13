<!-- 
카테고리관리 페이지
작성자 : 정은진
수정자:
최종수정일 : 17.11.16
 -->
 <head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width,initial-scale=1.0" />
  <title>카테고리 관리</title>
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
<div id="categoryManage" class="content-wrapper">
	<div class="container-fluid"><br>
		<ul>
			<li>카테고리 수 : ${count}</li>
		</ul>
		 <form method="post" action="/Animal/Controller?action=MngrCategoryAction&mngrAction=newCategory">
		 	<input type="text" id="cate_name" name="cate_name" placeholder="카테고리명">
		 	<input type="hidden" name="action" value="newCategory">
		 	<input type="submit" class="btn btn-default" value="카테고리 생성">
		 </form>
		<table class='table table-striped' style='border: 1px solid #dddddd' height='100'>
			<tr>
				<th>카테고리 이름</th>
				<th>카테고리 생성일</th>
				<th>카테고리 삭제</th>
			</tr>
			<c:forEach var="category" items="${categoryList}">
				<tr>
					<td>${category.cate_name}</td>
					<td>${category.cate_date}</td>
					<td><a href="/Animal/Controller?action=MngrCategoryAction&mngrAction=deleteCategory&cate_num=${category.cate_num}">카테고리 삭제</a></td>
				</tr>
			</c:forEach>
		</table>
	</div>
</div>
