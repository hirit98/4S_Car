<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>이벤트 등록</title>
<link rel="stylesheet" href="css/common/style.css">
<link rel="icon" type="image/png" sizes="32x32"
	href="images/favicon-32x32.png">
</head>
<body>
	<jsp:include page="/WEB-INF/include/complete_page.jsp" />
	<div class="managerpage_success_page_box">
		<div class="managerpage_success_box_inner">
			<div class="managerpage_success_box_inner_text1">

				<img src="images/매니저페이지체크.png" alt="완료">
				<p>이벤트 등록이 완료되었습니다</p>
			</div>
			<div class="managerpage_success_box_inner_text2">
				<p>이벤트 관리창으로 돌아가시려면 밑의 버튼을 클릭해주세요</p>
				<button type="button" onclick="location.href='admin_event_list'">돌아가기</button>
			</div>
		</div>
	</div>
</body>
</html>