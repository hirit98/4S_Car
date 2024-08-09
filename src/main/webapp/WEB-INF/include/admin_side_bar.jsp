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
	<div class="car_managerpage">
		<div class="car_managerpage_menu">
			<div class="car_managerpage_logo">
				<a href="main"><img class="managerpage_logo"
					src="images/관리자 아이콘.png" alt="로고"></a>
			</div>
			<a href="admin_main">관리자 홈</a>
			<div>
				<p>회원 관리</p>
				<ul>
					<li><a href="admin_user_list">회원 목록</a></li>
					<li><a href="admin_qna_list">고객 문의함</a></li>
				</ul>
			</div>
			<div>
				<p>차량 관리</p>
				<ul>
					<li><a href="admin_req_regcar_list">차량 등록 요청 게시판</a></li>
					<li><a href="admin_sell_car_list">차량 등록 게시판</a></li>
				</ul>
			</div>
			<div>
				<p>공지사항</p>
				<ul>
					<li><a href="admin_notice_list">공지사항 관리</a></li>
					<li><a href="admin_event_list">이벤트 관리</a></li>
				</ul>
			</div>
			<div>
				<p>환경설정</p>
				<ul>
					<li><a href="admin_company_info_modify">회사정보 관리</a></li>
					<li><a href="#">실시간 채팅</a></li>
					<li><a href="#">약관 설정</a></li>
					<li><a href="#">도메인 연결</a></li>
				</ul>
			</div>

		</div>
	</div>