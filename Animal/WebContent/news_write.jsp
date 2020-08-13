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
      <div class="card-header">글쓰기</div>
      <form method="post" action="./Controller?action=news_Write" enctype="multipart/form-data">
      <div class="card-body">
          <div class="form-group">
            <label>글제목</label>
            <input class="form-control" type="text" name="board_title" placeholder="Title">
          </div>
          <div class="form-group">
          	<select class="form-control" id="news_visible" name="news_visible">
            <option>범위를 선택해 주세요.</option>
            <option value="0">전체공개</option>
            <option value="1">나만보기</option>
            </select>
          </div>
          <div class="form-group">
            <label>내용</label>
           	<textarea rows="20" cols="20" class="form-control" name="board_content"></textarea>
          </div>
          <div class="form-group">
            <label>사진</label>
           	파일: <input type="file" class="form-control" name="file1"><br>
			파일: <input type="file" class="form-control" name="file2"><br>
			파일: <input type="file" class="form-control" name="file3"><br>
          </div>
          </div>
          <input type="submit" class="btn btn-primary btn-block" value="글쓰기">
        </form>
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