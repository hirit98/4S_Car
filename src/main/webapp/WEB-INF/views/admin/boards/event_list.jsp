<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>이벤트 관리</title>
<script src="http://code.jquery.com/jquery-1.9.0rc1.js"></script>
<link rel="icon" type="image/png" sizes="32x32"
   href="images/favicon-32x32.png">
</head>
<body>
   <jsp:include page="/WEB-INF/include/admin_side_bar.jsp" />
   <main class="car_managerpage_menual">
      <div class="admin_event_list">
         <section class="admin_event_list_title">
            <div class="admin_event_list_title_left">
               <img src="images/프로필.png" alt="사람" class="people">
               <div class="admin_event_list_text">
                  <p>이벤트 관리</p>
               </div>
               <img src="images/관리자페이지_톱니바퀴_스패너.png" alt="스패너">
            </div>
            <div class="admin_event_list_title_right">
               <button onclick="location.href='admin_event_insert'">이벤트 등록</button>
            </div>
         </section>

         <form method="get" action="admin_event_search">
            <section class="admin_event_list_search">
               <p>
                  <c:if test="${!is_Search }">
                  총
               </c:if>
                  <c:if test="${is_Search }">
                  검색된
               </c:if>
                  ${cnt }건의 글이 있습니다.
               </p>
               <div class="admin_event_list_search_area">
                  <select name="field" class="admin_event_list_select_box">
                     <option>전체</option>
                     <option>제목</option>
                     <option>내용</option>
                  </select> <input type="text" name="keyword" placeholder="검색어를 입력하세요."
                     class="admin_event_list_search_box"> <input type="submit"
                     value="검색">
               </div>
            </section>
         </form>

         <section class="admin_event_list_content">
            <table>
               <tr>
                  <th>글번호</th>
                  <th>제목</th>
                  <th>등록일</th>
               </tr>
               <c:if test="${!empty list }">
                    <c:if test="${!is_Search }">
                     <c:forEach var="dto" items="${list }">
                        <tr onclick="location.href='admin_event_content?no=${dto.getEvent_board_no() }&page=${page }'">
                           <td>${dto.getEvent_board_no() }</td>
                           <td>${dto.getEvent_board_title() }</td>
                           <c:if test="${empty dto.getEvent_board_update() }">
                              <td>${dto.getEvent_board_date().substring(0, 10) }</td>
                           </c:if>
                           <c:if test="${!empty dto.getEvent_board_update() }">
                              <td>${dto.getEvent_board_update().substring(0, 10) }</td>
                           </c:if>
                        </tr>
                     </c:forEach>
                  </c:if>
                  <c:if test="${is_Search }">
                     <c:forEach var="dto" items="${list }">
                        <tr onclick="location.href='admin_event_search_content?no=${dto.getEvent_board_no() }&page=${page }&field=${field }&keyword=${keyword }'">
                           <td>${dto.getEvent_board_no() }</td>
                           <td>${dto.getEvent_board_title() }</td>
                           <c:if test="${empty dto.getEvent_board_update() }">
                              <td>${dto.getEvent_board_date().substring(0, 10) }</td>
                           </c:if>
                           <c:if test="${!empty dto.getEvent_board_update() }">
                              <td>${dto.getEvent_board_update().substring(0, 10) }</td>
                           </c:if>
                        </tr>
                     </c:forEach>
                  </c:if>
               </c:if>
               <c:if test="${empty list }">
                  <th colspan="3">
                     <h3>등록된 공지사항이 없습니다.</h3>
                  </th>
               </c:if>
            </table>
         </section>

         <section>
            <c:if test="${!empty list }">
               <c:if test="${!is_Search }">
                  <div class="admin_pagination">
                     <c:if test="${page != 1 }">
                        <button type="button" class="first-btn"
                           onclick="location.href='admin_event_list?page=1'">First</button>
                        <button type="button" class="prev-btn"
                           onclick="location.href='admin_event_list?page=${page - 1 }'">Previous</button>
                     </c:if>
                     <c:forEach begin="${startBlock }" end="${endBlock }" var="i">
                        <div class="page-numbers">
                           <c:if test="${page == i }">
                              <button type="button" class="active-btn"
                                 onclick="location.href='admin_event_list?page=${i }'">${i }</button>
                           </c:if>
                           <c:if test="${page != i }">
                              <button type="button" class="page-btn"
                                 onclick="location.href='admin_event_list?page=${i }'">${i }</button>
                           </c:if>
                        </div>
                     </c:forEach>
                     <c:if test="${endBlock < allPage }">
                        <c:if test="${startBlock == page }">
                           <button type="button" class="next-btn"
                              onclick="location.href='admin_event_list?page=${page + 3 }'">Next</button>
                        </c:if>
                        <c:if test="${startBlock + 1 == page }">
                           <button type="button" class="next-btn"
                              onclick="location.href='admin_event_list?page=${page + 2 }'">Next</button>
                        </c:if>
                        <c:if test="${endBlock == page }">
                           <button type="button" class="next-btn"
                              onclick="location.href='admin_event_list?page=${page + 1 }'">Next</button>
                        </c:if>
                        <button type="button" class="end-btn"
                           onclick="location.href='admin_event_list?page=${allPage }'">End</button>
                     </c:if>
                  </div>
               </c:if>
               <c:if test="${is_Search }">
                  <div class="admin_pagination">
                     <c:if test="${page != 1 }">
                        <button type="button" class="first-btn"
                           onclick="location.href='admin_event_search?page=1&field=${field }&keyword=${keyword }'">First</button>
                        <button type="button" class="prev-btn"
                           onclick="location.href='admin_event_search?page=${page - 1 }&field=${field }&keyword=${keyword }'">Previous</button>
                     </c:if>
                     <c:forEach begin="${startBlock }" end="${endBlock }" var="i">
                        <div class="page-numbers">
                           <c:if test="${page == i }">
                              <button type="button" class="active-btn"
                                 onclick="location.href='admin_event_search?page=${i }&field=${field }&keyword=${keyword }'">${i }</button>
                           </c:if>
                           <c:if test="${page != i }">
                              <button type="button" class="page-btn"
                                 onclick="location.href='admin_event_search?page=${i }&field=${field }&keyword=${keyword }'">${i }</button>
                           </c:if>
                        </div>
                     </c:forEach>
                     <c:if test="${endBlock < allPage }">
                        <c:if test="${startBlock == page }">
                           <button type="button" class="next-btn"
                              onclick="location.href='admin_event_search?page=${page + 3 }&field=${field }&keyword=${keyword }'">Next</button>
                        </c:if>
                        <c:if test="${startBlock + 1 == page }">
                           <button type="button" class="next-btn"
                              onclick="location.href='admin_event_search?page=${page + 2 }&field=${field }&keyword=${keyword }'">Next</button>
                        </c:if>
                        <c:if test="${endBlock == page }">
                           <button type="button" class="next-btn"
                              onclick="location.href='admin_event_search?page=${page + 1 }&field=${field }&keyword=${keyword }'">Next</button>
                        </c:if>
                        <button type="button" class="end-btn"
                           onclick="location.href='admin_event_search?page=${allPage }&field=${field }&keyword=${keyword }'">End</button>
                     </c:if>
                  </div>
               </c:if>
            </c:if>
         </section>
      </div>
   </main>
</body>
</html>