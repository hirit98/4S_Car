<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>후기 상세보기</title>
<link rel="icon" type="image/png" sizes="32x32"
	href="images/favicon-32x32.png">
<style>
th {
	padding: 3px;
}

td {
	padding: 2px;
}
</style>
</head>
<body>
	<div class="car_mypage">
		<jsp:include page="/WEB-INF/include/main_top.jsp" />

		<div class="car_review_board_content">
			<div align="center">
				<h2>${cont.getBuy_review_board_writer_id() }님
					후기 <img src="images/후기이미지.png" alt="">
				</h2>
				<br>
				<br>
				<table class="car_review_board_content_table" border="1"
					style="text-align: center;">
					<tr>
						<th>글 제목</th>
						<td>${cont.getBuy_review_board_title() }</td>

						<th>작성일</th>
						<td>${cont.getBuy_review_board_date().substring(0,10)}</td>
					</tr>

					<tr>
						<th>작성자</th>
						<td>${cont.getBuy_review_board_writer_id()}</td>

						<th>조회수</th>
						<td>${cont.getBuy_review_board_hit() }</td>
					</tr>

					<tr>
						<th>글 내용</th>
						<td colspan="3">
							${cont.getBuy_review_board_cont() }
						</td>
					</tr>
					<tr class="empty_img">
						<th>사진첨부</th>
						
						<c:if test="${!empty cont.getBuy_review_board_file() }">
							<td colspan="3">
								<img class="car_review_img" src="upload_images/${cont.getBuy_review_board_file() }" width="700px">
							</td>
						</c:if>
						<c:if test="${empty cont.getBuy_review_board_file() }">
							<td colspan="3">
								<h3>업로드된 사진이 없습니다.</h3>
							</td>
						</c:if>
						
					</tr>
				</table>
				<br>
				
				<div>
					<c:if test="${empty field }">
						<button type="button" value="돌아가기" class="sell_review_btn"
							onclick="location.href='buy_review_board_list?page=${page }'">
							&nbsp;&nbsp;
							<div class="cb">목록으로</div>
						</button>
					</c:if>
					<c:if test="${!empty field }">
						<button type="button" value="돌아가기" class="sell_review_btn"
							onclick="location.href='buy_review_board_search?page=${page }&field=${field }&keyword=${keyword }'">
							&nbsp;&nbsp;
							<div class="cb">목록으로</div>
						</button>
					</c:if>
				</div>
			</div>
		</div>

	</div>
	<jsp:include page="/WEB-INF/include/main_bottom.jsp" />
</body>
</html>