<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>공지사항</title>
<script
   src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<link rel="icon" type="image/png" sizes="32x32"
   href="images/favicon-32x32.png">
</head>
<body>
   <jsp:include page="/WEB-INF/include/main_top.jsp" />

   <main class="notice">
      <div class="container">
         <div class="memo">
            <div class="memo-title">
               <h1>공지사항</h1>
               <p>4S_Car의 새로운 소식을 전달받으실 수 있습니다.</p>
            </div>
         </div>
         <div class="content">
            <div class="tab">
               <ul class="tab-btns">
                  <li class="tab-item-1 active" onclick="openTab(event, 'tab1')">
                     <button class="tab-item-bar">공지사항</button>
                     <div class="blue-bar notice-bar"></div>
                  </li>
                  <li class="tab-item-2" onclick="openTab(event, 'tab2')">
                     <button class="tab-item-bar">이벤트</button>
                     <div class="blue-bar event-bar"></div>
                  </li>
               </ul>
               <div class="main-content">
                  <ul class="tab-list">
                     <li id="tab1" class="tab-item1 tab-item active">
                        <div class="board-list">
                           <div class="board-search">
                              <p>총 ${notice_cnt }건의 글이 있습니다.</p>
                              <form class="searchForm" method="get" action="notice_search">
                                 <ul class="boardSearchForm">
                                    <li class="searchselect">
                                       <div class="select">
                                          <div class="input">
                                             <select class="selectinner" name="field">
                                                <option value="전체">전체</option>
                                                <option value="제목">제목</option>
                                                <option value="내용">내용</option>
                                             </select> <span class="select-check"></span>
                                          </div>
                                       </div>
                                    </li>
                                    <li class="searchInput">
                                       <div class="textInput">
                                          <input type="text" autocomplete="off"
                                             placeholder="검색어를 입력해주세요" class="textinner"
                                             name="keyword">
                                       </div>
                                    </li>
                                    <li>
                                       <button type="submit" class="searchButton sbtn">
                                          <img src="images/돋보기_블루.png" alt=""> <span
                                             class="hidden">검색</span>
                                       </button>
                                    </li>
                                 </ul>
                              </form>
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
                                                <div class="cell">번호</div>
                                             </th>
                                             <th colspan="1" rowspan="1" class="table-column2">
                                                <div class="cell">제목</div>
                                             </th>
                                             <th colspan="1" rowspan="1" class="table-column3">
                                                <div class="cell">등록일</div>
                                             </th>
                                          </tr>
                                          <c:if test="${!empty notice_list }">
                                          	 <c:if test="${!is_nSearch }">
	                                             <c:forEach items="${notice_list }" var="dto">
	                                                <tr class="board_list" onclick="location.href='notice_content?no=${dto.getNotice_board_no() }&notice_page=${notice_page }'">
	                                                   <th colspan="1" rowspan="1" class="table-column1">
	                                                      <div class="cell">${dto.getNotice_board_no() }</div>
	                                                   </th>
	                                                   <th colspan="1" rowspan="1" class="table-column2">
	                                                      <div class="cell">${dto.getNotice_board_title() }</div>
	                                                   </th>
	                                                   <c:if test="${empty dto.getNotice_board_update() }">
	                                                      <th colspan="1" rowspan="1" class="table-column3">
	                                                         <div class="cell">${dto.getNotice_board_date().substring(0, 10) }</div>
	                                                      </th>
	                                                   </c:if>
	                                                   <c:if test="${!empty dto.getNotice_board_update() }">
	                                                      <th colspan="1" rowspan="1" class="table-column3">
	                                                         <div class="cell">${dto.getNotice_board_update().substring(0, 10) }</div>
	                                                      </th>
	                                                   </c:if>
	                                                </tr>
	                                             </c:forEach>
                                             </c:if>
                                             <c:if test="${is_nSearch }">
	                                             <c:forEach items="${notice_list }" var="dto">
	                                                <tr class="board_list" onclick="location.href='notice_search_content?no=${dto.getNotice_board_no() }&notice_page=${notice_page }&field=${field }&keyword=${keyword }'">
	                                                   <th colspan="1" rowspan="1" class="table-column1">
	                                                      <div class="cell">${dto.getNotice_board_no() }</div>
	                                                   </th>
	                                                   <th colspan="1" rowspan="1" class="table-column2">
	                                                      <div class="cell">${dto.getNotice_board_title() }</div>
	                                                   </th>
	                                                   <c:if test="${empty dto.getNotice_board_update() }">
	                                                      <th colspan="1" rowspan="1" class="table-column3">
	                                                         <div class="cell">${dto.getNotice_board_date().substring(0, 10) }</div>
	                                                      </th>
	                                                   </c:if>
	                                                   <c:if test="${!empty dto.getNotice_board_update() }">
	                                                      <th colspan="1" rowspan="1" class="table-column3">
	                                                         <div class="cell">${dto.getNotice_board_update().substring(0, 10) }</div>
	                                                      </th>
	                                                   </c:if>
	                                                </tr>
	                                             </c:forEach>
                                             </c:if>
                                          </c:if>
                                          <c:if test="${empty notice_list }">
                                             <tr>
                                                <th colspan="1" rowspan="1" class="table-column1">
                                                   <div class="cell"></div>
                                                </th>
                                                <th colspan="1" rowspan="1" class="table-column2">
                                                   <div class="cell">등록된 공지사항이 없습니다.</div>
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
                           <c:if test="${!empty notice_list }">
                              <c:if test="${!is_nSearch }">
                                 <div class="pagination">
                                    <c:if test="${notice_page != 1 }">
                                       <button type="button" class="first-btn"
                                          onclick="location.href='notice_list?notice_page=1'">First</button>
                                       <button type="button" class="prev-btn"
                                          onclick="location.href='notice_list?notice_page=${notice_page - 1 }'">Previous</button>
                                    </c:if>
                                    <c:forEach begin="${notice_startBlock }"
                                       end="${notice_endBlock }" var="i">
                                       <div class="page-numbers">
                                          <c:if test="${notice_page == i }">
                                             <button type="button" class="active-btn"
                                                onclick="location.href='notice_list?notice_page=${i }'">${i }</button>
                                          </c:if>
                                          <c:if test="${notice_page != i }">
                                             <button type="button" class="page-btn"
                                                onclick="location.href='notice_list?notice_page=${i }'">${i }</button>
                                          </c:if>
                                       </div>
                                    </c:forEach>
                                    <c:if test="${notice_endBlock < notice_allPage }">
                                       <c:if test="${notice_startBlock == notice_page }">
                                          <button type="button" class="next-btn"
                                             onclick="location.href='notice_list?notice_page=${notice_page + 3 }'">Next</button>
                                       </c:if>
                                       <c:if test="${notice_startBlock + 1 == notice_page }">
                                          <button type="button" class="next-btn"
                                             onclick="location.href='notice_list?notice_page=${notice_page + 2 }'">Next</button>
                                       </c:if>
                                       <c:if test="${notice_endBlock == notice_page }">
                                          <button type="button" class="next-btn"
                                             onclick="location.href='notice_list?notice_page=${notice_page + 1 }'">Next</button>
                                       </c:if>
                                       <button type="button" class="end-btn"
                                          onclick="location.href='notice_list?notice_page=${notice_allPage }'">End</button>
                                    </c:if>
                                 </div>
                              </c:if>
                              <c:if test="${is_nSearch }">
                                 <div class="pagination">
                                    <c:if test="${notice_page != 1 }">
                                       <button type="button" class="first-btn"
                                          onclick="location.href='notice_search?notice_page=1&field=${field }&keyword=${keyword }'">First</button>
                                       <button type="button" class="prev-btn"
                                          onclick="location.href='notice_search?notice_page=${notice_page - 1 }&field=${field }&keyword=${keyword }'">Previous</button>
                                    </c:if>
                                    <c:forEach begin="${notice_startBlock }"
                                       end="${notice_endBlock }" var="i">
                                       <div class="page-numbers">
                                          <c:if test="${notice_page == i }">
                                             <button type="button" class="active-btn"
                                                onclick="location.href='notice_search?notice_page=${i }&field=${field }&keyword=${keyword }'">${i }</button>
                                          </c:if>
                                          <c:if test="${notice_page != i }">
                                             <button type="button" class="page-btn"
                                                onclick="location.href='notice_search?notice_page=${i }&field=${field }&keyword=${keyword }'">${i }</button>
                                          </c:if>
                                       </div>
                                    </c:forEach>
                                    <c:if test="${notice_endBlock < notice_allPage }">
                                       <c:if test="${notice_startBlock == notice_page }">
                                          <button type="button" class="next-btn"
                                             onclick="location.href='notice_search?notice_page=${notice_page + 3 }&field=${field }&keyword=${keyword }'">Next</button>
                                       </c:if>
                                       <c:if test="${notice_startBlock + 1 == notice_page }">
                                          <button type="button" class="next-btn"
                                             onclick="location.href='notice_search?notice_page=${notice_page + 2 }&field=${field }&keyword=${keyword }'">Next</button>
                                       </c:if>
                                       <c:if test="${notice_endBlock == notice_page }">
                                          <button type="button" class="next-btn"
                                             onclick="location.href='notice_search?notice_page=${notice_page + 1 }&field=${field }&keyword=${keyword }'">Next</button>
                                       </c:if>
                                       <button type="button" class="end-btn"
                                          onclick="location.href='notice_search?notice_page=${notice_allPage }&field=${field }&keyword=${keyword }'">End</button>
                                    </c:if>
                                 </div>
                              </c:if>
                           </c:if>
                        </div>
                     </li>
                     <li id="tab2" class="tab-item2 tab-item">
                        <div class="board-list">
                           <div class="board-search">
                              <p>총 ${event_cnt }건의 글이 있습니다.</p>
                              <form class="searchForm" method="get" action="event_search">
                                 <ul class="boardSearchForm">
                                    <li class="searchselect">
                                       <div class="select">
                                          <div class="input">
                                             <select class="selectinner" name="field">
                                                <option value="전체">전체</option>
                                                <option value="제목">제목</option>
                                                <option value="내용">내용</option>
                                             </select> <span class="select-check"></span>
                                          </div>
                                       </div>
                                    </li>
                                    <li class="searchInput">
                                       <div class="input">
                                          <input type="text" autocomplete="off"
                                             placeholder="검색어를 입력해주세요" class="textinner"
                                             name="keyword"> <span class="select-down">
                                             <span class="select-inner"> <i class="select-icon"></i>
                                          </span>
                                          </span>
                                       </div>
                                    </li>
                                    <li>
                                       <button type="submit" class="searchButton sbtn">
                                          <img src="images/돋보기_블루.png" alt=""> <span> <span
                                             class="hidden">검색</span>
                                          </span>
                                       </button>
                                    </li>
                                 </ul>
                              </form>
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
                                                <div class="cell">번호</div>
                                             </th>
                                             <th colspan="1" rowspan="1" class="table-column2">
                                                <div class="cell">제목</div>
                                             </th>
                                             <th colspan="1" rowspan="1" class="table-column3">
                                                <div class="cell">등록일</div>
                                             </th>
                                          </tr>
                                          <c:if test="${!empty event_list }">
                                          	 <c:if test="${!is_eSearch }">
	                                             <c:forEach items="${event_list }" var="dto">
	                                                <tr class="board_list" onclick="location.href='event_content?no=${dto.getEvent_board_no() }&event_page=${event_page }'">
	                                                   <th colspan="1" rowspan="1" class="table-column1">
	                                                      <div class="cell">${dto.getEvent_board_no() }</div>
	                                                   </th>
	                                                   <th colspan="1" rowspan="1" class="table-column2">
	                                                      <div class="cell">${dto.getEvent_board_title() }</div>
	                                                   </th>
	                                                   <c:if test="${empty dto.getEvent_board_update() }">
	                                                      <th colspan="1" rowspan="1" class="table-column3">
	                                                         <div class="cell">${dto.getEvent_board_date().substring(0, 10) }</div>
	                                                      </th>
	                                                   </c:if>
	                                                   <c:if test="${!empty dto.getEvent_board_update() }">
	                                                      <th colspan="1" rowspan="1" class="table-column3">
	                                                         <div class="cell">${dto.getEvent_board_update().substring(0, 10) }</div>
	                                                      </th>
	                                                   </c:if>
	                                                </tr>
	                                             </c:forEach>
                                             </c:if>
                                             <c:if test="${is_eSearch }">
	                                             <c:forEach items="${event_list }" var="dto">
	                                                <tr class="board_list" onclick="location.href='event_search_content?no=${dto.getEvent_board_no() }&event_page=${event_page }&field=${field }&keyword=${keyword }'">
	                                                   <th colspan="1" rowspan="1" class="table-column1">
	                                                      <div class="cell">${dto.getEvent_board_no() }</div>
	                                                   </th>
	                                                   <th colspan="1" rowspan="1" class="table-column2">
	                                                      <div class="cell">${dto.getEvent_board_title() }</div>
	                                                   </th>
	                                                   <c:if test="${empty dto.getEvent_board_update() }">
	                                                      <th colspan="1" rowspan="1" class="table-column3">
	                                                         <div class="cell">${dto.getEvent_board_date().substring(0, 10) }</div>
	                                                      </th>
	                                                   </c:if>
	                                                   <c:if test="${!empty dto.getEvent_board_update() }">
	                                                      <th colspan="1" rowspan="1" class="table-column3">
	                                                         <div class="cell">${dto.getEvent_board_update().substring(0, 10) }</div>
	                                                      </th>
	                                                   </c:if>
	                                                </tr>
	                                             </c:forEach>
                                             </c:if>
                                          </c:if>
                                          <c:if test="${empty event_list }">
                                             <tr>
                                                <th colspan="1" rowspan="1" class="table-column1">
                                                   <div class="cell"></div>
                                                </th>
                                                <th colspan="1" rowspan="1" class="table-column2">
                                                   <div class="cell">현재 진행중인 이벤트가 없습니다.</div>
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
                              <c:if test="${!empty event_list }">
                                 <c:if test="${!is_eSearch }">
                                    <div class="pagination">
                                       <c:if test="${event_page != 1 }">
                                          <button type="button" class="first-btn"
                                             onclick="location.href='notice_list?event_page=1'">First</button>
                                          <button type="button" class="prev-btn"
                                             onclick="location.href='notice_list?event_page=${event_page - 1 }'">Previous</button>
                                       </c:if>
                                       <c:forEach begin="${event_startBlock }"
                                          end="${event_endBlock }" var="i">
                                          <div class="page-numbers">
                                             <c:if test="${event_page == i }">
                                                <button type="button" class="active-btn"
                                                   onclick="location.href='notice_list?event_page=${i }'">${i }</button>
                                             </c:if>
                                             <c:if test="${event_page != i }">
                                                <button type="button" class="page-btn"
                                                   onclick="location.href='notice_list?event_page=${i }'">${i }</button>
                                             </c:if>
                                          </div>
                                       </c:forEach>
                                       <c:if test="${event_endBlock < event_allPage }">
                                          <c:if test="${event_startBlock == event_page }">
                                             <button type="button" class="next-btn"
                                                onclick="location.href='notice_list?event_page=${event_page + 3 }'">Next</button>
                                          </c:if>
                                          <c:if test="${event_startBlock + 1 == event_page }">
                                             <button type="button" class="next-btn"
                                                onclick="location.href='notice_list?event_page=${event_page + 2 }'">Next</button>
                                          </c:if>
                                          <c:if test="${event_endBlock == event_page }">
                                             <button type="button" class="next-btn"
                                                onclick="location.href='notice_list?event_page=${event_page + 1 }'">Next</button>
                                          </c:if>
                                          <button type="button" class="end-btn"
                                             onclick="location.href='notice_list?event_page=${event_page }'">End</button>
                                       </c:if>
                                    </div>
                                 </c:if>
                                 <c:if test="${is_eSearch }">
                                    <div class="pagination">
                                       <c:if test="${event_page != 1 }">
                                          <button type="button" class="first-btn"
                                             onclick="location.href='event_search?event_page=1&field=${field }&keyword=${keyword }'">First</button>
                                          <button type="button" class="prev-btn"
                                             onclick="location.href='event_search?event_page=${event_page - 1 }&field=${field }&keyword=${keyword }'">Previous</button>
                                       </c:if>
                                       <c:forEach begin="${event_startBlock }"
                                          end="${event_endBlock }" var="i">
                                          <div class="page-numbers">
                                             <c:if test="${event_page == i }">
                                                <button type="button" class="active-btn"
                                                   onclick="location.href='event_search?event_page=${i }&field=${field }&keyword=${keyword }'">${i }</button>
                                             </c:if>
                                             <c:if test="${event_page != i }">
                                                <button type="button" class="page-btn"
                                                   onclick="location.href='event_search?event_page=${i }&field=${field }&keyword=${keyword }'">${i }</button>
                                             </c:if>
                                          </div>
                                       </c:forEach>
                                       <c:if test="${event_endBlock < event_allPage }">
                                          <c:if test="${event_startBlock == event_page }">
                                             <button type="button" class="next-btn"
                                                onclick="location.href='event_search?event_page=${event_page + 3 }&field=${field }&keyword=${keyword }'">Next</button>
                                          </c:if>
                                          <c:if test="${event_startBlock + 1 == event_page }">
                                             <button type="button" class="next-btn"
                                                onclick="location.href='event_search?event_page=${event_page + 2 }&field=${field }&keyword=${keyword }'">Next</button>
                                          </c:if>
                                          <c:if test="${event_endBlock == event_page }">
                                             <button type="button" class="next-btn"
                                                onclick="location.href='event_search?event_page=${event_page + 1 }&field=${field }&keyword=${keyword }'">Next</button>
                                          </c:if>
                                          <button type="button" class="end-btn"
                                             onclick="location.href='event_search?event_page=${event_allPage }&field=${field }&keyword=${keyword }'">End</button>
                                       </c:if>
                                    </div>
                                 </c:if>
                              </c:if>
                           </div>
                        </div>
                     </li>
                  </ul>
               </div>
            </div>
         </div>
      </div>
   </main>

   <jsp:include page="/WEB-INF/include/main_bottom.jsp" />

   <script src="js/tab.js"></script>

</body>
</html>