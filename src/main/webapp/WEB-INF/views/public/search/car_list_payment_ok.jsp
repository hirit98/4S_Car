<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>구매완료 페이지</title>
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
				<p>구매해 주셔서 감사합니다.</p>
			</div>
			<div class="success_box_inner_text2">
				<p>빠른 시일 내에 배송해드리겠습니다.</p>
				<button type="button" onclick="location.href='car_list_filter'">내차사기로
					돌아가기</button>
			</div>
		</div>
	</div>
</body>
</html>