package com.car.action.customer;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.car.action.Action;
import com.car.action.ActionForward;
import com.car.model.NoticeBoardDTO;
import com.car.model.TotalDAO;

public class NoticeSearchAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		TotalDAO dao = TotalDAO.getInstance();

		int notice_cnt = 0;
		int notice_rowsize = 10;
		int notice_block = 3;
		int notice_allPage = 0;
		int notice_page = 1;

		if (request.getParameter("notice_page") != null) {
			notice_page = Integer.parseInt(request.getParameter("notice_page").trim());
			if (notice_page < 1) {
				notice_page = 1;
			}
		}

		int notice_startNo = (notice_page * notice_rowsize) - (notice_rowsize - 1);
		int notice_endNo = (notice_page * notice_rowsize);
		int notice_startBlock = (((notice_page - 1) / notice_block) * notice_block) + 1;
		int notice_endBlock = (((notice_page - 1) / notice_block) * notice_block) + notice_block;

		String notice_field = request.getParameter("field").trim();
		String notice_keyword = request.getParameter("keyword").trim();

		notice_cnt = dao.countNoticeSearchList(notice_field, notice_keyword);

		notice_allPage = (int) Math.ceil(notice_cnt / (double) notice_rowsize);

		if (notice_endBlock > notice_allPage) {
			notice_endBlock = notice_allPage;
		}

		boolean is_nSearch = true;

		List<NoticeBoardDTO> list = dao.searchNoticeList(notice_field, notice_keyword, notice_page, notice_rowsize);

		request.setAttribute("notice_list", list);
		request.setAttribute("notice_cnt", notice_cnt);
		request.setAttribute("notice_page", notice_page);
		request.setAttribute("notice_rowsize", notice_rowsize);
		request.setAttribute("notice_block", notice_block);
		request.setAttribute("notice_allPage", notice_allPage);
		request.setAttribute("notice_startNo", notice_startNo);
		request.setAttribute("notice_endNo", notice_endNo);
		request.setAttribute("notice_startBlock", notice_startBlock);
		request.setAttribute("notice_endBlock", notice_endBlock);
		request.setAttribute("field", notice_field);
		request.setAttribute("keyword", notice_keyword);
		request.setAttribute("is_nSearch", is_nSearch);

		ActionForward forward = new ActionForward();

		forward.setPath("/WEB-INF/views/public/customer/notice_list.jsp");

		return forward;
	}

}
