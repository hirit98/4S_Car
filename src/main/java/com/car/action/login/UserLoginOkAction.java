package com.car.action.login;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.car.action.Action;
import com.car.action.ActionForward;
import com.car.model.TotalDAO;
import com.car.model.UserDTO;

public class UserLoginOkAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String user_id = request.getParameter("user_id").trim();
		String user_pwd = request.getParameter("user_pwd").trim();

		UserDTO dto = new UserDTO();

		dto.setUser_id(user_id);
		dto.setUser_pwd(user_pwd);

		TotalDAO dao = TotalDAO.getInstance();

		int check = dao.checkUserLogin(dto);

		PrintWriter out = response.getWriter();

		if (check == 1) {

			HttpSession session = request.getSession();
			UserDTO cont = dao.getUserContent(dto);
			session.setMaxInactiveInterval(1800); // 세션 유지시간 설정 (초 단위)
			session.setAttribute("name", cont.getUser_name());
			session.setAttribute("no", cont.getUser_no());
			session.setAttribute("dto", cont);

			// 오늘 로그인 기록이 있는지 확인하는 메서드
			String checkLoginToday = dao.checkLoginToday(user_id);

			if (checkLoginToday == null) {
				checkLoginToday = "";
			}

			// 로그 남기는 메서드
			dao.remainLogin(user_id);

			// 현재 날짜 가져오기
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			Calendar c1 = Calendar.getInstance();
			String date = sdf.format(c1.getTime());

			// 현재 날짜와 비교해서 오늘 로그인 기록이 없다면
			if (!checkLoginToday.equals(date)) {
				// 로그인 마일리지 적립 메서드
				dao.setMileageLogin(user_id);
			}

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
