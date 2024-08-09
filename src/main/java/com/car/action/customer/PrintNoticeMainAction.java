package com.car.action.customer;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.*;

import com.car.action.Action;
import com.car.action.ActionForward;
import com.car.model.NoticeBoardDTO;
import com.car.model.TotalDAO;

public class PrintNoticeMainAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		TotalDAO dao = TotalDAO.getInstance();
		
		List<NoticeBoardDTO> list = dao.printNoticeMain();

		PrintWriter out = response.getWriter();

		// JSON 객체와 배열 생성
		if (list != null) {
			JSONArray jsonArray = new JSONArray();
			for (NoticeBoardDTO notice : list) {
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("no", notice.getNotice_board_no());
				jsonObject.put("title", notice.getNotice_board_title());
				String date = notice.getNotice_board_update();
				if (date == null || date.isEmpty()) {
					date = notice.getNotice_board_date();
				}
				jsonObject.put("date", date);
				jsonArray.add(jsonObject);
			}

			// 공지사항 리스트를 JSON 형식으로 변환하여 클라이언트에 반환
			response.setContentType("application/json;charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
			out.print(jsonArray.toJSONString());
			out.flush();
		} else {
			// 리스트가 null일 경우 예외 처리
			out.print("[]");// 예: out.print("[]"); // 빈 JSON 배열 반환
		}
		out.close();

		return null;
	}

}
