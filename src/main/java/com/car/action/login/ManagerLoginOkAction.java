package com.car.action.login;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.car.action.Action;
import com.car.action.ActionForward;
import com.car.model.AdminDTO;
import com.car.model.TotalDAO;

public class ManagerLoginOkAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String admin_id = request.getParameter("manager_id").trim();
		String admin_pwd = request.getParameter("manager_pwd").trim();

		AdminDTO dto = new AdminDTO();

		dto.setAdmin_id(admin_id);
		dto.setAdmin_pwd(admin_pwd);

		TotalDAO dao = TotalDAO.getInstance();

		int check = dao.checkAdminLogin(dto);

		PrintWriter out = response.getWriter();

		if (check == 1) {

			HttpSession session = request.getSession();
			AdminDTO cont = dao.adminContent(dto);
			session.setMaxInactiveInterval(1800); // 세션 유지시간 설정 (초 단위)
			session.setAttribute("name", cont.getAdmin_name());
			session.setAttribute("no", cont.getAdmin_no());
			session.setAttribute("dto", cont);

			// 로그 남기는 메서드
			dao.remainAdminLogin(admin_id);

			out.println("<script>");
			out.println("alert('로그인했습니다.')");
			out.println("location.href='main'");
			out.println("</script>");

		} else if (check == -1) {
			out.println("<script>");
			out.println("alert('비밀번호가 일치하지 않습니다!')");
			out.println("history.back()");
			out.println("</script>");
		} else {
			out.println("<script>");
			out.println("alert('일치하는 아이디를 찾을수 없습니다.')");
			out.println("history.back()");
			out.println("</script>");
		}
		out.close();
		return null;
	}

}
