<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>자유 게시판 글 등록</title>
</head>
<body>
	<jsp:include page="/WEB-INF/include/main_top.jsp" />
	<div class="community_insert">
		<div align="center" class="community_table">
			<h2>
				자유게시판 글쓰기 <img src="images/글쓰기.png">
			</h2>
			<br> <br>
			<form method="post" action="community_insert_ok">
				<table border="1" class="community_insert_content">
					<tr class="regiswidth">
						<th>작성자</th>
						<td><input type="text" name="writer" id=""></td>
						<th>등록일</th>
						<td></td>
					</tr>

					<tr class="regiswidth">
						<th>제목</th>
						<td colspan="4"><input type="text" name="title"
							class="community_write" id=""></td>
					</tr>
					<tr class="regiswidth">
						<th>내용</th>
						<td colspan="4"><textarea id="" cols="20" rows="7"
								name="content"></textarea></td>
					</tr>
				</table>
				<br> <br>
				<button type="submit" class="community_btn" id="insert_btn">등록</button>
			</form>
		</div>
	</div>
	<jsp:include page="/WEB-INF/include/main_bottom.jsp" />
</body>
</html>