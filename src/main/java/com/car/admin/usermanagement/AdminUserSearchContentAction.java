package com.car.admin.usermanagement;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.car.action.Action;
import com.car.action.ActionForward;
import com.car.action.StaticArea;
import com.car.model.TotalDAO;
import com.car.model.UserDTO;

public class AdminUserSearchContentAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		StaticArea.checkAdminDTO(request, response);
		
		String user_no = request.getParameter("no").trim();
		int page = Integer.parseInt(request.getParameter("page").trim());
		String field = request.getParameter("field").trim();
		String keyword = request.getParameter("keyword").trim();

		TotalDAO dao = TotalDAO.getInstance();

		UserDTO dto = dao.getUserContent(user_no);

		request.setAttribute("dto", dto);
		request.setAttribute("page", page);
		request.setAttribute("field", field);
		request.setAttribute("keyword", keyword);

		ActionForward forward = new ActionForward();

		forward.setPath("/WEB-INF/views/admin/usermanagement/user_content.jsp");

		return forward;
	}

}
