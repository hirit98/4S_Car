package com.car.action.mypage;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.car.action.Action;
import com.car.action.ActionForward;
import com.car.action.StaticArea;
import com.car.model.FavoriteDTO;
import com.car.model.TotalDAO;
import com.car.model.UserDTO;

public class MyfavoriteDeleteAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		StaticArea.checkUserDTO(request, response);

		int sell_no = Integer.parseInt(request.getParameter("no").trim());

		int page = Integer.parseInt(request.getParameter("page").trim());

		HttpSession session = request.getSession();

		UserDTO dto = (UserDTO) session.getAttribute("dto");

		String user_no = dto.getUser_no();

		FavoriteDTO fav_dto = new FavoriteDTO();

		fav_dto.setFavorite_user_no(user_no);
		fav_dto.setFavorite_sell_board_no(sell_no);

		TotalDAO dao = TotalDAO.getInstance();

		PrintWriter out = response.getWriter();

		int favorite_no = dao.selectFavoriteNo(fav_dto);

		int check = dao.MyFavorite_delete(fav_dto);

		int check2 = dao.countFavoriteNo();

		if (check > 0) {
			
			if(check2 != 0) {
				dao.updateFavoriteNo(favorite_no);
			}
			out.println("<script>");
			out.println("location.href='myfavorite_list?page=" + page + "'");
			out.println("</script>");
			out.close();

		} else {

			out.println("<script>");
			out.println("alert('찜목록 삭제에 실패했습니다.')");
			out.println("history.back()");
			out.println("</script>");
			out.close();
		}

		return null;
	}

}
