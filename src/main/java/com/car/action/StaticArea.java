package com.car.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.car.model.AdminDTO;
import com.car.model.UserDTO;

public class StaticArea {

	// jsp 파일에서 반복적으로 사용될 리스트
	public static List<String> companyList = new ArrayList<>(Arrays.asList("현대", "제네시스", "기아", "쉐보레", "르노코리아", "대우",
			"쌍용", "벤츠", "bmw", "아우디", "폭스바겐", "미니", "볼보", "폴스타", "포르쉐", "도요타"));

	public static List<String> typeList = new ArrayList<>(
			Arrays.asList("경차", "소형", "중형", "대형", "스포츠", "SUV", "승합", "화물"));

	public static List<String> fuelList = new ArrayList<>(Arrays.asList("가솔린", "디젤", "LPG", "전기", "하이브리드"));

	public static List<String> filterList = new ArrayList<>(Arrays.asList("전체", "제조사", "차종", "연료", "색상", "모델명"));

	public static List<String> transmissionList = new ArrayList<>(Arrays.asList("자동", "수동"));

	public static List<String> colorList = new ArrayList<>(Arrays.asList("흰색", "검정색", "회색", "은색", "빨간색", "파란색", "초록색", "노란색"));

	public static List<String> sequenceList = new ArrayList<>(
			Arrays.asList("최근 연식순", "오래된 연식순", "적은 주행거리순", "많은 주행거리순", "낮은 가격순", "높은 가격순"));

	public static List<String> cardCops = new ArrayList<>(
			Arrays.asList("KB국민", "NH농협", "BC비씨", "신한", "삼성", "롯데", "현대", "우리", "하나", "카카오뱅크", "토스뱅크"));

	public static List<String> getCompanyList() {
		return companyList;
	}

	public static List<String> getTypeList() {
		return typeList;
	}

	public static List<String> getFuelList() {
		return fuelList;
	}

	public static List<String> getFilterList() {
		return filterList;
	}

	public static List<String> getTransmissionList() {
		return transmissionList;
	}

	public static List<String> getColorList() {
		return colorList;
	}

	public static List<String> getSequenceList() {
		return sequenceList;
	}

	public static List<String> getCardCops() {
		return cardCops;
	}

	// 날짜 정보 month를 2자리로 맞추기 위한 메서드
	public static String getMonth(int month) {
		if (month < 10) {
			return "0" + String.valueOf(month);
		} else {
			return String.valueOf(month);
		}
	}

	// 날짜 정보 day를 2자리로 맞추기 위한 메서드
	public static String getDay(int day) {
		if (day < 10) {
			return "0" + String.valueOf(day);
		} else {
			return String.valueOf(day);
		}
	}

	// 마일리지 상태에 따라 유저 등급 나눠주는 메서드
	public static String getUserGrade(int mileage) {
		String grade = "bronze";

		if (mileage >= 300000) {
			grade = "platinum";
		} else if (mileage >= 200000) {
			grade = "gold";
		} else if (mileage >= 100000) {
			grade = "silver";
		}

		return grade;
	}

	public static void checkUserDTO(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();

		UserDTO dto = (UserDTO) session.getAttribute("dto");

		if (dto == null) {

			session.invalidate();
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('로그인이 필요한 서비스입니다.')");
			out.println("location.href='main'");
			out.println("</script>");
			out.close();

		}

	}

	public static void checkAdminDTO(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();

		AdminDTO dto = (AdminDTO) session.getAttribute("dto");

		if (dto == null) {

			session.invalidate();
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('세션이 종료되었습니다. 다시 로그인 해주세요.')");
			out.println("location.href='main'");
			out.println("</script>");
			out.close();

		}

	}
	
	public static void adminLoginStatus(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();

		AdminDTO dto = (AdminDTO) session.getAttribute("dto");

		if (dto != null) {

			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('관리자로 로그인 했습니다!!!')");
			out.println("history.back()");
			out.println("</script>");
			out.close();

		}

	}
}