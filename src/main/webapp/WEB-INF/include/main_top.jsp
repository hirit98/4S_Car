<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>4Scar 메인페이지</title>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<script src="js/motion.js"></script>
<script src="js/empty.js"></script>
<script src="js/checkSession.js"></script>
<!-- <script type="module" src="https://unpkg.com/@google/model-viewer/dist/model-viewer.min.js"></script> -->
<!--  <model-viewer id="model" src="./glb/free_bmw_m3_e30.glb" alt="model sample" camera-controls></model-viewer> -->
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link
	href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@100..900&display=swap"
	rel="stylesheet">
<link rel="stylesheet" href="css/common/style.css">
<link rel="icon" type="image/png" sizes="32x32"
	href="images/favicon-32x32.png">
</head>
<body>
	<header class="header_container">
		<div class="header_menu">
			<ul class="gnb">
				<li class="gnb-item">
					<div class="logo">
						<a href="main"> <img src="images/4scar_logo.png" alt="로고">
						</a>
					</div>
				</li>
				<li class="gnb-item">
					<div class="car_sell" id="req_regcar"
						onclick="location.href='req_regcar'">내차팔기</div>
					<ul class="lnb">
						<li class="lnb-item"><a href="req_regcar_spec"
							id="login_first">내차팔기 간편신청</a></li>
					</ul>
				</li>
				<li class="gnb-item">
					<div class="car_buy" onclick="location.href='car_list_filter'">내차사기</div>
					<ul class="lnb">
						<li class="lnb-item"><a href="car_list_filter">차량검색</a></li>
					</ul>
				</li>
				<li class="gnb-item">
					<div class="my_page" id="my_page" onclick="location.href='mypage'">마이페이지</div>
					<ul class="lnb">
						<li class="lnb-item"><a href="user_validate" id="login_first">회원
								정보 수정</a></li>
						<li class="lnb-item"><a href="my_review" id="login_first">내가
								쓴 글</a></li>
						<li class="lnb-item"><a href="myfavorite_list"
							id="login_first">찜한 차량</a></li>
						<li class="lnb-item"><a href="mycar_info" id="login_first">차량
								관리</a></li>
						<li class="lnb-item"><a href="mycar_buy_history"
							id="login_first">구매 이력</a></li>
						<li class="lnb-item"><a href="my_mileage_history"
							id="login_first">마일리지 내역</a></li>
					</ul>
				</li>
				<li class="gnb-item">
					<div class="comunity" onclick='location.href="community_main"'>커뮤니티</div>
					<ul class="lnb">
						<li class="lnb-item"><a href="sell_review_board_list">판매후기
								게시판</a></li>
						<li class="lnb-item"><a href="buy_review_board_list">구매후기
								게시판</a></li>
						<li class="lnb-item"><a href="community_board_list">자유
								게시판</a></li>
					</ul>
				</li>
				<li class="gnb-item">
					<div class="customer" onclick="location.href='customer_main'">고객지원</div>
					<ul class="lnb">
						<li class="lnb-item"><a href="faq">자주하는 질문</a></li>
						<li class="lnb-item"><a href="qna_insert">QnA 문의</a></li>
						<li class="lnb-item"><a href="company_info">회사 소개</a></li>
						<li class="lnb-item"><a href="company_directions">찾아오시는 길</a></li>
					</ul>
				</li>
				<li class="gnb-item">
					<div class="gongji" onclick="location.href='notice_list'">공지사항</div>
					<ul class="lnb">
						<li class="lnb-item"><a href="notice_list">공지사항</a></li>
						<li class="lnb-item"><a href="notice_list?event">이벤트</a></li>
					</ul>
				</li>
				<li class="gnb-item">
					<form method="get" action="car_list_search">
						<div class="search_area">
							<button type="submit" id="submit_btn">
								<img class="search_logo" src="images/돋보기_블루.png" alt="돋보기">
							</button>
							<div class="search_inner">
								<input type="text" name="keyword" autocapitalize="off"
									placeholder="차량을 검색해주세요." id="keyword">
							</div>
						</div>
					</form>
				</li>
				<li class="gnb-item login_main"><a href="user_login"
					class="button1">로그인</a> <span class="bar"></span> <a href="signup"
					class="button2">회원가입</a></li>
				<li class="gnb-item logout" style="display: none;"><span
					id="user_name" style="font-weight: 700;"></span> <span class="bar"></span>
					<a href="logout" class="button2" id="logout_btn">로그아웃</a></li>
			</ul>
		</div>
		<div class="inner">
			<ul>
				<li onclick="location.href='req_regcar'">
					<div>
						<img src="images/guide.png" alt="퀵가이드">
					</div> <span>퀵가이드</span>
				</li>
				<li onclick="location.href='qna_insert'">
					<div>
						<img src="images/inquire.png" alt="문의">
					</div> <span>문의하기</span>
				</li>
				<li onclick="location.href='myfavorite_list'" id="login_first">
					<div>
						<img src="images/product.png" alt="상품">
					</div> <span>찜한 상품</span>
				</li>
				<li onclick="location.href=''">
					<div>
						<img src="images/toktok.png" alt="톡톡">
					</div> <span>톡톡문의</span>
				</li>
			</ul>
			<div>
				<button type="button" class="scroll_top" id="scroll_top">위로
					가기</button>
			</div>
		</div>

	</header>