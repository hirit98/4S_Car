package com.car.action.logout;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.car.action.Action;
import com.car.action.ActionForward;
import com.car.model.TotalDAO;

public class LogoutAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();
		
		String user_no = (String) session.getAttribute("no");
		TotalDAO dao = TotalDAO.getInstance();
		if(user_no.substring(0, 1).equals("U")) {
			dao.remainLogout(user_no);
		} else if(user_no.substring(0, 1).equals("A")) {
			dao.remainAdminLogout(user_no);
		}

		session.invalidate();

		// 로그아웃 후에는 JSON 형태의 응답을 반환합니다.
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write("{\"success\": true}");

		return null;
	}

}
