<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>회원가입</title>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<script src="js/signup.js"></script>
<script src="js/empty.js"></script>
<link rel="icon" type="image/png" sizes="32x32"
	href="images/favicon-32x32.png">
</head>
<body>
	<jsp:include page="/WEB-INF/include/main_top.jsp" />

	<main class="main_sign_up">
		<div class="main_sign_up container">
			<h2>회원가입</h2>
		</div>
		<div class="main_sign_up_text">
			<p>안전한 회원가입을 위해 정확한 정보를 입력해주세요.</p>
		</div>
		<div class="input_text">
			<div class="input_sign_up_inner">
				<P>정보를 입력해주세요.</P>
				<hr>
			</div>
		</div>
		<form class="input_sign_up" id="form" action="signup_ok" method="post"
			name="form" onsubmit="return check();">
			<div>
				<div class="info">
					<div class="input_info_1">
						<div class="id_Area2">
							<div class="signup_title_id">아이디</div>
							<div id="input_id">
								<input id="id" type="text" autocomplete="off"
									placeholder="아이디를 입력하세요." class="signup_id_inner" name="id">
								<input type="button" id="id_check" class="id_Dupli"
									value="중복 확인">
								<div id="id_Error"></div>
							</div>
						</div>
						<div class="password_Area">
							<div class="signup_title_password">비밀번호</div>
							<div id="input_password">
								<input id="password" type="password" autocomplete="off"
									placeholder="비밀번호를 입력하세요." class="signup_id_inner" name="pwd">
								<div id="password_Error"></div>
							</div>
						</div>
						<div class="PasswordCheck_Area">
							<div class="signup_title_passwordcheck">비밀번호 확인</div>
							<div id="input_passwordcheck">
								<input id="passwordcheck" type="password" autocomplete="off"
									placeholder="비밀번호를 다시 입력해주세요." class="signup_id_inner"
									name="pwd_check">
								<div id="pwdcheck_Error"></div>
							</div>
						</div>
					</div>
					<div class="input_info_2">
						<div class="id_Area">
							<div class="signup_title_id">이름</div>
							<div id="input_id">
								<input id="name" type="text" autocomplete="off"
									placeholder="이름을 입력하세요." class="signup_id_inner" name="name">
								<div id="name_Error"></div>
							</div>
						</div>
						<div class="email_Area">
							<div class="signup_title_password">이메일</div>
							<div id="input_password">
								<input id="email" type="text" autocomplete="off"
									placeholder="이메일을 입력하세요." class="signup_id_inner" name="email">
							</div>
							<div id="email_Error"></div>
						</div>
						<div class="PhoneNum_Area">
							<div class="signup_title_phone">휴대폰번호</div>
							<div id="phone">
								<input id="phone1" type="text" size="1" maxlength="3"
									oninput="changePhone1()" name="phone1"> - <input
									id="phone2" type="text" size="3" maxlength="4"
									oninput="changePhone2()" name="phone2"> - <input
									id="phone3" type="text" size="3" maxlength="4"
									oninput="changePhone3()" name="phone3">
							</div>
							<!-- <div class="auth">
                                                  <div id="certificationNumber">000000</div>
                                                  <button disalbed id="sendMessage" onclick="getToken()">인증번호 전송</button>
                                              </div>
                                              <div class="timer">
                                                  <div id="timeLimit">3:00</div>
                                                  <button disalbed id="completion" onclick="checkCompletion()">인증확인</button>
                                              </div> -->
						</div>
					</div>
				</div>
				<div class="send">
					<div class="btnlogin">
						<div class="search box el-row">
							<input type="submit" id="signUpButton" value="가입하기">
						</div>
					</div>
				</div>
			</div>
		</form>
	</main>

	<jsp:include page="/WEB-INF/include/main_bottom.jsp" />

</body>
</html>