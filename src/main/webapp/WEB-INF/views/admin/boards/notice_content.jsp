<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
request.setAttribute("newLine", "\n");
request.setAttribute("br", "<br>");
%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>공지사항 내용</title>
<script src="http://code.jquery.com/jquery-1.9.0rc1.js"></script>
<link rel="icon" type="image/png" sizes="32x32"
	href="images/favicon-32x32.png">
</head>
<body>

	<jsp:include page="/WEB-INF/include/admin_side_bar.jsp" />

	<main class="car_managerpage_menual">
		<div class="admin_notice_content">
			<section class="admin_notice_content_title">
				<img src="images/프로필.png" alt="사람" class="people">
				<div class="admin_notice_content_text">
					<p>공지사항 관리</p>
				</div>
				<img src="images/관리자페이지_톱니바퀴_스패너.png" alt="스패너">
			</section>

			<section class="admin_notice_content_content">
				<h2>공지사항 내용</h2>
				<table>
					<tr>
						<th>글번호</th>
						<td>${dto.getNotice_board_no() }</td>
					</tr>

					<tr>
						<th>작성자</th>
						<td>${dto.getNotice_board_writer_id() }</td>
					</tr>

					<tr>
						<th>제목</th>
						<td>${dto.getNotice_board_title() }</td>
					</tr>

					<tr class="admin_notice_content_cont">
						<th>내용</th>
						<td><textarea readonly>${fn:replace(dto.getNotice_board_cont(), newLine, br) }</textarea></td>
					</tr>
					<c:if test="${empty dto.getNotice_board_update() }">
						<tr>
							<th>등록일시</th>
							<td>${dto.getNotice_board_date() }</td>
						</tr>
					</c:if>
					<c:if test="${!empty dto.getNotice_board_update() }">
						<tr>
							<th>수정일시</th>
							<td>${dto.getNotice_board_update() }</td>
						</tr>
					</c:if>

					<tr>
						<th>조회수</th>
						<td>${dto.getNotice_board_hit() }</td>
					</tr>

					<tr>
						<th>사진</th>
						<c:if test="${!empty dto.getNotice_board_file() }">
							<td colspan="3"><a
								href="upload_images/${dto.getNotice_board_file() }"
								target="_blank">${dto.getNotice_board_file() }</a></td>
						</c:if>
						<c:if test="${empty dto.getNotice_board_file() }">
							<td colspan="3">
								<h3>업로드된 사진이 없습니다.</h3>
							</td>
						</c:if>
					</tr>
				</table>
			</section>

			<section class="admin_notice_content_button">
				<button
					onclick="location.href='admin_notice_modify?no=${dto.getNotice_board_no() }'">수정하기</button>
				&nbsp;&nbsp;&nbsp;
				<button
					onclick="if(confirm('공지사항을 삭제하시겠습니까?')) {
				location.href='admin_notice_delete?no=${dto.getNotice_board_no() }'}
			else { return; }">삭제하기</button>
				&nbsp;&nbsp;&nbsp;
				<c:if test="${empty field }">
					<button onclick="location.href='admin_notice_list?page=${page }'">목록으로</button>
				</c:if>
				<c:if test="${!empty field }">
					<button
						onclick="location.href='admin_notice_search?page=${page }&field=${field }&keyword=${keyword }'">목록으로</button>
				</c:if>
			</section>

		</div>
	</main>
</body>
</html>