<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>판매요청 글 수정하기</title>
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
					<p>${dto.getUser_name() }님
						<img src="images/하트-removebg-preview.png" alt="하트" class="heart">
					</p>
					<p>요청글 수정하기</p>
				</div>
			</div>

			<div align="center" class="mycar_info_modify">
				<form method="post" action="mycar_info_modify_ok"
					enctype="multipart/form-data">
					<input type="hidden" name="board_no"
						value="${cont.getReq_sell_board_no() }"> <input
						type="hidden" name="user" value="${dto.getUser_no() }"> <input
						type="hidden" name="car_no"
						value="${cont.getReq_sell_board_car_no() }">
					<table border="1">
						<tr>
							<th>차량 번호</th>
							<td colspan="4">${cont.getReq_sell_board_car_no() }</td>

							<th>* 주행 거리</th>
							<td><input type="text" name="distance" required></td>
						</tr>

						<tr>
							<th>제조사</th>
							<td colspan="6">${cont.getReq_sell_board_car_std_no().split('_')[0] }</td>
						</tr>

						<tr>
							<th>차종</th>
							<td colspan="6">${cont.getReq_sell_board_car_std_no().split('_')[1] }</td>
						</tr>

						<tr>
							<th>연식</th>
							<td colspan="6">
								${cont.getReq_sell_board_car_std_no().split('_')[2] }년
								${cont.getReq_sell_board_car_std_no().split('_')[3] }월</td>
						</tr>

						<tr>
							<th>연료</th>
							<td colspan="6">${cont.getReq_sell_board_car_std_no().split('_')[4] }</td>
						</tr>

						<tr>
							<th>변속기</th>
							<td colspan="6">${cont.getReq_sell_board_car_std_no().split('_')[5] }</td>
						</tr>

						<tr>
							<th>색상</th>
							<td colspan="6">${cont.getReq_sell_board_car_std_no().split('_')[6] }</td>
						</tr>

						<tr>
							<th>모델</th>
							<td colspan="6">${cont.getReq_sell_board_car_std_no().split('_')[7] }</td>
						</tr>

						<tr>
							<th>* 희망가격</th>
							<td colspan="6"><input type="text" name="price" required>
							</td>
						</tr>

						<tr class="mycar_info_modify_detail">
							<th>* 상세정보</th>
							<td colspan="6">
							<textarea rows="15" cols="152" name="detail" required>${cont.getReq_sell_board_car_detail() }</textarea>
							</td>
						</tr>

						<tr class="mycar_info_modify_img">
							<th>* 차 사진</th>
							<td colspan="6">
								<div class="mycar_info_filebox">
									<input class="upload-modify" value="첨부파일" multiple placeholder="첨부파일">
									<label for="file">파일찾기</label>
									<input type="file" id="file" name="car_file">
								</div>
							</td>
						</tr>
					</table>
					<br> 
					<input type="submit" value="수정하기" class="mycar_info_btn"> &nbsp;&nbsp; 
					<input type="reset" value="다시쓰기" class="mycar_info_btn">
				</form>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		$("#file").on('change', function() {
			var fileName = $("#file").val();
			$(".upload-modify").val(fileName);
		});
	</script>
</body>
</html>