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
					<p>판매후기글 수정하기</p>
				</div>
			</div>
			<br>

			<div class="car_myreview_board_content">
				<div align="center">
					<form method="post" action="mypage_sell_review_content_modify_ok"
						enctype="multipart/form-data">
						<input type="hidden" name="board_no"
							value="${cont.getSell_review_board_no() }"> <input
							type="hidden" name="title"
							value="${cont.getSell_review_board_title() }"> <input
							type="hidden" name="writer_id"
							value="${cont.getSell_review_board_writer_id() }"> <input
							type="hidden" name="content"
							value="${cont.getSell_review_board_cont() }"> <input
							type="hidden" name="modify_date"
							value="${cont.getSell_review_board_date() }">

						<table class="car_myreview_board_content_table" border="1"
							style="text-align: center;">
							<tr>
								<th>글 번호</th>
								<td><input type="text" class="content_input"
									name="board_no" value="${cont.getSell_review_board_no() }"
									readonly></td>
								<th>작성자</th>
								<td><input type="text" id="content_title" name="writer_id"
									value="${cont.getSell_review_board_writer_id() }" readonly/></td>

								<th>수정일</th>
								<td><input class="content_input" name="modify_date"
									value="${cont.getSell_review_board_date().substring(0,10)}"
									readonly></td>
							</tr>

							<tr>
								<th>글 제목</th>
								<td colspan="3"><input type="text" class="content_input"
									name="title"
									value="${cont.getSell_review_board_title()}">
								</td>

								<th>조회수</th>
								<td>${cont.getSell_review_board_hit() }</td>
							</tr>

							<tr>
								<th>내용</th>
								<td colspan="5">
									<div class="content_input_cont_div">
										<textarea class="content_input_cont" name="content" rows="50"
											cols="50">${cont.getSell_review_board_cont() }</textarea>
									</div>
								</td>

							</tr>
							<tr>
								<th>사진첨부</th>
								<td colspan="5">
									<div class="filebox">
										<input class="upload-name"
											value="${cont.getSell_review_board_file() }"
											placeholder="첨부파일"> <label for="file">파일찾기</label> <input
											type="file" id="file">
									</div>
								</td>

							</tr>

						</table>
						<br>
						<br>
						<button class="mypage_review_btn" type="submit" class="regBtn"
							id="insert_btn">수정하기</button>
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