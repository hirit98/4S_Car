package com.car.action.customer;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.car.action.Action;
import com.car.action.ActionForward;
import com.car.model.EventBoardDTO;
import com.car.model.NoticeBoardDTO;
import com.car.model.TotalDAO;

public class EventListAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		TotalDAO dao = TotalDAO.getInstance();

		int notice_cnt = 0;
		int event_cnt = 0;

		// 한 페이지당 보여질 게시물의 수
		int notice_rowsize = 10;
		int event_rowsize = 10;

		// 아래에 보여질 페이지 최대 블럭 수
		int notice_block = 3;
		int event_block = 3;
		// [1][2][3] ... [4][5][6] ... [7][8][9]

		// 전체 페이지 수
		int notice_allPage = 0;
		int event_allPage = 0;

		// 현재 페이지 변수
		int notice_page = 1;
		int event_page = 1;

		if (request.getParameter("notice_page") != null) {
			notice_page = Integer.parseInt(request.getParameter("notice_page").trim());
			if (notice_page < 1) {
				notice_page = 1;
			}
		}
		if (request.getParameter("event_page") != null) {
			event_page = Integer.parseInt(request.getParameter("event_page").trim());
			if (event_page < 1) {
				event_page = 1;
			}
		}

		// 해당 페이지에서 시작 게시물번호
		int notice_startNo = (notice_page * notice_rowsize) - (notice_rowsize - 1);
		int event_startNo = (event_page * event_rowsize) - (event_rowsize - 1);

		// 해당 페이지에서 끝 게시물번호
		int notice_endNo = (notice_page * notice_rowsize);
		int event_endNo = (event_page * event_rowsize);

		// 해당 페이지에서 시작 블럭
		int notice_startBlock = (((notice_page - 1) / notice_block) * notice_block) + 1;
		int event_startBlock = (((event_page - 1) / event_block) * event_block) + 1;

		// 해당 페이지에서 끝 블럭
		int notice_endBlock = (((notice_page - 1) / notice_block) * notice_block) + notice_block;
		int event_endBlock = (((event_page - 1) / event_block) * event_block) + event_block;

		notice_cnt = dao.countNoticeList();
		event_cnt = dao.countEventList();

		// 전체 게시물의 수를 한 페이지당 보여질 게시물의 수로 나누어 주면 전체 페이지가 나옴.
		// 이 때 전체 페이지 수가 나올 때 나머지가 있으면 무저간 전체 페이지 수를 하나 올려 주어야 함.
		notice_allPage = (int) Math.ceil(notice_cnt / (double) notice_rowsize);
		event_allPage = (int) Math.ceil(event_cnt / (double) event_rowsize);

		// endBlock 을 allPage로 저장한다.
		if (notice_endBlock > notice_allPage) {
			notice_endBlock = notice_allPage;
		}
		if (event_endBlock > event_allPage) {
			event_endBlock = event_allPage;
		}

		boolean is_nSearch = false;
		boolean is_eSearch = false;

		List<NoticeBoardDTO> notice_list = dao.getNoticeList(notice_page, notice_rowsize);

		request.setAttribute("notice_cnt", notice_cnt);
		request.setAttribute("notice_list", notice_list);
		request.setAttribute("notice_page", notice_page);
		request.setAttribute("notice_rowsize", notice_rowsize);
		request.setAttribute("notice_block", notice_block);
		request.setAttribute("notice_allPage", notice_allPage);
		request.setAttribute("notice_startNo", notice_startNo);
		request.setAttribute("notice_endNo", notice_endNo);
		request.setAttribute("notice_startBlock", notice_startBlock);
		request.setAttribute("notice_endBlock", notice_endBlock);
		request.setAttribute("is_nSearch", is_nSearch);

		List<EventBoardDTO> event_list = dao.getEventList(event_page, event_rowsize);

		request.setAttribute("event_cnt", event_cnt);
		request.setAttribute("event_list", event_list);
		request.setAttribute("event_page", event_page);
		request.setAttribute("event_rowsize", event_rowsize);
		request.setAttribute("event_block", event_block);
		request.setAttribute("event_allPage", event_allPage);
		request.setAttribute("event_startNo", event_startNo);
		request.setAttribute("event_endNo", event_endNo);
		request.setAttribute("event_startBlock", event_startBlock);
		request.setAttribute("event_endBlock", event_endBlock);
		request.setAttribute("is_eSearch", is_eSearch);

		ActionForward forward = new ActionForward();

		forward.setPath("/WEB-INF/views/public/customer/event_list.jsp");

		return forward;
	}

}
