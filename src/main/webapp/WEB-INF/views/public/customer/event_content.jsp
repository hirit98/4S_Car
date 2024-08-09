<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html>
<head>
<meta charset="UTF-8">
<title>이벤트</title>
</head>
<body>
	<jsp:include page="/WEB-INF/include/main_top.jsp" />
	<div class="car_review_board_content">
		<div align="center">
			<h2>이벤트</h2>
			<br> <br>
			<table class="car_review_board_content_table" border="1">
				<tr>
					<th>글 제목</th>
					<td>${cont.getEvent_board_title() }</td>
					<c:if test="${empty cont.getEvent_board_update() }">
						<th>작성일</th>
						<td>${cont.getEvent_board_date().substring(0,10) }</td>
					</c:if>
					<c:if test="${!empty cont.getEvent_board_update() }">
						<th>작성일</th>
						<td>${cont.getEvent_board_update().substring(0,10)}</td>
					</c:if>
				</tr>

				<tr>
					<th>작성자</th>
					<td>관리자</td>
					<th>조회수</th>
					<td>${cont.getEvent_board_hit() }</td>
				</tr>
				<tr>
					<th>글 내용</th>
					<td colspan="3">${cont.getEvent_board_cont() }</td>
				</tr>
				<tr class="content_img">
					<th>사진첨부</th>
					<c:if test="${!empty cont.getEvent_board_file() }">
						<td colspan="3"><a
							href="<%=request.getContextPath() %>/upload_images/${cont.getEvent_board_file() }"
							target="_blank">${cont.getEvent_board_file() }</a></td>
					</c:if>
					<c:if test="${empty cont.getEvent_board_file() }">
						<td colspan="3"></td>
					</c:if>
				</tr>

			</table>
			<br>
			<c:if test="${empty field }">
				<input type="button" value="목록으로" class="notice_btn"
				onclick="location.href='notice_list?event_page=${event_page}'">
			</c:if>
			<c:if test="${!empty field }">
				<input type="button" value="목록으로" class="notice_btn"
				onclick="location.href='event_search?event_page=${event_page}&field=${field }&keyword=${keyword }'">
			</c:if>
		</div>
	</div>
	<jsp:include page="/WEB-INF/include/main_bottom.jsp" />
</body>
</html>
