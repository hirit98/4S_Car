package com.car.action.search;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.car.action.Action;
import com.car.action.ActionForward;
import com.car.action.StaticArea;
import com.car.model.PaymentDTO;
import com.car.model.SellBoardDTO;
import com.car.model.TotalDAO;
import com.car.model.UserDTO;

public class CarListCreditInsertAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		StaticArea.checkUserDTO(request, response);

		HttpSession session = request.getSession();

		int board_no = Integer.parseInt(request.getParameter("no").trim());

		UserDTO cont = (UserDTO) session.getAttribute("dto");

		TotalDAO dao = TotalDAO.getInstance();

		SellBoardDTO sell_dto = dao.getSellBoardContent(board_no);

		PaymentDTO pay_dto = new PaymentDTO();

		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		String month = StaticArea.getMonth(cal.get(Calendar.MONTH) + 1);
		String day = StaticArea.getDay(cal.get(Calendar.DAY_OF_MONTH));

		String payment_no = "P" + year + month + day;
		String buyer_no = cont.getUser_no();
		String method = "카드";
		String card_company = request.getParameter("cardCops").trim();

		if (card_company.equals("none")) {
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('카드사를 정확하게 선택해 주세요!!')");
			out.println("history.back()");
			out.println("</script>");
			out.close();
		}

		String card_num = null;
		String card_num1 = request.getParameter("cardNumber1").trim();
		String card_num2 = request.getParameter("cardNumber2").trim();
		String card_num3 = request.getParameter("cardNumber3").trim();
		String card_num4 = request.getParameter("cardNumber4").trim();
		card_num = card_num1 + "-" + card_num2 + "-" + card_num3 + "-" + card_num4;

		String card_pwd = request.getParameter("cardPwd").trim();
		String card_expDate = request.getParameter("expiryDate").trim();
		String card_cvc = request.getParameter("cvc").trim();
		int card_divMonth = Integer.parseInt(request.getParameter("installments").trim());
		int price = sell_dto.getSell_board_car_price();
		int sell_board_no = board_no;

		pay_dto.setPayment_no(payment_no);
		pay_dto.setPayment_buyer_no(buyer_no);
		pay_dto.setPayment_method(method);
		pay_dto.setPayment_card_company(card_company);
		pay_dto.setPayment_card_no(card_num);
		pay_dto.setPayment_car_pwd(card_pwd);
		pay_dto.setPayment_card_div_month(card_divMonth);
		pay_dto.setPayment_amount(price);
		pay_dto.setPayment_sell_board_no(sell_board_no);

		int check1 = dao.insertTransactionData(sell_dto, buyer_no);
		int check2 = dao.insertPaymentData(pay_dto);

		if (check1 > 0 && check2 > 0) {

			dao.updateSellBoardStatus(board_no);

			ActionForward forward = new ActionForward();

			forward.setPath("/WEB-INF/views/public/search/car_list_payment_ok.jsp");

			return forward;
		} else {

			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('구매에 실패했습니다. 입력한 정보를 다시한번 확인해 주세요.')");
			out.println("history.back()");
			out.println("</script>");
			out.close();
		}

		return null;
	}

}
