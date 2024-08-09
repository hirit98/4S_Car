<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<meta charset="UTF-8">
<title>상세보기</title>
</head>
<body>
	<jsp:include page="/WEB-INF/include/main_top.jsp" />
	<div class="community_board">
		<div align="center" class="community_table">
			<h2>${cont.getCommunity_board_writer_id() }님
				글 <img src="images/후기이미지.png">
			</h2>
			<br> <br>
			<table class="community_content" border="1"
				style="text-align: center;">
				<tr>
					<th>글 제목</th>
					<td>${cont.getCommunity_board_title() }</td>

					<th>작성일</th>
					<td>${cont.getCommunity_board_date().substring(0,10)}</td>
				</tr>

				<tr>
					<th>작성자</th>
					<td>${cont.getCommunity_board_writer_id()}</td>

					<th>조회수</th>
					<td>${cont.getCommunity_board_hit() }</td>
				</tr>

				<tr>
					<th>글 내용</th>
					<td colspan="3">${cont.getCommunity_board_cont() }</td>

				</tr>
			</table>
			<br>
			<div>
				<c:if test="${empty field }">
					<button type="button" class="community_btn" onclick="location.href='community_board_list?page=${page}'">
						목록으로</button>
				</c:if>
				<c:if test="${!empty field }">
					<button type="button" class="community_btn" onclick="location.href='community_search?page=${page}&field=${field }&keyword=${keyword }'">
						목록으로</button>
				</c:if>
				<c:if test="${id eq cont.getCommunity_board_writer_id() }">
					<input type="button" value="수정하기" class="community_btn"
						onclick="location.href='community_board_modify?no=${cont.getCommunity_board_no() }'">&nbsp;&nbsp;
					<input type="button" value="삭제하기" class="community_btn"
						onclick="if(confirm('해당 게시글을 삭제하시겠습니까?')) {
                          location.href='community_board_delete?no=${cont.getCommunity_board_no() }'}else { return; }">
				</c:if>
			</div>
		</div>
	</div>
	<jsp:include page="/WEB-INF/include/main_bottom.jsp" />

</body>
</html>