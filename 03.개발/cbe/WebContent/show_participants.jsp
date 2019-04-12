<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<link rel="stylesheet" href="http://localhost:8080/third_prj/resources/css/bootstrap.min.css">
<link rel="stylesheet" href="http://localhost:8080/third_prj/resources/css/font.css"/>

<title>Bootstrap Template By Young</title>
<style>
.bd-placeholder-img {
	font-size: 1.125rem;
	text-anchor: middle;
	-webkit-user-select: none;
	-moz-user-select: none;
	-ms-user-select: none;
	user-select: none;
}

@media ( min-width : 768px) {
	.bd-placeholder-img-lg {
		font-size: 3.5rem;
	}
}
</style>
<!-- Custom styles for this template -->
<link href="http://localhost:8080/third_prj/resources/css/jumbotron.css" rel="stylesheet">

</head>
<body>
	<!-- header -->
	<c:import url="http://localhost:8080/third_prj/layout/navbar.jsp"></c:import>

	<!-- body -->
	<div role="main" style="min-height: 900px">

		<!-- 점보트론 : 전광판 -->
		<section class="text-center bg-white mb-0" style="margin-top: 30px; margin-bottom: 20px;">
			<div class="container">
				<h1 class="jumbotron-heading">참여자 보기</h1>
			</div>
		</section>
		<div style="height: 20px;"></div>
		<!-- 점보트론 : 전광판 -->

		<!-- CONTAINER DIV -->
		<div class="container">
			<!-- row -->
			<div class="row">
				<div class="col-md-12 p-3 bg-light">

					<!-- 정렬바 row -->
					<div class="row" style="height: 3em;">
						<div class="row mr-3 ml-3 w-100 border-bottom align-items-center">
							<div class="col-auto mr-auto">
								<p class="h5">조회된 참여자들 : 0건</p>
							</div>
						</div>
					</div>
					<!-- 정렬바 row -->

					<!-- 썸네일 row -->
					<div class="row p-3">
						<div class="col-12 bg-white">
							<!-- 썸네일 row -->
							<div class="row pt-3">
								<!-- 1번째 줄 시작 -->
								<div class="col-3">
									<div class="card mb-4 shadow-sm text-center">
										<img class="card-img-top" src="http://localhost:8080/third_prj/resources/images/no_img.png" alt="Card image cap">
										<div class="card-body">
											<p class="card-title">닉네임</p>
											<p class="card-text">010-0000-0000</p>
										</div>
									</div>
								</div>
								<!-- 1번째 줄 시작 -->
							</div>
						</div>
					</div>
					<!-- 썸네일 row -->

				</div>
			</div>
			<!-- row -->
		</div>
		<!-- CONTAINER DIV -->
	</div>
	<!-- DIV ROLE MAIN -->

	<!-- footer -->
	<c:import url="http://localhost:8080/third_prj/layout/footer.jsp"></c:import>

	<!-- jQuery first, then Popper.js, then Bootstrap JS -->
	<script src="http://localhost:8080/third_prj/resources/js/jquery-3.3.1.slim.min.js"></script>
	<script src="http://localhost:8080/third_prj/resources/js/popper.min.js"></script>
	<script src="http://localhost:8080/third_prj/resources/js/bootstrap.min.js"></script>
</body>
</html>