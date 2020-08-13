<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="animal.bean.*"%>
<!DOCTYPE html>
<html>
<head>
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <meta name="description" content="">
  <meta name="author" content="">
  <!-- Bootstrap core CSS-->
   <link href="./Resources/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
  <link href="./Resources/vendor/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
  <link href="./Resources/vendor/datatables/dataTables.bootstrap4.css" rel="stylesheet">
  <link href="./Resources/css/sb-admin.css" rel="stylesheet">
	<%
		int comment_num = Integer.parseInt(request.getParameter("comment_num"));
		int board_num = Integer.parseInt(request.getParameter("board_num"));
		CommentDBBean comment = CommentDBBean.getinstance();
		CommentDataBean commentdt = comment.getComment(comment_num);
	%>
	<body class="bg-dark">
		<div class="container">
		<div class="card mb-3">
			<div class="card-header">
				<i class="fa fa-table"></i>글 수정하기
			</div>
			<div class="card-body">
				<div class="table-responsive">
					<form method="post"
						action="/Animal/Controller?action=updateComment">
						<h3 style="text-align: center;">댓글 수정</h3>
						<div class="form-group">
							<textarea class="form-control" name="comment_content"
								style="height: 100px;"><%=commentdt.getComment_content()%></textarea>
						</div>
						<input type="hidden" name="board_num" value=<%=commentdt.getBoard_num()%>>
						<input type="hidden" name="comment_num" value=<%=commentdt.getComment_num()%>>
						<input type="submit" class="btn btn-primary form-control"
							value="수정">
					</form>
				</div>
				<div class="col-lg-4"></div>
			</div>
		</div>
	</div>
</body>
</html>