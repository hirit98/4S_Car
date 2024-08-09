package com.car.action.mypage;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.car.action.Action;
import com.car.action.ActionForward;
import com.car.action.StaticArea;
import com.car.model.MileageDTO;
import com.car.model.TotalDAO;
import com.car.model.UserDTO;

public class MyMileageHistory implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		StaticArea.checkUserDTO(request, response);

		HttpSession session = request.getSession();
		UserDTO uDto = (UserDTO) session.getAttribute("dto");

		TotalDAO dao = TotalDAO.getInstance();
		List<MileageDTO> list = dao.getUserMileage(uDto.getUser_no());

		request.setAttribute("uDto", uDto);
		request.setAttribute("list", list);

		ActionForward forward = new ActionForward();

		forward.setPath("/WEB-INF/views/public/mypage/my_mileage_history.jsp");

		return forward;
	}

}
