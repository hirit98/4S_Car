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
import com.car.model.BuyReviewBoardDTO;
import com.car.model.TotalDAO;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

public class MypageBuyReviewInsertOkAction implements Action {

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

		String user_id = multi.getParameter("buy_writer").trim();
		String title = multi.getParameter("buy_title").trim();
		String content = multi.getParameter("buy_content").trim();

		File buyReview_file = multi.getFile("buyReview_file");

		BuyReviewBoardDTO dto = new BuyReviewBoardDTO();

		if (buyReview_file != null) { // 첨부파일이 존재하는 경우

			// getName(): 첨부파일의 이름을 문자열로 반환하는 메서드,
			String fileName = buyReview_file.getName();
			System.out.println("fileName = " + fileName);

			Calendar cal = Calendar.getInstance();
			int year = cal.get(Calendar.YEAR);
			// 날짜 정보 month와 day를 2자리로 맞추어주는 메서드
			String month = StaticArea.getMonth(cal.get(Calendar.MONTH) + 1);
			String day = StaticArea.getDay(cal.get(Calendar.DAY_OF_MONTH));

			String ymd = year + "-" + month + "-" + day;

			// ....../upload_car_images/U20240325001
			String homedir = saveFolder + "/" + user_id;

			// 날짜 폴더 생성
			File path = new File(homedir);

			if (!path.exists()) { // 폴더가 존재하지 않는 경우
				path.mkdir(); // 실제 폴더를 만들어 주는 메서드
			}

			// 파일을 생성 -> 예) 차량번호_날짜_파일명
			String reFileName = "buyreview_" + ymd + "_" + title + "_" + fileName;

			buyReview_file.renameTo(new File(homedir + "/" + reFileName));

			// 실제로 DB의 file 컬럼에 들어가는 파일 이름
			// "/회원번호/차량번호_날짜_파일명" 으로 저장할 예정
			String fileDBName = user_id + "/" + reFileName;

			dto.setBuy_review_board_file(fileDBName);
		}

		int t_no = Integer.parseInt(multi.getParameter("t_no").trim());

		dto.setBuy_review_board_writer_id(user_id);
		dto.setBuy_review_board_title(title);
		dto.setBuy_review_board_cont(content);

		TotalDAO dao = TotalDAO.getInstance();

		int check = dao.insertBuyReview(dto, t_no);

		if (check > 0) {

			dao.changeTransactionBuyStatus(t_no);

			ActionForward forward = new ActionForward();

			forward.setPath("/WEB-INF/views/public/mypage/mypage_buy_review_insert_ok.jsp");

			return forward;
		} else {
			PrintWriter out = response.getWriter();

			out.println("<script>");
			out.println("alert('구매 후기 등록에 실패했습니다. 입력한 정보를 다시 확인하세요.')");
			out.println("history.back()");
			out.println("</script>");
			out.close();
		}

		return null;

	}
}