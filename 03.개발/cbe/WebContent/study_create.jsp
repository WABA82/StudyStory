<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
  <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<style>
        #wrap{margin:100px auto; width: 700px; min-height: 800px}
        /* #link{margin-left: 1000px; margin-top: 20px} */
        #loginTitle{text-align: center; st}

.font30 {
	font-size: 30px;
}

</style>
<head>
        <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <link rel="stylesheet" href="./Resources/css/bootstrap.min.css">
 
        <title>Bootstrap Template By Young</title>
  <!-- Custom styles for this template -->
  <title>스터디그룹 개설하기</title>

  <script src="./Resources/js/jquery-3.3.1.slim.min.js" ></script>

  <script type="text/javascript">
  $(function() {
        $("#request").click(function (){
                if($("#subject").val()=="") {
                        alert("주제를 선택해주세요.");
                        $("#subject").focus();
                        return;
                }//end if
                if($("#loc").val()=="") {
                        alert("지역을 선택해주세요.");
                        $("#loc").focus();
                        return;
                }//end if
                if($("#name").val()=="") {
                        alert("스터디 이름을 입력해주세요.");
                        $("#name").focus();
                        return;
                }//end if
                if($("#detail").val()=="") {
                        alert("스터디의 상세설명을 입력해주세요.");
                        $("#detail").focus();
                        return;
                }//end if
        
        });//click
  });//ready
  </script>
  <script type="text/javascript">
  function schfile(){
          document.getElementById("real_file").click();
          document.getElementById("file_sub").value = document.getElementById("real_file").value;
         }
  function previewFile() {
          var preview = document.querySelector('#img');
          var file    = document.querySelector('input[type=file]').files[0];
          var reader  = new FileReader();
          reader.addEventListener("load", function () {
            preview.src = reader.result;
          }, false);
          if (file) {
            reader.readAsDataURL(file);
          }
        }
  </script>
 
</head>
<body>
        <!-- header -->
        <c:import url="http://localhost:8080/third_prj/layout/navbar.jsp"></c:import>
        <!--  -->
        
        
        <div id="wrap" >
                <form action="create_request.jsp" name="createFrm">
                 <br/>
                 <br/>
                <label id="title" style="font-size: 45px;"><strong>스터디그룹 개설하기</strong></label><img src="Resources/images/create.png" style="width: 150px; height: 120px; margin-left: 30px;">
                <br/>
                <br/>
                 <br/>
  <div class="form-group">
 <br/>
         <img alt="" src="Resources/images/sub.png" style="width: 50px; height: 50px; margin-bottom: 20px; margin-right: 15px;"><label  class="font30">어떤 <strong>주제</strong> 와 관련된 스터디인가요? </label>
    <select class="form-control" id="subject">
      <option value="">주제를 선택해주세요</option>
      <option value="언어">언어</option>
      <option value="취업">취업</option>
      <option value="취미">취미</option>
      <option value="기타">기타</option>
    </select>
  </div>
  <br/>
  <br/>
  <br/>
  <div class="form-group">
    <img alt="" src="Resources/images/loc.png" style="width: 50px; height: 50px;  margin-bottom: 20px; margin-right: 15px;"><label class="font30">새로운 스터디 그룹의 주 <strong>지역</strong>은 어디인가요? </label>
    <select class="form-control" id="loc">
      <option value="">지역을 선택해주세요</option>
      <option value="신촌">신촌</option>
      <option value="홍대">홍대</option>
      <option value="종각">종각</option>
      <option value="건대">건대</option>
      <option value="노원">노원</option>
      <option value="강남">강남</option>
    </select>
  </div>
<br/>
<br/>
<br/>
  <div class="form-group">
   <img alt="" src="Resources/images/subject.png" style="width: 50px; height: 50px;  margin-bottom: 20px; margin-right: 15px;"><label class="font30">스터디의 <strong>이름</strong>을 정해주세요</label>
    <input type="email" class="form-control" id="name" placeholder="스터디의 이름을 입력해주세요" id="name">
  </div>
        <br/>
        <br/>
        <br/>
  <div class="form-group">
    <img alt="" src="Resources/images/detail.png" style="width: 50px; height: 50px;  margin-bottom: 20px; margin-right: 15px;" ><label style="font-size: 26px;">스터디 <strong>주제</strong>와 이 방의 <strong>규칙</strong>등을 자세히 설명해주세요</label>
    <textarea id="detail"  class="form-control"  rows="3" style="width: 700px; height: 300px;" class="form_control"  name="contents" placeholder="스터디의 상세설명을 입력해주세요"></textarea>
  </div>
        <br/>
        <br/>
        <br/>
        
  <div class="form-group">
    <label for="exampleFormControlTextarea1" style="font-size: 30px;">대표이미지</label>
        <div class="input-group mb-3">
  		<input type='file' name='file' id='real_file' onchange="previewFile()"  style='display:none; ' />
        <input type="text" id='file_sub' style="width:700px; border:0px; display: none" readonly="readonly" ;>
<a href="<?echo $PHP_SELF;?>" onclick="schfile(); return false;"><img  width="700" height="300" id="img" src="Resources/images/Image.png" border="0" title='찾아보기' alt='찾아보기'></a>
</div>
  </div>
  <br/>
        <br/>
        <br/>
         <a class="btn btn-secondary btn-lg" href="#void" role="button" style="margin-left: 250px;" >취소</a>
        <a class="btn btn-primary btn-lg" href="#void" role="button" id="request" style="right: 100">승인요청</a>
</form>
        </div>
        
                <!-- footer -->
        <c:import url="http://localhost:8080/third_prj/layout/footer.jsp"></c:import>
        <!-- jQuery first, then Popper.js, then Bootstrap JS -->
  <script src="./Resources/js/popper.min.js" ></script>
  <script src="./Resources/js/bootstrap.min.js" ></script>
        
        
</body>
</html>







































