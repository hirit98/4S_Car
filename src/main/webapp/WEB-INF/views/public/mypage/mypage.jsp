<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>마이 페이지</title>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<link rel="icon" type="image/png" sizes="32x32"
	href="images/favicon-32x32.png">
</head>
<body>
	<div class="car_mypage">
		<jsp:include page="/WEB-INF/include/mypage_side_bar.jsp" />

		<div class="car_mypage_menual">
			<div class="mypage_peopleinfo">
				<img src="images/프로필.png" alt="사람" class="people">
				<div class="mypage_peopleinfo_text">
					<p>
						안녕하세요 ${dto.getUser_name() } 님 <img
							src="images/하트-removebg-preview.png" alt="하트" class="heart">
					</p>
					<p>현재 등급은 ${dto.getUser_grade() } 등급입니다!</p>
				</div>
			</div>
			<div class="car_mypage_menualpage">
				<div class="car_mypage_menualpage_1">
					<div class="mypage_box1" onclick="location.href='user_validate'">
						<img src="images/내정보수정.png" alt="수정">
						<p>내 정보 수정</p>
					</div>
					<div class="mypage_box2" onclick="location.href='my_review'">
						<img src="images/내가쓴후기.png" alt="후기">
						<p>내가 쓴 글</p>
					</div>
					<div class="mypage_box3" onclick="location.href='myfavorite_list'">
						<img src="images/찜하트.png" alt="찜">
						<p>내 찜 목록</p>
					</div>
				</div>
				<div class="car_mypage_menualpage_2">
					<div class="mypage_box4" onclick="location.href='mycar_info'">
						<img src="images/내차등록.png" alt="등록">
						<p>내 차 등록</p>
					</div>
					<div class="mypage_box5"
						onclick="location.href='mycar_buy_history'">
						<img src="images/구매이력.png" alt="이력">
						<p>내 거래내역</p>
					</div>
					<div class="mypage_box6"
						onclick="location.href='my_mileage_history'">
						<img src="images/네잎클로버_마일리지.png" alt="마일리지">
						<p>내 마일리지</p>
					</div>
				</div>
			</div>
			<div class="mypage_banner_img">
				<p>예상치 못한 고장이 걱정된다면?</p>
				<p>
					제조사 보증이 남은 챠량도, 보증이 끝난차량도 <br> 자유롭게 가입할 수 있습니다.
				</p>
				<img src="images/mypagecar-1880381.jpg" alt="자동차">
			</div>
		</div>
	</div>
</body>
</html>