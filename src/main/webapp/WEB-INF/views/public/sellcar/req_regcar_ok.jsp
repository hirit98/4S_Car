<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
// 줄바꿈 처리
pageContext.setAttribute("newLine", "\n");
pageContext.setAttribute("br", "<br/>");
%>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="icon" type="image/png" sizes="32x32"
	href="images/favicon-32x32.png">
</head>
<body>

	<jsp:include page="/WEB-INF/include/main_top.jsp" />
	<div class="car_detail_reg_ok">
		<div class="car_detail_inner">
			<div class="car_detail_ok">
				<img src="images/체크.png" alt="완료">
				<h3>차 등록 요청이 등록되었습니다.</h3>
				<br>
			</div>
			<div class="car_detail_ok2">
				<p>조금있으면 차량 등록 요청이 수락되오니 조금만 기다려주세요.</p>
				<p>메인페이지로 돌아가시려면 밑의 버튼을 클릭해주세요.</p>
				<input type="button" value="메인페이지 이동" class="detail_ok_btn"
					onclick="location.href='main'">
			</div>
		</div>
	</div>
	<jsp:include page="/WEB-INF/include/main_bottom.jsp" />
</body>
</html>