<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>문의함</title>
<script src="http://code.jquery.com/jquery-1.9.0rc1.js"></script>
<link rel="icon" type="image/png" sizes="32x32"
   href="images/favicon-32x32.png">
</head>
<body>
   <jsp:include page="/WEB-INF/include/admin_side_bar.jsp" />
   <main class="car_managerpage_menual">
      <div class="qna_list">
         <section class="qna_list_title">
            <img src="images/프로필.png" alt="사람" class="people">
            <div class="qna_list_text">
               <p>문의함</p>
            </div>
            <img src="images/관리자페이지_톱니바퀴_스패너.png" alt="스패너">
         </section>

         <form method="get" action="admin_qna_search">
            <section class="qna_list_search">
               <p>
                  <c:if test="${!is_Search }">
                  총
               </c:if>
                  <c:if test="${is_Search }">
                  검색된
               </c:if>
                  ${cnt }건의 글이 있습니다.
               </p>
               <div class="qna_list_search_area">
                  <select name="field" class="qna_list_select_box">
                     <option>전체</option>
                     <option>작성자</option>
                     <option>문의유형</option>
                     <option>제목</option>
                     <option>내용</option>
                  </select> <input type="text" name="keyword" placeholder="검색어를 입력하세요."
                     class="qna_list_search_box"> <input type="submit"
                     value="검색">
               </div>
            </section>
         </form>

         <section class="qna_list_content">
            <table>
               <tr>
                  <th>글번호</th>
                  <th>작성자</th>
                  <th>문의유형</th>
                  <th>제목</th>
                  <th>작성일시</th>
               </tr>
               <c:if test="${!empty list }">
               	  <c:if test="${!is_Search }">
	                  <c:forEach var="dto" items="${list }">
	                     <tr
	                        onclick="location.href='admin_qna_content?no=${dto.getQna_board_no() }&page=${page }'">
	                        <td>${dto.getQna_board_no() }</td>
	                        <td>${dto.getQna_board_writer_id() }</td>
	                        <td>${dto.getQna_board_type() }</td>
	                        <td>${dto.getQna_board_title() }</td>
	                        <td>${dto.getQna_board_date() }</td>
	                     </tr>
	                  </c:forEach>
                  </c:if>
                  <c:if test="${is_Search }">
	                  <c:forEach var="dto" items="${list }">
	                     <tr
	                        onclick="location.href='admin_qna_search_content?no=${dto.getQna_board_no() }&page=${page }&field=${field }&keyword=${keyword }'">
	                        <td>${dto.getQna_board_no() }</td>
	                        <td>${dto.getQna_board_writer_id() }</td>
	                        <td>${dto.getQna_board_type() }</td>
	                        <td>${dto.getQna_board_title() }</td>
	                        <td>${dto.getQna_board_date() }</td>
	                     </tr>
	                  </c:forEach>
                  </c:if>
               </c:if>
               <c:if test="${empty list }">
                  <th colspan="5">
                     <h3>등록된 문의글이 없습니다.</h3>
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
                           onclick="location.href='admin_qna_list?page=1'">First</button>
                        <button type="button" class="prev-btn"
                           onclick="location.href='admin_qna_list?page=${page - 1 }'">Previous</button>
                     </c:if>
                     <c:forEach begin="${startBlock }" end="${endBlock }" var="i">
                        <div class="page-numbers">
                           <c:if test="${page == i }">
                              <button type="button" class="active-btn"
                                 onclick="location.href='admin_qna_list?page=${i }'">${i }</button>
                           </c:if>
                           <c:if test="${page != i }">
                              <button type="button" class="page-btn"
                                 onclick="location.href='admin_qna_list?page=${i }'">${i }</button>
                           </c:if>
                        </div>
                     </c:forEach>
                     <c:if test="${endBlock < allPage }">
                        <c:if test="${startBlock == page }">
                           <button type="button" class="next-btn"
                              onclick="location.href='admin_qna_list?page=${page + 3 }'">Next</button>
                        </c:if>
                        <c:if test="${startBlock + 1 == page }">
                           <button type="button" class="next-btn"
                              onclick="location.href='admin_qna_list?page=${page + 2 }'">Next</button>
                        </c:if>
                        <c:if test="${endBlock == page }">
                           <button type="button" class="next-btn"
                              onclick="location.href='admin_qna_list?page=${page + 1 }'">Next</button>
                        </c:if>
                        <button type="button" class="end-btn"
                           onclick="location.href='admin_qna_list?page=${allPage }'">End</button>
                     </c:if>
                  </div>
               </c:if>
               <c:if test="${is_Search }">
                  <div class="admin_pagination">
                     <c:if test="${page != 1 }">
                        <button type="button" class="first-btn"
                           onclick="location.href='admin_qna_search?page=1&field=${field }&keyword=${keyword }'">First</button>
                        <button type="button" class="prev-btn"
                           onclick="location.href='admin_qna_search?page=${page - 1 }&field=${field }&keyword=${keyword }'">Previous</button>
                     </c:if>
                     <c:forEach begin="${startBlock }" end="${endBlock }" var="i">
                        <div class="page-numbers">
                           <c:if test="${page == i }">
                              <button type="button" class="active-btn"
                                 onclick="location.href='admin_qna_search?page=${i }&field=${field }&keyword=${keyword }'">${i }</button>
                           </c:if>
                           <c:if test="${page != i }">
                              <button type="button" class="page-btn"
                                 onclick="location.href='admin_qna_search?page=${i }&field=${field }&keyword=${keyword }'">${i }</button>
                           </c:if>
                        </div>
                     </c:forEach>
                     <c:if test="${endBlock < allPage }">
                        <c:if test="${startBlock == page }">
                           <button type="button" class="next-btn"
                              onclick="location.href='admin_qna_search?page=${page + 3 }&field=${field }&keyword=${keyword }'">Next</button>
                        </c:if>
                        <c:if test="${startBlock + 1 == page }">
                           <button type="button" class="next-btn"
                              onclick="location.href='admin_qna_search?page=${page + 2 }&field=${field }&keyword=${keyword }'">Next</button>
                        </c:if>
                        <c:if test="${endBlock == page }">
                           <button type="button" class="next-btn"
                              onclick="location.href='admin_qna_search?page=${page + 1 }&field=${field }&keyword=${keyword }'">Next</button>
                        </c:if>
                        <button type="button" class="end-btn"
                           onclick="location.href='admin_qna_search?page=${allPage }&field=${field }&keyword=${keyword }'">End</button>
                     </c:if>
                  </div>
               </c:if>
            </c:if>
         </section>
      </div>
   </main>
</body>
</html>