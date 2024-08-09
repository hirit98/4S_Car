package com.car.admin.boards;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.car.action.Action;
import com.car.action.ActionForward;
import com.car.action.StaticArea;
import com.car.model.NoticeBoardDTO;
import com.car.model.TotalDAO;

public class AdminNoticeSearchContentAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		StaticArea.checkAdminDTO(request, response);

		int board_no = Integer.parseInt(request.getParameter("no").trim());
		int page = Integer.parseInt(request.getParameter("page").trim());
		String field = request.getParameter("field").trim();
		String keyword = request.getParameter("keyword").trim();

		TotalDAO dao = TotalDAO.getInstance();

		NoticeBoardDTO dto = dao.getNoticeBoardContent(board_no);

		request.setAttribute("dto", dto);
		request.setAttribute("page", page);
		request.setAttribute("field", field);
		request.setAttribute("keyword", keyword);

		ActionForward forward = new ActionForward();

		forward.setPath("/WEB-INF/views/admin/boards/notice_content.jsp");

		return forward;

	}

}
