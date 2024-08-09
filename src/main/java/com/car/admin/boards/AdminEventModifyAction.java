package com.car.admin.boards;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.car.action.Action;
import com.car.action.ActionForward;
import com.car.action.StaticArea;
import com.car.model.EventBoardDTO;
import com.car.model.TotalDAO;

public class AdminEventModifyAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		StaticArea.checkAdminDTO(request, response);

		int event_no = Integer.parseInt(request.getParameter("no").trim());

		TotalDAO dao = TotalDAO.getInstance();

		EventBoardDTO dto = dao.getEventBoardContent(event_no);

		request.setAttribute("dto", dto);

		ActionForward forward = new ActionForward();

		forward.setPath("/WEB-INF/views/admin/boards/event_modify.jsp");

		return forward;
	}

}
