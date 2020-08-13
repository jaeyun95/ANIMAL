<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<!-- 부트스트랩 부분 -->
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  
  <title>비밀번호 확인</title>
  <!-- Bootstrap core CSS-->
 <link href="./Resources/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
  <link href="./Resources/vendor/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
  <link href="./Resources/vendor/datatables/dataTables.bootstrap4.css" rel="stylesheet">
  <link href="./Resources/css/sb-admin.css" rel="stylesheet">
  <script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
  <script src="js/bootstrap.js"></script>
  <link href="css/bootstrap.css" rel="stylesheet">
</head>
<body class="bg-dark">
  <div class="container">
    <div class="card card-register mx-auto mt-5">
      <div class="card-header">비밀번호 확인</div>
      <form action="./Controller" method="get">
      <div class="card-body">
          <div class="form-group">
           	 <input class="form-control" name="confirm_pw" type="password" placeholder="Password">
          </div>
          </div>
          <input type="hidden" name="action" value="${action}">
          <input type="submit" class="btn btn-primary btn-block" value="확인">
        </form>
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