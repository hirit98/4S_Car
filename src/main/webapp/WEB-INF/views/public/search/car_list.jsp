<%@ page import="java.util.List"%>
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
<title>내 차 사기</title>
<link rel="icon" type="image/png" sizes="32x32"
	href="images/favicon-32x32.png">
</head>
<body>
	<jsp:include page="/WEB-INF/include/main_top.jsp" />
	<main class="car_list">
		<div class="searchwrap">
			<div class="containerWrap">
				<form method="get" id="myForm" action="car_list_filter"
					onsubmit="return multiplyPrice(event);">
					<div class="kcarWrap">
						<div class="kcarsearch">
							<button type="submit" class="simpleSearch">간편검색</button>
							<div role="tablist" class=sortmenu>
								<div class="carsort" aria-label="차종">
									<div role="tab" class="carContent">
										<div role="button" class="carHead">
											<strong>차종</strong> <i class="arrow"></i>
										</div>
									</div>
									<div role="tabpanel" class="caritem">
										<div class="car-item">
											<div class="item-list">
												<div role="group" class="btnType">
													<c:forEach var="i" begin="0" end="${typeList.size() - 1 }">
														<label class="carCheck"> <span class="check-label">${typeList.get(i) }</span>
															<input type="checkbox" name="type"
															value="${typeList.get(i) }">
														</label>
													</c:forEach>
												</div>
											</div>
										</div>
									</div>
								</div>
								<div class="carsort" aria-label="제조사/모델">
									<div role="tab">
										<div role="button" class="carHead">
											<strong>제조사/모델</strong> <i class="arrow"></i>
										</div>
									</div>
									<div role="tabpanel" class="caritem">
										<div class="car-item">
											<div class="item-list">
												<div class="scrollbar">
													<div class="scrollerbarWrap">
														<div class="scrollerbarView">
															<div class="modelList">
																<div class="depth1">
																	<strong>국산</strong>
																	<ul class="depth2">
																		<c:forEach var="i" begin="0"
																			end="${companyList.size() - 1 }">
																			<c:if test="${companyList.get(i).equals('벤츠') }">
																				<br>
																				<div class="depth1">
																					<strong>수입</strong>
																				</div>
																				<br>
																			</c:if>
																			<li><label class="d-check"> <span
																					class="checkbox-inner"> <span
																						class="check-inner"></span>
																				</span> <span class="checkbox-label"> <img
																						src="images/logos-brand-${companyList.get(i) }.png"
																						alt="제조사로고">
																						${companyList.get(i).toUpperCase() }
																				</span> <input type="checkbox" name="company"
																					value="${companyList.get(i) }"> <span
																					class="num">${companyCountList.get(i) }대</span>
																			</label></li>
																		</c:forEach>
																	</ul>
																</div>
															</div>
														</div>
													</div>
												</div>
											</div>
										</div>
									</div>
								</div>
								<div class="carsort" aria-label="연식">
									<div role="tab">
										<div role="button" class="carHead">
											<strong>연식</strong> <i class="arrow"></i>
										</div>
									</div>
									<div role="tabpanel" class="caritem">
										<div class="car-item">
											<div class="item-list">
												<div class="year">
													<div class="input-year">
														<input type="number" name="min_year" placeholder="최소">
													</div>
													~
													<div class="input-year">
														<input type="number" name="max_year" placeholder="최대">
													</div>
												</div>
											</div>
										</div>
									</div>
								</div>
								<div class="carsort" aria-label="주행거리">
									<div role="tab">
										<div role="button" class="carHead">
											<strong>주행거리</strong> <i class="arrow"></i>
										</div>
									</div>
									<div role="tabpanel" class="caritem">
										<div class="car-item">
											<div class="item-list">
												<div class="year">
													<div class="input-year">
														<input type="number" name="min_distance" placeholder="최소">
													</div>
													~
													<div class="input-year">
														<input type="number" name="max_distance" placeholder="최대">
													</div>
												</div>
											</div>
										</div>
									</div>
								</div>
								<div class="carsort" aria-label="가격">
									<div role="tab">
										<div role="button" class="carHead">
											<strong>가격</strong> <i class="arrow"></i>
										</div>
									</div>
									<div role="tabpanel" class="caritem">
										<div class="car-item">
											<div class="item-list">
												<div class="year">
													<div class="input-year">
														<input type="number" name="min_price" id="min_price"
															placeholder="0 만원">
													</div>
													~
													<div class="input-year">
														<input type="number" name="max_price" id="max_price"
															placeholder="0 만원">
													</div>
												</div>
											</div>
										</div>
									</div>
								</div>
								<div class="carsort" aria-label="색상">
									<div role="tab">
										<div role="button" class="carHead">
											<strong>색상</strong> <i class="arrow"></i>
										</div>
									</div>
									<div role="tabpanel" class="caritem">
										<div class="car-item">
											<div class="item-list">
												<div role="group" class="carcolor">
													<c:forEach var="i" begin="0" end="${colorList.size() - 1 }">
														<label class="color colorWidth"> <span
															class="color-input"> <span class="color-inner"></span>
														</span> <span class="color-label">${colorList.get(i) }</span> <input
															type="checkbox" name="color" value="${colorList.get(i) }">
														</label>
													</c:forEach>
												</div>
											</div>
										</div>
									</div>
								</div>
								<div class="carsort" aria-label="연료">
									<div role="tab">
										<div role="button" class="carHead">
											<strong>연료</strong> <i class="arrow"></i>
										</div>
									</div>
									<div role="tabpanel" class="caritem">
										<div class="car-item">
											<div class="item-list">
												<div role="group" class="btnType chkgroup">
													<c:forEach var="i" begin="0" end="${fuelList.size() - 1 }">
														<label class="carCheck"> <span class="check-label">${fuelList.get(i) }</span>
															<input type="checkbox" name="fuel"
															value="${fuelList.get(i) }">
														</label>
													</c:forEach>
												</div>
											</div>
										</div>
									</div>
								</div>
								<div class="carsort" aria-label="변속기">
									<div role="tab">
										<div role="button" class="carHead">
											<strong>변속기</strong> <i class="arrow"></i>
										</div>
									</div>
									<div role="tabpanel" class="caritem">
										<div class="car-item">
											<div class="item-list">
												<div role="group" class="btnType chkgroup">
													<c:forEach var="i" begin="0"
														end="${transmissionList.size() - 1 }">
														<label class="carCheck"> <span class="check-label">${transmissionList.get(i) }</span>
															<input type="checkbox" name="transmission"
															value="${transmissionList.get(i) }">
														</label>
													</c:forEach>
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</form>
				<div class="kcarContent">
					<form method="get" action="car_list_search">
						<div class="centerSearch">
							<div class="searchBox">
								<div class="areaSearch">
									<div class="searchInput">
										<select class="year-inner company" name="filter">
											<c:forEach var="i" begin="0" end="${filterList.size() - 1 }">
												<option>${filterList.get(i) }</option>
											</c:forEach>
										</select> <input type="text" name="keyword" autocomplete="off"
											placeholder="원하는 차량을 검색해주세요." class="el-input_inner">
									</div>
									<button type="submit" class="el-button icon-search">
									</button>
									<button type="reset" class="el-button icon-refresh" onclick="location.href='car_list_filter'">
									</button>
								</div>
							</div>
						</div>
					</form>
					<div class="section">
						<div class="resultCnt">
							<div class="listLine">
								<ul>
									<li class="carTotal">총 매물 <span class="textblue">${allCount }</span>
										대
									</li>
									<li class="listBtn">
										<div class="listSelect">
											<form method="get" name="form" action="car_list_${action }">
												<div class="el-input sequence_content">
													<c:forEach var="i" begin="0"
														end="${sequenceList.size() - 1 }">
														<label class="sequence_check"> <span
															class="check-label">${sequenceList.get(i) }</span> <input
															type="radio" name="sequence"
															value="${sequenceList.get(i) }" onclick="form.submit();">
														</label>
													</c:forEach>
													<input type="hidden" name="page" value="1">
													<c:if test="${!empty types}">
														<c:forEach var="i" begin="0" end="${types.size() - 1 }">
															<input type="hidden" name="type" value="${types.get(i) }">
														</c:forEach>
													</c:if>

													<c:if test="${!empty companies}">
														<c:forEach var="i" begin="0"
															end="${companies.size() - 1 }">
															<input type="hidden" name="company"
																value="${companies.get(i) }">
														</c:forEach>
													</c:if>

													<c:if test="${!empty colors}">
														<c:forEach var="i" begin="0" end="${colors.size() - 1 }">
															<input type="hidden" name="color"
																value="${colors.get(i) }">
														</c:forEach>
													</c:if>

													<c:if test="${!empty fuels}">
														<c:forEach var="i" begin="0" end="${fuels.size() - 1 }">
															<input type="hidden" name="fuel" value="${fuels.get(i) }">
														</c:forEach>
													</c:if>

													<c:if test="${!empty transmissions}">
														<c:forEach var="i" begin="0"
															end="${transmissions.size() - 1 }">
															<input type="hidden" name="transmission"
																value="${transmissions.get(i) }">
														</c:forEach>
													</c:if>

													<input type="hidden" name="min_year" value="${min_year }">
													<input type="hidden" name="max_year" value="${max_year }">
													<input type="hidden" name="min_distance"
														value="${min_distance }"> <input type="hidden"
														name="max_distance" value="${max_distance }"> <input
														type="hidden" name="min_price" value="${min_price }">
													<input type="hidden" name="max_price" value="${max_price }">
													<input type="hidden" name="page" value="1"> <input
														type="hidden" name="filter" value="${filter }"> <input
														type="hidden" name="keyword" value="${keyword }">
												</div>
											</form>
										</div>
									</li>
								</ul>
							</div>
							<div class="subtibox">
								<h2 class="subTitle">
									검색 매물 <span class="textblue">${list.size() }/${count }</span> 대
								</h2>
							</div>
							<div class="carListWrap">
								<c:forEach var="dto" items="${list }">
									<div class="carListBox box">
										<div class="car_wrap"
											onclick="location.href='car_list_content?no=${dto.getSell_board_no() }&page=${page }'">
											<img class="car_content_image"
												src="upload_images/${dto.getSell_board_car_file() }">
											<br>
											<ul class="car_content_wrap">
												<li class="car_content_title">
													${dto.getSell_board_car_std_no().split('_')[0] }
													${dto.getSell_board_car_std_no().split('_')[7] }
													${dto.getSell_board_car_std_no().split('_')[6] }
													${dto.getSell_board_car_std_no().split('_')[4] } <c:if
														test="${dto.getSell_board_car_std_no().split('_')[5].equals('수동') }">
                                                 ${dto.getSell_board_car_std_no().split('_')[5] }
                                              </c:if>
												</li>
												<li class="car_content_year">
													${dto.getSell_board_car_std_no().split('_')[2] }년 <c:if
														test="${dto.getSell_board_car_std_no().split('_')[3] < 10 }">
                                              0</c:if>${dto.getSell_board_car_std_no().split('_')[3] }월
												</li>
												<li class="car_content_distance"><fmt:formatNumber>
                                                 ${dto.getSell_board_car_distance() }
                                              </fmt:formatNumber> km</li>
												<li class="car_content_price"><c:if
														test="${dto.getSell_board_car_price() != 0 }">
														<fmt:formatNumber type="number"
															value="${dto.getSell_board_car_price() / 10000 }"
															maxFractionDigits="0" />
                                          만원
                                             </c:if></li>

											</ul>

										</div>
									</div>
								</c:forEach>
							</div>
							<c:if test="${!empty list }">
								<div class="pagination">
									<c:if test="${page != 1 }">
										<button type="button" class="first-btn"
											onclick="location.href='car_list_${action }?page=1${queryParameter }'">First</button>
										<button type="button" class="prev-btn"
											onclick="location.href='car_list_${action }?page=${page - 1 }${queryParameter }'">Previous</button>
									</c:if>
									<c:forEach begin="${startBlock }" end="${endBlock }" var="i">
										<div class="page-numbers">
											<c:if test="${page == i }">
												<button type="button" class="active-btn"
													onclick="location.href='car_list_${action }?page=${i }${queryParameter }'">${i }</button>
											</c:if>
											<c:if test="${page != i }">
												<button type="button" class="page-btn"
													onclick="location.href='car_list_${action }?page=${i }${queryParameter }'">${i }</button>
											</c:if>
										</div>
									</c:forEach>
									<c:if test="${endBlock < allPage }">
										<c:if test="${startBlock == page }">
											<button type="button" class="next-btn"
												onclick="location.href='car_list_${action }?page=${page + 3 }${queryParameter }'">Next</button>
										</c:if>
										<c:if test="${startBlock + 1 == page }">
											<button type="button" class="next-btn"
												onclick="location.href='car_list_${action }?page=${page + 2 }${queryParameter }'">Next</button>
										</c:if>
										<c:if test="${endBlock == page }">
											<button type="button" class="next-btn"
												onclick="location.href='car_list_${action }?page=${page + 1 }${queryParameter }'">Next</button>
										</c:if>
										<button type="button" class="end-btn"
											onclick="location.href='car_list_${action }?page=${allPage }${queryParameter }'">End</button>
									</c:if>
								</div>
							</c:if>
						</div>
					</div>
				</div>
			</div>
		</div>
	</main>

	<script src="js/check.js"></script>

	<jsp:include page="/WEB-INF/include/main_bottom.jsp" />
</body>
</html>