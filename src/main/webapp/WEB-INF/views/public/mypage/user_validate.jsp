<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>비밀번호 확인</title>
<link rel="stylesheet" href="css/common/style.css">
<link rel="icon" type="image/png" sizes="32x32"
	href="images/favicon-32x32.png">
</head>
<body>
	<jsp:include page="/WEB-INF/include/mypage_side_bar.jsp" />

	<div class="modify_container">
		<form class="modify_passwordform" method="post" action="myinfo_modify">
			<img class="modify_password_4scar_logo"
				src="images/mypage_4scar_logo.png" alt="로고">
			<h2>내 정보 수정하기</h2>
			<div class="modifypassword_form_inner">
				<div class="modifypassword_form_inner_box">

					<p>정보를 안전하게 보호하기 위해 비밀번호를 다시 한번 입력해주세요.</p>
					<div>

						<div class="myinfo_password_inner">
							<div class="passwordinner_img">
								<img id="password_key" src="images/키.png" alt="키">
							</div>
							<div class="inner_password">
								<input type="password" id="modify_password" name="input_pwd"
									placeholder="비밀번호를 입력해주세요." required>
								<button type="submit" class="modify_password_click"
									id="check_btn">확인</button>
							</div>
						</div>

					</div>
				</div>
				<a class="myinfo_back_mypage" href="mypage">
					<div class="myinfo_back">
						<img src="images/뒤로가기.png" alt="뒤로가기"> 뒤로가기
					</div>
				</a>
			</div>
		</form>
	</div>
</body>
</html>