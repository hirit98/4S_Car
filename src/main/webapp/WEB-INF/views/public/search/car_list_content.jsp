<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html>
<head>
<meta charset="UTF-8">
<title>내차 사기 상세보기</title>
<link rel="icon" type="image/png" sizes="32x32"
	href="images/favicon-32x32.png">
<style>
tr th:first-child {
	width: 60px;
}
</style>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
</head>
<body>
	<jsp:include page="/WEB-INF/include/main_top.jsp" />
	<c:set value="${carPrice }" var="carPrice" />
	<div class="car_list_content">
		<div class="car_list_content_carinfo">
			<span class="car_list_content_carinfo_span"><a
				href="car_list_filter" class="car_list_content_carinfo_span_a">차량검색</a>
				<img src="images/carlist화살표.png" /> 선택 차량상세 정보</span>
			<div class="car_list_content_container">
				<div class="car_text_box">
					<div class="car_text_box1">
						<div class="car_text_box1_carname">${cont.getSell_board_car_std_no().split('_')[7] }</div>
						<div class="text_box1_inner_text_img">
							<div class="text_box1_inner_text">
								<img class="text_box1_inner_img1" src="images/조회수.png" alt="" />
								<div>${cont.getSell_board_hit()}</div>
							</div>
							<div class="text_box1_inner_text">
								<img class="text_box1_inner_img2" src="images/찜목록수.png" alt="" />
								<div>${favorite_count }</div>
							</div>
						</div>
					</div>
					<hr>
				</div>
				<div class="car_image_box">
					<div class="car_image_box_circle"></div>
					<c:if test="${!check_favorite }">
						<button class="car_image_heartbutton" type="button" onclick="location.href='car_list_favorite_insert?no=${cont.getSell_board_no() }'">
							<div class="right_area">
								<a href="javascript:;" class="icon heart">
	  							   <img src="https://cdn-icons-png.flaticon.com/512/812/812327.png" alt="찜하기">
	  							</a>
							</div>
						</button>
					</c:if>
					<c:if test="${check_favorite }">
						<button class="car_image_heartbutton" type="button" onclick="location.href='car_list_favorite_delete?no=${cont.getSell_board_no() }'">
							<div class="right_area">
								<a href="javascript:;" class="icon heart">
	  							   <img src="https://cdn-icons-png.flaticon.com/512/803/803087.png" alt="찜하기 해제">
	  							</a>
							</div>
						</button>
					</c:if>
					<div class="car_image_box_inner">
						<div class="car_image">
							<img src="upload_images/${cont.getSell_board_car_file() }">
						</div>
						<div class="car_image_box_text">
							<p>4scar에서 차량 구매시 0.5% 금리할인</p>
						</div>
					</div>
					<img class="carpicture_info" src="images/차사진정보.png" />
					<div class="carlistinfo_content_box">
						<div class="carlistinfo_content_box_inner1">
							<div class="carlistinfo_price_text1">판매 가격</div>
							<div class="carlistinfo_price_text2">
								<fmt:formatNumber>${cont.getSell_board_car_price() }</fmt:formatNumber>
								원
							</div>
							<div>
								<button class="popup_payment_button" type="button"
									id="popup_payment_type">지금 구매하기</button>
							</div>
						</div>
						<div class="carlistinfo_content_box_inner1_1">차량기본정보?</div>
						<div class="carlistinfo_content_box_inner2">
							<div class="carlistinfo_content_box_inner2_text">
								<div class="carlistinfo_content_box_inner2_text_innerbox">
									<div class="carlistinfo_content_box_inner2_circle">
										<img class="carlistinfo_content_box_inner2_circle_img"
											src="images/제조사.png" />

									</div>
									<div class="carlistinfo_content_box_inner2_circle_text">
										<div class="carlistinfo_content_box_inner2_circle_text_1">제조사</div>
										<input name="car_content_info1"
											class="carlistinfo_content_box_inner2_circle_text_2"
											value="${cont.getSell_board_car_std_no().split('_')[0] }" readonly/>
									</div>
								</div>
								<div class="carlistinfo_content_box_inner2_text_innerbox">
									<div class="carlistinfo_content_box_inner2_circle">
										<img class="carlistinfo_content_box_inner2_circle_img1"
											src="images/자동차연식.png" />

									</div>
									<div class="carlistinfo_content_box_inner2_circle_text">
										<div class="carlistinfo_content_box_inner2_circle_text_1">연식</div>
										<input name="car_content_info2"
											class="carlistinfo_content_box_inner2_circle_text_2"
											value="${cont.getSell_board_car_std_no().split('_')[2] }년 ${cont.getSell_board_car_std_no().split('_')[3] }월" readonly/>
									</div>
								</div>

							</div>
							<div class="carlistinfo_content_box_inner2_text">
								<div class="carlistinfo_content_box_inner2_text_innerbox">
									<div class="carlistinfo_content_box_inner2_circle">
										<img class="carlistinfo_content_box_inner2_circle_img2"
											src="images/차량번호판.png" />

									</div>
									<div class="carlistinfo_content_box_inner2_circle_text">
										<div class="carlistinfo_content_box_inner2_circle_text_1">차량번호</div>
										<input name="car_content_info3"
											class="carlistinfo_content_box_inner2_circle_text_2"
											value="${cont.getSell_board_car_no() }" readonly/>
									</div>
								</div>
								<div class="carlistinfo_content_box_inner2_text_innerbox">
									<div class="carlistinfo_content_box_inner2_circle">
										<img class="carlistinfo_content_box_inner2_circle_img"
											src="images/주행거리.png" />

									</div>
									<div class="carlistinfo_content_box_inner2_circle_text">
										<div class="carlistinfo_content_box_inner2_circle_text_1">주행거리</div>
										<input name="car_content_info4"
											class="carlistinfo_content_box_inner2_circle_text_2"
											value="<fmt:formatNumber>${cont.getSell_board_car_distance() }</fmt:formatNumber> km" readonly/>
									</div>


								</div>

							</div>

						</div>
						<div class="carlistinfo_content_box_inner2_circle_text4_img">
							<img
								class="carlistinfo_content_box_inner2_circle_text4_inner_img"
								src="images/판매자.png" />
						</div>
						<h2 class="carlistinfo_content_box_inner2_circle_text4_h2">판매자
							설명</h2>
						<div class="carlistinfo_content_box_inner2_circle_text4">
							<textarea readonly>${cont.getSell_board_car_detail() }</textarea>
						</div>
					</div>

				</div>
				<div class="carlistinfo_content_box_inner3">
					<div class="carlistinfo_content_box_inner3_textbox">
						<p class="carlistinfo_content_box_inner3_text1">4scar 진단매니저가</p>
						<p class="carlistinfo_content_box_inner3_text2">&nbsp;무사고
							진단&nbsp;</p>
						<p class="carlistinfo_content_box_inner3_text1">을 완료한 차량입니다.</p>
					</div>
				</div>

			</div>
			<div class="carlistinfo_content_box_inner2_circle_text5">
				<div class="carlistinfo_content_box_inner2_circle_text5_box1">
					<div class="carlistinfo_content_box_inner2_circle_text5_title">판매자와
						편안하게 상담하세요!</div>
					<div class="carlistinfo_content_box_inner2_circle_text5_people_box">
						<div class="carlistinfo_content_box_inner2_circle_text5_people">
							<strong>${dealer.getAdmin_name() } </strong> 딜러
						</div>
						<img class="phone_image" src="images/전화기.png" alt="" />
						<div class="admin_phoen_number">${dealer.getAdmin_phone() }</div>
					</div>
					<div class="carlistinfo_content_box_inner2_circle_text5_button">
						<div>
							<button
								class="carlistinfo_content_box_inner2_circle_text5_button1"
								type="button">상담요청</button>
						</div>
						<div>
							<button
								class="carlistinfo_content_box_inner2_circle_text5_button2"
								type="button">방문예약</button>
						</div>
					</div>
				</div>
				<div class="carlistinfo_content_box_inner2_circle_text5_box2">
					<img src="images/상담사.png" alt="" />
				</div>
			</div>
		</div>

		<div class="basic-info-box">
			<div class="car_text_box1_carname">관련정보</div>
			<hr class=detail_box02_hr>
			<div class="detail-box01">
				<h3 class="detail-sub_title">기본정보</h3>
				<table class="basic_info_table">
					<tr>
						<th>판매번호</th>
						<td>${cont.getSell_board_no() }</td>
						<th>차량정보</th>
						<td>${cont.getSell_board_car_no() }</td>
					</tr>
					<tr>
						<th>주행거리</th>
						<td><fmt:formatNumber>${cont.getSell_board_car_distance() }</fmt:formatNumber>
							km</td>
						<th>연식</th>
						<td>
							${cont.getSell_board_car_std_no().split('_')[2].substring(2,4) }년
							${cont.getSell_board_car_std_no().split('_')[3] }월
							(${cont.getSell_board_car_std_no().split('_')[2] }년형)</td>
					</tr>
					<tr>
						<th>변속기</th>
						<td>${cont.getSell_board_car_std_no().split('_')[5] }</td>
						<th>차종</th>
						<td>${cont.getSell_board_car_std_no().split('_')[1] }</td>
					</tr>
					<tr>
						<th>색상</th>
						<td>${cont.getSell_board_car_std_no().split('_')[6] }</td>
						<th>연료</th>
						<td>${cont.getSell_board_car_std_no().split('_')[4] }</td>
					</tr>
				</table>
			</div>


			<div class="detail-box02">
				<h3 class="detail-sub_title">금융서비스</h3>
				<div class="price_calc">
					<input type="hidden" value="${carPrice }" id="carPrice">
					<p>
						보고 계신 차량의 판매가격<br> <strong id="car_price"> <fmt:formatNumber>${cont.getSell_board_car_price() }</fmt:formatNumber>
						</strong> 원을 &nbsp;<select name="month" id="selectMonth">
							<option value="12">12개월</option>
							<option value="24">24개월</option>
							<option value="36">36개월</option>
							<option value="48">48개월</option>
							<option value="60">60개월</option>
						</select> &nbsp;로 이용 하신다면,
					</p>
					<div class="div_price"></div>
				</div>
			</div>
			<div class="detail-box03">
				<h3 class="detail-sub_title">딜러정보</h3>
				<div class="dealer_info">
					<img src="images/프로필.png" alt="사진" width="100px">
					<div class="dealer_info_box">
						<strong>${dealer.getAdmin_name() } </strong> 딜러<br> <strong>연락처</strong>
						${dealer.getAdmin_phone() }
					</div>
				</div>
				<div class="dealer_msg">
					<p>"궁금한 사항이 있다면 편하게 연락주세요."</p>
				</div>
			</div>
		</div>
		<br>
		<div id="payment_type_Modal" class="modal">
			<div class="type_modal-content">
				<span class="type_close">&times;</span>
				<h2>결제 방식을 선택해주세요.</h2>
				<br>
				<div class="button_img">
					<button type="button" id="popup_cash">
						<img src="images/현금결제.png">
						<p>현금/무통장</p>
					</button>
					&nbsp;&nbsp;&nbsp;
					<button type="button" id="popup_credit">
						<img src="images/신용결제.png">
						<p>체크/신용카드</p>
					</button>
				</div>
			</div>
		</div>
		<div id="cash_Modal" class="modal">
			<div class="cash-content">
				<span class="cash_close">&times;</span>
				<h2>무통장 입금, 계좌이체</h2>
				<br>
				<div class="cash_info">
					<label for="copsName">예금주:</label> <input type="text" id="copsName"
						value="${cops.getCompany_name() }" readonly> <br> <label
						for="cardName">카드 회사:</label> <input type="text" id="cardName"
						value="${cops.getCompany_account().split(' ')[0] }" readonly>
					<br> <label for="account">계좌번호:</label> <input type="text"
						id="account" value="${cops.getCompany_account().split(' ')[1] }"
						readonly>
				</div>
			</div>
		</div>
		<div id="paymentModal" class="modal">
			<div class="modal-content">
				<span class="close">&times;</span>
				<h2>결제 정보 입력</h2>
				<br>
				<form method="post" action="car_list_credit_insert">
					<input type="hidden" name="user_no" value="${user_no }">
					<input type="hidden" name="no" value="${cont.getSell_board_no() }">
					<div class="credit_form">
						<label for="cardCops">카드 회사:</label> <select name="cardCops"
							id="cardCops" required>
							<option value="none">::선택::</option>
							<c:forEach items="${cardList }" var="cardList">
								<option>${cardList }</option>
							</c:forEach>
						</select><br>
						<div class="cardNum_box">
							<span>카드 번호:</span> <input type="text" id="cardNumber1"
								name="cardNumber1" maxlength="4" oninput="inputCardNum1()"
								required>- <input type="password" id="cardNumber2"
								name="cardNumber2" maxlength="4" oninput="inputCardNum2()"
								required>- <input type="text" id="cardNumber3"
								name="cardNumber3" maxlength="4" oninput="inputCardNum3()"
								required>- <input type="password" id="cardNumber4"
								name="cardNumber4" maxlength="4" required> <label
								for="cardPwd">비밀 번호:</label> <input type="password" id="cardPwd"
								name="cardPwd" maxlength="2" style="width: 35px;" required>●●<br>
						</div>
						<label for="expiryDate">만료 날짜:</label> <input type="text"
							id="expiryDate" name="expiryDate" placeholder="MM/YY" size="6"
							maxlength="5" required> <label for="cvc">CVC:</label> <input
							type="password" id="cvc" name="cvc" size="4" maxlength="3"
							required> <label for="installments">할부 개월:</label> <select
							name="installments" id="installments" required>
							<option value="0">일시불</option>
							<option value="12">12개월</option>
							<option value="24">24개월</option>
							<option value="36">36개월</option>
							<option value="48">48개월</option>
							<option value="60">60개월</option>
						</select>
					</div>
					<br>
					<button type="submit" id="" class="submit_btn">결제하기</button>
				</form>
			</div>
		</div>
	</div>
	<jsp:include page="/WEB-INF/include/main_bottom.jsp" />
	<script src="js/payment.js"></script>
	<script src="js/heart_icon_favorite.js"></script>
</body>
</html>