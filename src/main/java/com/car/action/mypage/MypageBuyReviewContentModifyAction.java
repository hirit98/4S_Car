package com.car.action.mypage;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.car.action.Action;
import com.car.action.ActionForward;
import com.car.action.StaticArea;
import com.car.model.BuyReviewBoardDTO;
import com.car.model.TotalDAO;

public class MypageBuyReviewContentModifyAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		StaticArea.checkUserDTO(request, response);

		int buy_review_board_no = Integer.parseInt(request.getParameter("no"));

		TotalDAO dao = TotalDAO.getInstance();

		BuyReviewBoardDTO cont = dao.getMypageBuyReviewBoardContent(buy_review_board_no);

		request.setAttribute("cont", cont);

		request.setAttribute("no", buy_review_board_no);

		ActionForward forward = new ActionForward();

		forward.setPath("/WEB-INF/views/public/mypage/mypage_buy_review_content_modify.jsp");

		return forward;

	}

}
