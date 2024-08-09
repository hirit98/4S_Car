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
<title>찜 목록</title>
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
					<p>내 찜 목록</p>
				</div>
			</div>
			<section class="favorite_list">
				<div class="favorite-box">
					<hr>
					<p>찜 목록</p>
				</div>
			</section>
			<div class="star">
				<div class="favorite">
					<div class="carListWrap">
						<c:if test="${!empty sell_list }">
							<c:forEach var="sell_dto" items="${sell_list }">
								<div class="carListBox box">
									<div class="car_wrap">
										<img class="car_content_image"
											onclick="location.href='car_list_content?no=${sell_dto.getSell_board_no() }'"
											src="upload_images/${sell_dto.getSell_board_car_file() }">
										<br>
										<ul class="car_content_wrap">
											<li class="car_content_title">
												${sell_dto.getSell_board_car_std_no().split('_')[0] }
												${sell_dto.getSell_board_car_std_no().split('_')[7] }
												${sell_dto.getSell_board_car_std_no().split('_')[6] }
												${sell_dto.getSell_board_car_std_no().split('_')[4] } <c:if
													test="${sell_dto.getSell_board_car_std_no().split('_')[5].equals('수동') }">
	                                                 ${sell_dto.getSell_board_car_std_no().split('_')[5] }
	                                              </c:if>
											</li>
											<li class="car_content_year">
												${sell_dto.getSell_board_car_std_no().split('_')[2] }년 <c:if
													test="${sell_dto.getSell_board_car_std_no().split('_')[3] < 10 }">
	                                              0</c:if>${sell_dto.getSell_board_car_std_no().split('_')[3] }월
											</li>
											<li class="car_content_distance"><fmt:formatNumber>
	                                                 ${sell_dto.getSell_board_car_distance() }
	                                              </fmt:formatNumber> km</li>
											<li class="car_content_price"><c:if
													test="${sell_dto.getSell_board_car_price() != 0 }">
													<fmt:formatNumber type="number"
														value="${sell_dto.getSell_board_car_price() / 10000 }"
														maxFractionDigits="0" />
	                                          만원
	                                             </c:if></li>
											<li class="favorite_heart">
												<c:if test="${sell_list.size() != 1 }">
													<button class="heartbutton" type="button"
														onclick="if(confirm('찜목록에서 삭제하시겠습니까?')) {
															location.href='myfavorite_delete?no=${sell_dto.getSell_board_no() }&page=${page }'}
															else { return; }">
														<div class="right_area">
															<a href="javascript:;" class="icon heart"> <img
																src="https://cdn-icons-png.flaticon.com/512/803/803087.png"
																alt="찜하기 해제">
															</a>
														</div>
													</button>
												</c:if>
												<c:if test="${sell_list.size() == 1 }">
													<button class="heartbutton" type="button"
														onclick="if(confirm('찜목록에서 삭제하시겠습니까?')) {
															location.href='myfavorite_delete?no=${sell_dto.getSell_board_no() }&page=${page-1 }'}
															else { return; }">
														<div class="right_area">
															<a href="javascript:;" class="icon heart"> <img
																src="https://cdn-icons-png.flaticon.com/512/803/803087.png"
																alt="찜하기 해제">
															</a>
														</div>
													</button>
												</c:if>
											</li>
										</ul>
									</div>
								</div>
							</c:forEach>
						</c:if>
						<c:if test="${empty sell_list }">
							<div style="width: 100%; text-align: center;">
								<p>찜목록 리스트가 없습니다.</p>
							</div>
						</c:if>
					</div>
				</div>
				<c:if test="${!empty sell_list }">
					<div class="pagination">
						<c:if test="${page != 1 }">
							<button type="button" class="first-btn"
								onclick="location.href='myfavorite_list?page=1'">First</button>
							<button type="button" class="prev-btn"
								onclick="location.href='myfavorite_list?page=${page - 1 }'">Previous</button>
						</c:if>
						<c:forEach begin="${startBlock }" end="${endBlock }" var="i">
							<div class="page-numbers">
								<c:if test="${page == i }">
									<button type="button" class="active-btn"
										onclick="location.href='myfavorite_list?page=${i }'">${i }</button>
								</c:if>
								<c:if test="${page != i }">
									<button type="button" class="page-btn"
										onclick="location.href='myfavorite_list?page=${i }'">${i }</button>
								</c:if>
							</div>
						</c:forEach>
						<c:if test="${endBlock < allPage }">
							<c:if test="${startBlock == page }">
								<button type="button" class="next-btn"
									onclick="location.href='myfavorite_list?page=${page + 3 }'">Next</button>
							</c:if>
							<c:if test="${startBlock + 1 == page }">
								<button type="button" class="next-btn"
									onclick="location.href='myfavorite_list?page=${page + 2 }'">Next</button>
							</c:if>
							<c:if test="${endBlock == page }">
								<button type="button" class="next-btn"
									onclick="location.href='myfavorite_list?page=${page + 1 }'">Next</button>
							</c:if>
							<button type="button" class="end-btn"
								onclick="location.href='myfavorite_list?page=${allPage }'">End</button>
						</c:if>
					</div>
				</c:if>
			</div>
		</div>
	</div>
	<script src="js/heart_icon_favorite.js"></script>
</body>
</html>