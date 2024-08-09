package com.car.action.mypage;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.car.action.Action;
import com.car.action.ActionForward;
import com.car.action.StaticArea;
import com.car.model.BuyReviewBoardDTO;
import com.car.model.QnaBoardDTO;
import com.car.model.SellReviewBoardDTO;
import com.car.model.TotalDAO;
import com.car.model.UserDTO;

public class MyReviewAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		StaticArea.checkUserDTO(request, response);

		HttpSession session = request.getSession();

		UserDTO dto = (UserDTO) session.getAttribute("dto");
		TotalDAO dao = TotalDAO.getInstance();

		int cnt = dao.countQnaUserList(dto.getUser_id());
		int buy_review_cnt = dao.countBuyReviewBoardList(dto.getUser_id());
		int sell_review_cnt = dao.countSellReviewBoardList(dto.getUser_id());

		List<SellReviewBoardDTO> sell_review_list = dao.getSellReviewBoardList(dto.getUser_id());
		List<BuyReviewBoardDTO> buy_review_list = dao.getBuyReviewBoardList(dto.getUser_id());
		List<QnaBoardDTO> list = dao.getQnaBoardUserList(dto.getUser_id());

		Collections.reverse(list);

		request.setAttribute("buy_review_cnt", buy_review_cnt);
		request.setAttribute("buy_review_list", buy_review_list);

		request.setAttribute("qna_cnt", cnt);
		request.setAttribute("qna_list", list);

		request.setAttribute("sell_review_cnt", sell_review_cnt);
		request.setAttribute("sell_review_list", sell_review_list);

		ActionForward forward = new ActionForward();

		forward.setPath("/WEB-INF/views/public/mypage/my_review.jsp");

		return forward;
	}

}
