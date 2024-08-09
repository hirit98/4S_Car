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
<title>고객 공지사항</title>
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
	<jsp:include page="/WEB-INF/include/main_top.jsp" />

	<div class="car_review_board_content">
		<div align="center">
			<h2>공지사항</h2>
			<br> <br>
			<table class="car_review_board_content_table" border="1"
				style="text-align: center;">
				<tr>
					<th>글 제목</th>
					<td>${cont.getNotice_board_title() }</td>
					<c:if test="${empty cont.getNotice_board_update() }">
						<th>작성일</th>
						<td>${cont.getNotice_board_date().substring(0,10)}</td>
					</c:if>
					<c:if test="${!empty cont.getNotice_board_update() }">
						<th>작성일</th>
						<td>${cont.getNotice_board_update().substring(0,10)}</td>
					</c:if>
				</tr>

				<tr>
					<th>작성자</th>
					<td>관리자</td>

					<th>조회수</th>
					<td>${cont.getNotice_board_hit() }</td>
				</tr>

				<tr>
					<th>글 내용</th>
					<td colspan="3"><textarea class="content_input_cont" rows="50"
							cols="50" readonly>${cont.getNotice_board_cont() }</textarea></td>
				</tr>
				<tr class="content_img">
					<th>사진첨부</th>
					<c:if test="${!empty cont.getNotice_board_file() }">
						<td colspan="3"><a
							href="<%=request.getContextPath() %>/upload_images/${cont.getNotice_board_file() }"
							target="_blank">${cont.getNotice_board_file() }</a></td>
					</c:if>
					<c:if test="${empty cont.getNotice_board_file() }">
						<td colspan="3">업로드된 사진이 없습니다.</td>
					</c:if>
				</tr>
			</table>
			<br>
			<c:if test="${page != 0 }">
				<div>
					<c:if test="${empty field }">
						<button type="button" class="notice_btn" onclick="location.href='notice_list?notice_page=${page }'">목록으로</button>
					</c:if>
					<c:if test="${!empty field }">
						<button type="button" class="notice_btn" onclick="location.href='notice_search?notice_page=${page }&field=${field }&keyword=${keyword }'">목록으로</button>
					</c:if>
				</div>
			</c:if>
			<c:if test="${page == 0 }">
				<div>
					<button type="button" class="notice_btn"
						onclick="location.href='notice_list'">목록으로</button>
				</div>
			</c:if>
		</div>
	</div>
	<jsp:include page="/WEB-INF/include/main_bottom.jsp" />
</body>
</html>