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
      <div class="card-header">글수정</div>
      <form method="post" action="./Controller?action=news_Update" enctype="multipart/form-data">
      <div class="card-body">
      <% BoardDataBean board = BoardDBBean.getinstance().news_getboard(Integer.parseInt(request.getParameter("board_num"))); %>
          <div class="form-group">
            <label>글제목</label>
            <input class="form-control" type="text" name="board_title" placeholder="Title" value="<%=board.getBoard_title()%>">
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
           	<textarea rows="20" cols="20" class="form-control" name="board_content"><%=board.getBoard_content()%></textarea>
          </div>
          <div class="form-group">
          <center>
          	<label>업로드된 파일 목록</label><br>
          	<%
          		String image = board.getBoard_image();
          		String[] images = image.split("/");
          		for(int i = 0; i < images.length; i++){
          	%>
          		<img src="<%= board.getBoard_path() %>\<%= images[i] %>" height= 100px width=100px>&nbsp;<input type="checkbox" name="oldfile" value="<%=i%>"><br>
          	<%}%>
          	</center>
          </div>
          <div class="form-group">
            <label>최대 업로드 파일 수: 3개</label><br>
           	파일: <input type="file" class="form-control" name="file1"><br>
			파일: <input type="file" class="form-control" name="file2"><br>
			파일: <input type="file" class="form-control" name="file3"><br>
          </div>
          </div>
          <input type="hidden" name="board_num" value="<%=board.getBoard_num() %>">
          <input type="submit" class="btn btn-primary btn-block" value="글쓰기">
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