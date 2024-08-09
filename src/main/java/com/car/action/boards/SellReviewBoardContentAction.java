package com.car.action.boards;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.car.action.Action;
import com.car.action.ActionForward;
import com.car.model.SellReviewBoardDTO;
import com.car.model.TotalDAO;

public class SellReviewBoardContentAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		int board_no = Integer.parseInt(request.getParameter("no").trim());
		
		int page = Integer.parseInt(request.getParameter("page").trim());

		TotalDAO dao = TotalDAO.getInstance();

		dao.sellReviewBoardHit(board_no);

		SellReviewBoardDTO dto = dao.getSellReviewContent(board_no);

		request.setAttribute("page", page);
		request.setAttribute("cont", dto);

		ActionForward forward = new ActionForward();

		forward.setPath("/WEB-INF/views/public/boards/sell_review_board_content.jsp");

		return forward;
	}

}