<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>회원 탈퇴</title>
<link rel="stylesheet" href="css/common/style.css">
<link rel="icon" type="image/png" sizes="32x32"
	href="images/favicon-32x32.png">
</head>
<body>
	<jsp:include page="/WEB-INF/include/mypage_side_bar.jsp" />
	<div class="user_out_page_menual">

		<section class="user_out_page">
			<p>회원탈퇴</p>
			<div class="user_out_page_hrbox1"></div>
			<div class="user_out_page_hrbox2"></div>

			<div class="out_info">

				<p>회원 정보 및 구매 내역 삭제</p>
				<p>회원 탈퇴 즉시, 회원 정보는 모두 삭제되고 재가입시에도 복원되지 않으며 이점 꼭 양지해 주시기 바랍니다.</p>
				<div class="out_info_text">
					<p>삭제 되는 정보</p>
					<p style="margin: 0px 0px 18px 0px;">개인정보, 구매내역, 마일리지, 리뷰, 찜목록
						내역 등</p>
				</div>
				<hr>
				<p>탈퇴 후 정보보관에 대한 안내</p>
				<p>
					전자상거래 등에서의 소비자보호에 관한 법률 제6조에 의거하여 성명, 주소, 주민등록번호 등 거래의 주체를 식별할 수 있는
					정보에 한하여<br> 서비스 이용에 관한 동의를 철회한 경우에도 이를 보전할 수 있으며, 동법 시행령 제6조에
					의거해 다음과 같이 거래기록을 보관합니다.
				</p>

				<ul>
					<li style="font-size: 18px; margin-top: 20px; font-weight: 700;">표시,
						광고에 관한 기록 : 6개월</li>
					<li>계약 또는 청약철회 등에 관한 기록 : 5년</li>
					<li>대금결제 및 재화 등의 공급에 관한 기록 : 5년</li>
					<li style="margin: 0px 0px 20px 0px;">소비자의 불만 또는 분쟁처리에 관한 기록 :
						3년</li>
				</ul>
				<hr>
				<p class="out_thankyou">그동안 4scar를 이용해 주셔서 감사합니다.</p>
				<p>회원님의 소중한 의견을 받아 더 좋은 4scar가 되도록 노력하겠습니다.</p>
				<div class="user_out_checkbox1" style="margin: 20px 0px 10px 0px;">
					<input type="checkbox" name="out"> 사이트 사용 빈도 낮음 <input
						type="checkbox" name="out" id="inforadio1"> 원하는 매물 부족
				</div>

				<div class="user_out_checkbox2">

					<input type="checkbox" name="out"> 허위매물 우려 <input
						type="checkbox" name="out" id="inforadio2"> 개인정보 유출 우려
				</div>
				<div class="user_out_page_hrbox1"></div>
				<div class="user_out_page_hrbox2"></div>
				<div class="user_out_button_box">
					<button type="button" class="user_out_button"
						onclick="location.href='user_unresister_validate'">회원
						탈퇴하기</button>
				</div>
			</div>
		</section>
	</div>
	</div>

</body>
</html>