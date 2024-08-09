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
import com.car.model.ReqSellBoardDTO;
import com.car.model.TotalDAO;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

public class MycarInfoModifyOkAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		StaticArea.checkUserDTO(request, response);

		String saveFolder = "D:\\JSP\\4S_Car\\src\\main\\webapp\\upload_images";

		int fileSize = 10 * 1024 * 1024; // 10MB

		MultipartRequest multi = new MultipartRequest(request, // 일반적인 request 객체
				saveFolder, // 첨부파일이 저장될 경로
				fileSize, // 업로드할 첨부파일의 최대 크기
				"UTF-8", // 문자 인코딩 방식
				new DefaultFileRenamePolicy() // 첨부파일의 이름이 같은 경우 중복이 안되게 설정
		);

		String user_no = multi.getParameter("user").trim();
		int board_no = Integer.parseInt(multi.getParameter("board_no").trim());
		String car_no = multi.getParameter("car_no").trim();
		int distance = Integer.parseInt(multi.getParameter("distance").trim());
		int price = Integer.parseInt(multi.getParameter("price").trim());
		String detail = multi.getParameter("detail").trim();

		File car_file = multi.getFile("car_file");

		ReqSellBoardDTO dto = new ReqSellBoardDTO();

		if (car_file != null) { // 첨부파일이 존재하는 경우

			// getName(): 첨부파일의 이름을 문자열로 반환하는 메서드,
			String fileName = car_file.getName();
			System.out.println("fileName = " + fileName);

			Calendar cal = Calendar.getInstance();
			int year = cal.get(Calendar.YEAR);
			// 날짜 정보 month와 day를 2자리로 맞추어주는 메서드
			String month = StaticArea.getMonth(cal.get(Calendar.MONTH) + 1);
			String day = StaticArea.getDay(cal.get(Calendar.DAY_OF_MONTH));

			String ymd = year + "-" + month + "-" + day;

			// ....../upload_car_images/U20240325001
			String homedir = saveFolder + "/" + user_no;

			// 날짜 폴더 생성
			File path = new File(homedir);

			if (!path.exists()) { // 폴더가 존재하지 않는 경우
				path.mkdir(); // 실제 폴더를 만들어 주는 메서드
			}

			// 파일을 생성 -> 예) 차량번호_날짜_파일명
			String reFileName = "req_" + ymd + "_" + car_no + "_" + fileName;

			car_file.renameTo(new File(homedir + "/" + reFileName));

			// 실제로 DB의 file 컬럼에 들어가는 파일 이름
			// "/회원번호/차량번호_날짜_파일명" 으로 저장할 예정
			String fileDBName = user_no + "/" + reFileName;

			dto.setReq_sell_board_car_file(fileDBName);
		}

		dto.setReq_sell_board_no(board_no);
		dto.setReq_sell_board_car_distance(distance);
		dto.setReq_sell_board_car_price(price);
		dto.setReq_sell_board_car_detail(detail);

		TotalDAO dao = TotalDAO.getInstance();

		int check = dao.updateReqSellBoard(dto);

		if (check > 0) {
			ActionForward forward = new ActionForward();

			forward.setPath("/WEB-INF/views/public/mypage/mycar_info_modify_ok.jsp");

			return forward;
		} else {
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('요청글 수정에 실패했습니다.. 입력한 정보를 다시한번 확인하세요.')");
			out.println("history.back()");
			out.println("</script>");
			out.close();
		}

		return null;
	}

}
