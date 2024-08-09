<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>naver_CallBack 페이지</title>
<script type="text/javascript"
	src="https://static.nid.naver.com/js/naverLogin_implicit-1.0.3.js"
	charset="utf-8"></script>
<script type="text/javascript"
	src="http://code.jquery.com/jquery-1.11.3.min.js"></script>
</head>
<body>
	<script type="text/javascript">
		var naver_id_login = new naver_id_login("bZczp5NQvRwL__y2mGCI",
				"http://localhost:8585/4S_Car/views/public/login/naver_callBack.jsp");
		// 접근 토큰 값 출력
		// alert(naver_id_login.oauthParams.access_token);
		// 네이버 사용자 프로필 조회
		naver_id_login.get_naver_userprofile("naverSignInCallback()");
		// 네이버 사용자 프로필 조회 이후 프로필 정보를 처리할 callback function
		function naverSignInCallback() {

			alert(naver_id_login.getProfileData('email')); // 가입자 등록 이메일
			alert(naver_id_login.getProfileData('name')); // 가입자 이름
			alert(naver_id_login.getProfileData('mobile')); // 가입자 등록 전화번호

		}
	</script>

</body>
</html>