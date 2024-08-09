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
<title>공지사항 수정하기</title>
<script src="http://code.jquery.com/jquery-1.9.0rc1.js"></script>
<link rel="icon" type="image/png" sizes="32x32"
	href="images/favicon-32x32.png">
</head>
<body>

	<jsp:include page="/WEB-INF/include/admin_side_bar.jsp" />

	<main class="car_managerpage_menual">
		<div class="admin_notice_modify">
			<section class="admin_notice_modify_title">
				<img src="images/프로필.png" alt="사람" class="people">
				<div class="admin_notice_modify_text">
					<p>공지사항 수정</p>
				</div>
				<img src="images/관리자페이지_톱니바퀴_스패너.png" alt="스패너">
			</section>

			<section class="admin_notice_modify_content">
				<h2>공지사항 내용</h2>
				<form method="post" action="admin_notice_modify_ok">
					<table>
						<tr>
							<th>글번호</th>
							<td><input type="text" name="no"
								value="${dto.getNotice_board_no() }" readonly></td>
						</tr>

						<tr>
							<th>작성자</th>
							<td><input type="text" name="writer"
								value="${dto.getNotice_board_writer_id() }" readonly></td>
						</tr>

						<tr>
							<th>제목</th>
							<td><input class="admin_notice_modify_board_title"
								type="text" name="title" value="${dto.getNotice_board_title() }">
							</td>
						</tr>

						<tr class="admin_notice_modify_content_content">
							<th>내용</th>
							<td><textarea inputmode="text" name="cont">${fn:replace(dto.getNotice_board_cont(), newLine, br) }</textarea></td>
						</tr>

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
					<div class="admin_notice_modify_button">
						<button type="reset">다시작성</button>&nbsp;&nbsp;&nbsp;
						<button type="submit">수정하기</button>
					</div>
				</form>
			</section>
		</div>
	</main>
</body>
</html>