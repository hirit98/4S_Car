package com.car.action.customer;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.car.action.Action;
import com.car.action.ActionForward;
import com.car.model.EventBoardDTO;
import com.car.model.TotalDAO;

public class EventSearchAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		TotalDAO dao = TotalDAO.getInstance();

		int event_cnt = 0;
		int event_rowsize = 10;
		int event_block = 3;
		int event_allPage = 0;
		int event_page = 1;

		if (request.getParameter("event_page") != null) {
			event_page = Integer.parseInt(request.getParameter("event_page").trim());
			if (event_page < 1) {
				event_page = 1;
			}
		}

		int event_startNo = (event_page * event_rowsize) - (event_rowsize - 1);
		int event_endNo = (event_page * event_rowsize);
		int event_startBlock = (((event_page - 1) / event_block) * event_block) + 1;
		int event_endBlock = (((event_page - 1) / event_block) * event_block) + event_block;

		String event_field = request.getParameter("field").trim();
		String event_keyword = request.getParameter("keyword").trim();

		event_cnt = dao.countEventSearchList(event_field, event_keyword);

		event_allPage = (int) Math.ceil(event_cnt / (double) event_rowsize);

		if (event_endBlock > event_allPage) {
			event_endBlock = event_allPage;
		}

		boolean is_eSearch = true;

		List<EventBoardDTO> list = dao.searchEventList(event_field, event_keyword, event_page, event_rowsize);

		request.setAttribute("event_list", list);
		request.setAttribute("event_cnt", event_cnt);
		request.setAttribute("event_page", event_page);
		request.setAttribute("event_rowsize", event_rowsize);
		request.setAttribute("event_block", event_block);
		request.setAttribute("event_allPage", event_allPage);
		request.setAttribute("event_startNo", event_startNo);
		request.setAttribute("event_endNo", event_endNo);
		request.setAttribute("event_startBlock", event_startBlock);
		request.setAttribute("event_endBlock", event_endBlock);
		request.setAttribute("field", event_field);
		request.setAttribute("keyword", event_keyword);
		request.setAttribute("is_eSearch", is_eSearch);

		ActionForward forward = new ActionForward();

		forward.setPath("/WEB-INF/views/public/customer/notice_list.jsp");

		return forward;
	}

}
