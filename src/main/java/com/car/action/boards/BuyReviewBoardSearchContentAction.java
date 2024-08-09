package com.car.action.boards;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.car.action.Action;
import com.car.action.ActionForward;
import com.car.model.BuyReviewBoardDTO;
import com.car.model.TotalDAO;

public class BuyReviewBoardSearchContentAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		int buy_review_board_no = Integer.parseInt(request.getParameter("no").trim());
		int page = Integer.parseInt(request.getParameter("page").trim());
		String field = request.getParameter("field").trim();
		String keyword = request.getParameter("keyword").trim();

		TotalDAO dao = TotalDAO.getInstance();

		dao.BuyReviewBoardHit(buy_review_board_no);

		BuyReviewBoardDTO dto = dao.getBuyReviewBoardContent(buy_review_board_no);

		request.setAttribute("cont", dto);
		request.setAttribute("page", page);
		request.setAttribute("field", field);
		request.setAttribute("keyword", keyword);

		ActionForward forward = new ActionForward();

		forward.setPath("/WEB-INF/views/public/boards/buy_review_board_content.jsp");

		return forward;
	}

}
