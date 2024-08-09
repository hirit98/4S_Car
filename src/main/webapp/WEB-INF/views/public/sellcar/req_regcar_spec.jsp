<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="icon" type="image/png" sizes="32x32"
	href="images/favicon-32x32.png">
</head>
<body>
	<jsp:include page="/WEB-INF/include/main_top.jsp" />

	<div class="car_spec">
		<h3>차량 기본정보 선택</h3>

		<form method="post" action="req_regcar_detail">
			<table>
				<tr>
					<th>제조사</th>
					<td colspan="3"><select name="company">
							<c:forEach var="i" begin="0" end="${companyList.size() - 1 }">
								<option>${companyList.get(i) }</option>
							</c:forEach>
					</select></td>
				</tr>

				<tr>
					<th>차종</th>
					<td colspan="3"><select name="type">
							<c:forEach var="i" begin="0" end="${typeList.size() - 1 }">
								<option>${typeList.get(i) }</option>
							</c:forEach>
					</select></td>
				</tr>

				<tr class="car_simple_year">
					<th>연식</th>
					<td>
						<div class="simple_year_input">
							<div>
								<input type="number" name="year" width="20" required> <span>
									년 </span>
							</div>
							<div>
								<select name="month">
									<c:forEach var="i" begin="1" end="12">
										<option>${i }</option>
									</c:forEach>
								</select> <span> 월 </span>
							</div>
						</div>
					</td>
				</tr>

				<tr>
					<th>연료</th>
					<td colspan="3"><select name="fuel">
							<c:forEach var="i" begin="0" end="${fuelList.size() - 1 }">
								<option>${fuelList.get(i) }</option>
							</c:forEach>
					</select></td>
				</tr>

				<tr>
					<th>변속기</th>
					<td colspan="3"><select name="transmission">
							<c:forEach var="i" begin="0"
								end="${transmissionList.size() - 1 }">
								<option>${transmissionList.get(i) }</option>
							</c:forEach>
					</select></td>
				</tr>

				<tr>
					<th>색상</th>
					<td colspan="3"><select name="color">
							<c:forEach var="i" begin="0" end="${colorList.size() - 1 }">
								<option>${colorList.get(i) }</option>
							</c:forEach>
					</select></td>
				</tr>

				<tr class="car_simple_model">
					<th>모델</th>
					<td colspan="3"><input type="text" name="model" required>
					</td>
				</tr>
			</table>
			<br> <input type="submit" value="다음" class="car_spec_btn">
			<input type="reset" value="초기화" class="car_spec_btn">
		</form>
	</div>

	<jsp:include page="/WEB-INF/include/main_bottom.jsp" />
</body>
</html>