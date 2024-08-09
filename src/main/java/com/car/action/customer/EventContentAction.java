package com.car.action.customer;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.car.action.Action;
import com.car.action.ActionForward;
import com.car.model.EventBoardDTO;
import com.car.model.TotalDAO;

public class EventContentAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		int board_no = Integer.parseInt(request.getParameter("no").trim());

		int page = 0;
		
		if (request.getParameter("event_page") != null) {
			page = Integer.parseInt(request.getParameter("event_page").trim());
			if (page < 1) {
				page = 1;
			}
		}
		
		TotalDAO dao = TotalDAO.getInstance();

		EventBoardDTO dto = dao.getEventBoardContent(board_no);

		dao.eventBoardHit(board_no);

		request.setAttribute("cont", dto);
		request.setAttribute("event_page", page);

		ActionForward forward = new ActionForward();

		forward.setPath("/WEB-INF/views/public/customer/event_content.jsp");

		return forward;
	}

}