<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>차량 상세보기</title>
<link rel="icon" type="image/png" sizes="32x32"
	href="images/favicon-32x32.png">
<style>
th {
	padding: 3px;
}

td {
	padding: 2px;
}
</style>
</head>
<body>
	<div class="car_mypage">
		<jsp:include page="/WEB-INF/include/mypage_side_bar.jsp" />

		<div class="mycar_registration_menual">
			<div class="mycar_registration_peopleinfo">
				<img src="images/프로필.png" alt="사람" class="people">
				<div class="mycar_registration_peopleinfo_text">
					<p>${dto.getUser_name() }
						님 <img src="images/하트-removebg-preview.png" alt="하트" class="heart">
					</p>
					<p>판매요청 글 상세보기</p>
				</div>
			</div>
			<br>
			<div class="mycar_info_content" align="center">
				<table class="info_table">
					<tr>
						<th class="info_table_th">차량 번호</th>
						<td class="info_table_td">${cont.getReq_sell_board_car_no() }</td>

						<th class="info_table_dis">주행 거리</th>
						<td class="info_table_td"><fmt:formatNumber>${cont.getReq_sell_board_car_distance() }</fmt:formatNumber>
							km</td>
					</tr>

					<tr>
						<th class="info_table_th">제조사</th>
						<td colspan="6" class="info_table_td">${cont.getReq_sell_board_car_std_no().split('_')[0] }</td>
					</tr>

					<tr>
						<th class="info_table_th">차종</th>
						<td colspan="6" class="info_table_td">${cont.getReq_sell_board_car_std_no().split('_')[1] }</td>
					</tr>

					<tr>
						<th class="info_table_th">연식</th>
						<td colspan="6" class="info_table_td">
							${cont.getReq_sell_board_car_std_no().split('_')[2] }년
							${cont.getReq_sell_board_car_std_no().split('_')[3] }월</td>
					</tr>

					<tr>
						<th class="info_table_th">연료</th>
						<td colspan="6" class="info_table_td">${cont.getReq_sell_board_car_std_no().split('_')[4] }</td>
					</tr>

					<tr>
						<th class="info_table_th">변속기</th>
						<td colspan="6" class="info_table_td">${cont.getReq_sell_board_car_std_no().split('_')[5] }</td>
					</tr>

					<tr>
						<th class="info_table_th">색상</th>
						<td colspan="6" class="info_table_td">${cont.getReq_sell_board_car_std_no().split('_')[6] }</td>
					</tr>

					<tr>
						<th class="info_table_th">모델</th>
						<td colspan="6" class="info_table_td">${cont.getReq_sell_board_car_std_no().split('_')[7] }</td>
					</tr>

					<tr>
						<th class="info_table_th">희망가격</th>
						<td colspan="6" class="info_table_td"><fmt:formatNumber>${cont.getReq_sell_board_car_price() }</fmt:formatNumber>
							원</td>
					</tr>

					<tr>
						<th class="info_table_th">상세정보</th>
						<td height="300px" colspan="6" class="info_table_td"><textarea
								rows="15" cols="152" readonly>${cont.getReq_sell_board_car_detail() }</textarea></td>
					</tr>

					<tr>
						<th class="info_table_th">차량사진</th>
						<td colspan="6" class="info_table_td"><img width="300px"
							height="200px"
							src="upload_images/${cont.getReq_sell_board_car_file() }">
						</td>
					</tr>

					<tr>
						<th class="info_table_th">등록일</th>
						<c:if test="${empty cont.getReq_sell_board_update() }">
							<td colspan="6" class="info_table_td">${cont.getReq_sell_board_date().substring(0, 16) }</td>
						</c:if>
						<c:if test="${!empty cont.getReq_sell_board_update() }">
							<td colspan="6" class="info_table_td">${cont.getReq_sell_board_update().substring(0, 16) }</td>
						</c:if>
					</tr>
				</table>
				<div class="btn_boxs">
					<input type="button" value="수정하기" class="mycar_info_btn"
						onclick="location.href='mycar_info_modify?no=${cont.getReq_sell_board_no() }'">&nbsp;&nbsp;
					<input type="button" value="삭제하기" class="mycar_info_btn"
						onclick="if(confirm('해당 게시글을 삭제하시겠습니까?')) {
				              location.href='mycar_info_delete?no=${cont.getReq_sell_board_no() }'}else { return; }">
				</div>
			</div>
		</div>
	</div>
</body>
</html>