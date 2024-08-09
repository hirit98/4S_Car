<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>관리자 로그인</title>
<script
   src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<script src="motion.js"></script>
<script type="text/javascript"
   src="https://static.nid.naver.com/js/naverLogin_implicit-1.0.3.js"
   charset="utf-8"></script>
<script type="text/javascript"
   src="http://code.jquery.com/jquery-1.11.3.min.js"></script>
<script src="https://developers.kakao.com/sdk/js/kakao.min.js"></script>
<link rel="icon" type="image/png" sizes="32x32"
   href="images/favicon-32x32.png">
</head>
<body>

   <jsp:include page="/WEB-INF/include/main_top.jsp" />

   <main>
      <div class="manager_login">

         <div class="manager_login_title">
            <img src="images/free-icon-manager-5435513.png" alt="관리자로그인">
            Manager Login
         </div>

         <form method="post" action="manager_login_ok">
            <div class="manager_id_area">
               <h4>아이디</h4>
               <input type="text" name="manager_id" placeholder="아이디를 입력하세요.">
            </div>

            <div class="manager_password_area">
               <h4>비밀번호</h4>
               <input type="password" name="manager_pwd"
                  placeholder="비밀번호를 입력하세요.">
            </div>

            <div class="manager_login_save_area">
               <label> <input type="checkbox"> <span>로그인 상태
                     유지</span>
               </label>
               <div class="manager_login_save_area_bar"></div>
               <label> <input type="checkbox"> <span>아이디 저장</span>
               </label>
            </div>

            <input type="submit" value="로그인" class="manager_login_submit">
         </form>

         <div class="manager_login_move">
            <span class="manager_login_move_signup"
               onclick="location.href='signup'">회원가입</span>

            <div class="manager_login_find">
               <span>아이디 찾기</span>
               <div class="manager_login_find_bar"></div>
               <span>비밀번호 찾기</span>
            </div>
         </div>
      </div>
   </main>

   <jsp:include page="/WEB-INF/include/main_bottom.jsp" />
</body>
</html>