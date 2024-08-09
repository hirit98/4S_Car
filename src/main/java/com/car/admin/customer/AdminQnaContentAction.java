package com.car.admin.customer;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.car.action.Action;
import com.car.action.ActionForward;
import com.car.action.StaticArea;
import com.car.model.QnaBoardDTO;
import com.car.model.TotalDAO;

public class AdminQnaContentAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		StaticArea.checkAdminDTO(request, response);

		int qna_no = Integer.parseInt(request.getParameter("no").trim());
		int page = Integer.parseInt(request.getParameter("page").trim());

		TotalDAO dao = TotalDAO.getInstance();

		QnaBoardDTO dto = dao.getQnaBoardContent(qna_no);

		request.setAttribute("dto", dto);
		request.setAttribute("page", page);

		ActionForward forward = new ActionForward();

		forward.setPath("/WEB-INF/views/admin/customer/qna_content.jsp");

		return forward;
	}

}
