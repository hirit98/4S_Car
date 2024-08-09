package com.car.admin.boards;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.car.action.Action;
import com.car.action.ActionForward;
import com.car.action.StaticArea;
import com.car.model.SellBoardDTO;
import com.car.model.TotalDAO;

public class AdminReqRegcarApproveAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		StaticArea.checkAdminDTO(request, response);

		HttpSession session = request.getSession();
		String admin_no = (String) session.getAttribute("no");

		int board_no = Integer.parseInt(request.getParameter("board_no").trim());

		String user_no = request.getParameter("user_no").trim();
		String car_std_no = request.getParameter("car_std_no").trim();
		String car_no = request.getParameter("car_no").trim();
		int car_distance = Integer.parseInt(request.getParameter("car_distance").trim());
		int car_price = Integer.parseInt(request.getParameter("car_price").trim());
		String car_detail = request.getParameter("car_detail").trim();
		String car_file = request.getParameter("car_file").trim();

		SellBoardDTO dto = new SellBoardDTO();
		dto.setSell_board_admin_no(admin_no);
		dto.setSell_board_seller_no(user_no);
		dto.setSell_board_car_std_no(car_std_no);
		dto.setSell_board_car_no(car_no);
		dto.setSell_board_car_distance(car_distance);
		dto.setSell_board_car_price(car_price);
		dto.setSell_board_car_detail(car_detail);
		dto.setSell_board_car_file(car_file);

		TotalDAO dao = TotalDAO.getInstance();

		int check = dao.insertSellBoard(dto);

		PrintWriter out = response.getWriter();
		if (check == 0) {
			out.println("<script>");
			out.println("alert('판매 등록 실패..')");
			out.println("history.back()");
			out.println("</script>");
			out.close();
		}

		dao.changeReqSellBoardStatus(board_no);

		ActionForward forward = new ActionForward();

		forward.setPath("/WEB-INF/views/admin/boards/req_regcar_approve_ok.jsp");

		return forward;

	}

}
