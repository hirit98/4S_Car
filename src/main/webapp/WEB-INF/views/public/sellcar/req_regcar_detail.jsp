<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<head>
<meta charset="UTF-8">
<title>차량 상세정보 입력 페이지</title>
<style>
</style>
</head>
<style>
</style>
<body>
	<jsp:include page="/WEB-INF/include/main_top.jsp" />

	<form method="post" action="req_regcar_ok"
		enctype="multipart/form-data">
		<input type="hidden" name="car_std_no" value="${car_std_no }">
		<div class="car_detail">
			<h3>차량 상세정보 입력</h3>
			<div class="car_detail_table">
				<table border="1" class="table1">
					<tr>
						<th colspan="2">차량 기본정보</th>
					</tr>
					<tr>
						<th>제조사</th>
						<td>${dto.getCar_manufact_company() }</td>
					</tr>

					<tr>
						<th>차종</th>
						<td>${dto.getCar_type() }</td>
					</tr>

					<tr>
						<th>연식</th>
						<td>${dto.getCar_manufact_year() }년
							${dto.getCar_manufact_month() }월</td>
					</tr>

					<tr>
						<th>연료</th>
						<td>${dto.getCar_fuel() }</td>
					</tr>

					<tr>
						<th>변속기</th>
						<td>${dto.getCar_transmission() }</td>
					</tr>

					<tr>
						<th>색상</th>
						<td>${dto.getCar_color() }</td>
					</tr>

					<tr>
						<th>모델</th>
						<td>${dto.getCar_model() }</td>
					</tr>
				</table>

				<table class="table2" border="1">
					<tr class="car_detail_input detail_input">
						<th>회원번호</th>
						<td><input type="text" name="user_no" value="${user_no }"
							readonly></td>
					</tr>
					<tr class="car_detail_car_no detail_input car_detail_sort">
						<th>차량번호</th>
						<td><input type="text" name="car_no" required></td>
					</tr>

					<tr class="detail_input detail_distance car_detail_sort">
						<th>주행거리</th>
						<td><input type="number" name="car_distance" required>
							km</td>
					</tr>

					<tr class="detail_input detail_price car_detail_sort">
						<th>희망가격</th>
						<td><input type="number" name="car_price" required> 원
						</td>
					</tr>

					<tr>
						<th>상세정보</th>
						<td><textarea rows="12" cols="50" name="car_detail"
								placeholder=" 딜러에게 요청할 정보를<%="\n"%> 입력하세요." required></textarea>
						</td>
					</tr>

					<tr>
						<th>첨부파일</th>
						<td>
							<div class="car_filebox">
								<input class="upload-car" value="첨부파일" multiple
									placeholder="첨부파일"> <label for="file">파일찾기</label> <input
									type="file" id="file" name="car_file">
							</div>
						</td>
					</tr>
				</table>
			</div>
			<br>

			<div class="car_detail_btn">
				<input type="submit" value="딜러에게 전송" class="car_spec_btn"> <input
					type="reset" value="초기화" class="car_spec_btn">
			</div>
		</div>
	</form>
	<script type="text/javascript">
		$("#file").on('change', function() {
			var fileName = $("#file").val();
			$(".upload-car").val(fileName);
		});
	</script>
	<jsp:include page="/WEB-INF/include/main_bottom.jsp" />
</body>
</html>