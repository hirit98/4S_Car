<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>고객지원 메인페이지</title>
<link rel="icon" type="image/png" sizes="32x32"
	href="images/favicon-32x32.png">
</head>
<body>
	<jsp:include page="/WEB-INF/include/main_top.jsp" />
	<section class="review_list">
		<h2>고객지원</h2>
		<div class="review_list_container">
			<div class="review_list_box">
				<p class="review_p1">4scar에 대해 궁금한게 있다면?</p>
				<div class="review_list_menu">
					<div class="review1" onclick="location.href='qna_insert'">
						<img src="images/1대1문의.png" alt="">
						<p>1:1 문의</p>
						<hr>
					</div>
					<div class="review2" onclick="location.href='faq'">
						<img src="images/자주찾는 질문.png" alt="">
						<p>자주찾는 질문</p>
						<hr>
					</div>
					<div class="review3" onclick="location.href='company_info'">
						<img src="images/찾아오시는길.png" alt="">
						<p>찾아오시는 길</p>
						<hr>
					</div>
				</div>
			</div>
		</div>
	</section>
	<jsp:include page="/WEB-INF/include/main_bottom.jsp" />
</body>
</html>