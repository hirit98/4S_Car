<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>회원 목록</title>
<script src="http://code.jquery.com/jquery-1.9.0rc1.js"></script>
<link rel="icon" type="image/png" sizes="32x32"
   href="images/favicon-32x32.png">
</head>
<body>

   <jsp:include page="/WEB-INF/include/admin_side_bar.jsp" />

   <main class="car_managerpage_menual">
      <div class="user_content">
         <section class="user_content_title">
            <img src="images/프로필.png" alt="사람" class="people">
            <div class="user_content_text">
               <p>회원 정보</p>
            </div>
            <img src="images/관리자페이지_톱니바퀴_스패너.png" alt="스패너">
         </section>

         <section class="user_content_content">
            <h2>${dto.getUser_name() }님의 정보</h2>
            <table>
               <tr>
                  <th>회원번호</th>
                  <td>${dto.getUser_no() }</td>
               </tr>

               <tr>
                  <th>아이디</th>
                  <td>${dto.getUser_id() }</td>
               </tr>

               <tr>
                  <th>회원명</th>
                  <td>${dto.getUser_name() }</td>
               </tr>
               
               <tr>
                  <th>전화번호</th>
                  <td>${dto.getUser_phone() }</td>
               </tr>
               
               <tr>
                  <th>이메일</th>
                  <td>${dto.getUser_email() }</td>
               </tr>
               
               <tr>
                  <th>마일리지</th>
                  <td>${dto.getUser_mileage() }</td>
               </tr>
               
               <tr>
                  <th>등급</th>
                  <td>${dto.getUser_grade() }</td>
               </tr>

               <tr>
                  <th>가입일</th>
                  <td>${dto.getUser_regdate().substring(0, 10) }</td>
               </tr>
            </table>
         </section>
         
         <section class="user_content_button">
         	<c:if test="${empty field }">
            	<button onclick="location.href='admin_user_list?page=${page }'">목록으로</button>
            </c:if>
            <c:if test="${!empty field }">
            	<button onclick="location.href='admin_user_search?page=${page }&field=${field }&keyword=${keyword }'">목록으로</button>
            </c:if>
         </section>

      </div>
   </main>
</body>
</html>