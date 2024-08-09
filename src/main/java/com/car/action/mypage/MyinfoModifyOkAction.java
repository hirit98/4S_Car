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

public class MyinfoModifyOkAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		StaticArea.checkUserDTO(request, response);

		HttpSession session = request.getSession();

		UserDTO cont = (UserDTO) session.getAttribute("dto");
		String phone = request.getParameter("phone").trim();
		String email = request.getParameter("email").trim();
		String new_pwd = request.getParameter("new_pwd").trim();
		String pwdcheck = request.getParameter("pwdcheck").trim();

		PrintWriter out = response.getWriter();

		if (new_pwd.equals(pwdcheck)) { // 회원 정보 수정폼에서 입력받은 두개의 비밀번호가 일치하는지 확인.

			UserDTO dto = new UserDTO();
			dto.setUser_no(cont.getUser_no());
			dto.setUser_pwd(new_pwd);
			dto.setUser_phone(phone);
			dto.setUser_email(email);

			TotalDAO dao = TotalDAO.getInstance();

			int check = dao.updateUserInfo(dto);

			if (check > 0) {

				UserDTO updateUser = dao.getUserContent(cont.getUser_no());

				session.removeAttribute("dto");
				session.setAttribute("dto", updateUser); // update 되어 바뀐 유저 정보를 다시 'dto'라는 이름으로 세션에 set 함.

				ActionForward forward = new ActionForward();

				forward.setPath("/WEB-INF/views/public/mypage/myinfo_modify_ok.jsp");

				return forward;

			} else {
				out.println("<script>");
				out.println("alert('정보수정에 실패했습니다. 관리자에게 문의해주세요.')");
				out.println("history.back()");
				out.println("</script>");
				out.close();
			}
		} else {
			out.println("<script>");
			out.println("alert('비밀번호가 일치하지 않습니다. 다시 입력해주세요!')");
			out.println("history.back()");
			out.println("</script>");
			out.close();
		}

		return null;
	}

}
