package com.car.action.mypage;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.car.action.Action;
import com.car.action.ActionForward;
import com.car.action.StaticArea;
import com.car.model.FavoriteDTO;
import com.car.model.SellBoardDTO;
import com.car.model.TotalDAO;
import com.car.model.UserDTO;

public class MyfavoriteListAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession(false);

		UserDTO dto = null;

		String login_no = null;

		if (session != null) {
			login_no = (String) session.getAttribute("no");
			if (login_no != null && login_no.substring(0, 1).equals("U")) {
				dto = (UserDTO) session.getAttribute("dto");
			} else {
				StaticArea.adminLoginStatus(request, response);
			}
		}
		
		TotalDAO dao = TotalDAO.getInstance();

		int cnt = 0;

		int rowsize = 10;

		int block = 3;

		int allPage = 0;

		int page = 1;

		if (request.getParameter("page") != null) {
			page = Integer.parseInt(request.getParameter("page").trim());
			if (page < 1) {
				page = 1;
			}
		}

		int startNo = (page * rowsize) - (rowsize - 1);

		int endNo = (page * rowsize);

		int startBlock = (((page - 1) / block) * block) + 1;

		int endBlock = (((page - 1) / block) * block) + block;

		cnt = dao.countFavoriteList(login_no);

		allPage = (int) Math.ceil(cnt / (double) rowsize);

		if (endBlock > allPage) {
			endBlock = allPage;
		}

		List<FavoriteDTO> list = dao.getFavoriteList(login_no, page, rowsize);

		List<SellBoardDTO> sell_list = new ArrayList<SellBoardDTO>();

		for (FavoriteDTO temp : list) {

			int sell_no = temp.getFavorite_sell_board_no();

			SellBoardDTO sell_cont = new SellBoardDTO();

			sell_cont = dao.getSellBoardContent(sell_no);

			sell_list.add(sell_cont);
		}

		request.setAttribute("cnt", cnt);
		request.setAttribute("list", list);
		request.setAttribute("sell_list", sell_list);
		request.setAttribute("page", page);
		request.setAttribute("rowsize", rowsize);
		request.setAttribute("block", block);
		request.setAttribute("allPage", allPage);
		request.setAttribute("startNo", startNo);
		request.setAttribute("endNo", endNo);
		request.setAttribute("startBlock", startBlock);
		request.setAttribute("endBlock", endBlock);

		StaticArea.checkUserDTO(request, response);
		
		ActionForward forward = new ActionForward();

		forward.setPath("/WEB-INF/views/public/mypage/myfavorite_list.jsp");

		return forward;
	}

}
