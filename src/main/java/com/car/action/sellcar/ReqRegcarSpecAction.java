package com.car.action.sellcar;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.car.action.Action;
import com.car.action.ActionForward;
import com.car.action.StaticArea;

public class ReqRegcarSpecAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		StaticArea.checkUserDTO(request, response);

		ActionForward forward = new ActionForward();

		request.setAttribute("companyList", StaticArea.getCompanyList());
		request.setAttribute("typeList", StaticArea.getTypeList());
		request.setAttribute("fuelList", StaticArea.getFuelList());
		request.setAttribute("transmissionList", StaticArea.getTransmissionList());
		request.setAttribute("colorList", StaticArea.getColorList());

		forward.setPath("/WEB-INF/views/public/sellcar/req_regcar_spec.jsp");

		return forward;
	}

}