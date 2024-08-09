<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>내차 팔기 홈서비스</title>
<script
   src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<link rel="icon" type="image/png" sizes="32x32"
   href="images/favicon-32x32.png">
</head>
<body>
   <jsp:include page="/WEB-INF/include/main_top.jsp" />

   <main>
      <div class="carsell_home">
         <div class="carsell_visual">
            <div class="carsell_text">
               <p>내차팔기 서비스</p>
               <p>포에스카에서 차를 팔면?</p>
               <div class="carsell_text2">
                  <p>흥정 없는 높은 가격</p>
                  <p>
                     기다림은 <span>NO!</span> 하루만에 내차 팔기
                  </p>
                  <p>수수료 없는 무료 견적</p>
                  <div class="sell_btn">
                     <button class="car_sell_click" id="car_sell_btn"
                        onclick="location.href='req_regcar_spec'">내차팔기 간편신청</button>
                  </div>
               </div>
            </div>
         </div>
         <section>
            <p class="car_sell_process_title">포에스카 내차 팔기 진행과정</p>
            <div class="car_sell_process">
               <div>
                  <img src="images/carsellbtn.png" alt="" class="carsell_img">
                  <p>간편신청 버튼 클릭</p>
               </div>
               <div>
                  <img src="images/carsell화살표.png" alt="" class="carsell_arrow">
               </div>
               <div>
                  <img src="images/carsellwrite.png" alt="" class="carsell_img">
                  <p>차량 기본정보 입력</p>
               </div>
               <div>
                  <img src="images/carsell화살표.png" alt="" class="carsell_arrow">
               </div>
               <div>
                  <img src="images/carsell_carwrtie.png" alt="" class="carsell_img">
                  <p>차량 상세정보 입력</p>
               </div>
               <div>
                  <img src="images/carsell화살표.png" alt="" class="carsell_arrow">
               </div>
               <div>
                  <img src="images/carsell_finally.png" alt="" class="carsell_img">
                  <p>내차팔기 완료 클릭</p>
               </div>
            </div>
         </section>
      </div>
   </main>

   <jsp:include page="/WEB-INF/include/main_bottom.jsp" />
</body>
</html>