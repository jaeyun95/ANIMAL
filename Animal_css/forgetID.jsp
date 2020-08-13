<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
      <div class="card-header">아이디 찾기</div>
      <div class="card-body">
        <form method="post" action="/Animal/Controller?action=showID">
          <div class="form-group">
            <label>이름</label>
            <input class="form-control" id="user_name" name="user_name" type="text" placeholder="Name">
          </div>
          <div class="form-group">
            <label>전화번호</label>
            <input class="form-control" id="user_phone" name="user_phone" type="text" placeholder="Phone">
          </div>
          <!--<input type="hidden" name="action" value="login_comp">-->
          <input type="submit" class="btn btn-primary btn-block" value="찾기">
        </form>
        <div class="text-center">
          <a class="d-block small mt-3" href="./Controller?action=login">로그인</a>
          <a class="d-block small" href="./Controller?action=register">회원 가입</a>
          <a class="d-block small" href="./Controller?action=forgetPW">비밀번호 찾기</a>
        </div>
      </div>
    </div>
  </div>
  <!-- Bootstrap core JavaScript-->
  <script src="vendor/jquery/jquery.min.js"></script>
  <script src="vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
  <!-- Core plugin JavaScript-->
  <script src="vendor/jquery-easing/jquery.easing.min.js"></script>
  <jsp:include page="alert.jsp"/>
  
</body>

</html>
