<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>관리자 페이지</title>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<script
   src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
<link rel="icon" type="image/png" sizes="32x32"
   href="images/favicon-32x32.png">
</head>
<body>
   <jsp:include page="/WEB-INF/include/admin_side_bar.jsp" />
   <div class="car_managerpage_menual">
   <div class="admin_main">
      <section class="admin_main_title">
            <img src="images/프로필.png" alt="사람" class="people">
            <div class="admin_main_text">
               <p>관리자 페이지</p>
            </div>
            <img src="images/관리자페이지_톱니바퀴_스패너.png" alt="스패너">
         </section>
         
      <div class="car_managerpage_main">
         <div class="car_managerpage_user">
            <div class="p1">
               <p class="sub_title">회원차트</p>
               <button class="plus_btn" type="button"
                  onclick="location.href='admin_user_list'">
                  <img class="plus_img" src="images/announce-search.png">
               </button>
            </div>
            <div class="car_managerpage_user_chart">
               <table>
                  <tr>
                     <th>회원번호</th>
                     <th>회원아이디</th>
                     <th>회원명</th>
                     <th>전화번호</th>
                     <th>이메일</th>
                     <th>마일리지</th>
                     <th>등급</th>
                     <th>가입일</th>
                  </tr>
                  <c:if test="${!empty list }">
                     <c:forEach var="dto" items="${list }" begin="0" end="3" step="1">
                        <tr>
                           <td colspan="1" rowspan="1">
                              <div class="cell">${dto.getUser_no() }</div>
                           </td>
                           <td colspan="1" rowspan="1">
                              <div class="cell">${dto.getUser_id() }</div>
                           </td>
                           <td colspan="1" rowspan="1">
                              <div class="cell">${dto.getUser_name() }</div>
                           </td>
                           <td colspan="1" rowspan="1">
                              <div class="cell">${dto.getUser_phone() }</div>
                           </td>
                           <td colspan="1" rowspan="1">
                              <div class="cell">${dto.getUser_email() }</div>
                           </td>
                           <td colspan="1" rowspan="1">
                              <div class="cell">${dto.getUser_mileage() }</div>
                           </td>
                           <td colspan="1" rowspan="1">
                              <div class="cell">${dto.getUser_grade() }</div>
                           </td>
                           <td colspan="1" rowspan="1">
                              <div class="cell">${dto.getUser_regdate().substring(0, 10) }</div>
                           </td>
                        </tr>
                     </c:forEach>
                  </c:if>

                  <c:if test="${empty list }">
                     <tr>
                        <td colspan="8" align="center">
                           <h3 class="cell">회원 정보가 없습니다...</h3>
                        </td>
                     </tr>
                  </c:if>
               </table>
            </div>
         </div>
         <div class="car_managerpage_car_user">
            <div class="car_managerpage_request">
               <div class="p2">
                  <p class="sub_title">최근 차량등록 요청</p>
                  <button class="plus_btn" type="button"
                     onclick="location.href='admin_req_regcar_list'">
                     <img class="plus_img" src="images/announce-search.png">
                  </button>
               </div>
               <div>
                  <table>
                     <tr>
                        <th>회원번호</th>
                        <th>차량번호</th>
                        <th>요청일시</th>
                     </tr>
                     <c:if test="${!empty req }">
                        <c:forEach var="req" items="${req }" begin="0" end="3" step="1">
                           <tr>
                              <td colspan="1" rowspan="1">
                                 <div class="cell">${req.getReq_sell_board_user_no() }</div>
                              </td>
                              <td colspan="1" rowspan="1">
                                 <div class="cell">${req.getReq_sell_board_car_no() }</div>
                              </td>
                              <td colspan="1" rowspan="1">
                                 <div class="cell">${req.getReq_sell_board_date().substring(0, 10) }</div>
                              </td>
                           </tr>
                        </c:forEach>
                     </c:if>
                     <c:if test="${empty req }">
                        <tr>
                           <td colspan="3" align="center">
                              <h3 class="cell">차량 요청 게시글이 없습니다..</h3>
                           </td>
                        </tr>
                     </c:if>
                  </table>

               </div>
            </div>
            <div class="car_managerpage_ask">
               <div class="p3">
                  <p class="sub_title">최근 문의함</p>
                  <button class="plus_btn" type="button"
                     onclick="location.href='admin_qna_list'">
                     <img class="plus_img" src="images/announce-search.png">
                  </button>
               </div>
               <div>
                  <table>
                     <tr>
                        <th>글번호</th>
                        <th>문의 종류</th>
                        <th>문의 제목</th>
                        <th>문의일시</th>
                     </tr>
                     <c:if test="${!empty qna }">
                        <c:forEach var="qna" items="${qna }" begin="0" end="3" step="1">
                           <tr>
                              <td colspan="1" rowspan="1">
                                 <div class="cell">${qna.getQna_board_no() }</div>
                              </td>
                              <td colspan="1" rowspan="1">
                                 <div class="cell">${qna.getQna_board_type() }</div>
                              </td>
                              <td colspan="1" rowspan="1">
                                 <div class="cell">${qna.getQna_board_title() }</div>
                              </td>
                              <td colspan="1" rowspan="1">
                                 <div class="cell">${qna.getQna_board_date().substring(0, 10) }</div>
                              </td>
                           </tr>
                        </c:forEach>
                     </c:if>
                     <c:if test="${empty qna }">
                        <tr>
                           <td colspan="4">
                              <h3 class="cell">최근 문의가 없습니다...</h3>
                           </td>
                        </tr>
                     </c:if>
                  </table>
               </div>
            </div>
         </div>
         <div class="car_managerpage_carsell_graph">
            <div class="p4">
               <p class="sub_title">2023년 판매율 및 가입자 수</p>
            </div>
            <div class="carsell_graph">
               <div class="chartbox1">
                  <canvas id="myChart" width="500" height="190"></canvas>
               </div>
               <div class="chartbox2">
                  <canvas id="lineChart" width="500" height="190"></canvas>
               </div>
            </div>
         </div>
         </div>
      </div>
   </div>
   <script type="text/javascript">
      var months = ${monthList};
      var sales = ${priceList}
      var subscribers = ${signupList}; 
      var purchases =   ${cntSalesCarList};
      var reqcars = ${cntReqCarList};
      var targetsales = ${targetSalesList};
   </script>
   <script src="js/graph.js"></script>
</body>
</html>