<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>내 마일리지 내역</title>
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
               <p>${dto.getUser_name() }님
                  <img src="images/하트-removebg-preview.png" alt="하트" class="heart">
               </p>
               <p>내 마일리지</p>
            </div>
         </div>
         <br>
         <div class="my_mileage_container">
            <p>
               현재 보유 마일리지 : <b><fmt:formatNumber>${list.get(list.size()-1).getMileage_status() }</fmt:formatNumber></b>
            </p>
            <div class="my_mileage">

               <table>
                  <colgroup>
                     <col width="100">
                     <col width="300">
                     <col width="300">
                     <col width="100">
                  </colgroup>
                  <thead class="gutter">
                     <tr>
                        <th colspan="1" rowspan="1" class="table-column1">
                           <div class="cell">적립일시</div>
                        </th>
                        <th colspan="1" rowspan="1" class="table-column2">
                           <div class="cell">적립내용</div>
                        </th>
                        <th colspan="1" rowspan="1" class="table-column3">
                           <div class="cell">적립 마일리지</div>
                        </th>
                        <th colspan="1" rowspan="1" class="table-column4">
                           <div class="cell">보유 마일리지</div>
                        </th>
                        <th colspan="1" rowspan="1" class="table-column5">
                           <div class="cell">회원 등급</div>
                        </th>
                     </tr>
                     <c:if test="${!empty list }">
                        <c:forEach var="dto" items="${list }">
                           <tr>
                              <th colspan="1" rowspan="1" class="table-column2">
                                 <div class="cell">${dto.getMileage_date() }</div>
                              </th>
                              <th colspan="1" rowspan="1" class="table-column2">
                                 <div class="cell">${dto.getMileage_cont() }</div>
                              </th>
                              <th colspan="1" rowspan="1" class="table-column1">
                                 <div class="cell">${dto.getMileage_type() }
                                    <fmt:formatNumber>${dto.getMileage_amount() }</fmt:formatNumber>
                                 </div>
                              </th>

                              <th colspan="1" rowspan="1" class="table-column1">
                                 <div class="cell">
                                    <fmt:formatNumber>${dto.getMileage_status() }</fmt:formatNumber>
                                 </div>
                              </th>

                              <th colspan="1" rowspan="1" class="table-column1">
                                 <div class="cell">${dto.getMileage_grade() }</div>
                              </th>
                           </tr>
                        </c:forEach>
                     </c:if>
                  </thead>
               </table>
            </div>
         </div>
      </div>
   </div>
</body>
</html>