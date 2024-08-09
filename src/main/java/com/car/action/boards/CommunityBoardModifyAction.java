package com.car.action.boards;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.car.action.Action;
import com.car.action.ActionForward;
import com.car.action.StaticArea;
import com.car.model.CommunityBoardDTO;
import com.car.model.TotalDAO;

public class CommunityBoardModifyAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		StaticArea.checkUserDTO(request, response);

		int community_no = Integer.parseInt(request.getParameter("no"));

		TotalDAO dao = TotalDAO.getInstance();

		CommunityBoardDTO dto = dao.getCommunityBoardContent(community_no);

		request.setAttribute("cont", dto);

		ActionForward forward = new ActionForward();

		forward.setPath("/WEB-INF/views/public/boards/community_board_modify.jsp");

		return forward;
	}

}