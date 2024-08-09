<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>회원 로그인</title>
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
      <div class="user_login">
         <div class="user_login_title">Login</div>

         <form method="post" action="user_login_ok">
            <div class="user_id_area">
               <h4>아이디</h4>
               <input type="text" name="user_id" placeholder="아이디를 입력하세요.">
            </div>

            <div class="user_password_area">
               <h4>비밀번호</h4>
               <input type="password" name="user_pwd" placeholder="비밀번호를 입력하세요.">
            </div>

            <div class="user_login_save_area">
               <label> <input type="checkbox"> <span>로그인 상태
                     유지</span>
               </label>
               <div class="user_login_save_area_bar"></div>
               <label> <input type="checkbox"> <span>아이디 저장</span>
               </label>
            </div>

            <input type="submit" value="로그인" class="user_login_submit">
         </form>

         <div class="user_login_move">
            <span class="user_login_move_signup"
               onclick="location.href='signup'">회원가입</span>

            <div class="user_login_find">
               <span>아이디 찾기</span>
               <div class="user_login_find_bar"></div>
               <span>비밀번호 찾기</span>
            </div>
         </div>

         <div class="user_login_simple">
            <div class="user_login_simple_bar"></div>
            <span>간편 로그인</span>
            <div class="user_login_simple_bar"></div>
         </div>

         <div class="user_login_logo">
            <div id="naver_id_login"></div>
            <div id="kakao-login-btn">
               <img src="https://www.kcar.com/images/user/icon-login-kakao.svg">
            </div>
            <div>
               <img src="https://www.kcar.com/images/user/icon-login-apple.svg">
            </div>
         </div>
      </div>
   </main>

   <jsp:include page="/WEB-INF/include/main_bottom.jsp" />

   <script type="text/javascript">
      var naver_id_login = new naver_id_login("bZczp5NQvRwL__y2mGCI",
            "http://localhost:8585/4S_Car/views/public/login/naver_callBack.jsp");
      var state = naver_id_login.getUniqState();
      naver_id_login.setButton("white", 1, 33);
      naver_id_login
            .setDomain("http://localhost:8585/4S_Car/views/public/login/user_login.jsp");
      naver_id_login.setState(state);
      naver_id_login.setPopup();
      naver_id_login.init_naver_id_login();
   </script>
   <script type='text/javascript'>
      Kakao.init('792b4092def3d1ec400130e2d55eaea5');

      $("#kakao-login-btn")
            .on(
                  "click",
                  function() {
                     //1. 로그인 시도
                     Kakao.Auth
                           .login({
                              success : function(authObj) {

                                 //2. 로그인 성공시, API 호출
                                 Kakao.API
                                       .request({
                                          url : '/v2/user/me',
                                          success : function(res) {
                                             console.log(res);
                                             var id = res.id;
                                             scope: 'account_email';
                                             alert('로그인성공');
                                             location.href = "http://localhost:8585/4S_Car/views/public/login/kakao_callBack.jsp";
                                          }
                                       })
                                 console.log(authObj);
                                 var token = authObj.access_token;
                              },
                              fail : function(err) {
                                 alert(JSON.stringify(err));
                              }
                           });

                  })
   </script>
</body>
</html>