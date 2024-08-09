package com.car.action.sellcar;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

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

public class ReqRegcarOkAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		StaticArea.checkUserDTO(request, response);

		ReqSellBoardDTO dto = new ReqSellBoardDTO();

		// 파일 업로드 설정 내용
		// 1. 첨부 파일 저장할 경로 설정
		String saveFolder = "D:\\JSP\\4S_Car\\src\\main\\webapp\\upload_images";

		// 2. 첨부 파일 최대 크기 설정
		int fileSize = 10 * 1024 * 1024; // 10MB

		// 3. MultipartRequest 객체 생성
		// -> 파일 업로드를 진행하기 위한 객체
		MultipartRequest multi = new MultipartRequest(request, // 일반적인 request 객체
				saveFolder, // 첨부파일이 저장될 경로
				fileSize, // 업로드할 첨부파일의 최대 크기
				"UTF-8", // 문자 인코딩 방식
				new DefaultFileRenamePolicy() // 첨부파일의 이름이 같은 경우 중복이 안되게 설정
		);

		// form 태그로 넘어온 데이터들을 HttpServletRequest 객체 대신 MultipartRequest 객체로 받아주어야 한다.
		String user_no = multi.getParameter("user_no").trim();
		String car_std_no = multi.getParameter("car_std_no").trim();
		String car_no = multi.getParameter("car_no").replaceAll(" ", "");
		int car_distance = Integer.parseInt(multi.getParameter("car_distance").trim());
		int car_price = Integer.parseInt(multi.getParameter("car_price").trim());
		String car_detail = multi.getParameter("car_detail").trim();

		// form 태그에서 type="file" 속성으로 되어 있으면
		// getFile() 메서드로 받아주어야 한다.
		// form 태그에서 enctype="multipart/form-data" 속성을 추가 해주어야 한다.
		File car_file = multi.getFile("car_file");

		if (car_file != null) { // 첨부파일이 존재하는 경우

			// 우선은 첨부파일의 이름을 알아야 한다.
			// getName(): 첨부파일의 이름을 문자열로 반환하는 메서드,
			String fileName = car_file.getName();

			// 날짜 객체 생성
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

		// DB 연동을 위한 DAO 객체 생성
		TotalDAO dao = TotalDAO.getInstance();

		// 등록 요청 게시판 객체에 정보를 담는다.
		dto.setReq_sell_board_user_no(user_no);
		dto.setReq_sell_board_car_std_no(car_std_no);
		dto.setReq_sell_board_car_no(car_no);
		dto.setReq_sell_board_car_distance(car_distance);
		dto.setReq_sell_board_car_price(car_price);
		dto.setReq_sell_board_car_detail(car_detail);

		// DB에 요청한 정보 저장
		int check = dao.insertReqRegcar(dto);
		System.out.println("check >>> " + check);

		// DB에서 생성된 글번호 가져와서 객체에 저장
		dto.setReq_sell_board_no(dao.getReqSellBoardNo());
		// DB에서 생선된 날짜정보 가져와서 객체에 저장
		dto.setReq_sell_board_date(dao.getReqSellBoardDate());

		// 차량 구분번호를 _(언더바)를 기준으로 나누어 리스트에 저장
		List<String> car_info_list = Arrays.asList(car_std_no.split("_"));
		for (String i : car_info_list) {
			System.out.println(i);
		}

		// Model에 등록 요청 게시판 객체 저장
		request.setAttribute("dto", dto);
		// Model에 차량 구분번호를 _(언더바)를 기준으로 나눈 값들을 담은 list 저장
		request.setAttribute("list", car_info_list);

		// 페이지 이동 객체 생성
		ActionForward forward = new ActionForward();

		// 출력 객체 생성
		PrintWriter out = response.getWriter();
		// 저장 성공 시 결과창 이동
		if (check > 0) {
			forward.setPath("/WEB-INF/views/public/sellcar/req_regcar_ok.jsp");
			return forward;
		} else { // 저장 실패 시 알림창 띄우고 뒤로가기
			out.println("<script>");
			out.println("alert('딜러에게 정보 전송 실패')");
			out.println("history.back()");
			out.println("</script>");
			out.close(); // 출력 버퍼를 비우고 닫어주어야 한다. -> 제거하면 알림창 출력이 안된다.
		}

		return null;
	}

}