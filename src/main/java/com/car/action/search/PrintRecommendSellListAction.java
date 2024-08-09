package com.car.action.search;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.car.action.Action;
import com.car.action.ActionForward;
import com.car.model.SellBoardDTO;
import com.car.model.TotalDAO;

public class PrintRecommendSellListAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		TotalDAO dao = TotalDAO.getInstance();
		
		List<SellBoardDTO> list = dao.printRecommendSellList();
		
		PrintWriter out = response.getWriter();
		
		if(list != null) {
			JSONArray jsonArray = new JSONArray();
			for (SellBoardDTO board : list) {
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("no", board.getSell_board_no());
				jsonObject.put("file", board.getSell_board_car_file());
				int price = board.getSell_board_car_price();
				jsonObject.put("price", String.format("%,d", price));
				jsonObject.put("std", board.getSell_board_car_std_no().split("_")[7]);
				jsonArray.add(jsonObject);
			}
			
			response.setContentType("application/json;charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
			out.print(jsonArray.toJSONString());
			out.flush();
		} else {
			out.print("[]");
		}
		out.close();
		
		return null;

		
	}

}