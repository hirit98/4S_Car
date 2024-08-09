package com.car.action.logout;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.car.action.Action;
import com.car.action.ActionForward;
import com.car.model.TotalDAO;

public class CheckSessionStatusAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession(false);

		if (session != null && session.getAttribute("name") != null) { // 세션이 존재하는 경우
			// 세션의 최종 액세스 시간을 가져옵니다.
			long lastAccessedTime = session.getLastAccessedTime();
			// 세션의 유효 기간 (초)을 가져옵니다.
			int sessionTimeoutSeconds = session.getMaxInactiveInterval();
			// 현재 시간을 밀리초로 가져옵니다.
			long currentTimeMillis = System.currentTimeMillis();
			// 세션의 만료 시간을 계산합니다.
			long sessionExpirationTime = lastAccessedTime + (sessionTimeoutSeconds * 1000);

			String user_no = (String) session.getAttribute("no");

			if (currentTimeMillis > sessionExpirationTime) {

				// 세션 만료
				TotalDAO dao = TotalDAO.getInstance();

				System.out.println("세션 시간 만료");

				dao.remainLogout(user_no);
				// 세션이 없거나 사용자가 인증되지 않은 경우 "expired"를 반환합니다.
				session.invalidate(); // 세션 무효화
				response.getWriter().print("expired");
			}
		}

		return null;
	}

}
