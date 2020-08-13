<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.io.PrintWriter"%>
<%@ page import="animal.bean.*"%>
<%@ page import="java.util.ArrayList"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="menutag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- Bootstrap core CSS-->
<link href="./Resources/vendor/bootstrap/css/bootstrap.min.css"
	rel="stylesheet">
<link href="./Resources/vendor/font-awesome/css/font-awesome.min.css"
	rel="stylesheet" type="text/css">
<link href="./Resources/vendor/datatables/dataTables.bootstrap4.css"
	rel="stylesheet">
<link href="./Resources/css/sb-admin.css" rel="stylesheet">
<!-- 
   게시글 보는 페이지
   board.jsp에서 누른 게시글에 대한 board_num를 받아옴.
   boardDAO의 getBoard메소드 호출을 통해 해당 게시글에 대한 정보를 boardDTO로 받아옴.
   session id와  boardDTO()의 id와 같을 경우에만'수정','삭제'버튼이 보임.
   최종 수정: 2017/11/05
-->

<!DOCTYPE>
<html>
<head>
<title>메인 화면</title>
</head>
<body>
	<%
      int board_num = 0;
      if (request.getAttribute("board_num") != null) {
         board_num = Integer.parseInt(request.getParameter("board_num"));
      }
      if (board_num == 0) {
         PrintWriter script = response.getWriter();
         script.println("<script>");
         script.println("alert('유효하지 않은 글입니다.')");
         script.println("location.href = 'board.jsp'");
         script.println("</script>");
      }
      BoardDataBean board = BoardDBBean.getinstance().getBoard(board_num);
   %>
	<!-- 상단바 -->
	<jsp:include page="form.jsp" />
	<br>
	<!-- 게시글에 관련된 부분 -->
	<div class="content-wrapper">
		<div class="container-fluid">
			<ol class="breadcrumb">
				<li class="breadcrumb-item"><a href="#"></a></li>
				<li class="breadcrumb-item active">게시글</li>
			</ol>
			<div class="card mb-3"">
				<div class="card-body">
					<div class="table-responsive">
						<!-- 테이블 색 -->
						<table class="table table-bordered" id="dataTable" width="100%"
							height="30" cellspacing="0">
							<thead>
								<tr>
									<th colspan="3"
										style="background-color: #eeeeee; text-align: center;">게시판
										글보기</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td style="width: 20%;">글 제목</td>
									<td colspan="2"><%=board.getBoard_title().replaceAll(" ", "&nbsp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>")%></td>
								</tr>
								<tr>
									<td>작성자</td>
									<td colspan="2"><%=board.getUser_id()%></td><c:set var="board_user_id" value="<%=board.getUser_id() %>"/>
								</tr>
								<tr>
									<td>조회수</td>
									<td colspan="2"><%=board.getBoard_hit()%></td>
								</tr>
								<tr>
									<td>작성 일자</td>
									<td><%=board.getBoard_date().substring(0, 11) + board.getBoard_date().substring(11, 13) + "시"
               + board.getBoard_date().substring(14, 16) + "분"%></td>
								</tr>
								<tr>
									<td>내용</td>
									<td colspan="2" style="min-height: 200px, text-align: left;"><%=board.getBoard_content().replaceAll(" ", "&nbsp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;")
               .replaceAll("\n", "<br>")%></td>
								</tr>
								<tr>
									<td>사진</td>
									<td colspan="2" align="center">
									<%
										String image = board.getBoard_image();
										if (image == null) {
									%> 
									사진이 없습니다. 
									<%
										} else {
											String[] images = image.split("/");
											for (int i = 0; i < images.length; i++) {
									%> 
									<img src="/test/<%=images[i]%>" height=300px width=300px>
									<br>
									<%
 											}
  										}
 									%>
									</td>
								</tr>
							</tbody>
						</table>
						<c:if test="${user_id ne null}">
							<a class="mr-3 d-inline-block"
								href="./Controller?action=like&board_num=<%=board.getBoard_num()%>&from=Boardlike">
								<i class="fa fa-fw fa-thumbs-up"></i><%=board.getBoard_like()%></a>
							<a class="mr-3 d-inline-block" href="/Animal/Controller?action=boardAction&cate_num=<%=board.getCate_num()%>"><span><i class="fa fa-list"></i></span><span>list</span></a>
							<a class="mr-3 d-inline-block" href="/Animal/Controller?action=add_scrap&board_num=<%=board.getBoard_num()%>"><i class="fa fa-clipboard" aria-hidden="true"></i>scrap</a>
						</c:if>
						<c:if test="${user_id eq null }">
							<a href="/Animal/Controller?action=boardAction&cate_num=<%=board.getCate_num()%>">
							<i class="fa fa-list"></i>list</a>
						</c:if>
						<c:if test="${user_id eq board_user_id }">
						 <a class="mr-3 d-inline-block" href="./Controller?action=declaration&board_num=<%=board.getBoard_num()%>&news_num=<%=board.getNews_num()%>">
                  			<i class="fa fa-fw fa-thumbs-down"></i>declaration</a>
							<%-- <a href="/Animal/Controller?action=boardAction&cate_num=<%=board.getCate_num()%>" class="btn btn-default btn-lg">목록</a>  --%>
						<a href="/Animal/Controller?action=UpdateAction&board_num=<%=board.getBoard_num()%>" ><i class="fa fa-fw fa-wrench" aria-hidden="true"></i>update</a><!-- <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span> -->
						<a onclick="return confirm('정말로 삭제하시겠습니까?')" href="/Animal/Controller?action=boardDelete&cate_num=<%=board.getCate_num()%>&board_num=<%=board.getBoard_num()%>">
						<i class="fa fa-scissors" aria-hidden="true"></i>delete</a>
							<!-- <span class="glyphicon glyphicon-trash" aria-hidden="true"></span> -->
						</c:if>
						<%-- <%
							if (request.getSession().getAttribute("user_id") != null) {
						%>
						<%
								if (request.getSession().getAttribute("user_id").equals(board.getUser_id())) {
						%>
						<a href="/Animal/Controller?action=boardAction&cate_num=<%=board.getCate_num()%>" class="btn btn-default btn-lg">목록</a> 
						<a href="/Animal/Controller?action=boardUpdate&board_num=<%=board.getBoard_num()%>" class="mr-3 d-inline-block" >수정 <!-- <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span> --></a>
						<a onclick="return confirm('정말로 삭제하시겠습니까?')" href="/Animal/Controller?action=boardDelete&cate_num=<%=board.getCate_num()%>&board_num=<%=board.getBoard_num()%>"class="mr-3 d-inline-block" >
							<!-- <span class="glyphicon glyphicon-trash" aria-hidden="true"></span> -->삭제
						</a>
						<%
							}
						%> --%>
					</div>
				</div>
			</div>
		</div>
	</div>

	<!-- 댓글 기능 -->
	<div class="content-wrapper">
		<div class="container-fluid">
			<br>
			<div class="card mb-3"">
				<div class="card-body">
					<div class="table-responsive">
						<!-- 테이블 색 -->
						<form method="post"
							action="/Animal/Controller?action=writeComment&board_num=<%=board.getBoard_num()%>&user_id=${user_id}&cate_num=<%=board.getCate_num()%>">
							<table class="table table-bordered" id="dataTable" width="100%"
								height="30" cellspacing="0">
								<thead>
									<tr>
										<th colspan="4"
											style="background-color: #eeeeee; text-align: center;"><i class="fa fa-fw fa-comment"></i>댓글</th>
									</tr>
								</thead>
								<tbody>
									<c:if test="${user_id ne null}">
										<tr>
											<td colspan="4"><textarea class="form-control"
													placeholder="댓글  내용" name="comment_content" maxlength="200"
													style="height: 100px;"></textarea></td>
										</tr>
										<tr>
											<td colspan="4"><input type="submit"
												class="btn btn-primary" value="등록"></td>
										</tr>
									</c:if>
									<c:if test="${user_id eq null}">
										<tr>
											<td colspan="4">댓글을 남기려면 로그인을 해주세요!</td>
										</tr>
									</c:if>
									<%
										ArrayList<CommentDataBean> list = CommentDBBean.getinstance().getList(board.getBoard_num());
										for (int i = 0; i < list.size(); i++) {
									%>
									<tr>
										<td><%=list.get(i).getUser_id()%></td>
										<td><%=list.get(i).getComment_content()%></td>
										<td><%=list.get(i).getComment_date().substring(0, 11) + list.get(i).getComment_date().substring(11, 13) + "시" + list.get(i).getComment_date().substring(14, 16) + "분"%></td>
										<%
											if(request.getSession().getAttribute("user_id")!=null){
												if (request.getSession().getAttribute("user_id").equals(list.get(i).getUser_id())) {
														int comment_num = list.get(i).getComment_num();
										%>
										<td><a
											href="/Animal/Controller?action=updatecomment&board_num=<%=board_num %>&comment_num=<%=comment_num%>">
												<input type=button class="btn btn-primary" value="수정">
										</a> <a onclick="return confirm('정말로 삭제하시겠습니까?')"
											href="/Animal/Controller?action=deleteComment&comment_num=<%=comment_num%>&board_num=<%=board_num%>"
											class="btn btn-primary">삭제</a></td>
									</tr>
									<%
										} else {
									%>
									<td></td>
									</tr>
									<%}}}%>
								</tbody>
							</table>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>

</body>
</html>