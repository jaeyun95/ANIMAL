
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="animal.bean.*" %>
<!DOCTYPE html>
<html>
<!-- 부트스트랩 부분 -->
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <title>정보 수정</title>
  <!-- Bootstrap core CSS-->
  <link href="./Resources/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
  <link href="./Resources/vendor/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
  <link href="./Resources/vendor/datatables/dataTables.bootstrap4.css" rel="stylesheet">
  <link href="./Resources/css/sb-admin.css" rel="stylesheet">
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
  <script src="js/bootstrap.js"></script>
  <link href="css/bootstrap.css" rel="stylesheet">
   <script type="text/javascript">
  function passwordCheckFunction(){
			var user_pw = $('#user_pw').val();
			var check_passwd = $('#check_passwd').val();
			if(user_pw != check_passwd){
				$('#passwordCheckMessage').html('비밀번호가 서로 일치하지 않습니다.');
			}else{
				$('#passwordCheckMessage').html('');
			}
		}
  </script>
</head>
<body class="bg-dark">
  <div class="container">
    <div class="card card-register mx-auto mt-5">
      <div class="card-header">아래 정보를 입력해주세요</div>
      <form method="post" action="./Controller?action=update_pw">
      <div class="card-body">
          <div class="form-group">
            <label>이름</label><input class="form-control" id="user_name" name="user_name" type="text"  placeholder="Name">
          </div>
          <div class="form-group">
            <label>아이디</label><input class="form-control" id="user_id" name="user_id" type="text"  placeholder="ID">
          </div>
          <div class="form-group">
            <div class="form-row">
              <div class="col-md-6">
                <label>비밀번호</label>
                <input class="form-control" id="user_pw" name="user_pw" type="password" onkeyup="passwordCheckFunction();" placeholder="Password">
              </div>
              <div class="col-md-6">
                <label>비밀번호 확인</label>
                <input class="form-control" id="check_passwd" name="check_passwd" type="password" onkeyup="passwordCheckFunction();" placeholder="Confirm password">
              </div>
            </div>
          </div>
          <div class="form-group">
            <h5 align="center" style="color: red;" id="passwordCheckMessage"></h5>
          </div>
          </div>
          <input type="submit" class="btn btn-primary btn-block" value="수정">
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