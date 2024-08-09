package com.car.action.mypage;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.car.action.Action;
import com.car.action.ActionForward;
import com.car.action.StaticArea;
import com.car.model.BuyReviewBoardDTO;
import com.car.model.TotalDAO;

public class MypageBuyReviewContentDeleteAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		StaticArea.checkUserDTO(request, response);

		int buy_review_board_no = Integer.parseInt(request.getParameter("no").trim());

		TotalDAO dao = TotalDAO.getInstance();

		BuyReviewBoardDTO cont = dao.getBuyReviewBoardContent(buy_review_board_no);

		int t_no = cont.getBuy_review_transaction_no();

		int check = dao.delete_mypagebuyreviewcontent(buy_review_board_no);

		if (check > 0) {

			dao.changeTransactionBuy_Status(t_no);

			ActionForward forward = new ActionForward();

			forward.setPath("/WEB-INF/views/public/mypage/mypage_buy_review_delete_ok.jsp");

			return forward;
		} else {
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('글 삭제에 실패했습니다. 관리자에게 문의하세요.')");
			out.println("history.back()");
			out.println("</script>");
			out.close();
		}

		return null;

	}

}
