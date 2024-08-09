<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>회사소개</title>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<link rel="icon" type="image/png" sizes="32x32"
	href="images/favicon-32x32.png">
</head>
<body>

	<jsp:include page="/WEB-INF/include/main_top.jsp" />
	<main>
		<section class="company_info">
			<div class="company_info_visual">
				<p class="company_info_visual_title">미래를 지향하는 4scar</p>
				<p class="company_info_visual_content">최고를 지향하기 보다는 누구나 신뢰할 수 있는
					기업으로 도약하는 기업!</p>
				<p class="company_info_visual_content">글로벌 비즈니스 플랫폼은 새로운 비즈니스
					세계를 만들어갑니다.</p>
			</div>
		</section>
		<section class="company_content container">
			<div class="company_info_box1">
				<h2>회사 소개</h2>
			</div>
			<div class="company_info_box2">
				<img src="images/신뢰.jpg" alt="">
				<div class="company_info_text">
					<p class="company_info_text_p1">최고보다는 신뢰를 지향하는 4scar!</p>
					<p class="company_info_text_p2">
						회사는 수년간 쌓아온 노하우와 신뢰를 바탕으로 중고차 분야에서 <br> 높은 수준의 실적을 이루어냈습니다.<br>
						이러한 경험을 바탕으로 고객님들께 항상 좋은 서비스를 공급하는 것을 4scar를 목표로 하여, <br>
						앞으로도 좋은 서비스를 제공하는 것을 약속드립니다.
					</p>
				</div>
			</div>
			<div class="company_info_box3">
				<img src="images/계약.jpg" alt="">
				<div class="company_info_text">
					<p class="company_info_text_p1">고객의 눈높이에 맞추는 4scar</p>
					<p class="company_info_text_p2">
						당사는 중고차 수출 전문업체로서 현대자동차, 기아자동차, 쉐보레 등<br>다양한 브랜드 전차종을 취급하고
						있습니다.<br> 고객님의 소중한 차량을 최고가 가격에 매입하려고 노력하고 있습니다.<br> 언제나
						고객만족에서 고객감동을 목표로 최선을 다하고 있습니다.<br> 정확한 견적, 정직한 가격, 한결같은 성실함으로
						성원에 확실히 보답하겠습니다.
					</p>
				</div>
			</div>
			<div class="company_info_box4">
				<img src="images/자동차.jpg" alt="">
				<div class="company_info_text">
					<p class="company_info_text_p1">마지막 CEO 인사</p>
					<p class="company_info_text_p2">
						저희 4scar는 수년간의 노력과 시행착오를 통해 보다 나은 서비스와 고객응대를 위하여<br> 발로 뛰고
						가슴으로 공감하며 고객님들과 더불어 성장을 거듭해왔습니다.<br> 오프라인에서 쌓은 노하우를 바탕으로 좋은
						서비스와 만족도 높은 매물로<br> 한발짝 더 다가가기 위해 노력하겠습니다. <br>
					</p>
				</div>
			</div>
		</section>
		<section class="company_map container">
			<h2>4scar로 찾아오시는 길</h2>

			<div class="company_map_if">
				<iframe
					src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d399.2669524325789!2d127.10852591308468!3d36.815269968136136!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x357b27a71822d995%3A0xd1422f77f849d698!2z7LKc7JWI7Lu07ZOo7YSw7ZWZ7JuQLVNCU-yVhOy5tOuNsOuvuOy7tO2TqO2EsOyVhO2KuO2VmeybkA!5e0!3m2!1sko!2skr!4v1711888024169!5m2!1sko!2skr"
					width="79%" height="600" style="border: 0;" allowfullscreen=""
					loading="lazy" referrerpolicy="no-referrer-when-downgrade"></iframe>
			</div>
			<div class="company_map_info">
				<div class="company_address">
					<ul>
						<li class="company_ad">주소</li>
						<li class="company_li">충청남도 천안시 서북구 불당22대로 92 7층</li>
					</ul>
					<ul>
						<li class="company_ad">대중교통</li>
						<li class="company_li2">천안터미널에서 버스 탑승(1, 3, 21, 90, 800, 990)</li>
					</ul>
					<ul>
						<li class="company_ad">자가용</li>
						<li class="company_li3">마블러스T타워(본 건물) 지하 주차장 이용</li>
					</ul>
				</div>
				<div class="company_map_inquiry">
					<h2 class="company_ad">제휴문의</h2>
					<p>4Scar와 함께 당신의 비즈니스를 더하세요.</p>
					<button onclick="location.href='qna_insert'">문의하기</button>
				</div>
			</div>
		</section>
	</main>

	<jsp:include page="/WEB-INF/include/main_bottom.jsp" />
	<script src="js/move_map.js"></script>
</body>
</html>