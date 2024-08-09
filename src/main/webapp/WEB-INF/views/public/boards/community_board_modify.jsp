<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>자유게시판 수정 폼 페이지</title>
</head>
<body>
	<jsp:include page="/WEB-INF/include/main_top.jsp" />
	<div class="community_board">
		<div align="center">
			<h2>자유게시판 글 수정</h2>
			<br>
			<br>
			<form method="post" action="community_board_modify_ok">
				<input type="hidden" name="community_board_no"
					value="${cont.getCommunity_board_no() }">
				<table border="1" class="community_content">
					<tr class="regiswidth">
						<th>작성자</th>
						<td>${cont.getCommunity_board_writer_id()}</td>
						<th>등록일</th>
						<td>${cont.getCommunity_board_date().substring(0,10) }</td>
					</tr>

					<tr class="regiswidth">
						<th>제목</th>
						<td>
						<input type="text" name="title"
							value="${cont.getCommunity_board_title()}">
						</td>
						<th>조회수</th>
						<td>${cont.getCommunity_board_hit() }</td>
					</tr>
					<tr class="community_textarea">
						<th>내용</th>
						<td colspan="3"><textarea id="" cols="20" rows="7"
								name="content">${cont.getCommunity_board_cont() }</textarea></td>
					</tr>
				</table>
				<br>
				<br>
				<button type="submit" class="community_btn" id="insert_btn">수정</button>
			</form>
		</div>
	</div>
	<jsp:include page="/WEB-INF/include/main_bottom.jsp" />

</body>
</html>