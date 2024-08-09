<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>판매 후기 등록 완료</title>
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
				<p>판매 후기 등록이 완료되었습니다.</p>
			</div>
			<div class="success_box_inner_text2">
				<p>메인페이지로 돌아가시려면 밑의 버튼을 클릭해주세요</p>
				<button type="button" class="success_button success_ok"
					onclick="location.href='my_review'">돌아가기</button>
			</div>
		</div>
	</div>

</body>
</html>