package com.car.action.boards;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.car.action.Action;
import com.car.action.ActionForward;
import com.car.model.CommunityBoardDTO;
import com.car.model.TotalDAO;
import com.car.model.UserDTO;

public class CommunityBoardContentAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession(false);
		
		UserDTO uDto = null;

		String user_id = null;

		String login_no = null;
		
		if (session != null) {
			login_no = (String) session.getAttribute("no");
			if(login_no != null && login_no.substring(0, 1).equals("U")) {
				uDto = (UserDTO) session.getAttribute("dto");
			}
		}

		int board_no = Integer.parseInt(request.getParameter("no").trim());
		int page = Integer.parseInt(request.getParameter("page").trim());

		TotalDAO dao = TotalDAO.getInstance();

		dao.communityBoardHit(board_no);

		CommunityBoardDTO dto = dao.getCommunityBoardContent(board_no);

		if (uDto != null) {
			user_id = uDto.getUser_id();
			request.setAttribute("id", user_id);
		}

		request.setAttribute("cont", dto);
		request.setAttribute("page", page);

		ActionForward forward = new ActionForward();

		forward.setPath("/WEB-INF/views/public/boards/community_board_content.jsp");

		return forward;
	}

}