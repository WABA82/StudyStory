<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<style>
#wrap {
	margin: 100px auto;
	width: 800px;
	min-height: 800px
}
/* #link{margin-left: 1000px; margin-top: 20px} */
#loginTitle {
	text-align: center;
	st
}

.font20bold {
	font-size: 20px;
	font-weight: bold;
}
</style>
<head>
<meta charset="UTF-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<link rel="stylesheet" href="./Resources/css/bootstrap.min.css">

<title>세부 알림보기</title>
<!-- Custom styles for this template -->

<script src="./Resources/js/jquery-3.3.1.slim.min.js"></script>

</head>
<body>
	<!-- header -->
	<c:import url="http://localhost:8080/third_prj/layout/navbar.jsp"></c:import>
	<!--  -->
	<div class="container form-group" id="wrap">

		<div class="row" style="margin-top: 300px;">
			<div class="col-3 font20bold">
				<strong style="float: right;">제목 </strong>
			</div>
			<div class="col-7 font20bold">
				<input type="text" class="form-control">
			</div>
		</div>
		<div class="row" style="margin-top: 10px;">
			<div class="col-3 font20bold">
				<strong style="float: right;">분류 </strong>
			</div>
			<div class="col-7 font20bold">
				<input type="text" class="form-control">
			</div>
		</div>
		<div class=" row" style="margin-top: 10px;">
			<div class="col-3 font20bold">
				<strong style="float: right;">내용 </strong>
			</div>
			<div class="col-7 font20bold">
				<textarea class="form-control" rows="3"
					style="resize: none;  height: 300px;" name="contents"></textarea>
			</div>
		</div>

		<div class=" row" style="margin-top: 10px;">
			<div class="col-3 font20bold"></div>
			<div class="col-7 ont20bold">
				<label>날짜 : </label>
			</div>
		</div>
		<a class="btn btn-secondary btn" href="#void" role="button"
			style="margin-left: 340px; margin-top: 15px;">목록으로</a>
	</div>





	<!-- footer -->
	<c:import url="http://localhost:8080/third_prj/layout/footer.jsp"></c:import>


	<!-- jQuery first, then Popper.js, then Bootstrap JS -->
	<script src="./Resources/js/popper.min.js"></script>
	<script src="./Resources/js/bootstrap.min.js"></script>



</body>
</html>