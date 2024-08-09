<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>이벤트 등록하기</title>
<script src="http://code.jquery.com/jquery-1.9.0rc1.js"></script>
<link rel="icon" type="image/png" sizes="32x32"
	href="images/favicon-32x32.png">
</head>
<body>

	<jsp:include page="/WEB-INF/include/admin_side_bar.jsp" />

	<main class="car_managerpage_menual">
		<div class="admin_event_insert">
			<section class="admin_event_insert_title">
				<img src="images/프로필.png" alt="사람" class="people">
				<div class="admin_event_insert_text">
					<p>이벤트 등록</p>
				</div>
				<img src="images/관리자페이지_톱니바퀴_스패너.png" alt="스패너">
			</section>

			<section class="admin_event_insert_content">
				<h2>이벤트 내용</h2>
				<form method="post" action="admin_event_insert_ok" enctype="multipart/form-data">
					<table>
						<tr>
							<th>작성자</th>
							<td><input type="text" name="writer"
								value="${dto.getAdmin_id() }" readonly></td>
						</tr>
						<tr>
							<th>제목</th>
							<td><input class="admin_event_insert_board_title"
								type="text" name="title" placeholder="제목을 입력하세요.">
							</td>
						</tr>
						<tr class="admin_event_insert_content_content">
							<th>내용</th>
							<td><textarea inputmode="text" name="cont" placeholder="내용을 입력하세요."></textarea></td>
						</tr>
						<tr class="admin_insert_file">
							<th>사진</th>
							<td><input type="file" name="event_file" placeholder="첨부파일을 등록하세요."></td>
	 					</tr>
					</table>
					<div class="admin_event_insert_button">
						<button type="reset">다시작성</button>
						&nbsp;&nbsp;&nbsp;
						<button type="submit">등록하기</button>
					</div>
				</form>
			</section>
		</div>
	</main>
</body>
</html>