<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.4/jquery.min.js"></script>
<script type="text/javascript">
	$(function() {
		
		$.ajax({
			url:"../check_alarm.do",
			dataType:"json",
			async:"true",
			error:function(xhr) {
				alert("에러코드 : "+xhr+status+", 에러 메시지 : "+xhr.statusText);
			},
			success:function(json) {
				if(json.newAlarmFlag) {
					$("#showNewAlarm").attr("src", "/third_prj/resources/images/nav_icon_alarm_new.png");
				} else {
					$("#showNewAlarm").attr("src", "/third_prj/resources/images/nav_icon_alarm.png");
				}
			}
		});
		
		var getAlarmFlag = false;
		
		$("#showNewAlarm").click(function() {
			if (!getAlarmFlag) {
				$.ajax({
					url:"../new_alarm.do",
					dataType:"json",
					async:"true",
					error:function(xhr) {
						alert("에러코드 : "+xhr+status+", 에러 메시지 : "+xhr.statusText);
					},
					success:function(jsonArr) {
						var jsonArrLength = jsonArr.length;
						if (jsonArrLength == 0) {
							$("#alarmMenu").append("<a class='dropdown-item' href='../common_bbs/alarm.do'>더보기</a>");
							getAlarmFlag = true;
							alert("새로운 알림이 없습니다.");
						} else {
							for(var i=0; i<jsonArrLength; i++){
								$("#alarmMenu").append("<a class='dropdown-item' href='../common_bbs/detail_alarm.do?a_num="+jsonArr[i].a_num+"'>["
										+jsonArr[i].category+"] "+jsonArr[i].subject+" "+jsonArr[i].input_date+"</a>");
							}
							$("#alarmMenu").append("<a class='dropdown-item' href='../common_bbs/alarm.do'>더보기</a>");
							getAlarmFlag = true;
						}
					}
				});//ajax
			}
		});
	});
</script>

<!-- 검색창 -->
<script type="text/javascript">
	$(function() { // <input>요소에 문자가 입력될 때마다 호출됨.

		// 검색버튼 눌렀을 때.
		$("#word_search_btn").click(function() {
			$("#word_search_frm").submit();
		}); // click

	}); // ready
</script>
<!-- 검색창 -->


<nav class="navbar navbar-expand-lg fixed-top navbar-light bg-light">

	<div class="container">

		<!-- 로고 -->
		<button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarsExample07" aria-controls="navbarsExample07" aria-expanded="false" aria-label="Toggle navigation">
			<span class="navbar-toggler-icon"></span>
		</button>

		<div class="collapse navbar-collapse" id="navbarsExample07">
			<ul class="navbar-nav mr-auto">
				<li class="nav-item dropdown">
					<a class="nav-link text-secondary dropdown-toggle" href="#" id="menu" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">메뉴</a>
					<div class="dropdown-menu" aria-labelledby="menu">
						<a class="dropdown-item" href="../common/introduction.do">서비스소개</a>
						<a class="dropdown-item" href="../common_bbs/notice.do">공지사항</a>
						<a class="dropdown-item" href="../common/faq.do">FAQ</a>
						<a class="dropdown-item" href="../common_bbs/ask.do">문의하기</a>
						<a class="dropdown-item" href="../common_bbs/question.do">내 문의내역</a>
					</div>
				</li>
				<li class="nav-item"><a class="nav-link text-secondary" href="../search/search.do">스터디 찾기</a></li>
				<li class="nav-item"><a class="nav-link text-secondary" href="../study_group/create_study.do">스터디 만들기</a></li>
			</ul>
			<a class="navbar-brand mx-auto text-dark" href="../study_info/main.do"><img src="/third_prj/resources/images/study_story_logo2.png" width="140" height="40"/></a>
			
			<!-- 검색창 : 엔터 서브밋 막기. -->
			<form id="word_search_frm" class="form-inline my-2 my-lg-0" action="../search/search.do" method="get">
				<input name="search_inputBox" id="word_search_inputBox"class="form-control mr-sm-2" type="text" placeholder="스터디검색" aria-label="Search">
				<button id="word_search_btn" class="btn btn-outline-info my-2 my-sm-0 mr-sm-2" type="button">검색</button> 
				<!-- 제안  -->
				<!-- 
				<div id="word_search_show" class="dropdown-menu" style="display: block;">
				  <a id="word_suggest_item" class="dropdown-item" href="#">Action</a>
				</div>
				 -->
				<!-- 제안  -->
			</form>
			<!-- 검색창 -->
			
			<ul class="navbar-nav text-center">
				<li class="nav-item dropdown">
					<a class="nav-link" href="#" id="my_menu" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
						<img class="rounded-circle" src="/third_prj/resources/images/nav_icon_alarm.png" id="showNewAlarm" style="width: 36px; height: 36px;">
					</a>
					<div class="dropdown-menu" aria-labelledby="notice" id="alarmMenu">
					</div>
				</li>
				<li class="nav-item dropdown">
					<a class="nav-link" href="#" id="my_menu" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
						<img class="rounded-circle" src="/third_prj/resources/images/nav_icon_my_page.png" style="width: 35px; height: 35px;">
					</a>
					<div class="dropdown-menu" aria-labelledby="my_menu">
						<a class="dropdown-item" href="../study_group/my_study.do">내 스터디</a>
						<a class="dropdown-item" href="../study_group/study_i_made.do">내가 만든 스터디</a>
						<a class="dropdown-item" href="../interest/show_interest_study.do">관심 스터디 보기</a>
						<a class="dropdown-item" href="../common_bbs/alarm.do">알림보기</a>
						<a class="dropdown-item" href="../common/profile.do">프로필 관리</a>
						<a class="dropdown-item" href="../common/user_modify_menu.do">회원정보 관리</a>
						<a class="dropdown-item" href="../logout.do">로그아웃</a>
					</div>
				</li>
			</ul>
		</div>
	</div>
</nav>


