package com.car.action.boards;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.car.action.Action;
import com.car.action.ActionForward;
import com.car.model.SellReviewBoardDTO;
import com.car.model.TotalDAO;

public class SellReviewBoardListAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		int rowsize = 10;

		int block = 3;

		int cnt = 0;

		int allPage = 0;

		int page = 0;

		if (request.getParameter("page") != null) {
			page = Integer.parseInt(request.getParameter("page").trim());

		} else {
			page = 1;
		}

		int startNo = (page * rowsize) - (rowsize - 1);

		int endNo = (page * rowsize);

		int startBlock = (((page - 1) / block) * block) + 1;

		int endBlock = (((page - 1) / block) * block) + block;

		TotalDAO dao = TotalDAO.getInstance();

		cnt = dao.countSellReviewBoardList();

		allPage = (int) Math.ceil(cnt / (double) rowsize);

		if (endBlock > allPage) {
			endBlock = allPage;
		}

		boolean is_srSearch = false;

		List<SellReviewBoardDTO> list = dao.getSellReviewList(page, rowsize);

		request.setAttribute("rowsize", rowsize);
		request.setAttribute("block", block);
		request.setAttribute("cnt", cnt);
		request.setAttribute("allPage", allPage);
		request.setAttribute("page", page);
		request.setAttribute("startNo", startNo);
		request.setAttribute("endNo", endNo);
		request.setAttribute("startBlock", startBlock);
		request.setAttribute("endBlock", endBlock);
		request.setAttribute("list", list);
		request.setAttribute("is_srSearch", is_srSearch);

		ActionForward forward = new ActionForward();

		forward.setPath("/WEB-INF/views/public/boards/sell_review_board_list.jsp");

		return forward;
	}

}