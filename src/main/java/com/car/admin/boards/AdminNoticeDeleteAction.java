package com.car.admin.boards;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.car.action.Action;
import com.car.action.ActionForward;
import com.car.action.StaticArea;
import com.car.model.TotalDAO;

public class AdminNoticeDeleteAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		StaticArea.checkAdminDTO(request, response);

		int notice_no = Integer.parseInt(request.getParameter("no").trim());

		TotalDAO dao = TotalDAO.getInstance();

		int check = dao.deleteNoticeBoard(notice_no);

		if (check > 0) {
			ActionForward forward = new ActionForward();

			forward.setPath("/WEB-INF/views/admin/boards/notice_delete_ok.jsp");

			return forward;
		} else {
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('공지사항 삭제에 실패했습니다.')");
			out.println("history.back()");
			out.println("</script>");
			out.close();
		}

		return null;
	}

}
