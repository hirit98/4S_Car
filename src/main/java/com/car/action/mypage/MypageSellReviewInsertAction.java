package com.car.action.mypage;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.car.action.Action;
import com.car.action.ActionForward;
import com.car.action.StaticArea;
import com.car.model.TotalDAO;
import com.car.model.TransactionDTO;

public class MypageSellReviewInsertAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		StaticArea.checkUserDTO(request, response);

		int transaction_no = Integer.parseInt(request.getParameter("no"));

		TotalDAO dao = TotalDAO.getInstance();

		TransactionDTO dto = new TransactionDTO();

		dto = dao.getTransactionInfo(transaction_no);

		request.setAttribute("cont", dto);

		ActionForward forward = new ActionForward();

		forward.setPath("/WEB-INF/views/public/mypage/mysell_review_insert.jsp");

		return forward;

	}

}