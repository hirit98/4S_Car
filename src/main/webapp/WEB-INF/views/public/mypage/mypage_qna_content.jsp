<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>문의 내역 상세</title>
<link rel="stylesheet" href="css/common/style.css">
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
				<img alt="사람" src="images/프로필.png" class="people">
				<div class="car_registration_peopleinfo_text">
					<p>
						${dto.getUser_name() }님 <img alt="하트"
							src="images/하트-removebg-preview.png" class="heart">
					</p>
					<p>문의 내역 상세보기</p>
				</div>
			</div>
			<br>
			<div class="mypage_qna_content">
				<div align="center">
					<table class="mypage_qna_content_table" border="1" style="text-align: center;">
						<tr>
							<th class="mypage_qna_content_th">글 번호</th>
							<td class="mypage_qna_content_td">${cont.getQna_board_no() }</td>
							<th class="mypage_qna_content_th">문의 형태</th>
							<td class="mypage_qna_content_td">${cont.getQna_board_type() }</td>
						</tr>
						<tr>
							<th class="mypage_qna_content_th">문의 제목</th>
							<td class="mypage_qna_content_td">${cont.getQna_board_title() }</td>
							<th class="mypage_qna_content_th">작성자</th>
							<td class="mypage_qna_content_td">${cont.getQna_board_writer_id() }</td>
						</tr>
						<tr>
							<th class="mypage_qna_content_th">문의 일시</th>
							<td class="mypage_qna_content_td">${cont.getQna_board_date() }</td>
							<c:if test="${empty cont.getQna_board_update() }">
								<th class="mypage_qna_content_th">문의 상태</th>
								<td class="mypage_qna_content_td">N</td>
							</c:if>
							<c:if test="${!empty cont.getQna_board_update() }">
								<th class="mypage_qna_content_th">문의 상태</th>
								<td class="mypage_qna_content_td">Y</td>
							</c:if>
						</tr>
						<tr>
							<th class="mypage_qna_content_th">문의 내용</th>
							<td colspan="3" class="mypage_qna_content_td">${cont.getQna_board_cont() }</td>
						</tr>
						<tr>
							<th class="mypage_qna_content_th">사진</th>
							<c:if test="${!empty cont.getQna_board_file() }">
								<td colspan="3" class="mypage_qna_content_td"><img
									src="upload_images/${cont.getQna_board_file() }" width="650px">
								</td>
							</c:if>
							<c:if test="${empty cont.getQna_board_file() }">
								<td colspan="3" class="mypage_qna_content_td">
									업로드된 사진이 없습니다.
								</td>
							</c:if>
						</tr>
					</table>
					<br>

					<div>
						<button type="button" class="qna_btn"
							onclick="location.href='my_review?no=${cont.getQna_board_no()}'">돌아가기</button>
						<button type="button" class="qna_btn"
							onclick="if(confirm('해당 문의내역을 삭제하시겠습니까?')) {
						location.href='mypage_qna_content_delete?no=${cont.getQna_board_no()}'}else {return;}">삭제하기</button>
					</div>


				</div>

			</div>

		</div>



	</div>

</body>
</html>