package com.car.action.login;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;

import com.car.action.Action;
import com.car.action.ActionForward;
import com.car.model.UserDTO;

public class CheckLoginStatusAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// 로그인 상태 확인
		HttpSession session = request.getSession();

		String no = (String) session.getAttribute("no");

		JSONObject jsonResponse = new JSONObject();

		// 예외 처리 추가
		if (no == null) {
			jsonResponse.put("loggedIn", -1);
		} else {
			try {
				if (session != null && no.substring(0, 1).equals("U")) {
					// 유저 로그인 상태일 경우
					jsonResponse.put("loggedIn", 1);
					String name = (String) session.getAttribute("name");
					jsonResponse.put("username", name);
					jsonResponse.put("userno", no);
				} else if (session != null && no.substring(0, 1).equals("A")) {
					// 관리자 로그인 상태일 경우
					jsonResponse.put("loggedIn", 0);
					String name = (String) session.getAttribute("name");
					jsonResponse.put("adminname", name);
					jsonResponse.put("adminno", no);
				} else { // 로그인 상태가 아닐 경우
					jsonResponse.put("loggedIn", -1);
				}
			} catch (NullPointerException | StringIndexOutOfBoundsException e) {
				// 예외 처리
				jsonResponse.put("loggedIn", -1);
			}
		}

		// 응답 데이터 전송
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(jsonResponse.toString());

		return null; // ActionForward를 사용하지 않으므로 null 반환
	}

}
