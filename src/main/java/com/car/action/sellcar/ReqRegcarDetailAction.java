package com.car.action.sellcar;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.car.action.Action;
import com.car.action.ActionForward;
import com.car.action.StaticArea;
import com.car.model.CarDTO;

public class ReqRegcarDetailAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		StaticArea.checkUserDTO(request, response);

		HttpSession session = request.getSession();
		String user_no = (String) session.getAttribute("no");
		// 차량 기본정보를 받아온다.
		String company = request.getParameter("company").trim();
		String type = request.getParameter("type").trim();
		int year = Integer.parseInt(request.getParameter("year").trim());
		int month = Integer.parseInt(request.getParameter("month").trim());
		String fuel = request.getParameter("fuel").trim();
		String transmission = request.getParameter("transmission").trim();
		String color = request.getParameter("color").trim();
		String model = request.getParameter("model").trim();

		// 차량 객체에 받아온 차량 기본정보를 담는다.
		CarDTO dto = new CarDTO();
		dto.setCar_manufact_company(company);
		dto.setCar_type(type);
		dto.setCar_manufact_year(year);
		dto.setCar_manufact_month(month);
		dto.setCar_fuel(fuel);
		dto.setCar_transmission(transmission);
		dto.setCar_color(color);
		dto.setCar_model(model);

		// 받아온 차량 스펙을 _(언더바)를 기준으로 차량 구분번호를 생성한다.
		String car_std_no = (company + "_" + type + "_" + year + "_" + month + "_" + fuel + "_" + transmission + "_"
				+ color + "_" + model).toUpperCase(); // 정보를 모두 대문자로 변환한다.

		// 차량 기본정보 객체와 차량 구분번호를 다음페이지로 넘겨준다.
		request.setAttribute("dto", dto);
		request.setAttribute("car_std_no", car_std_no);
		request.setAttribute("user_no", user_no);

		ActionForward forward = new ActionForward();

		forward.setPath("/WEB-INF/views/public/sellcar/req_regcar_detail.jsp");

		return forward;
	}

}