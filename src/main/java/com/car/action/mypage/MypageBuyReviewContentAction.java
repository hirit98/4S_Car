package com.car.action.mypage;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.car.action.Action;
import com.car.action.ActionForward;
import com.car.action.StaticArea;
import com.car.model.BuyReviewBoardDTO;

import com.car.model.TotalDAO;
import com.car.model.UserDTO;

public class MypageBuyReviewContentAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		StaticArea.checkUserDTO(request, response);

		HttpSession session = request.getSession();

		UserDTO dto = (UserDTO) session.getAttribute("dto");

		TotalDAO dao = TotalDAO.getInstance();

		int review_board_no = Integer.parseInt(request.getParameter("no").trim());

		BuyReviewBoardDTO cont = dao.getBuyReviewBoardContent(review_board_no);

		request.setAttribute("dto", dto);
		request.setAttribute("cont", cont);
		request.setAttribute("no", review_board_no);

		ActionForward forward = new ActionForward();

		forward.setPath("/WEB-INF/views/public/mypage/mypage_buy_review_content.jsp");

		return forward;
	}

}