package com.car.action.customer;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.car.action.Action;
import com.car.action.ActionForward;
import com.car.model.NoticeBoardDTO;
import com.car.model.TotalDAO;

public class MainNoticeContentAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		int board_no = Integer.parseInt(request.getParameter("no").trim());

		int page = 0;

		TotalDAO dao = TotalDAO.getInstance();

		NoticeBoardDTO dto = dao.getNoticeBoardContent(board_no);

		request.setAttribute("cont", dto);
		request.setAttribute("page", page);

		ActionForward forward = new ActionForward();

		forward.setPath("/WEB-INF/views/public/customer/notice_content.jsp");

		return forward;
	}

}
