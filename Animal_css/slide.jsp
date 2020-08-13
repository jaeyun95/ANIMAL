<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<link href="css/slide.css" rel="stylesheet">

	<div align="center">
	 <div id="myCarousel" class="carousel slide" data-ride="carousel">
	 	<ol class="carousel-indicators">
	 		<li data-target="#myCarousel" data-slide-to="0" class="active"></li>
	 		<li data-target="#myCarousel" data-slide-to="1" ></li>
	 		<li data-target="#myCarousel" data-slide-to="2" ></li>
	 	</ol>
	 	<div class="carousel-inner">
	 		<div class="item active">
	 			<img src="images/cat.jpg" width="500" height="500">
	 		</div>
	 		<div class="item">
	 			<img src="images/cat2.jpg" width="500" height="500">
	 		</div>
	 		<div class="item">
	 			<img src="images/dog.jpg" width="500" height="500">
	 		</div>
	 	</div>
	 		<a class="left carousel-control" href="#myCarousel" data-slide="prev">
	 			<span class="glyphicon glyphicon-chevron-left"></span>
	 		</a>
	 		<a class="right carousel-control" href="#myCarousel" data-slide="next">
	 			<span class="glyphicon glyphicon-chevron-right"></span>
	 		</a>
	 </div>
	</div>