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
  <meta name="description" content="">
  <meta name="author" content="">
  <title>login</title>
  <!-- Bootstrap core CSS-->
  <link href="./Resources/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
  <link href="./Resources/vendor/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
  <link href="./Resources/vendor/datatables/dataTables.bootstrap4.css" rel="stylesheet">
  <link href="./Resources/css/sb-admin.css" rel="stylesheet">
</head>
<body class="bg-dark">
  <div class="container">
    <div class="card card-register mx-auto mt-5">
      <div class="card-header">신고</div>
      <form method="post" action="./Controller">
      <div class="card-body">
          <div class="form-group">
            <label>신고 사유</label>
           	<textarea rows="5" cols="5" class="form-control" name="declaration_content"></textarea>
          </div>
        </div>
          <input type="hidden" name="action" value="declaration_comp">
          <input type="hidden" name="news_num" value="<%=request.getAttribute("news_num") %>">
          <input type="hidden" name="board_num" value="<%=request.getAttribute("board_num")%>">
          <input type="submit" class="btn btn-primary btn-block" value="신고">
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