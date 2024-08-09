package com.car.action.mypage;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.car.action.Action;
import com.car.action.ActionForward;
import com.car.action.StaticArea;
import com.car.model.TotalDAO;
import com.car.model.TransactionDTO;
import com.car.model.UserDTO;

public class MycarBuyHistory implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		StaticArea.checkUserDTO(request, response);

		HttpSession session = request.getSession();

		UserDTO dto = (UserDTO) session.getAttribute("dto");

		TotalDAO dao = TotalDAO.getInstance();

		List<TransactionDTO> buy_list = dao.getTransactionBuyList(dto.getUser_no());

		List<TransactionDTO> list = dao.getTransactionSellList(dto.getUser_no());

		int sell_cnt = dao.countTransactionSell(dto.getUser_no());
		int buy_cnt = dao.countTransactionBuy(dto.getUser_no());

		request.setAttribute("list", list);
		request.setAttribute("buylist", buy_list);

		request.setAttribute("sell_cnt", sell_cnt);
		request.setAttribute("buy_cnt", buy_cnt);

		ActionForward forward = new ActionForward();

		forward.setPath("/WEB-INF/views/public/mypage/mycar_buy_history.jsp");

		return forward;
	}

}