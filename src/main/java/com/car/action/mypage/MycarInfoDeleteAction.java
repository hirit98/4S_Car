package com.car.action.mypage;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.car.action.Action;
import com.car.action.ActionForward;
import com.car.action.StaticArea;
import com.car.model.TotalDAO;

public class MycarInfoDeleteAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		StaticArea.checkUserDTO(request, response);

		int board_no = Integer.parseInt(request.getParameter("no").trim());

		TotalDAO dao = TotalDAO.getInstance();

		int check = dao.deleteMycarInfo(board_no);

		if (check > 0) {
			ActionForward forward = new ActionForward();

			forward.setPath("/WEB-INF/views/public/mypage/mycar_info_delete_ok.jsp");

			return forward;
		} else {
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('글 삭제에 실패했습니다. 관리자에게 문의하세요.')");
			out.println("history.back()");
			out.println("</script>");
			out.close();
		}

		return null;
	}

}
