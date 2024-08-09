<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>QnA 업로드 완료</title>
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
				<p>QnA 글 업로드가 완료되었습니다</p>
			</div>
			<div class="success_box_inner_text2">
				<p>메인페이지로 돌아가시려면 밑의 버튼을 클릭해주세요</p>
				<button type="button" class="success_ok success_button"
					onclick="location.href='main'">돌아가기</button>
			</div>
		</div>
	</div>
</body>
</html>