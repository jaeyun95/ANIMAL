<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="animal.bean.*" %>
<%@ page import="java.util.*" %>
<!DOCTYPE html>
<html>
<!-- 부트스트랩 부분 -->
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <title>Animal</title>
  <link href="./Resources/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
  <link href="./Resources/vendor/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
  <link href="./Resources/vendor/datatables/dataTables.bootstrap4.css" rel="stylesheet">
  <link href="./Resources/css/sb-admin.css" rel="stylesheet">
  <script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
</head>
<!-- 메뉴부분 -->
<body>
 <jsp:include page="form.jsp"/>
      <!-- Breadcrumbs-->
  <div class="content-wrapper">
    <div class="container-fluid">
    <br>
      <ol class="breadcrumb">
        <li class="breadcrumb-item">
          <h5>${click_id}님의 뉴스피드</h5>
        </li>
      </ol>
      <% String user_id = (String)request.getSession().getAttribute("user_id");
      	if(user_id.equals(request.getAttribute("click_id"))){%>
      	<center>
      	<a href="./Controller?action=news"><button type="button" class="btn btn-default">뉴스피드</button></a>
      	<a href="./Controller?action=news_write"><button type="button" class="btn btn-default">글쓰기</button></a>
      	<a href="./Controller?action=confirm&goal=update_user"><button type="button" class="btn btn-default">정보 수정</button></a>
      	<a href="./Controller?action=confirm&goal=delete_user"><button type="button" class="btn btn-default">회원 탈퇴</button></a>
      	<a href="./Controller?action=scrap"><button type="button" class="btn btn-default">스크랩함</button></a>
      	</center>
      <%} %>
          <!-- Card Columns Example Social Feed-->
          <div class="mb-0 mt-4">
            <i class="fa fa-newspaper-o"></i>News Feed</div>
          <hr class="mt-2">
          <div class="card-columns">
            <!-- Example Social Card-->
            <% 
               BoardDBBean board = BoardDBBean.getinstance();
               ArrayList<BoardDataBean> preboarddt =board.news_getlist((String)request.getAttribute("click_id"));
               ArrayList<BoardDataBean> boarddt = new ArrayList<BoardDataBean>();
         
               if(user_id.equals(request.getAttribute("click_id"))){
            	  boarddt = preboarddt;
            	  }
               else{
            	  for(int j = 0; j < preboarddt.size(); j++){
            		if(preboarddt.get(j).getNews_visible() == 0){
            		   boarddt.add(preboarddt.get(j));
            			  }
            		  }
            	 }
               for(int i = 0; i < boarddt.size(); i++){
            	   if(boarddt.get(i).getBoard_image() != null){
                	   String image = boarddt.get(i).getBoard_image();
    				   String[] images = image.split("/");
    				   for (int j = 0; j < images.length; j++) {
            %>
            <div class="card mb-3">
              <a href="#">
                <img class="card-img-top" width="300" height="500" src="/test/<%=images[j]%>" alt="">
              </a>
              <%} %>
              <div class="card-body">
                <h6 class="card-title mb-1"><a href="./Controller?action=mypage&click_id=<%=boarddt.get(i).getUser_id()%>"><%=boarddt.get(i).getUser_id() %></a></h6>
                <h7 class="card-title mb-1"><%=boarddt.get(i).getBoard_title() %></h7>
                <p class="card-text small"><%=boarddt.get(i).getBoard_content() %></p>
                <p class="card-text small"><%=boarddt.get(i).getBoard_date().substring(0, 11) + boarddt.get(i).getBoard_date().substring(11, 13)+ "시" + boarddt.get(i).getBoard_date().substring(14, 16) + "분" %>
              </div>
              <hr class="my-0">
              <div class="card-body py-2 small">
                <a class="mr-3 d-inline-block" href="./Controller?action=like&board_num=<%=boarddt.get(i).getBoard_num()%>&from=mypage">
                  <i class="fa fa-fw fa-thumbs-up"></i><%=boarddt.get(i).getBoard_like() %></a>
                <a class="mr-3 d-inline-block" href="./Controller?action=declaration&board_num=<%=boarddt.get(i).getBoard_num()%>&news_num=<%=boarddt.get(i).getNews_num()%>">
                  <i class="fa fa-fw fa-thumbs-down"></i>declaration</a>
                <% if(user_id.equals(request.getAttribute("click_id"))){ %>
                <a class="mr-3 d-inline-block" href="./Controller?action=news_update&board_num=<%=boarddt.get(i).getBoard_num()%>">
                	<i class="fa fa-fw fa-wrench"></i>update</a>
                <a class="mr-3 d-inline-block" href="./Controller?action=news_delete&board_num=<%=boarddt.get(i).getBoard_num()%>">
                	<i class="fa fa-scissors" aria-hidden="true"></i>delete</a>
                <%} %>
              </div>
            </div>
            <%} }%>
         </div>
        </div>
       </div>
    <!-- /.content-wrapper-->
    <footer class="sticky-footer">
      <div class="container">
        <div class="text-center">
          <small>Copyright © Animal 2017</small>
        </div>
      </div>
    </footer>
</body>
</html>
