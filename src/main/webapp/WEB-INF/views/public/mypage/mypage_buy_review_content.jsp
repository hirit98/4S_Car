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
		<jsp:include page="/WEB-INF/include/mypage_side_bar.jsp" />

		<div class="car_registration_menual">
			<div class="car_registration_peopleinfo">
				<img src="images/프로필.png" alt="사람" class="people">
				<div class="car_registration_peopleinfo_text">
					<p>${dto.getUser_name() }
						님 <img src="images/하트-removebg-preview.png" alt="하트" class="heart">
					</p>
					<p>구매후기글 상세보기</p>
				</div>
			</div>
			<br>

			<div class="car_myreview_board_content">
				<div align="center">

					<table class="car_myreview_board_content_table" border="1"
						style="text-align: center;">
						<tr>
							<th>글 제목</th>
							<td>${cont.getBuy_review_board_title() }</td>

							<th>작성일</th>
							<td>${cont.getBuy_review_board_date()}</td>
						</tr>

						<tr>
							<th>작성자</th>
							<td>${cont.getBuy_review_board_writer_id()}</td>

							<th>조회수</th>
							<td>${cont.getBuy_review_board_hit() }</td>
						</tr>
						<tr>
							<th>글 내용</th>
							<td colspan="3"><textarea class="content_input_cont"
									name="content" rows="50" cols="50" readonly>${cont.getBuy_review_board_cont() }</textarea>
							</td>
						</tr>
						<tr>
							<th>사진첨부</th>
							<c:if test="${!empty cont.getBuy_review_board_file() }">
								<td colspan="3"><img
									src="upload_images/${cont.getBuy_review_board_file() }"
									width="700px"></td>
							</c:if>
							<c:if test="${empty cont.getBuy_review_board_file() }">
								<td colspan="3">업로드된 사진이 없습니다.</td>
							</c:if>
						</tr>
					</table>
					<br>
					<div>
						<input class="mypage_review_btn" type="button" value="글 수정하기"
							onclick="location.href='mypage_buy_review_content_modify?no=${cont.getBuy_review_board_no()}'">&nbsp;&nbsp;
						<input class="mypage_review_btn" type="button" value="글 삭제하기"
							onclick="if(confirm('해당 게시글을 삭제하시겠습니까?')) {
                          location.href='mypage_buy_review_content_delete?no=${cont.getBuy_review_board_no()}'}else { return; }">
					</div>
				</div>
			</div>
		</div>

	</div>
</body>
</html>