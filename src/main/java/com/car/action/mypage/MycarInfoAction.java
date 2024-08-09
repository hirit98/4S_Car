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
import com.car.model.ReqSellBoardDTO;
import com.car.model.TotalDAO;
import com.car.model.UserDTO;

public class MycarInfoAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		StaticArea.checkUserDTO(request, response);

		HttpSession session = request.getSession();
		UserDTO dto = (UserDTO) session.getAttribute("dto");

		TotalDAO dao = TotalDAO.getInstance();

		int cnt_B = dao.countReqSellBoardUserBefore(dto.getUser_no());
		int cnt_A = dao.countReqSellBoardUserAfter(dto.getUser_no());

		List<ReqSellBoardDTO> list = dao.getReqSellBoardList(dto.getUser_no());

		request.setAttribute("cnt_b", cnt_B);
		request.setAttribute("cnt_a", cnt_A);
		request.setAttribute("req_list", list);

		ActionForward forward = new ActionForward();

		forward.setPath("/WEB-INF/views/public/mypage/mycar_info.jsp");

		return forward;
	}

}
