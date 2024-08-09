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
<title>Insert title here</title>
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
					<p>구매후기글 등록하기</p>
				</div>
			</div>
			<br>

			<div class="car_myreview_insert_board_content">
				<div align="center">
					<form method="post" action="mypage_buy_review_insert_ok"
						enctype="multipart/form-data">
						<input type="hidden" name="t_no"
							value="${cont.getTransaction_no() }">

						<table class="car_myreview_insert_board_content_table" border="1"
							style="text-align: center;">
							<tr>
								<th>차량정보</th>
								<td><input class="review_insert_input_cont" type="text"
									name="car_info" value="${cont.getTransaction_car_std_no()}"
									readonly></td>
							</tr>
							<tr>
								<th>작성자</th>
								<td><input class="review_insert_input_cont" type="text"
									name="buy_writer" value="${dto.getUser_id() }" readonly>
								</td>
							</tr>

							<tr>
								<th>제목</th>
								<td><input class="review_insert_input_cont" type="text"
									name="buy_title" id=""></td>
							</tr>

							<tr>
								<th>내용</th>
								<td><textarea class="review_insert_input_cont" id=""
										name="buy_content"></textarea></td>
							</tr>
							<tr>
								<th>첨부파일</th>
								<td>
									<div class="filebox">
										<input class="upload-name" value="첨부파일" multiple
											placeholder="첨부파일"> <label for="file">파일찾기</label> <input
											type="file" id="file" name="buyReview_file">
									</div>
								</td>
							</tr>

						</table>
						<br>
						<br>
						<button class="mypage_review_btn" type="submit" class="regBtn"
							id="insert_btn">등록하기</button>
					</form>
				</div>
			</div>
		</div>
	</div>

	<script type="text/javascript">
		$("#file").on('change', function() {
			var fileName = $("#file").val();
			$(".upload-name").val(fileName);
		});
	</script>
</body>
</html>