<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>자유게시판</title>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<link rel="icon" type="image/png" sizes="32x32"
	href="images/favicon-32x32.png">
</head>
<body>
	<jsp:include page="/WEB-INF/include/main_top.jsp" />
	<main class="sellReviewB">
		<div class="sellReviewB-container">
			<div class="sellReviewB-memo">
				<div class="sellReviewB-title">
					<h1>자유 게시판</h1>
				</div>
				<div class="sellReviewB-content">
					<div class="sellReviewB-list">
						<div class="sellReviewB-search">
							<p>총 ${cnt }건의 글이 있습니다.</p>

							<form class="searchForm" method="get" action="community_search">
								<ul class="sellReviewB-form">
									<li class="sellReviewB-select">
										<div class="sellReviewB-sel">
											<div class="sellReviewB-iput">
												<select class="sellReviewB-inner" name="field">
													<option value="전체">전체</option>
													<option value="제목">제목</option>
													<option value="내용">내용</option>
												</select>
											</div>
										</div>
									</li>
									<li class="sellReviewB-input">
										<div class="sellReviewB-textinput">
											<input type="text" placeholder="검색어를 입력해주세요."
												class="sellReviewB-textinner" name="keyword">
										</div>
									</li>
									<li>
										<button type="submit" class="sellReviewB-button">
											<img src="images/돋보기_블루.png" alt=""> <span
												class="hidden"></span>
										</button>
									</li>
									<li>
										<button type="button" class="rebtn" id="insert_btn"
											onclick="location.href='community_insert'">글쓰기</button>
									</li>
								</ul>
							</form>
						</div>
						<div class="sellReviewB-table">
							<div class="sellReviewB-listtable">
								<div class="sellRevieB-headertable">
									<table class="table">
										<thead>
											<tr>
												<th colspan="1" rowspan="1" class="tablecolumn1">
													<div class="cell">No.</div>
												</th>
												<th colspan="1" rowspan="1" class="tablecolumn2">
													<div class="cell">제목</div>
												</th>
												<th colspan="1" rowspan="1" class="tablecolumn3">
													<div class="cell">작성자</div>
												</th>
												<th colspan="1" rowspan="1" class="tablecolumn4">
													<div class="cell">작성일</div>
												</th>
												<th colspan="1" rowspan="1" class="tablecolumn5">
													<div class="cell">조회수</div>
												</th>

											</tr>
											<c:if test="${!empty list }">
												<c:if test="${!is_cSearch }">
													<c:forEach items="${list }" var="dto">
														<tr class="board_list" onclick="location.href='community_board_content?no=${dto.getCommunity_board_no() }&page=${page}'">
															<th colspan="1" rowspan="1" class="table-column1">
																<div class="cell">${dto.getCommunity_board_no() }</div>
															</th>
															<th colspan="1" rowspan="1" class="table-column2">
																<div class="cell">${dto.getCommunity_board_title() }</div>
															</th>
															<th colspan="1" rowspan="1" class="table-column3">
																<div class="cell">${dto.getCommunity_board_writer_id() }</div>
															</th>
															<th colspan="1" rowspan="1" class="table-column4">
																<div class="cell">${dto.getCommunity_board_date().substring(0,10) }</div>
															</th>
															<th colspan="1" rowspan="1" class="table-column5">
																<div class="cell">${dto.getCommunity_board_hit() }</div>
															</th>
														</tr>
													</c:forEach>
												</c:if>
												<c:if test="${is_cSearch }">
													<c:forEach items="${list }" var="dto">
														<tr class="board_list" onclick="location.href='community_board_search_content?no=${dto.getCommunity_board_no() }&page=${page}&field=${field }&keyword=${keyword }'">
															<th colspan="1" rowspan="1" class="table-column1">
																<div class="cell">${dto.getCommunity_board_no() }</div>
															</th>
															<th colspan="1" rowspan="1" class="table-column2">
																<div class="cell">${dto.getCommunity_board_title() }</div>
															</th>
															<th colspan="1" rowspan="1" class="table-column3">
																<div class="cell">${dto.getCommunity_board_writer_id() }</div>
															</th>
															<th colspan="1" rowspan="1" class="table-column4">
																<div class="cell">${dto.getCommunity_board_date().substring(0,10) }</div>
															</th>
															<th colspan="1" rowspan="1" class="table-column5">
																<div class="cell">${dto.getCommunity_board_hit() }</div>
															</th>
														</tr>
													</c:forEach>
												</c:if>
											</c:if>
											<c:if test="${empty list }">
												<tr>
													<th colspan="1" rowspan="1" class="table-column1">
														<div class="cell"></div>
													</th>
													<th colspan="1" rowspan="1" class="table-column2">
														<div class="cell"></div>
													</th>
													<th colspan="1" rowspan="1" class="table-column3">
														<div class="cell">등록된 내용이 없습니다.</div>
													</th>
													<th colspan="1" rowspan="1" class="table-column3">
														<div class="cell"></div>
													</th>
													<th colspan="1" rowspan="1" class="table-column3">
														<div class="cell"></div>
													</th>

												</tr>
											</c:if>
										</thead>
									</table>
								</div>
							</div>
						</div>

					</div>
				</div>
				<c:if test="${!empty list }">
					<c:if test="${!is_cSearch }">
						<div class="pagination">
							<c:if test="${page != 1 }">
								<button type="button" class="first-btn"
									onclick="location.href='community_board_list?page=1'">First</button>
								<button type="button" class="prev-btn"
									onclick="location.href='community_board_list?page=${page - 1}'">Previous</button>
							</c:if>
							<c:forEach begin="${startBlock }" end="${endBlock }" var="i">
								<div class="page-numbers">
									<c:if test="${page == i }">
										<button type="button" class="active-btn"
											onclick="location.href='community_board_list?page=${i}'">${i }</button>
									</c:if>
									<c:if test="${page != i }">
										<button type="button" class="page-btn"
											onclick="location.href='community_board_list?page=${i}'">${i }</button>
									</c:if>
								</div>
							</c:forEach>
							<c:if test="${endBlock < allPage }">
								<c:if test="${startBlock == page }">
									<button type="button" class="next-btn"
										onclick="location.href='community_board_list?page=${page + 3}'">Next</button>
								</c:if>
								<c:if test="${startBlock + 1 == page }">
									<button type="button" class="next-btn"
										onclick="location.href='community_board_list?page=${page + 2}'">Next</button>
								</c:if>
								<c:if test="${endBlock == page }">
									<button type="button" class="next-btn"
										onclick="location.href='community_board_list?page=${page + 1}'">Next</button>
								</c:if>
								<button type="button" class="end-btn"
									onclick="location.href='community_board_list?page=${allPage}'">End</button>
							</c:if>
						</div>
					</c:if>
					<c:if test="${is_cSearch  }">
						<div class="pagination">
							<c:if test="${page != 1 }">
								<button type="button" class="first-btn"
									onclick="location.href='community_search?page=1&field=${field}&keyword=${keyword }'">First</button>
								<button type="button" class="prev-btn"
									onclick="location.href='community_search?page=${page - 1}&field=${field }&keyword=${keyword }'">Previous</button>
							</c:if>
							<c:forEach begin="${startBlock }"
								end="${endBlock }" var="i">
								<div class="page-numbers">
									<c:if test="${page == i }">
										<button type="button" class="active-btn"
											onclick="location.href='community_search?page=${i}&field=${field }&keyword=${keyword }'">${i }</button>
									</c:if>
									<c:if test="${page != i }">
										<button type="button" class="page-btn"
											onclick="location.href='community_search?page=${i}&field=${field }&keyword=${keyword }'">${i }</button>
									</c:if>
								</div>
							</c:forEach>
							<c:if test="${endBlock < allPage }">
								<c:if test="${startBlock == page }">
									<button type="button" class="next-btn"
										onclick="location.href='community_search?page=${page + 3}&field=${field }&keyword=${keyword }'">Next</button>
								</c:if>
								<c:if test="${startBlock + 1 == page }">
									<button type="button" class="next-btn"
										onclick="location.href='community_search?page=${page + 2}&field=${field }&keyword=${keyword }'">Next</button>
								</c:if>
								<c:if test="${endBlock == page }">
									<button type="button" class="next-btn"
										onclick="location.href='community_search?page=${page + 1}&field=${field}&keyword=${keyword }'">Next</button>
								</c:if>
								<button type="button" class="end-btn"
									onclick="location.href='community_search?page=${allPage}&field=${field }&keyword=${keyword }'">End</button>
							</c:if>
						</div>
					</c:if>
				</c:if>
			</div>
		</div>
	</main>
	<jsp:include page="/WEB-INF/include/main_bottom.jsp" />
</body>
</html>