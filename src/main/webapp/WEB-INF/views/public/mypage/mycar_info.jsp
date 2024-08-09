<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>등록 이력</title>
<script src="http://code.jquery.com/jquery-1.9.0rc1.js"></script>
<script src="js/tab.js"></script>
<link rel="icon" type="image/png" sizes="32x32"
	href="images/favicon-32x32.png">
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
					<p>내차 등록 페이지</p>
				</div>
			</div>
			<section class="car_registration">
				<div class="car_registration_box">
					<button type="button" onclick="location.href='req_regcar'">
						내차 등록하러 가기 <img src="images/내차등록하기버튼.png" alt="">
					</button>
					<hr>
					<p>내차 목록</p>
				</div>
			</section>
			<main class="car_registration_main">

				<div class="memo">
					<div class="content">
						<div class="tab">
							<ul class="tab-btns">
								<li class="tab-item-1 active" onclick="openTab(event, 'tab1')">
									<button>승인 대기</button>
									<div class="blue-bar notice-bar"></div>
								</li>
								<li class="tab-item-2" onclick="openTab(event, 'tab2')">
									<button>승인 완료</button>
									<div class="blue-bar event-bar"></div>
								</li>
							</ul>
							<div class="main-content">
								<ul class="tab-list">
									<li id="tab1" class="tab-item1 tab-item active">
										<div class="board-list">
											<div class="board-search">
												<p>총 ${cnt_b }건의 요청 내역이 있습니다.</p>
											</div>
											<div class="board-table">
												<div class="list-table">
													<div class="header-table">
														<table class="table">
															<colgroup>
																<col width="100">
																<col width="980">
																<col width="200">
																<col width="0">
															</colgroup>
															<thead class="has-gutter">
																<tr>
																	<th colspan="1" rowspan="1" class="table-column1">
																		<div class="cell">등록 날짜</div>
																	</th>
																	<th colspan="1" rowspan="1" class="table-column2">
																		<div class="cell">차량 번호</div>
																	</th>
																	<th colspan="1" rowspan="1" class="table-column3">
																		<div class="cell">차량 모델</div>
																	</th>
																	<th colspan="1" rowspan="1" class="table-column4">
																		<div class="cell">주행 거리</div>
																	</th>
																	<th colspan="1" rowspan="1" class="table-column5">
																		<div class="cell">차량 가격</div>
																	</th>
																	<th colspan="1" rowspan="1" class="table-column5">
																		<div class="cell">상세 보기</div>
																	</th>
																</tr>
																<c:if test="${cnt_b > 0 }">
																	<c:forEach items="${req_list }" var="dto">
																		<c:if test="${!dto.isReq_sell_board_status() }">
																			<tr>
																				<c:if
																					test="${empty dto.getReq_sell_board_update() }">
																					<th colspan="1" rowspan="1" class="table-column3">
																						<div class="cell">${dto.getReq_sell_board_date().substring(0, 10) }</div>
																					</th>
																				</c:if>
																				<c:if
																					test="${!empty dto.getReq_sell_board_update() }">
																					<th colspan="1" rowspan="1" class="table-column3">
																						<div class="cell">${dto.getReq_sell_board_update().substring(0, 10) }</div>
																					</th>
																				</c:if>
																				<th colspan="1" rowspan="1" class="table-column2">
																					<div class="cell">${dto.getReq_sell_board_car_no() }</div>
																				</th>
																				<th colspan="1" rowspan="1" class="table-column3">
																					<div class="cell">${dto.getReq_sell_board_car_std_no().split('_')[7] }</div>
																				</th>
																				<th colspan="1" rowspan="1" class="table-column3">
																					<div class="cell">
																						<fmt:formatNumber>${dto.getReq_sell_board_car_distance() }</fmt:formatNumber>
																						km
																					</div>
																				</th>
																				<th colspan="1" rowspan="1" class="table-column3">
																					<div class="cell">
																						<fmt:formatNumber>${dto.getReq_sell_board_car_price() }</fmt:formatNumber>
																						원
																					</div>
																				</th>
																				<th colspan="1" rowspan="1" class="table-column3">
																					<div class="cell">
																						<button type="button"
																							onclick="location.href='mycar_info_content?no=${dto.getReq_sell_board_no() }'">
																							<img class="carinfo_detail_btn" src="images/상세보기.png">
																						</button>
																					</div>
																				</th>
																			</tr>
																		</c:if>
																	</c:forEach>
																</c:if>
																<c:if test="${cnt_b == 0 }">
																	<tr>
																		<th colspan="6" rowspan="1" class="table-column2">
																			<div class="cell">요청 내역이 존재하지 않습니다...</div>
																		</th>
																	</tr>
																</c:if>
															</thead>
														</table>
													</div>
												</div>
											</div>
										</div>
									</li>
									<li id="tab2" class="tab-item2 tab-item">
										<div class="board-list">
											<div class="board-search">
												<p>총 ${cnt_a }건의 승인 내역이 있습니다.</p>
											</div>
											<div class="board-table">
												<div class="list-table">
													<div class="header-table">
														<table class="table">
															<colgroup>
																<col name="table-column1" width="100">
																<col name="table-column2" width="980">
																<col name="table-column3" width="200">
																<col name="gutter" width="0">
															</colgroup>
															<thead class="has-gutter">
																<tr>
																	<th colspan="1" rowspan="1" class="table-column1">
																		<div class="cell">등록 날짜</div>
																	</th>
																	<th colspan="1" rowspan="1" class="table-column2">
																		<div class="cell">차량 번호</div>
																	</th>
																	<th colspan="1" rowspan="1" class="table-column3">
																		<div class="cell">차량 모델</div>
																	</th>
																	<th colspan="1" rowspan="1" class="table-column4">
																		<div class="cell">주행 거리</div>
																	</th>
																	<th colspan="1" rowspan="1" class="table-column5">
																		<div class="cell">차량 가격</div>
																	</th>
																</tr>
																<c:if test="${cnt_a > 0 }">
																	<c:forEach items="${req_list }" var="dto">
																		<c:if test="${dto.isReq_sell_board_status() }">
																			<tr>
																				<c:if
																					test="${empty dto.getReq_sell_board_update() }">
																					<th colspan="1" rowspan="1" class="table-column3">
																						<div class="cell">${dto.getReq_sell_board_date().substring(0, 10) }</div>
																					</th>
																				</c:if>
																				<c:if
																					test="${!empty dto.getReq_sell_board_update() }">
																					<th colspan="1" rowspan="1" class="table-column3">
																						<div class="cell">${dto.getReq_sell_board_update().substring(0, 10) }</div>
																					</th>
																				</c:if>
																				<th colspan="1" rowspan="1" class="table-column2">
																					<div class="cell">${dto.getReq_sell_board_car_no() }</div>
																				</th>
																				<th colspan="1" rowspan="1" class="table-column3">
																					<div class="cell">${dto.getReq_sell_board_car_std_no().split('_')[7] }</div>
																				</th>
																				<th colspan="1" rowspan="1" class="table-column3">
																					<div class="cell">
																						<fmt:formatNumber>${dto.getReq_sell_board_car_distance() }</fmt:formatNumber>
																						km
																					</div>
																				</th>
																				<th colspan="1" rowspan="1" class="table-column3">
																					<div class="cell">
																						<fmt:formatNumber>${dto.getReq_sell_board_car_price() }</fmt:formatNumber>
																						원
																					</div>
																				</th>
																			</tr>
																		</c:if>
																	</c:forEach>
																</c:if>
																<c:if test="${cnt_a == 0 }">
																	<tr>
																		<th colspan="5" rowspan="1" class="table-column2">
																			<div class="cell">승인 내역이 존재하지 않습니다...</div>
																		</th>
																	</tr>
																</c:if>
															</thead>
														</table>
													</div>
												</div>
											</div>
										</div>
									</li>

								</ul>

							</div>
							<script src="js/tab.js"></script>
						</div>
					</div>
				</div>

			</main>

		</div>
	</div>
</body>
</html>