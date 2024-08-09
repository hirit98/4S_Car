<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>삭제 완료</title>
<link rel="stylesheet" href="css/common/style.css">
<link rel="icon" type="image/png" sizes="32x32"
	href="images/favicon-32x32.png">
</head>
<body>
	<jsp:include page="/WEB-INF/include/complete_page.jsp" />
	<div class="success_page_box">
		<div class="success_box_inner">
			<div class="success_box_inner_text1">

				<img src="images/체크.png" alt="완료">
				<p>판매요청 게시글 삭제가 완료되었습니다</p>
			</div>
			<div class="success_box_inner_text2">
				<p class="success_p">다음에도 또 이용해주세요!</p>
				<button type="button" onclick="location.href='mycar_info'">돌아가기</button>
			</div>
		</div>
	</div>
</body>
</html>