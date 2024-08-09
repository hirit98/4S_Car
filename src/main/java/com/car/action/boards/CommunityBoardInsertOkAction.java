package com.car.action.boards;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.car.action.Action;
import com.car.action.ActionForward;
import com.car.action.StaticArea;
import com.car.model.CommunityBoardDTO;
import com.car.model.TotalDAO;
import com.car.model.UserDTO;

public class CommunityBoardInsertOkAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		StaticArea.checkUserDTO(request, response);

		HttpSession session = request.getSession();

		UserDTO cont = (UserDTO) session.getAttribute("dto");

		CommunityBoardDTO dto = new CommunityBoardDTO();

		String user_id = cont.getUser_id();
		String title = request.getParameter("title");
		String content = request.getParameter("content");

		dto.setCommunity_board_writer_id(user_id);
		dto.setCommunity_board_title(title);
		dto.setCommunity_board_cont(content);

		TotalDAO dao = TotalDAO.getInstance();

		int check = dao.insertCommunity(dto);

		if (check > 0) {

			ActionForward forward = new ActionForward();

			forward.setPath("/WEB-INF/views/public/boards/community_board_insert_ok.jsp");

			return forward;

		} else {
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('글 등록에 실패했습니다. 입력한 정보를 다시한번 확인하세요.')");
			out.println("history.back()");
			out.println("</script>");
			out.close();
		}

		return null;
	}

}
