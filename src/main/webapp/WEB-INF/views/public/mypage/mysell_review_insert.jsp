<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
// 줄바꿈 처리
pageContext.setAttribute("newLine", "\n");
pageContext.setAttribute("br", "<br/>");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>판매후기 등록 페이지</title>
<link rel="icon" type="image/png" sizes="32x32"
	href="images/favicon-32x32.png">
</head>
<body>
	<div class="car_mypage">
		<jsp:include page="/WEB-INF/include/mypage_side_bar.jsp" />
		<div class="car_registration_menual">
			<div class="car_registration_peopleinfo">
				<img src="images/프로필.png" alt="사람" class="people">
				<div class="car_registration_peopleinfo_text">
					<p>${dto.getUser_name() }
						님 <img src="images/하트-removebg-preview.png" alt="하트" class="heart">
					</p>
					<p>판매후기 글 등록하기</p>
				</div>
			</div>
			<br>

			<div class="car_myreview_insert_board_content" align="center">
				<form method="post" action="mypage_sell_review_insert_ok"
					enctype="multipart/form-data" id="form" name="form" onsubmit="return check();">
					<input type="hidden" name="t_no"
						value="${cont.getTransaction_no() }">
					<table border="1" class="car_myreview_insert_board_content_table"
						style="text-align: center;">
						<tr>
							<th>차량정보</th>
							<td><input type="text" name="car_info"
								class="review_insert_input_cont"
								value="${cont.getTransaction_car_std_no()}" readonly></td>
						</tr>
						<tr>
							<th>작성자</th>
							<td><input type="text" name="writer"
								class="review_insert_input_cont" value="${dto.getUser_id() }"
								readonly></td>
						</tr>

						<tr>
							<th>제목</th>
							<td><input type="text" name="title"
								class="review_insert_input_cont"></td>
						</tr>
						<tr>
							<th>내용</th>
							<td><textarea id="" cols="20" rows="7" name="content"
									class="review_insert_input_cont"></textarea></td>
						</tr>
						<tr>
							<th>첨부파일</th>
							<td>
								<div class="filebox">
									<input class="upload-name" value="첨부파일" multiple
										placeholder="첨부파일"> <label for="file">파일찾기</label> <input
										type="file" id="file" name="sellReview_file">
								</div>
							</td>
						</tr>


					</table>
					<br>
					<br>
					<button type="submit" class="mypage_review_btn" id="insert_btn">등록하기</button>
				</form>
			</div>
		</div>
	</div>

	<script type="text/javascript">
		$("#file").on('change', function() {
			var fileName = $("#file").val();
			$(".upload-name").val(fileName);
		});
	</script>
	<script src="js/empty.js"></script>
</body>
</html>