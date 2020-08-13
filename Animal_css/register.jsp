<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<!-- 부트스트랩 부분 -->
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <title>register</title>
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
  function registerCheckFunction(){
		var user_id = $('#user_id').val();
		$.ajax({
			type: 'POST',
			url: './ConfirmAction',
			dateType : "JSON",
			data: {user_id: user_id},
			success: function(result){
			if(result == 1){
					$('#checkMessage').html('사용할 수 있는 아이디입니다.');
					$('#checkType').attr('class', 'modal-content panel-success');
			}
			else{
				$('#checkMessage').html('사용할 수 없는 아이디입니다.');
				$('#checkType').attr('class', 'modal-content panel-warning');
			}
			$('#checkModal').modal("show");
			}
		})
	}
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
      <div class="card-header">회원가입</div>
      <div class="card-body">
        <form method="get" action="./Controller">
          <div class="form-group">
            <div class="form-row">
              <div class="col-md-6">
                <label>이름</label>
                <input class="form-control" type="text" id="user_name" name="user_name" placeholder="Name">
              </div>
            </div>
          </div>
          <div class="form-group">
            <div class="form-row">
              <div class="col-md-6">
               <label>아이디</label>
            <input class="form-control" type="text" id="user_id" name="user_id" placeholder="ID">
              </div>
              <div class="col-md-6">
              	 <br>
                 <button class="btn btn-primary" onclick="registerCheckFunction();" type="button">중복체크</button>
              </div>
            </div>
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
          <div class="form-group">
            <label>핸드폰 번호</label>
            <input class="form-control" type="text" id="user_phone" name="user_phone" placeholder="Phone">
          </div>
          <input type="hidden" name="action" value="register_comp">
          <input type="submit" class="btn btn-primary btn-block" value="회원가입">
        </form>
        <div class="text-center">
          <a class="d-block small mt-3" href="./Controller?action=login">로그인</a>
          <a class="d-block small" href="./Controller?action=forgetID">아이디 찾기</a>
          <a class="d-block small" href="./Controller?action=forgetPW">비밀번호 찾기</a>
        </div>
      </div>
    </div>
  </div>
  <!-- Bootstrap core JavaScript-->
  <jsp:include page="alert.jsp"/>
</body>

</html>