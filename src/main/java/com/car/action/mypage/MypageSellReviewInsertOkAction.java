package com.car.action.mypage;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.car.action.Action;
import com.car.action.ActionForward;
import com.car.action.StaticArea;
import com.car.model.SellReviewBoardDTO;
import com.car.model.TotalDAO;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

public class MypageSellReviewInsertOkAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		StaticArea.checkUserDTO(request, response);

		String saveFolder = "D:\\JSP\\4S_Car\\src\\main\\webapp\\upload_images";

		int fileSize = 10 * 1024 * 1024;

		MultipartRequest multi = new MultipartRequest(request, // 일반적인 request 객체
				saveFolder, // 첨부파일이 저장될 경로
				fileSize, // 업로드할 첨부파일의 최대 크기
				"UTF-8", // 문자 인코딩 방식
				new DefaultFileRenamePolicy() // 첨부파일의 이름이 같은 경우 중복이 안되게 설정
		);

		String user_id = multi.getParameter("writer").trim();
		String title = multi.getParameter("title").trim();
		String content = multi.getParameter("content").trim();

		File sellReview_file = multi.getFile("sellReview_file");

		SellReviewBoardDTO dto = new SellReviewBoardDTO();

		if (sellReview_file != null) {
			String fileName = sellReview_file.getName();
			System.out.println("fileName = " + fileName);

			Calendar cal = Calendar.getInstance();
			int year = cal.get(Calendar.YEAR);

			String month = StaticArea.getMonth(cal.get(Calendar.MONTH) + 1);
			String day = StaticArea.getDay(cal.get(Calendar.DAY_OF_MONTH));

			String ymd = year + "-" + month + "-" + day;

			String homedir = saveFolder + "/" + user_id;

			File path = new File(homedir);

			if (!path.exists()) {
				path.mkdir();
			}

			String reFileName = "sellreview_" + ymd + "_" + title + "_" + fileName;

			sellReview_file.renameTo(new File(homedir + "/" + reFileName));

			String fileDBName = user_id + "/" + reFileName;

			dto.setSell_review_board_file(fileDBName);

		}

		dto.setSell_review_board_writer_id(user_id);
		dto.setSell_review_board_title(title);
		dto.setSell_review_board_cont(content);

		int t_no = Integer.parseInt(multi.getParameter("t_no").trim());

		TotalDAO dao = TotalDAO.getInstance();

		int check = dao.insertSellReview(dto, t_no);

		if (check > 0) {

			dao.changeTransactionSellStatus(t_no);

			ActionForward forward = new ActionForward();

			forward.setPath("/WEB-INF/views/public/mypage/mysell_review_insert_ok.jsp");

			return forward;

		} else {
			PrintWriter out = response.getWriter();

			out.println("<script>");
			out.println("alert('판매 후기 등록에 실패했습니다. 입력한 정보를 다시 확인하세요.')");
			out.println("history.back()");
			out.println("</script>");
			out.close();
		}

		return null;

	}

}