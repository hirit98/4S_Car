<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>차량 등록 페이지</title>
<script src="http://code.jquery.com/jquery-1.9.0rc1.js"></script>
<link rel="icon" type="image/png" sizes="32x32"
   href="images/favicon-32x32.png">
</head>
<body>

   <jsp:include page="/WEB-INF/include/admin_side_bar.jsp" />

   <main class="car_managerpage_menual">
      <div class="admin_req_regcar_content">
         <section class="admin_req_regcar_content_title">
            <img src="images/프로필.png" alt="사람" class="people">
            <div class="admin_req_regcar_content_text">
               <p>차량 등록 페이지</p>
            </div>
            <img src="images/관리자페이지_톱니바퀴_스패너.png" alt="스패너">
         </section>

         <h2>차량 정보</h2>
         <div class="admin_req_regcar_content_top">

            <section class="admin_req_regcar_content_content">
               <table>
                  <tr>
                     <th>글번호</th>
                     <td>${dto.getReq_sell_board_no() }</td>
                  </tr>

                  <tr>
                     <th>회원번호</th>
                     <td>${dto.getReq_sell_board_user_no() }</td>
                  </tr>

                  <tr>
                     <th>제조사</th>
                     <td>${dto.getReq_sell_board_car_std_no().split('_')[0] }</td>
                  </tr>

                  <tr>
                     <th>차종</th>
                     <td>${dto.getReq_sell_board_car_std_no().split('_')[1] }</td>
                  </tr>

                  <tr>
                     <th>연식</th>
                     <td>${dto.getReq_sell_board_car_std_no().split('_')[2] }년
                        ${dto.getReq_sell_board_car_std_no().split('_')[3] }월</td>
                  </tr>
                  <tr>
                     <th>연료</th>
                     <td>${dto.getReq_sell_board_car_std_no().split('_')[4] }</td>
                  </tr>

                  <tr>
                     <th>변속기</th>
                     <td>${dto.getReq_sell_board_car_std_no().split('_')[5] }</td>
                  </tr>

                  <tr>
                     <th>색상</th>
                     <td>${dto.getReq_sell_board_car_std_no().split('_')[6] }</td>
                  </tr>

                  <tr>
                     <th>모델</th>
                     <td>${dto.getReq_sell_board_car_std_no().split('_')[7] }</td>
                  </tr>

                  <tr>
                     <th>차량번호</th>
                     <td>${dto.getReq_sell_board_car_no() }</td>
                  </tr>

                  <tr>
                     <th>주행거리</th>
                     <td><fmt:formatNumber>${dto.getReq_sell_board_car_distance() }</fmt:formatNumber>
                        km</td>
                  </tr>

                  <c:if test="${empty dto.getReq_sell_board_update() }">
                     <tr>
                        <th>요청일시</th>
                        <td>${dto.getReq_sell_board_date() }</td>
                     </tr>
                  </c:if>
                  <c:if test="${!empty dto.getReq_sell_board_update() }">
                     <tr>
                        <th>수정일시</th>
                        <td>${dto.getReq_sell_board_update() }</td>
                     </tr>
                  </c:if>
               </table>
            </section>

            <section class="admin_req_regcar_content_content">
               <table>
                  <tr>
                     <th>고객 희망 가격</th>
                     <td><fmt:formatNumber>${dto.getReq_sell_board_car_price() }</fmt:formatNumber>
                        원</td>
                  </tr>

                  <tr>
                     <th>상세 정보</th>
                     <td><textarea rows="12" readonly>${dto.getReq_sell_board_car_detail() }</textarea></td>
                  </tr>

                  <tr>
                     <th>사진</th>
                     <td><img
                        src="upload_images/${dto.getReq_sell_board_car_file() }"
                        width="500px" height="300px"></td>
                  </tr>
               </table>
            </section>
         </div>
         <form method="post" action="admin_req_regcar_approve">
            <section class="admin_req_regcar_content_answer">
               <table>
                  <tr>
                     <th>등록 가격</th>
                     <td><input type="text" name="car_price" required> 원</td>
                  </tr>
                  <tr>
                     <th>등록 정보</th>
                     <td><textarea name="car_detail" rows="12" required></textarea></td>
                  </tr>
               </table>
            </section>

            <section class="admin_req_regcar_content_button">
               <button type="submit">등록</button>
               <button type="reset">초기화</button>
               <c:if test="${empty field }">
               		<button
                  		onclick="location.href='admin_req_regcar_list?page=${page }'">목록으로</button>
               </c:if>
               <c:if test="${!empty field }">
               		<button
                  		onclick="location.href='admin_req_regcar_search?page=${page }&field=${field }&keyword=${keyword }'">목록으로</button>
               </c:if>
            </section>

            <input type="hidden" name="board_no"
               value="${dto.getReq_sell_board_no() }"> <input
               type="hidden" name="user_no"
               value="${dto.getReq_sell_board_user_no() }"> <input
               type="hidden" name="car_std_no"
               value="${dto.getReq_sell_board_car_std_no() }"> <input
               type="hidden" name="car_no"
               value="${dto.getReq_sell_board_car_no() }"> <input
               type="hidden" name="car_distance"
               value="${dto.getReq_sell_board_car_distance() }"> <input
               type="hidden" name="car_file"
               value="${dto.getReq_sell_board_car_file() }">
         </form>
      </div>
   </main>
</body>
</html>