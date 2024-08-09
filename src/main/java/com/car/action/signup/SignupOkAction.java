package com.car.action.signup;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.car.action.Action;
import com.car.action.ActionForward;
import com.car.model.TotalDAO;
import com.car.model.UserDTO;

public class SignupOkAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String name = request.getParameter("name");
		String id = request.getParameter("id");
		String pwd = request.getParameter("pwd");
		String email = request.getParameter("email");
		String phone1 = request.getParameter("phone1");
		String phone2 = request.getParameter("phone2");
		String phone3 = request.getParameter("phone3");

		String phone = phone1 + "-" + phone2 + "-" + phone3;

		UserDTO dto = new UserDTO();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Calendar c1 = Calendar.getInstance();
		String date = sdf.format(c1.getTime());
		
		dto.setUser_no("U" + date);
		dto.setUser_name(name);
		dto.setUser_id(id);
		dto.setUser_pwd(pwd);
		dto.setUser_email(email);
		dto.setUser_phone(phone);

		TotalDAO dao = TotalDAO.getInstance();

		int check = dao.insertUser(dto);

		PrintWriter out = response.getWriter();

		if (check > 0) {
			// 회원가입 로그 남기는 메서드
			dao.remainSignup();
			// 회원가입 마일리지 적립 메서드
			dao.setMileageSignup();

			ActionForward forward = new ActionForward();

			forward.setPath("/WEB-INF/views/public/signup/signup_ok.jsp");

			return forward;
		} else {
			out.println("<script>");
			out.println("alert('회원가입에 실패했습니다. 입력한 정보를 다시한번 확인해주세요!!')");
			out.println("history.back()");
			out.println("</script>");
			out.close();
		}

		return null;
	}

}
