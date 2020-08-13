<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="animal.bean.UserDBBean" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>    
<!DOCTYPE html>
<html>
<!-- 부트스트랩 부분 -->
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <meta name="description" content="">
  <meta name="author" content="">
  <title>Animal</title>
  <link href="./Resources/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
  <link href="./Resources/vendor/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
  <link href="./Resources/vendor/datatables/dataTables.bootstrap4.css" rel="stylesheet">
  <link href="./Resources/css/sb-admin.css" rel="stylesheet">
  <link href="css/bootstrap.css" rel="stylesheet">
</head>
<body class="bg-dark">
  <div class="container">
    <div class="card card-login mx-auto mt-5">
      <div class="card-header">비밀번호 확인</div>
      	<c:if test="${user_id ne null }">
			<center>
	      		<div class="card-body">
	          		<div class="form-group">
						${user_id}님의 비밀번호가 변경되었습니다.
					</div>
				</div>
			</center>
	      <div class="text-center">
	          <a class="btn btn-primary btn-block" href="./Controller?action=login">로그인</a>
	       </div>	
      </c:if>
     </div> 			
      <c:if test="${user_id eq null }">
			<div class="form-group">
				<br/><br/><br/>
				<center><h3>가입정보가 없습니다.</h3></center>				
			</div><br/><br/><br/><br/>
			<center>		
			   	<input type="button" value="아이디찾기" class="btn btn-primary btn-block" onclick="location.href='./Controller?action=forgetID'">     											
       			<input type="button" value="회원가입 하기" class="btn btn-primary btn-block" onclick="location.href='./Controller?action=join'">
        		<input type="button" value="처음으로" class="btn btn-primary btn-block" onclick="location.href='./Controller?action=forgetPW'">     							
			</center>
		</c:if>
      </div>
  <!-- Bootstrap core JavaScript-->
  <script src="vendor/jquery/jquery.min.js"></script>
  <script src="vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
  <!-- Core plugin JavaScript-->
  <script src="vendor/jquery-easing/jquery.easing.min.js"></script>
  <jsp:include page="alert.jsp"/>
  
</body>

</html>
