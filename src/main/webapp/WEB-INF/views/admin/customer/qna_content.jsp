<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>문의 내용</title>
<script src="http://code.jquery.com/jquery-1.9.0rc1.js"></script>
<link rel="icon" type="image/png" sizes="32x32"
   href="images/favicon-32x32.png">
</head>
<body>

   <jsp:include page="/WEB-INF/include/admin_side_bar.jsp" />

   <main class="car_managerpage_menual">
      <div class="admin_qna_content">
         <section class="admin_qna_content_title">
            <img src="images/프로필.png" alt="사람" class="people">
            <div class="admin_qna_content_text">
               <p>문의 내용</p>
            </div>
            <img src="images/관리자페이지_톱니바퀴_스패너.png" alt="스패너">
         </section>

         <h2>${dto.getQna_board_writer_id() }님의문의 내용</h2>
         <div class="admin_qna_content_top">

            <section class="admin_qna_content_content">
               <table>
                  <tr>
                     <th>글번호</th>
                     <td>${dto.getQna_board_no() }</td>
                  </tr>

                  <tr>
                     <th>작성자</th>
                     <td>${dto.getQna_board_writer_id() }</td>
                  </tr>

                  <tr>
                     <th>문의 유형</th>
                     <td>${dto.getQna_board_type() }</td>
                  </tr>

                  <tr>
                     <th>제목</th>
                     <td>${dto.getQna_board_title() }</td>
                  </tr>

                  <c:if test="${empty dto.getQna_board_update() }">
                     <tr>
                        <th>등록일시</th>
                        <td>${dto.getQna_board_date() }</td>
                     </tr>
                  </c:if>
                  <c:if test="${!empty dto.getQna_board_update() }">
                     <tr>
                        <th>수정일시</th>
                        <td>${dto.getQna_board_update() }</td>
                     </tr>
                  </c:if>
               </table>
            </section>

            <section class="admin_qna_content_content">
               <table>
                  <tr>
                     <th>내용</th>
                     <td><textarea rows="12" readonly>${dto.getQna_board_cont() }</textarea></td>
                  </tr>

                  <tr>
                     <th>사진</th>
                     <td>${dto.getQna_board_file() }</td>
                  </tr>
               </table>
            </section>
         </div>
         <section class="admin_qna_content_answer">
            <table>
               <tr>
                  <th>답변</th>
                  <td><textarea name="answer" rows="12"></textarea></td>
               </tr>
            </table>
         </section>

         <section class="admin_qna_content_button">
            <button type="submit">등록</button>
            <button type="reset">초기화</button>
            <c:if test="${empty field }">
            	<button
               		onclick="location.href='admin_qna_list?page=${page }'">목록으로</button>
            </c:if>
            <c:if test="${!empty field }">
            	<button
               		onclick="location.href='admin_qna_search?page=${page }&field=${field }&keyword=${keyword }'">목록으로</button>
            </c:if>
         </section>
      </div>
   </main>
</body>
</html>