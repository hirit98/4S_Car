<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>내가 쓴 후기</title>
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
					<p>내가 쓴 글</p>
				</div>
			</div>
			<section class="review-list">
				<div class="review-box">
					<hr>
					<p>글 목록</p>
				</div>
			</section>
			<main class="myreview">
				<div class="reviews">
					<div class="review-content">
						<div class="tab">
							<ul class="tab-btns">
								<li class="tab-item-1 active" onclick="openTab(event, 'tab1')">
									<button>구매 후기</button>
									<div class="blue-bar notice-bar"></div>
								</li>
								<li class="tab-item-2" onclick="openTab(event, 'tab2')">
									<button>판매 후기</button>
									<div class="blue-bar event-bar"></div>
								</li>
								<li class="tab-item-3" onclick="openTab(event, 'tab3')">
									<button>문의 내역</button>
									<div class="blue-bar qna-bar"></div>
								</li>
							</ul>
							<div class="content-review">
								<ul class="review-tab-list">
									<li id="tab1" class="tab-item-1 tab-item active">
										<div class="review-board-list">
											<div class="review-search">
												<p>총 ${buy_review_cnt }건의 구매 후기가 있습니다.</p>
											</div>
											<div class="review-table">
												<div class="table-list">
													<div class="header-review">
														<table class="table">
															<colgroup>
																<col width="100">
																<col width="300">
																<col width="300">
																<col width="100">
															</colgroup>
															<thead class="gutter">
																<tr>
																	<th colspan="1" rowspan="1" class="table-column1">
																		<div class="cell">No.</div>
																	</th>
																	<th colspan="1" rowspan="1" class="table-column2">
																		<div class="cell">제목</div>
																	</th>
																	<th colspan="1" rowspan="1" class="table-column3">
																		<div class="cell">작성일</div>
																	</th>
																	<th colspan="1" rowspan="1" class="table-column4">
																		<div class="cell">조회수</div>
																	</th>
																</tr>
																<c:if test="${!empty buy_review_list }">
																	<c:forEach items="${buy_review_list }"
																		var="buy_review_list">
																		<tr class="review_board_list"
																			onclick="location.href='mypage_buy_review_content?no=${buy_review_list.getBuy_review_board_no()}'">
																			<th colspan="1" rowspan="1" class="table-column2">
																				<div class="cell">${buy_review_list.getBuy_review_board_no() }</div>
																			</th>
																			<th colspan="1" rowspan="1" class="table-column2">
																				<div class="cell">${buy_review_list.getBuy_review_board_title() }</div>
																			</th>
																			<th colspan="1" rowspan="1" class="table-column1">
																				<div class="cell">${buy_review_list.getBuy_review_board_date().substring(0, 10) }
																				</div>
																			</th>
																			<th colspan="1" rowspan="1" class="table-column1">
																				<div class="cell">${buy_review_list.getBuy_review_board_hit() }
																				</div>
																			</th>
																		</tr>
																	</c:forEach>
																</c:if>
																<c:if test="${empty buy_review_list }">
																	<th colspan="5" rowspan="1" class="table-column2">
																		<div class="cell">구매후기 내역이 없습니다...</div>
																	</th>
																</c:if>
															</thead>
														</table>
													</div>
												</div>
											</div>
										</div>
									</li>
									<li id="tab2" class="tab-item-2 tab-item">
										<div class="review-board-list">
											<div class="review-search">
												<p>총 ${sell_review_cnt }건의 판매 후기가 있습니다.</p>
											</div>
											<div class="review-table">
												<div class="table-list">
													<div class="header-review">
														<table class="table">
															<colgroup>
																<col width="100">
																<col width="300">
																<col width="300">
																<col width="100">
															</colgroup>
															<thead class="gutter">
																<tr>
																	<th colspan="1" rowspan="1" class="table-column1">
																		<div class="cell">No.</div>
																	</th>
																	<th colspan="1" rowspan="1" class="table-column2">
																		<div class="cell">제목</div>
																	</th>
																	<th colspan="1" rowspan="1" class="table-column3">
																		<div class="cell">작성일</div>
																	</th>
																	<th colspan="1" rowspan="1" class="table-column4">
																		<div class="cell">조회수</div>
																	</th>
																</tr>
																<c:if test="${!empty sell_review_list }">
																	<c:forEach items="${sell_review_list }"
																		var="sell_review_list">
																		<tr class="review_board_list"
																			onclick="location.href='mypage_sell_review_content?no=${sell_review_list.getSell_review_board_no()}'">
																			<th colspan="1" rowspan="1" class="table-column2">
																				<div class="cell">${sell_review_list.getSell_review_board_no() }</div>
																			</th>
																			<th colspan="1" rowspan="1" class="table-column2">
																				<div class="cell">${sell_review_list.getSell_review_board_title() }</div>
																			</th>
																			<th colspan="1" rowspan="1" class="table-column1">
																				<div class="cell">${sell_review_list.getSell_review_board_date().substring(0, 10) }
																				</div>
																			</th>
																			<th colspan="1" rowspan="1" class="table-column1">
																				<div class="cell">${sell_review_list.getSell_review_board_hit() }
																				</div>
																			</th>
																		</tr>
																	</c:forEach>
																</c:if>
																<c:if test="${empty sell_review_list }">
																	<th colspan="5" rowspan="1" class="table-column2">
																		<div class="cell">판매후기 내역이 없습니다...</div>
																	</th>
																</c:if>

															</thead>
														</table>
													</div>
												</div>
											</div>
										</div>
									</li>
									<li id="tab3" class="tab-item-3 tab-item">
										<div class="review-board-list">
											<div class="review-search">
												<p>총 ${qna_cnt }건의 문의 내역이 있습니다.</p>
											</div>
											<div class="review-table">
												<div class="table-list">
													<div class="header-review">
														<table class="table">
															<colgroup>
																<col width="100">
																<col width="300">
																<col width="300">
																<col width="100">
															</colgroup>
															<thead class="gutter">
																<tr>
																	<th colspan="1" rowspan="1" class="table-column1">
																		<div class="cell">No.</div>
																	</th>
																	<th colspan="1" rowspan="1" class="table-column2">
																		<div class="cell">제목</div>
																	</th>
																	<th colspan="1" rowspan="1" class="table-column3">
																		<div class="cell">작성일</div>
																	</th>
																	<th colspan="1" rowspan="1" class="table-column4">
																		<div class="cell">처리 상태</div>
																	</th>
																	<th colspan="1" rowspan="1" class="table-column4">
																		<div class="cell">상세 보기</div>
																	</th>
																</tr>
																<c:if test="${!empty qna_list }">
																	<c:forEach items="${qna_list }" var="list">
																		<tr>
																			<th colspan="1" rowspan="1" class="table-column1">
																				<div class="cell">${list.getQna_board_no() }</div>
																			</th>
																			<th colspan="1" rowspan="1" class="table-column2">
																				<div class="cell">${list.getQna_board_title() }</div>
																			</th>
																			<c:if test="${empty list.getQna_board_update() }">
																				<th colspan="1" rowspan="1" class="table-column3">
																					<div class="cell">${list.getQna_board_date().substring(0, 10) }</div>
																				</th>
																			</c:if>
																			<c:if test="${!empty list.getQna_board_update() }">
																				<th colspan="1" rowspan="1" class="table-column3">
																					<div class="cell">${list.getQna_board_update().substring(0, 10) }</div>
																				</th>
																			</c:if>
																			<c:if test="${!list.isQna_board_status() }">
																				<th colspan="1" rowspan="1" class="table-column4">
																					<div class="cell">N</div>
																				</th>
																			</c:if>
																			<c:if test="${list.isQna_board_status() }">
																				<th colspan="1" rowspan="1" class="table-column4">
																					<div class="cell">Y</div>
																				</th>
																			</c:if>
																			<th colspan="1" rowspan="1" class="table-column6">
																				<div class="cell">
																					<button type="button"
																						onclick="location.href='mypage_qna_content?no=${list.getQna_board_no() }'">
																						<img class="review_detail_btn"
																							src="images/상세보기.png">
																					</button>
																				</div>
																			</th>
																		</tr>
																	</c:forEach>
																</c:if>
																<c:if test="${empty qna_list }">
																	<tr>
																		<th colspan="5" rowspan="1" class="table-column2">
																			<div class="cell">문의 이력이 존재하지 않습니다.</div>
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
						</div>
					</div>
				</div>
			</main>
		</div>
	</div>

	<script src="js/tab.js"></script>

</body>
</html>