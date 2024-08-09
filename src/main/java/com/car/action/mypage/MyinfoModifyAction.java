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
import com.car.model.UserDTO;

public class MyinfoModifyAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		StaticArea.checkUserDTO(request, response);

		HttpSession session = request.getSession();

		UserDTO dto = (UserDTO) session.getAttribute("dto");

		String input_pwd = request.getParameter("input_pwd").trim();

		PrintWriter out = response.getWriter();

		if (input_pwd.equals(dto.getUser_pwd())) {
			request.setAttribute("dto", dto);

			ActionForward forward = new ActionForward();

			forward.setPath("/WEB-INF/views/public/mypage/myinfo_modify.jsp");

			return forward;
		} else {
			out.println("<script>");
			out.println("alert('비밀번호를 확인해주세요.')");
			out.println("history.back()");
			out.println("</script>");
			out.close();
		}

		return null;
	}

}
