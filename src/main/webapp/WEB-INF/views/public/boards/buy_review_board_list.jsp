<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" />
<title>구매 후기 게시판</title>
<link rel="icon" type="image/png" sizes="32x32"
	href="images/favicon-32x32.png" />
</head>
<body>
	<jsp:include page="/WEB-INF/include/main_top.jsp" />
	<main class="sellReviewB">
		<div class="sellReviewB-container">
			<div class="sellReviewB-memo">
				<div class="sellReviewB-title">
					<h1 class="sellReviewB-H1">구매 후기 게시판</h1>
				</div>
				<div class="sellReviewB-content">
					<div class="sellReviewB-list">
						<div class="sellReviewB-search">
							<p>총 ${cnt } 건의 글이 있습니다.</p>
							<form class="searchForm" method="get"
								action="buy_review_board_search">
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
												class="sellReviewB-textinner" name="keyword" />
										</div>
									</li>
									<li>
										<button type="submit" class="sellReviewB-button">
											<img src="images/돋보기_블루.png" alt="" /> <span class="hidden"></span>
										</button>
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
												<th colspan="1" rowspan="1" class="tablecolumn3">
													<div class="cell">제목</div>
												</th>
												<th colspan="1" rowspan="1" class="tablecolumn4">
													<div class="cell">등록일</div>
												</th>
												<th colspan="1" rowspan="1" class="tablecolumn5">
													<div class="cell">조회수</div>
												</th>
											</tr>
											<c:if test="${!empty buy_review_list }">
												<c:if test="${!is_cSearch }">
													<c:forEach items="${buy_review_list }" var="list">
														<tr class="board_list" 
															onclick="location.href='buy_review_board_content?no=${list.getBuy_review_board_no()}&page=${page }'">
															<th colspan="1" rowspan="1" class="table-column2">
																<div class="cell">${list.getBuy_review_board_no()}</div>
															</th>
															<th colspan="1" rowspan="1" class="table-column2">
																<div class="cell">${list.getBuy_review_board_title()}</div>
															</th>
															<th colspan="1" rowspan="1" class="table-column1">
																<div class="cell">${list.getBuy_review_board_date().substring(0,10) }</div>
															</th>
															<th colspan="1" rowspan="1" class="table-column1">
																<div class="cell">${list.getBuy_review_board_hit()}</div>
															</th>
														</tr>
													</c:forEach>
												</c:if>
												<c:if test="${is_cSearch }">
													<c:forEach items="${buy_review_list }" var="list">
														<tr class="board_list" 
															onclick="location.href='buy_review_board_search_content?no=${list.getBuy_review_board_no()}&page=${page }&field=${field }&keyword=${keyword }'">
															<th colspan="1" rowspan="1" class="table-column2">
																<div class="cell">${list.getBuy_review_board_no()}</div>
															</th>
															<th colspan="1" rowspan="1" class="table-column2">
																<div class="cell">${list.getBuy_review_board_title()}</div>
															</th>
															<th colspan="1" rowspan="1" class="table-column1">
																<div class="cell">${list.getBuy_review_board_date().substring(0,10) }</div>
															</th>
															<th colspan="1" rowspan="1" class="table-column1">
																<div class="cell">${list.getBuy_review_board_hit()}</div>
															</th>
														</tr>
													</c:forEach>
												</c:if>
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
				</div>
				<c:if test="${!empty buy_review_list }">
					<c:if test="${!is_cSearch }">
						<div class="pagination">
							<c:if test="${page != 1 }">
								<button type="button" class="first-btn"
									onclick="location.href='buy_review_board_list?page=1'">First</button>
								<button type="button" class="prev-btn"
									onclick="location.href='buy_review_board_list?page=${page - 1}'">Previous</button>
							</c:if>
							<c:forEach begin="${startBlock }" end="${endBlock }" var="i">
								<div class="page-numbers">
									<c:if test="${page == i }">
										<button type="button" class="active-btn"
											onclick="location.href='buy_review_board_list?page=${i}'">${i }</button>
									</c:if>
									<c:if test="${page != i }">
										<button type="button" class="page-btn"
											onclick="location.href='buy_review_board_list?page=${i}'">${i }</button>
									</c:if>
								</div>
							</c:forEach>
							<c:if test="${endBlock < allPage }">
								<c:if test="${startBlock == page }">
									<button type="button" class="next-btn"
										onclick="location.href='buy_review_board_list?page=${page + 3}'">Next</button>
								</c:if>
								<c:if test="${startBlock + 1 == page }">
									<button type="button" class="next-btn"
										onclick="location.href='buy_review_board_list?page=${page + 2}'">Next</button>
								</c:if>
								<c:if test="${endBlock == page }">
									<button type="button" class="next-btn"
										onclick="location.href='buy_review_board_list?page=${page + 1}'">Next</button>
								</c:if>
								<button type="button" class="end-btn"
									onclick="location.href='buy_review_board_list?page=${allPage}'">End</button>
							</c:if>
						</div>
					</c:if>
					<c:if test="${is_cSearch  }">
						<div class="pagination">
							<c:if test="${page != 1 }">
								<button type="button" class="first-btn"
									onclick="location.href='buy_review_board_search?page=1&field=${field}&keyword=${keyword }'">First</button>
								<button type="button" class="prev-btn"
									onclick="location.href='buy_review_board_search?page=${page - 1}&field=${field }&keyword=${keyword }'">Previous</button>
							</c:if>
							<c:forEach begin="${startBlock }"
								end="${endBlock }" var="i">
								<div class="page-numbers">
									<c:if test="${page == i }">
										<button type="button" class="active-btn"
											onclick="location.href='buy_review_board_search?page=${i}&field=${field }&keyword=${keyword }'">${i }</button>
									</c:if>
									<c:if test="${page != i }">
										<button type="button" class="page-btn"
											onclick="location.href='buy_review_board_search?page=${i}&field=${field }&keyword=${keyword }'">${i }</button>
									</c:if>
								</div>
							</c:forEach>
							<c:if test="${endBlock < allPage }">
								<c:if test="${startBlock == page }">
									<button type="button" class="next-btn"
										onclick="location.href='buy_review_board_search?page=${page + 3}&field=${field }&keyword=${keyword }'">Next</button>
								</c:if>
								<c:if test="${startBlock + 1 == page }">
									<button type="button" class="next-btn"
										onclick="location.href='buy_review_board_search?page=${page + 2}&field=${field }&keyword=${keyword }'">Next</button>
								</c:if>
								<button type="button" class="end-btn"
									onclick="location.href='buy_review_board_search?page=${allPage}&field=${field }&keyword=${keyword }'">End</button>
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
