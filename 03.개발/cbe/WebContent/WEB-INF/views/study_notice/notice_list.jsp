<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <link rel="stylesheet" href="/third_prj/resources/css/bootstrap.min.css">
  <link rel="stylesheet" href="/third_prj/resources/css/font.css"/>
	<title>스터디 공지 리스트</title>
	<!-- jQuery first, then Popper.js, then Bootstrap JS -->
  <script src="/third_prj/resources/js/jquery-3.3.1.slim.min.js" ></script>
  <script src="/third_prj/resources/js/popper.min.js" ></script>
  <script src="/third_prj/resources/js/bootstrap.min.js" ></script>
  <style type="text/css">
  	.select-card:hover {
  		background-color:#F1F3F4;
  	}
  </style>
</head>
<body>
	<!-- header -->
	<c:import url="/WEB-INF/views/layout/navbar.jsp"></c:import>
	<!--  header  끝 -->
	
		<!-- 점보트론 : 전광판 -->
  <section class="text-center bg-white mb-0" style="margin-top:90px; margin-bottom:20px;">
     <div class="container">
        <h1 class="jumbotron-heading"><c:out value="${ study_name }"/></h1>
     </div>
  </section>
  <div style="height:20px;"></div>
  <!-- 점보트론 : 전광판 -->
	
		  <div class="row justify-content-center">
		  	<div class="col-6 border-top border-bottom" style="padding:15px;">
	      	<a href="#">스터디 탈퇴하기</a>
      	</div>
      </div>
      
  		<div class="container-fluid" style="min-height:500px; width:450px;">
	  	<div class="row col-sm justify-content-center">
	      <!-- 공지가 존재하지 않을 경우 공지사항이 없다고 보여주기  -->
	      <c:if test="${ empty snList  }">
		   	<div class="card select-card border-dark m-3 p-2" style="width:250px; height:150px;">
		   		<div class="card-body">
		   			 <h6 class="card-title"><strong>등록된 스터디 공지가 없습니다</strong></h6>
		   		</div>
		 		</div>
	      </c:if>
      	<!--공지사항이 있으면 표시  -->
		    <c:forEach items="${ snList }" var="studyNotice">
		   	<div class="card select-card border-dark m-3 p-2" style="width:300px; height:150px;" onclick="location.href='notice_detail.do?sn_num=${ studyNotice.snNum }&s_num=${ param.s_num }'">
		   		<div class="card-body">
		   			 <div style="float:left;" class="card-title">
		   			 	<strong>${ studyNotice.subject }</strong>&nbsp;&nbsp;
		   			 	<span style="color:#E36539">
		   			 		<c:out value="${ studyNotice.commentNum }"/>
		   			 	</span>
		   			 </div>
		   			 <br/>
				    <p class="card-text">
				    	<p class="text-left">
				    		<span style="font-size:12px;"><c:out value="${ studyNotice.inputDate }"/></span>
				    		<img src="/third_prj/profile_img/${ studyNotice.img }" width="50" height="60" style="float:right;"/>
				    	</p>
			    	</p>
		   		</div>
		 		</div>
		 		</c:forEach>
		 	</div>
      </div>

	<!-- footer -->
	<c:import url="/WEB-INF/views/layout/footer.jsp"></c:import>
</body>
</html>