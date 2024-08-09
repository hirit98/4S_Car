<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>내 정보 수정</title>
<link rel="icon" type="image/png" sizes="32x32"
	href="images/favicon-32x32.png">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<script src="js/pwd_check.js"></script>
</head>
<body>
	<div class="car_mypage">
		<jsp:include page="/WEB-INF/include/mypage_side_bar.jsp" />

		<div class="correct">
			<div class="title_bar">
				<hr class="correct-bar">
				<h2>내 정보 수정</h2>
				<hr class="correct-bar">
			</div>

			<form method="post" action="myinfo_modify_ok">
				<div class="form-group">
					<label for="username" style="font-weight: 500;">이름:</label> <input
						type="text" id="username" name="username"
						value="${dto.getUser_name() }" readonly>
				</div>
				<div class="form-group">
					<label for="userid" style="font-weight: 500;">아이디:</label> <input
						type="text" id="userid" name="userid" value="${dto.getUser_id() }"
						readonly>
				</div>
				<div class="form-group">
					<label for="phone" style="font-weight: 500;">연락처:</label> <input
						type="tel" id="phone" name="phone" value="${dto.getUser_phone() }">
				</div>
				<div class="form-group">
					<label for="email" style="font-weight: 500;">이메일:</label> <input
						type="email" id="email" name="email"
						value="${dto.getUser_email() }">
				</div>
				<div class="form-group">
					<label for="password" style="font-weight: 500;">새 비밀번호:</label> <input
						type="password" id="password" name="new_pwd" required>
					<div id="password_Error"></div>
				</div>

				<div class="form-group">
					<label for="passwordcheck" style="font-weight: 500;">비밀번호
						확인:</label> <input type="password" id="passwordcheck" name="pwdcheck"
						required>
					<div id="pwdcheck_Error"></div>
				</div>
				<div class="form-group btn">
					<button type="reset" class="reset">취소</button>
					<button type="submit" class="submit">수정 완료</button>
				</div>

			</form>
		</div>
	</div>
</body>
</html>