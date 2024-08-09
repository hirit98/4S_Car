<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>구매 이력</title>
<script src="http://code.jquery.com/jquery-1.9.0rc1.js"></script>
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
					<p>차 거래내역 페이지</p>
				</div>
			</div>
			<section class="car_purchase_details">
				<div class="car_purchase_details_box">
					<div class="car_purchase_details_text">
						<p>내가 사고 팔았던 차는?</p>
						<img src="images/내차구매내역car.png" alt="">
					</div>
					<hr>
					<span>거래 목록</span>
				</div>
			</section>
			<main class="car_purchase_details_main">

				<div class="memo">
					<div class="content">
						<div class="tab">
							<ul class="tab-btns">
								<li class="tab-item-1 active" onclick="openTab(event, 'tab1')">
									<button>구매 내역</button>
									<div class="blue-bar notice-bar"></div>
								</li>
								<li class="tab-item-2" onclick="openTab(event, 'tab2')">
									<button>판매 내역</button>
									<div class="blue-bar event-bar"></div>
								</li>
							</ul>
							<div class="main-content">
								<ul class="tab-list">
									<li id="tab1" class="tab-item1 tab-item active">
										<div class="board-list">
											<div class="board-search">
												<p>총 ${buy_cnt }건의 구매내역이 있습니다.</p>
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

																	<th colspan="1" rowspan="1" class="table-column4">
																		<div class="cell">차량가격</div>
																	</th>
																	<th colspan="1" rowspan="1" class="table-column5">
																		<div class="cell">차량정보</div>
																	</th>
																	<th colspan="1" rowspan="1" class="table-column6">
																		<div class="cell">후기</div>
																	</th>

																</tr>
																<c:if test="${!empty buylist }">
																	<c:forEach items="${buylist }" var="buylist">
																		<tr>
																			<th colspan="1" rowspan="1" class="table-column1">
																				<div class="cell">${buylist.getTransaction_date().substring(0, 10) }
																				</div>
																			</th>
																			<th colspan="1" rowspan="1" class="table-column2">
																				<div class="cell">${buylist.getTransaction_car_no() }</div>
																			</th>
																			<th colspan="1" rowspan="1" class="table-column4">
																				<div class="cell">
																					<fmt:formatNumber>${buylist.getTransaction_car_price() }</fmt:formatNumber>
																					만원
																				</div>
																			</th>
																			<th colspan="1" rowspan="1" class="table-column5">
																				<div class="cell">
																					${buylist.getTransaction_car_std_no().split('_')[7] }
																				</div>
																			</th>
																			<c:if
																				test="${!buylist.isTransaction_buy_review_status() }">
																				<th colspan="1" rowspan="1" class="table-column6">
																					<div class="cell">
																						<button type="button"
																							onclick="location.href='mypage_buy_review_insert?no=${buylist.getTransaction_no()}'">후기등록</button>
																					</div>
																				</th>
																			</c:if>
																			<c:if
																				test="${buylist.isTransaction_buy_review_status() }">
																				<th colspan="1" rowspan="1" class="table-column6">
																					<div class="cell">작성완료</div>
																				</th>
																			</c:if>
																		</tr>
																	</c:forEach>
																</c:if>
																<c:if test="${empty buylist }">
																	<th colspan="5" rowspan="1" class="table-column4">
																		<div class="cell">구매한 차량 정보가 없습니다...</div>
																	</th>
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
												<p>총 ${sell_cnt }건의 판매내역이 있습니다.</p>
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
																		<div class="cell">거래 날짜</div>
																	</th>
																	<th colspan="1" rowspan="1" class="table-column2">
																		<div class="cell">차량 번호</div>
																	</th>
																	<th colspan="1" rowspan="1" class="table-column4">
																		<div class="cell">차량가격</div>
																	</th>
																	<th colspan="1" rowspan="1" class="table-column5">
																		<div class="cell">차량정보</div>
																	</th>
																	<th colspan="1" rowspan="1" class="table-column6">
																		<div class="cell">후기</div>
																	</th>
																</tr>
																<c:if test="${!empty list }">
																	<c:forEach items="${list }" var="list">
																		<tr>
																			<th colspan="1" rowspan="1" class="table-column1">
																				<div class="cell">${list.getTransaction_date().substring(0, 10) }
																				</div>
																			</th>
																			<th colspan="1" rowspan="1" class="table-column2">
																				<div class="cell">${list.getTransaction_car_no() }</div>
																			</th>
																			<th colspan="1" rowspan="1" class="table-column4">
																				<div class="cell">
																					<fmt:formatNumber>${list.getTransaction_car_price() }</fmt:formatNumber>
																					만원
																				</div>
																			</th>
																			<th colspan="1" rowspan="1" class="table-column5">
																				<div class="cell">${list.getTransaction_car_std_no().split('_')[7] }</div>
																			</th>
																			<c:if
																				test="${!list.isTransaction_sell_review_status() }">
																				<th colspan="1" rowspan="1" class="table-column6">
																					<div class="cell">
																						<button type="button"
																							onclick="location.href='mypage_sell_review_insert?no=${list.getTransaction_no()}'">후기등록</button>
																					</div>
																				</th>
																			</c:if>
																			<c:if
																				test="${list.isTransaction_sell_review_status() }">
																				<th colspan="1" rowspan="1" class="table-column6">
																					<div class="cell">작성완료</div>
																				</th>
																			</c:if>
																		</tr>
																	</c:forEach>
																</c:if>
																<c:if test="${empty list }">
																	<th colspan="5" rowspan="1" class="table-column2">
																		<div class="cell">판매한 차량 정보가 없습니다...</div>
																	</th>
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