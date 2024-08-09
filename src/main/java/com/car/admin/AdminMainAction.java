package com.car.admin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.car.action.Action;
import com.car.action.ActionForward;
import com.car.action.StaticArea;
import com.car.model.QnaBoardDTO;
import com.car.model.ReqSellBoardDTO;
import com.car.model.TotalDAO;
import com.car.model.UserDTO;

public class AdminMainAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		StaticArea.checkAdminDTO(request, response);

		ActionForward forward = new ActionForward();

		TotalDAO dao = TotalDAO.getInstance();

		List<UserDTO> list = dao.getUserList();

		List<ReqSellBoardDTO> req = dao.getReqSellBoardList();

		List<QnaBoardDTO> qna = dao.getQnaBoardList();

		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		int month = Integer.parseInt(StaticArea.getMonth(cal.get(Calendar.MONTH) + 1));

		List<Integer> signupList = dao.countSignupList(year, month);
		List<String> monthList = new ArrayList<String>();
		List<Integer> priceList = new ArrayList<Integer>();
		List<Long> targetSalesList = new ArrayList<Long>();
		List<Integer> cntSalesCarList = dao.countSalesCarList(year, month);
		List<Integer> cntReqCarList = dao.countReqCarList(year, month);

		for (int i = month + 1; i <= 12; i++) {
			monthList.add("'" + i + "월" + "'");
			
			List<Integer> tempList = dao.getCarSales(year - 1, i);

			int addPrice = 0;

			for (int j : tempList) {

				addPrice += j;
			}
			priceList.add(addPrice);
		}

		for (int i = 1; i <= month; i++) {
			monthList.add("'" + i + "월" + "'");
			
			List<Integer> tempList = dao.getCarSales(year, i);

			int addPrice = 0;

			for (int j : tempList) {

				addPrice += j;
			}
			priceList.add(addPrice);
		}

		for(int temp : cntReqCarList) {
			
			long targetPrice = 0;
			
			targetPrice = (long)(temp * 50000000 * 0.7);
			
			targetSalesList.add(targetPrice);
		}
		
		Collections.reverse(list);

		Collections.reverse(req);

		Collections.reverse(qna);

		request.setAttribute("list", list);
		request.setAttribute("req", req);
		request.setAttribute("qna", qna);
		request.setAttribute("signupList", signupList);
		request.setAttribute("monthList", monthList);
		request.setAttribute("priceList", priceList);
		request.setAttribute("cntSalesCarList", cntSalesCarList);
		request.setAttribute("cntReqCarList", cntReqCarList);
		request.setAttribute("targetSalesList", targetSalesList);

		forward.setPath("/WEB-INF/views/admin/admin_main.jsp");

		return forward;
	}

}
