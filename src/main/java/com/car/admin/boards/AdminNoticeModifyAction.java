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

public class AdminNoticeModifyAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		StaticArea.checkAdminDTO(request, response);

		int notice_no = Integer.parseInt(request.getParameter("no").trim());

		TotalDAO dao = TotalDAO.getInstance();

		NoticeBoardDTO dto = dao.getNoticeBoardContent(notice_no);

		request.setAttribute("dto", dto);

		ActionForward forward = new ActionForward();

		forward.setPath("/WEB-INF/views/admin/boards/notice_modify.jsp");

		return forward;
	}

}
