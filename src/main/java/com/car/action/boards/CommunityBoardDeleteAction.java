package com.car.action.boards;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.car.action.Action;
import com.car.action.ActionForward;
import com.car.action.StaticArea;
import com.car.model.TotalDAO;

public class CommunityBoardDeleteAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		StaticArea.checkUserDTO(request, response);

		int community_no = Integer.parseInt(request.getParameter("no").trim());

		TotalDAO dao = TotalDAO.getInstance();

		int check = dao.deleteCommunityBoard(community_no);

		PrintWriter out = response.getWriter();

		if (check > 0) {

			ActionForward forward = new ActionForward();

			forward.setPath("/WEB-INF/views/public/boards/community_delete_ok.jsp");

			return forward;
		} else {

			out.println("<script>");
			out.println("alert('글 삭제 실패')");
			out.println("history.back()");
			out.println("</script>");
		}
		return null;
	}

}
