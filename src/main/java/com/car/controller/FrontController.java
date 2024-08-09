package com.car.controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.Properties;
import java.util.StringTokenizer;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.car.action.Action;
import com.car.action.ActionForward;

public class FrontController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");

		String uri = request.getRequestURI();
		// System.out.println("uri = " + uri);
		String path = request.getContextPath();
		// System.out.println("path = " + path);
		String command = uri.substring(path.length() + 1);
		System.out.println("command = " + command);

		Action action = null;
		ActionForward forward = null;
		Properties prop = new Properties();

		FileInputStream fis = new FileInputStream(
				"D:\\JSP\\4S_Car\\src\\main\\java\\com\\car\\controller\\mapping.properties");

		prop.load(fis);

		String value = prop.getProperty(command);
		System.out.println("value = " + value);

		if (value.substring(0, 7).equals("execute")) {

			StringTokenizer st = new StringTokenizer(value, "|");

			String url_1 = st.nextToken();
			String url_2 = st.nextToken();

			try {

				Class<?> url = Class.forName(url_2);

				Constructor<?> constructor = url.getConstructor();

				action = (Action) constructor.newInstance();

				forward = action.execute(request, response);

			} catch (Exception e) {
				e.printStackTrace();
			}

		} else {

			forward = new ActionForward();
			forward.setRedirect(false);
			forward.setPath(value);

		}

		if (forward != null) {

			if (forward.isRedirect()) {
				response.sendRedirect(forward.getPath());
			} else {
				RequestDispatcher rd = request.getRequestDispatcher(forward.getPath());
				rd.forward(request, response);
			}

		}
	}
}
