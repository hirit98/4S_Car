package com.car.action.search;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.car.action.Action;
import com.car.action.ActionForward;
import com.car.action.StaticArea;
import com.car.model.AdminDTO;
import com.car.model.CompanyDTO;
import com.car.model.FavoriteDTO;
import com.car.model.SellBoardDTO;
import com.car.model.TotalDAO;
import com.car.model.UserDTO;

public class CarListContentAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		int board_no = Integer.parseInt(request.getParameter("no"));
		
		HttpSession session = request.getSession(false);
		
		UserDTO dto = null;
		
		String login_no = null;
		
		if(session != null) {
			login_no = (String) session.getAttribute("no");
			if(login_no != null && login_no.substring(0, 1).equals("U")) {
				dto = (UserDTO)session.getAttribute("dto");
			}
		}
		
		TotalDAO dao = TotalDAO.getInstance();
		
		dao.addSellBoardHit(board_no);
		
		SellBoardDTO cont = dao.getSellBoardContent(board_no);
		
		AdminDTO dealer = dao.getDealerInfo(cont.getSell_board_admin_no());
		
		CompanyDTO cops = dao.getCompanyInfo();
		
		List<String> cardList = StaticArea.getCardCops();
		
		FavoriteDTO content =  new FavoriteDTO();
		
		boolean check_favorite = false;
		
		String user_no = null;
		
		if(dto != null) {
			
			user_no = dto.getUser_no();
			content.setFavorite_user_no(user_no);
			content.setFavorite_sell_board_no(board_no);
			
			check_favorite = dao.check_favorite_content(user_no, board_no);
			
		}
		
		int carPrice = cont.getSell_board_car_price();
		
		int favorite_count = dao.countfavorite_list(board_no);
		
		request.setAttribute("cont", cont);
		request.setAttribute("dealer", dealer);
		request.setAttribute("cops", cops);
		request.setAttribute("cardList", cardList);
		request.setAttribute("carPrice", carPrice);
		request.setAttribute("check_favorite", check_favorite);
		request.setAttribute("favorite_count",favorite_count);
		
		ActionForward forward = new ActionForward();
		
		forward.setPath("/WEB-INF/views/public/search/car_list_content.jsp");
		
		return forward;
	}

}
