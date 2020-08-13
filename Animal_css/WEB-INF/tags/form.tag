<%@ tag language="java" pageEncoding="UTF-8" body-content="empty"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

 <!-- Navigation-->
  <nav class="navbar navbar-expand-lg navbar-dark bg-dark fixed-top" id="mainNav">
    <a class="navbar-brand" href="index.html"><h2>Animal</h2></a>
    <button class="navbar-toggler navbar-toggler-right" type="button" data-toggle="collapse" data-target="#navbarResponsive" aria-controls="navbarResponsive" aria-expanded="false" aria-label="Toggle navigation">
      <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarResponsive">
      <ul class="navbar-nav navbar-sidenav" id="exampleAccordion">
        <li class="nav-item" data-toggle="tooltip" data-placement="right" title="Dashboard">
          <a class="nav-link" href="CatIntroduce.jsp">
            <i class="icon-heart"></i>
            <span class="nav-link-text">고양이 자랑</span>
          </a>
        </li>
          </ul>
        </li>
      <ul class="navbar-nav sidenav-toggler">
        <li class="nav-item">
          <a class="nav-link text-center" id="sidenavToggler">
            <i class="fa fa-fw fa-angle-left"></i>
          </a>
        </li>
      </ul>
      <ul class="navbar-nav ml-auto">
        <li class="nav-item">
          <form class="form-inline my-2 my-lg-0 mr-lg-2">
            <div class="input-group">
              <input class="form-control" type="text" placeholder="Search for...">
              <span class="input-group-btn">
                <button class="btn btn-primary" type="button">
                  <i class="fa fa-search"></i>
                </button>
              </span>
            </div>
          </form>
        </li>
        <c:if test="${user_id ne null }">
          <a class="nav-link" href="mypage.jsp">
            <span class="nav-link-text">My Page</span></a>
         </c:if>
        <li class="nav-item">
          <c:if test="${user_id eq null}">
          	<a class="nav-link" data-toggle="modal" data-target="#exampleModal" href="./Controller?action=login">
            <i class="fa fa-fw fa-sign-in"></i>Login</a>
          </c:if>
          <c:if test="${user_id ne null }">
          	<a class="nav-link" data-toggle="modal" data-target="#exampleModal" href="./Controller?action=logout&user_id=${user_id}">
          	<i class="fa fa-fw fa-sign-out"></i>Logout</a>
          </c:if>
        </li>
      </ul>
    </div>
  </nav>
  <div class="content-wrapper">
    <div class="container-fluid">
      <!-- Breadcrumbs-->
      <ol class="breadcrumb">
        <li class="breadcrumb-item">
          <a href="#">메인</a>
        </li>
      </ol>
    <!-- /.content-wrapper-->
    <footer class="sticky-footer">
      <div class="container">
        <div class="text-center">
          <small>Copyright © Animal 2017</small>
        </div>
      </div>
    </footer>
    <!-- Scroll to Top Button-->
    <a class="scroll-to-top rounded" href="#page-top">
      <i class="fa fa-angle-up"></i>
    </a>