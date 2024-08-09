<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<meta charset="UTF-8">
<title>판매 후기 게시판 상세내용</title>
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
				<h2>${cont.getSell_review_board_writer_id() }님 후기</h2>
				<br>
				<br>
				<table border="1" class="car_review_board_content_table">
					<tr class="regiswidth">
						<th>글 제목</th>
						<td>${cont.getSell_review_board_title() }</td>
						<th>작성일</th>
						<td>${cont.getSell_review_board_date().substring(0,10) }</td>

					</tr>

					<tr class="regiswidth">
						<th>작성자</th>
						<td>${cont.getSell_review_board_writer_id() }</td>
						<th>조회수</th>
						<td>${cont.getSell_review_board_hit() }</td>
					</tr>

					<tr class="regiswidth">
						<th>글 내용</th>
						<td colspan="3" rowspan="1">${cont.getSell_review_board_cont() }</td>
					<tr class="regiswidth empty_img">
						<th>사진 첨부</th>
						<c:if test="${!empty cont.getSell_review_board_file() }">
						<td colspan="3" rowspan="1">
							<img class="car_review_img" src="upload_images/${cont.getSell_review_board_file() }" width="700px">
						</td>
						</c:if>
						<c:if test="${empty cont.getSell_review_board_file() }">
							<td colspan="3" rowspan="1">
								<h3>업로드된 사진이 없습니다.</h3>
							</td>
						</c:if>
					</tr>

				</table>
				<br>

				<div>
					<c:if test="${empty field }">
						<button type="button" value="돌아가기" class="sell_review_btn"
							onclick="location.href='sell_review_board_list?page=${page }'">
							&nbsp;&nbsp;
							<div class="cb">목록으로</div>
						</button>
					</c:if>
					<c:if test="${!empty field }">
						<button type="button" value="돌아가기" class="sell_review_btn"
							onclick="location.href='sell_review_board_search?page=${page }&field=${field }&keyword=${keyword }'">
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