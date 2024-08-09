<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>4Scar 메인페이지</title>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<link rel="icon" type="image/png" sizes="32x32"
	href="images/favicon-32x32.png">
</head>
<body>

	<jsp:include page="/WEB-INF/include/main_top.jsp" />
	<main>
		<div id="container_visual">
			<section class="main_visual">
				<div class="main_visual_wrap">
					<ul>
						<li class="v1">
							<p class="vtext1">믿고 맡기며 안전하게 거래할 수 있는 4scar</p>
						</li>
						<li class="v2">
							<p class="vtext1">중고차 최고의 매물은? 4scar</p>
						</li>
						<li class="v3">
							<p class="vtext1">중고차 No.1? 4scar</p>
						</li>
					</ul>
				</div>
			</section>
		</div>
		<div class="container">
			<section class="hom_container">
				<section class="service">
					<p class="service_p">4scar의 서비스를 소개합니다</p>
					<div class="introduce">
						<ul>
							<li><img
								src="https://groobee.net/wp-content/themes/groobee/images/main/main-service-targeting.gif">
								<span>내차팔기</span>
								<p>
									차량 등록 후 딜러와의 협의를 통해<br>쉽고 빠른 온라인 판매 서비스를 제공합니다.
								</p>
								<div onclick="location.href='req_regcar'">서비스 안내</div></li>
							<li><img
								src="https://groobee.net/wp-content/themes/groobee/images/main/main-service-onsite.gif">
								<span>내차사기</span>
								<p>
									원하는 차량을 검색하여 찜하고<br>간편한 온라인 결제가 가능합니다.
								</p>
								<div onclick="location.href='car_list_filter'">서비스 안내</div></li>
							<li><img
								src="https://groobee.net/wp-content/themes/groobee/images/main/main-service-data.gif">
								<span>1:1 문의</span>
								<p>
									모든 서비스에 대한 문의를<br>최대한 신속하고 친절하게 답변드리겠습니다.
								</p>
								<div onclick="location.href='qna_insert'">서비스 안내</div></li>
							<li><img
								src="   https://groobee.net/wp-content/themes/groobee/images/main/main-service-ai.gif">
								<span>판매후기</span>
								<p>
									판매 후기 게시판을 통해<br>모든 판매자의 후기를 확인할 수 있습니다.
								</p>
								<div onclick="location.href='sell_review_board_list'">서비스
									안내</div></li>
							<li><img
								src="https://groobee.net/wp-content/themes/groobee/images/main/main-service-offsite.gif
                                      ">
								<span>구매후기</span>
								<p>
									구매 후기 게시판을 통해<br>모든 구매자의 후기를 확인할 수 있습니다.
								</p>
								<div onclick="location.href='buy_review_board_list'">서비스
									안내</div></li>
							<li><img
								src="https://groobee.net/wp-content/themes/groobee/images/main/main-service-analysis.gif">
								<span>마일리지</span>
								<p>
									많은 이벤트를 통해 차량 구매 시<br>사용할 수 있는 마일리지를 적립해드립니다.
								</p>
								<div onclick="location.href='notice_list'">서비스 안내</div></li>
						</ul>
					</div>
				</section>
				<section class="vision">
					<p>
						쉬운 길보다는 옳은 길로,<br>나를 위한 선택! 4scar
					</p>
					<ul class="slide">
						<li><img class="slide_img1" src="images/내차사기후기사진.jpg" alt="">
							<div class="slide_textbox">
								<p>
									<strong>qnq11님</strong>
								</p>
								<p>내차사기 - 좋은차 싸게 샀어요!</p>
							</div></li>
						<li><img class="slide_img1" src="images/내차팔기후기사진2.jpg" alt="">

							<div class="slide_textbox">
								<p>
									<strong>psm9024님</strong>
								</p>
								<p>내차사기 - 제 첫차 너무좋..</p>
							</div></li>
						<li><img class="slide_img1" src="images/내차사기후기사진2.jpg" alt="">

							<div class="slide_textbox">
								<p>
									<strong>rlfghtjd님</strong>
								</p>
								<p>내차팔기 - 구매자분도 만족...</p>
							</div></li>
						<li><img class="slide_img1" src="images/내차팔기후기사진2.jpg" alt="">

							<div class="slide_textbox">
								<p>
									<strong>lmo05200님</strong>
								</p>
								<p>내차사기 - 중고차는 역시...</p>
							</div></li>
						<li><img class="slide_img1" src="images/내차팔기후기사진1.jpg" alt="">

							<div class="slide_textbox">
								<p>
									<strong>hong님</strong>
								</p>
								<p>내차팔기 - 서비스가 정말 친...</p>
							</div></li>
						<li><img class="slide_img1" src="images/내차팔기후기사진2.jpg" alt="">

							<div class="slide_textbox">
								<p>
									<strong>iron94님</strong>
								</p>
								<p>내차사기 - 다양한 정보를 한..</p>
							</div></li>
						<li><img class="slide_img1" src="images/내차팔기후기사진1.jpg" alt="">

							<div class="slide_textbox">
								<p>
									<strong>yong01님</strong>
								</p>
								<p>내차사기 - 상태가 정말 괜찮...</p>
							</div></li>
						<li><img class="slide_img1" src="images/내차팔기후기사진2.jpg" alt="">

							<div class="slide_textbox">
								<p>
									<strong>ilerl님</strong>
								</p>
								<p>내차사기 - 4scar 너무좋아요!..</p>
							</div></li>
					</ul>
				</section>
			</section>
			<section class="famous">
				<div class="title_more">
					<h2 class="notice_h2">인기매물</h2>
					<a href="car_list_filter"> <span>더 둘러보기<img
							class="more_img" src="./images/자동차둘러보기.png" alt="더 둘러보기"></span>
					</a>
				</div>
				<div class="fam-product"></div>
			</section>
			<section class="recommend">
				<div class="title_more">
					<h2 class="notice_h2">추천매물</h2>
					<a href="car_list_filter"> <span>더 둘러보기<img
							class="more_img" src="./images/자동차둘러보기.png" alt="더 둘러보기"></span></a>
				</div>
				<div class="recom-product"></div>
			</section>
			<section class="notice_mainpage_section">
				<div class="title_more">
					<h2 class="notice_h2">공지사항</h2>
					<span onclick="location.href='notice_list'">더보기<img
						class="more_img" src="./images/자동차둘러보기.png" alt="더 둘러보기"></span>
				</div>
				<div class="notice_mainpage_box">
					<div class="notice_mainpage_box1">
						<img id="main_notice_img" src="./images/배경없는수정R.png" alt="">
					</div>
					<div class="notice_mainpage_box2">
						<div class="notice_display"></div>
					</div>
				</div>
			</section>
			<div class="inquiry">
				<div>
					<p>견적상담문의</p>
					<span>담아두신 제품의 견적을 바로 문의해보세요.<br> 빠르게 담당자와 연결하여 제품을
						추천받아 견적을 진행할 수 있습니다.
					</span>
					<button>바로가기</button>
				</div>
				<div class="inquiry-img">
					<img src="./images/couple.png" alt="">
				</div>
			</div>

			<!-- 팝업 광고창 -->
			<div id="popup_layer" class="modal">
				<div class="popup_box">
					<!--팝업 컨텐츠 영역-->
					<div class="popup_cont">
						<img src="images/광고배너.png" alt="event page">
					</div>
					<!--팝업 버튼 영역-->
					<div class="popup_btn">
						<!--하루동안 보지않기-->
						<a id="chk_today" class="close_day">하루동안 보지않기</a>
						<!--7일간 보지않기-->
						<!-- <a id="chk_today" href="javascript:closeToday();" class="close_day">Do not open for 7 days</a>-->
						<a id="chk_close">닫기</a>
					</div>
				</div>
			</div>
		</div>
	</main>
	<jsp:include page="/WEB-INF/include/main_bottom.jsp" />

	<script src="js/print_notice.js"></script>
	<script src="js/popup.js"></script>
</body>
</html>