<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<link rel="stylesheet" href="css/common/style.css">
<script src="js/checkSession.js"></script>
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link
	href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@100..900&display=swap"
	rel="stylesheet">
<link rel="icon" type="image/png" sizes="32x32"
	href="images/favicon-32x32.png">
</head>
<body>
	<div class="car_mypage_menu">
		<a href="main"><img class="mypage_logo"
			src="images/mypage_4scar_logo.png" alt="로고"></a> <a href="mypage">마이페이지
			홈</a>
		<div>
			<p>바로가기</p>
			<ul>
				<li><a href="car_list_filter">내차사기</a></li>
				<li><a href="req_regcar">내차팔기</a></li>
			</ul>
		</div>
		<div>
			<p>게시판</p>
			<ul>
				<li><a href="sell_review_board_list">판매후기</a></li>
				<li><a href="buy_review_board_list">구매후기</a></li>
				<li><a href="community_board_list">게시판</a></li>
			</ul>
		</div>
		<div>
			<p>고객지원</p>
			<ul>
				<li><a href="qna_insert">1:1 문의</a></li>
				<li><a href="faq">자주찾는 질문</a></li>
				<li><a href="company_directions">찾아오시는 길</a></li>
			</ul>
		</div>
		<div>
			<p>공지사항</p>
			<ul>
				<li><a href="notice_list">공지사항</a></li>
				<li><a href="notice_list?event">이벤트</a></li>
			</ul>
		</div>
		<div>
			<p>계정</p>
			<ul>
				<li><a href="user_unresister">회원탈퇴하기</a></li>
			</ul>
		</div>
	</div>