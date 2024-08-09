package com.car.action.mypage;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.car.action.Action;
import com.car.action.ActionForward;
import com.car.action.StaticArea;
import com.car.model.TotalDAO;
import com.car.model.UserDTO;

public class UserUnresisterOkAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		StaticArea.checkUserDTO(request, response);

		HttpSession session = request.getSession();

		UserDTO cont = (UserDTO) session.getAttribute("dto");

		String user_pwd = cont.getUser_pwd();

		String check_pwd = request.getParameter("check_pwd").trim();

		PrintWriter out = response.getWriter();

		if (check_pwd.equals(user_pwd)) {

			TotalDAO dao = TotalDAO.getInstance();

			int check = dao.deleteUser(cont.getUser_no());

			if (check > 0) {

				session.invalidate();

				ActionForward forward = new ActionForward();

				forward.setPath("/WEB-INF/views/public/mypage/user_unresister_ok.jsp");

				return forward;

			} else {
				out.println("<script>");
				out.println("alert('회원탈퇴에 실패했습니다. 관리자에게 문의하세요.')");
				out.println("history.back()");
				out.println("</script>");
				out.close();
			}

		} else {
			out.println("<script>");
			out.println("alert('비밀번호를 확인해주세요!!')");
			out.println("history.back()");
			out.println("</script>");
			out.close();
		}

		return null;
	}

}
