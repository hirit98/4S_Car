package com.car.action.customer;

import java.io.File;
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
import com.car.model.QnaBoardDTO;
import com.car.model.TotalDAO;
import com.car.model.UserDTO;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

public class QnaInsertOkAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		StaticArea.checkUserDTO(request, response);

		QnaBoardDTO dto = new QnaBoardDTO();
		HttpSession session = request.getSession();

		UserDTO uDto = (UserDTO) session.getAttribute("dto");

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
		String type = multi.getParameter("qna_board_type");
		String writer_no = uDto.getUser_no();
		String writer_id = uDto.getUser_id();
		String title = multi.getParameter("qna_board_title").trim();
		String content = multi.getParameter("qna_board_content").trim();

		// form 태그에서 type="file" 속성으로 되어 있으면
		// getFile() 메서드로 받아주어야 한다.
		// form 태그에서 enctype="multipart/form-data" 속성을 추가 해주어야 한다.
		File qna_file = multi.getFile("qna_board_file");

		if (qna_file != null) { // 첨부파일이 존재하는 경우

			// 우선은 첨부파일의 이름을 알아야 한다.
			// getName(): 첨부파일의 이름을 문자열로 반환하는 메서드,
			String fileName = qna_file.getName();
			System.out.println("fileName = " + fileName);

			// 날짜 객체 생성
			Calendar cal = Calendar.getInstance();
			int year = cal.get(Calendar.YEAR);
			// 날짜 정보 month와 day를 2자리로 맞추어주는 메서드
			String month = StaticArea.getMonth(cal.get(Calendar.MONTH) + 1);
			String day = StaticArea.getDay(cal.get(Calendar.DAY_OF_MONTH));

			String ymd = year + "-" + month + "-" + day;

			// ....../upload_car_images/U20240325001
			String homedir = saveFolder + "/" + writer_no;

			// 날짜 폴더 생성
			File path = new File(homedir);

			if (!path.exists()) { // 폴더가 존재하지 않는 경우
				path.mkdir(); // 실제 폴더를 만들어 주는 메서드
			}

			// 파일을 생성 -> 예) 차량번호_날짜_파일명
			String reFileName = "qna_" + ymd + "_" + fileName;

			qna_file.renameTo(new File(homedir + "/" + reFileName));

			// 실제로 DB의 file 컬럼에 들어가는 파일 이름
			// "/회원번호/차량번호_날짜_파일명" 으로 저장할 예정
			String fileDBName = writer_no + "/" + reFileName;

			dto.setQna_board_file(fileDBName);
		}

		dto.setQna_board_type(type);
		dto.setQna_board_writer_id(writer_id);
		dto.setQna_board_title(title);
		dto.setQna_board_cont(content);

		TotalDAO dao = TotalDAO.getInstance();

		int check = dao.insertQna(dto);

		if (check == 0) {
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('문의 등록 실패..')");
			out.println("history.back()");
			out.println("</script>");
			out.close();
		}

		ActionForward forward = new ActionForward();

		forward.setPath("/WEB-INF/views/public/customer/qna_insert_ok.jsp");

		return forward;
	}

}