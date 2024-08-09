package com.car.action.mypage;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.car.action.Action;
import com.car.action.ActionForward;
import com.car.action.StaticArea;
import com.car.model.UserDTO;

public class MypageAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		StaticArea.checkUserDTO(request, response);

		HttpSession session = request.getSession();

		UserDTO dto = (UserDTO) session.getAttribute("dto");

		ActionForward forward = new ActionForward();

		request.setAttribute("dto", dto);
		forward.setPath("/WEB-INF/views/public/mypage/mypage.jsp");

		return forward;
	}

}
