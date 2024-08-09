package com.car.admin.boards;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.car.action.Action;
import com.car.action.ActionForward;
import com.car.action.StaticArea;
import com.car.model.AdminDTO;

public class AdminEventInsertAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		StaticArea.checkAdminDTO(request, response);
		
		HttpSession session = request.getSession();
		
		AdminDTO dto = (AdminDTO) session.getAttribute("dto");
		
		request.setAttribute("dto", dto);

		ActionForward forward = new ActionForward();

		forward.setPath("/WEB-INF/views/admin/boards/event_insert.jsp");

		return forward;
	}

}
