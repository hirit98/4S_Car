package com.car.action.login;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.car.action.Action;
import com.car.action.ActionForward;

public class SimpleLoginInfoAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String email = request.getParameter("email").trim();
		String name = request.getParameter("name").trim();
		String phone = request.getParameter("mobile").trim();

		System.out.println(email);
		System.out.println(name);
		System.out.println(phone);

		return null;
	}

}
