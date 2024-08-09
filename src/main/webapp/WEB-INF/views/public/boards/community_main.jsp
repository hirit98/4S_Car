<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시판 메인페이지</title>
<link rel="icon" type="image/png" sizes="32x32"
	href="images/favicon-32x32.png">
</head>
<body>
	<jsp:include page="/WEB-INF/include/main_top.jsp" />
	<section class="review_list">
		<h2>커뮤니티</h2>
		<div class="review_list_container">
			<div class="review_list_box">
				<p class="review_p1">사고 팔기 모든 후기를 한번에 확인할 수 있는</p>
				<p class="review_p2">4scar커뮤니티</p>

				<div class="review_list_menu">
					<div class="review1"
						onclick="location.href='buy_review_board_list'">
						<img src="images/내차팔기.png" alt="">
						<p>내차 사기 게시판</p>
						<hr>
					</div>


					<div class="review2"
						onclick="location.href='sell_review_board_list'">
						<img src="images/내차사기.png" alt="">
						<p>내차 팔기 게시판</p>
						<hr>
					</div>


					<div class="review3" onclick="location.href='community_board_list'">
						<img src="images/게시판.png" alt="">
						<p>자유 게시판</p>
						<hr>
					</div>
				</div>
			</div>
		</div>
	</section>
	<jsp:include page="/WEB-INF/include/main_bottom.jsp" />
</body>
</html>