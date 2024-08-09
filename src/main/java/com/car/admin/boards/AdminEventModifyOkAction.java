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
import com.car.model.AdminDTO;
import com.car.model.EventBoardDTO;
import com.car.model.TotalDAO;

public class AdminEventModifyOkAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		StaticArea.checkAdminDTO(request, response);

		HttpSession session = request.getSession();

		AdminDTO cont = (AdminDTO) session.getAttribute("dto");

		int event_board_no = Integer.parseInt(request.getParameter("no").trim());
		String writer = cont.getAdmin_no();
		String title = request.getParameter("title").trim();
		String content = request.getParameter("cont").trim();

		EventBoardDTO dto = new EventBoardDTO();

		dto.setEvent_board_no(event_board_no);
		dto.setEvent_board_writer_id(writer);
		dto.setEvent_board_title(title);
		dto.setEvent_board_cont(content);

		TotalDAO dao = TotalDAO.getInstance();

		int check = dao.updateEvent(dto);

		if (check > 0) {
			ActionForward forward = new ActionForward();

			forward.setPath("/WEB-INF/views/admin/boards/event_modify_ok.jsp");

			return forward;

		} else {
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('글 수정에 실패했습니다. 입력한 정보를 다시 확인하세요.')");
			out.println("history.back()");
			out.println("</script>");
			out.close();
		}

		return null;

	}

}
