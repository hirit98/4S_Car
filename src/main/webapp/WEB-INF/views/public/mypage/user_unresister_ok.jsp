<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>탈퇴 완료</title>
<link rel="stylesheet" href="css/common/style.css">
</head>
<body>
	<jsp:include page="/WEB-INF/include/complete_page.jsp" />
	<div class="success_page_box">
		<div class="success_box_inner">
			<div class="success_box_inner_text1">

				<img src="images/체크.png" alt="완료">
				<p>회원탈퇴가 완료되었습니다</p>
			</div>
			<div class="success_box_inner_text2">
				<p>그동안 4s_car를 이용해주셔서 감사합니다.</p>
				<p class="success_p">다음에도 또 이용해주세요!</p>
				<button type="button" class="success_button success_ok"
					onclick="location.href='main'">돌아가기</button>
			</div>
		</div>
	</div>
</body>
</html>