<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>회사 정보 관리</title>
<script src="http://code.jquery.com/jquery-1.9.0rc1.js"></script>
<link rel="icon" type="image/png" sizes="32x32"
   href="images/favicon-32x32.png">
</head>
<body>

   <jsp:include page="/WEB-INF/include/admin_side_bar.jsp" />

   <main class="car_managerpage_menual">
      <div class="admin_company_info_modify">
         <section class="admin_company_info_modify_title">
            <img src="images/프로필.png" alt="사람" class="people">
            <div class="admin_company_info_modify_text">
               <p>회사 정보 관리</p>
            </div>
            <img src="images/관리자페이지_톱니바퀴_스패너.png" alt="스패너">
         </section>

         <div class="admin_company_info_modify_all">
            <div class="admin_company_info_modify_left">
               <img src="images/회사사진.jpg">
            </div>
            <form method="post" action="admin_company_info_modify_ok">
               <section class="admin_company_info_modify_content">
                  <table>
                     <tr>
                        <th>사업자번호</th>
                        <td><input type="text" name="company_no" value="${dto.getCompany_business_no() }" readonly></td>
                     </tr>

                     <tr>
                        <th>회사명</th>
                        <td><input type="text" name="company_name" value="${dto.getCompany_name() }"></td>
                     </tr>

                     <tr>
                        <th>대표자</th>
                        <td><input type="text" name="company_ceo" value="${dto.getCompany_ceo_name() }"></td>
                     </tr>

                     <tr>
                        <th>주소</th>
                        <td><input type="text" name="company_addr" value="${dto.getCompany_addr() }"></td>
                     </tr>

                     <tr>
                        <th>설립일</th>
                        <td><input type="text" name="company_date" value="${dto.getCompany_found_date() }" readonly></td>
                     </tr>

                     <tr>
                        <th>전화번호</th>
                        <td><input type="text" name="company_phone" value="${dto.getCompany_phone() }"></td>
                     </tr>

                     <tr>
                        <th>계좌번호</th>
                        <td><input type="text" name="company_account" value="${dto.getCompany_account() }"></td>
                     </tr>
                  </table>
               </section>

               <section class="admin_company_info_modify_button">
                  <button type="submit">수정</button>
                  <button type="reset">초기화</button>
               </section>
            </form>
         </div>
      </div>
   </main>
</body>
</html>