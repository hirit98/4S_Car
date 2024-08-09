package com.car.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.car.action.StaticArea;

public class TotalDAO {

	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	String sql = "";

	// TotalDAO 객체를 싱글톤 방식으로 만든다.

	// 1. TotalDAO 객체를 정적(static) 멤버로 선언 해 준다.
	private static TotalDAO instance = null;

	// 2. 기본 생성자를 private 로 선언한다.
	private TotalDAO() {
	}

	// 3. 기본 생성자 대신 instance 를 return 해 주는 getInstance() 매서드 선언.
	public static TotalDAO getInstance() {

		if (instance == null) {
			instance = new TotalDAO();
		}

		return instance;
	} // getInstance() end.

	// JDBC 방식이 아닌 DBCP 방식으로 DB와 연동 작업 진행.
	public void openConn() {

		try {

			// 1. JNDI 서버 객체 생성.
			// 자바의 네이밍 서비스(JNDI)에서 이름과 실제 객체를 연결 해 주는 개념이 Context 객체이며
			// , InitialContext 객체는 네이밍 서비스를 이용하기 위한 시작점이 된다.
			Context initCtx = new InitialContext();

			// 2. Context 객체를 얻어와야 함.
			// "java:comp/env" 라는 이름의 인수로 Context 객체를 얻어옴.
			// "java:comp/env" 는 현재 웹 애플리케이션에서 네이밍 서비스를 이용시 루트 디렉토리라고 생각하면 됨.
			// 즉, 현재 웹 애플리케이션이 사용 할 수 있는 모든 자원은 "java:comp/env" 아래에 위치를 하게 됨.
			Context ctx = (Context) initCtx.lookup("java:comp/env");

			// 3. lookup() 매서드를 이용하여 매칭되는 커넥션을 찾아옴.
			// "java:comp/env" 아래에 위치한 "jdbc/mysql" 자원을 얻어옴
			// 이 자원이 바로 데이터 소스(커넥션 풀)임.
			// 여기서 "jdbc/mysql" 은 context.xml 파일에 추가했던
			// <Resource> 태그 안에 있던 name 속성의 값임.
			DataSource ds = (DataSource) ctx.lookup("jdbc/mysql");

			// 4. DataSource 객체를 이용하여 커넥션을 하나 가져오면 됨.
			con = ds.getConnection();

		} catch (Exception e) {
			e.printStackTrace();
		}

	} // openConn() end.

	// DB에 연결된 자원을 종료하는 매서드.
	public void closeConn(ResultSet rs, PreparedStatement pstmt, Connection con) {

		try {

			if (rs != null)
				rs.close();
			if (pstmt != null)
				pstmt.close();
			if (con != null)
				con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	} // closeConn() end.

	public void closeConn(PreparedStatement pstmt, Connection con) {

		try {

			if (pstmt != null)
				pstmt.close();
			if (con != null)
				con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	} // closeConn() end.

	// 회원가입 페이지에서 입력받은 회원의 정보를 user Table에 insert 하는 매서드.
	public int insertUser(UserDTO dto) {

		int result = 0, cnt = 0;

		try {

			openConn();

			sql = "select count(user_no) from user where user_no like ?";

			pstmt = con.prepareStatement(sql);

			pstmt.setString(1, dto.getUser_no() + "%");

			rs = pstmt.executeQuery();

			if (rs.next()) {
				cnt = rs.getInt(1) + 1;
			}

			sql = "insert into user values(?,?,?,?,?,?, default, default, default)";

			pstmt = con.prepareStatement(sql);

			if (cnt < 10) {
				pstmt.setString(1, dto.getUser_no() + "00" + cnt);
			} else if (cnt < 100) {
				pstmt.setString(1, dto.getUser_no() + "0" + cnt);
			} else {
				pstmt.setString(1, dto.getUser_no() + cnt);
			}
			pstmt.setString(2, dto.getUser_id());
			pstmt.setString(3, dto.getUser_pwd());
			pstmt.setString(4, dto.getUser_name());
			pstmt.setString(5, dto.getUser_phone());
			pstmt.setString(6, dto.getUser_email());

			result = pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}

		return result;
	} // insertUser(dto) end.

	// 사용자가 입력한 id와 pwd 가 유효한 값인지 check 하는 매서드.
	public int checkUserLogin(UserDTO dto) {

		int result = 0;

		try {

			openConn();

			sql = "select * from user where user_id = ?";

			pstmt = con.prepareStatement(sql);

			pstmt.setString(1, dto.getUser_id());

			rs = pstmt.executeQuery();

			if (rs.next()) { // 입력받은 아이디와 일치한 아이디가 DB에 존재할 경우
				if (dto.getUser_pwd().equals(rs.getString("user_pwd"))) { // 아이디가 존재하고, 입력받은 pwd가 일치 할 경우
					result = 1;
				} else { // 일치하는 아이디가 DB에 존재하지만 입력한 비밀번호가 틀린경우
					result = -1;
				}
			} else { // 입력받은 아이디가 DB에 존재하지 않을 경우.
				result = 0;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}

		return result;
	} // checkUserLogin(dto) end.

	// 로그인 페이지에서 유효성 검사를 통과 후 관련 회원 정보를 모두 불러오는 매서드.
	public UserDTO getUserContent(UserDTO dto) {

		UserDTO cont = new UserDTO();

		try {

			openConn();

			sql = "select * from user where user_id = ?";

			pstmt = con.prepareStatement(sql);

			pstmt.setString(1, dto.getUser_id());

			rs = pstmt.executeQuery();

			if (rs.next()) {
				cont.setUser_no(rs.getString("user_no"));
				cont.setUser_id(rs.getString("user_id"));
				cont.setUser_pwd(rs.getString("user_pwd"));
				cont.setUser_name(rs.getString("user_name"));
				cont.setUser_phone(rs.getString("user_phone"));
				cont.setUser_email(rs.getString("user_email"));
				cont.setUser_mileage(rs.getInt("user_mileage"));
				cont.setUser_grade(rs.getString("user_grade"));
				cont.setUser_regdate(rs.getString("user_regdate"));

			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}

		return cont;
	} // userContent(dto) end.

	// User_no 으로 회원정보를 조회하는 매서드(오버로딩).
	public UserDTO getUserContent(String user_no) {

		UserDTO cont = new UserDTO();

		try {

			openConn();

			sql = "select * from user where user_no = ?";

			pstmt = con.prepareStatement(sql);

			pstmt.setString(1, user_no);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				cont.setUser_no(rs.getString("user_no"));
				cont.setUser_id(rs.getString("user_id"));
				cont.setUser_pwd(rs.getString("user_pwd"));
				cont.setUser_name(rs.getString("user_name"));
				cont.setUser_phone(rs.getString("user_phone"));
				cont.setUser_email(rs.getString("user_email"));
				cont.setUser_mileage(rs.getInt("user_mileage"));
				cont.setUser_grade(rs.getString("user_grade"));
				cont.setUser_regdate(rs.getString("user_regdate"));

			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}

		return cont;
	} // userContent(user_no) end.

	// 회원가입 폼에서 id 중복체크 하는 매서드.
	public int checkUserId(String user_id) {

		int result = 0;

		try {

			openConn();

			sql = "select * from user where user_id = ?";

			pstmt = con.prepareStatement(sql);

			pstmt.setString(1, user_id);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				result = -1;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}

		return result;
	} // checkUserId(userId) end.

	// 관리자 로그인 유효성 검사 매서드.
	public int checkAdminLogin(AdminDTO dto) {

		int result = 0;

		try {

			openConn();

			sql = "select * from admin where admin_id = ?";

			pstmt = con.prepareStatement(sql);

			pstmt.setString(1, dto.getAdmin_id());

			rs = pstmt.executeQuery();

			if (rs.next()) { // 입력받은 아이디와 일치한 아이디가 DB에 존재할 경우
				if (dto.getAdmin_pwd().equals(rs.getString("admin_pwd"))) { // 아이디가 존재하고, 입력받은 pwd가 일치 할 경우
					result = 1;
				} else { // 일치하는 아이디가 DB에 존재하지만 입력한 비밀번호가 틀린경우
					result = -1;
				}
			} else { // 입력받은 아이디가 DB에 존재하지 않을 경우.
				result = 0;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}

		return result;
	} // checkAdminLogin(dto) end.

	// 관리자 로그인 페이지에서 유효성 검사를 통과 후 관련 관리자 정보를 모두 불러오는 매서드.
	public AdminDTO adminContent(AdminDTO dto) {

		AdminDTO cont = new AdminDTO();

		try {

			openConn();

			sql = "select * from admin where admin_id = ?";

			pstmt = con.prepareStatement(sql);

			pstmt.setString(1, dto.getAdmin_id());

			rs = pstmt.executeQuery();

			if (rs.next()) {
				cont.setAdmin_no(rs.getString("admin_no"));
				cont.setAdmin_id(rs.getString("admin_id"));
				cont.setAdmin_pwd(rs.getString("admin_pwd"));
				cont.setAdmin_name(rs.getString("admin_name"));
				cont.setAdmin_job(rs.getString("admin_job"));
				cont.setAdmin_phone(rs.getString("admin_phone"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}

		return cont;
	} // adminContent(dto) end.

	// 판매 요청 게시판에 글 업로드하는 메서드
	public int insertReqRegcar(ReqSellBoardDTO dto) {

		int result = 0, count = 0;

		try {
			openConn();

			// 글번호 카운트
			sql = "select max(req_sell_board_no) from req_sell_board";

			pstmt = con.prepareStatement(sql);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				count = rs.getInt(1);
			}

			// DB에 데이터 저장
			sql = "insert into req_sell_board values(?, ?, ?, ?, ?, ?, ?, default, null, ?, default)";

			pstmt = con.prepareStatement(sql);

			pstmt.setInt(1, count + 1);
			pstmt.setString(2, dto.getReq_sell_board_user_no());
			pstmt.setString(3, dto.getReq_sell_board_car_std_no());
			pstmt.setString(4, dto.getReq_sell_board_car_no());
			pstmt.setInt(5, dto.getReq_sell_board_car_distance());
			pstmt.setInt(6, dto.getReq_sell_board_car_price());
			pstmt.setString(7, dto.getReq_sell_board_car_detail());
			pstmt.setString(8, dto.getReq_sell_board_car_file());

			result = pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}

		return result;
	}

	// DB에 정보 저장 후 등록 요청 게시판 글번호 가져오는 메서드
	public int getReqSellBoardNo() {

		int result = 0;

		try {
			openConn();

			sql = "select max(req_sell_board_no) from req_sell_board";

			pstmt = con.prepareStatement(sql);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				result = rs.getInt(1);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}

		return result;
	}

	// DB에 정보 저장 후 등록 요청 게시판 요청일자 가져오는 메서드
	public String getReqSellBoardDate() {

		String result = null;

		try {

			openConn();

			sql = "select max(req_sell_board_date) from req_sell_board";

			pstmt = con.prepareStatement(sql);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				result = rs.getString(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}

		return result;
	}

	// 사용자의 중고차 판매 요청 리스트를 가져오는 매서드.
	public List<ReqSellBoardDTO> getReqSellBoardList() {

		List<ReqSellBoardDTO> list = new ArrayList<>();

		try {

			openConn();

			sql = "select * from req_sell_board where req_sell_board_status = false order by req_sell_board_no";

			pstmt = con.prepareStatement(sql);

			rs = pstmt.executeQuery();

			while (rs.next()) {

				ReqSellBoardDTO dto = new ReqSellBoardDTO();

				dto.setReq_sell_board_no(rs.getInt("req_sell_board_no"));
				dto.setReq_sell_board_user_no(rs.getString("req_sell_board_user_no"));
				dto.setReq_sell_board_car_std_no(rs.getString("req_sell_board_car_std_no"));
				dto.setReq_sell_board_car_no(rs.getString("req_sell_board_car_no"));
				dto.setReq_sell_board_car_distance(rs.getInt("req_sell_board_car_distance"));
				dto.setReq_sell_board_car_price(rs.getInt("req_sell_board_car_price"));
				dto.setReq_sell_board_car_detail(rs.getString("req_sell_board_car_detail"));
				dto.setReq_sell_board_date(rs.getString("req_sell_board_date"));
				dto.setReq_sell_board_update(rs.getString("req_sell_board_update"));
				dto.setReq_sell_board_car_file(rs.getString("req_sell_board_car_file"));
				dto.setReq_sell_board_status(rs.getBoolean("req_sell_board_status"));

				list.add(dto);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}

		return list;
	}

	// 해당 유저의 중고차 판매 요청 리스트를 가져오는 매서드.
	public List<ReqSellBoardDTO> getReqSellBoardList(String user_no) {

		List<ReqSellBoardDTO> list = new ArrayList<>();

		try {

			openConn();

			sql = "select * from req_sell_board where req_sell_board_user_no = ? order by req_sell_board_no desc";

			pstmt = con.prepareStatement(sql);

			pstmt.setString(1, user_no);

			rs = pstmt.executeQuery();

			while (rs.next()) {

				ReqSellBoardDTO dto = new ReqSellBoardDTO();

				dto.setReq_sell_board_no(rs.getInt("req_sell_board_no"));
				dto.setReq_sell_board_user_no(rs.getString("req_sell_board_user_no"));
				dto.setReq_sell_board_car_std_no(rs.getString("req_sell_board_car_std_no"));
				dto.setReq_sell_board_car_no(rs.getString("req_sell_board_car_no"));
				dto.setReq_sell_board_car_distance(rs.getInt("req_sell_board_car_distance"));
				dto.setReq_sell_board_car_price(rs.getInt("req_sell_board_car_price"));
				dto.setReq_sell_board_car_detail(rs.getString("req_sell_board_car_detail"));
				dto.setReq_sell_board_date(rs.getString("req_sell_board_date"));
				dto.setReq_sell_board_update(rs.getString("req_sell_board_update"));
				dto.setReq_sell_board_car_file(rs.getString("req_sell_board_car_file"));
				dto.setReq_sell_board_status(rs.getBoolean("req_sell_board_status"));

				list.add(dto);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}

		return list;
	}

	// 사용자별 판매(승인 전) 요청 게시글 카운팅하는 매서드.
	public int countReqSellBoardUserBefore(String user_no) {

		int cnt = 0;

		try {

			openConn();

			sql = "select count(req_sell_board_no) from req_sell_board where req_sell_board_user_no = ? and req_sell_board_status = false";

			pstmt = con.prepareStatement(sql);

			pstmt.setString(1, user_no);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				cnt = rs.getInt(1);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}
		return cnt;
	}

	// 사용자별 판매(승인 후) 요청 게시글 카운팅하는 매서드.
	public int countReqSellBoardUserAfter(String user_no) {

		int cnt = 0;

		try {

			openConn();

			sql = "select count(req_sell_board_no) from req_sell_board where req_sell_board_user_no = ? and req_sell_board_status = true";

			pstmt = con.prepareStatement(sql);

			pstmt.setString(1, user_no);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				cnt = rs.getInt(1);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}
		return cnt;
	}

	// 회원 수정 폼페이지에서 수정 할 정보를 user 테이블에 update하는 매서드.
	public int updateUserInfo(UserDTO dto) {

		int result = 0;

		try {

			openConn();

			sql = "update user set user_pwd = ?, user_phone = ?, user_email = ? where user_no = ?";

			pstmt = con.prepareStatement(sql);

			pstmt.setString(1, dto.getUser_pwd());
			pstmt.setString(2, dto.getUser_phone());
			pstmt.setString(3, dto.getUser_email());
			pstmt.setString(4, dto.getUser_no());

			result = pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConn(pstmt, con);
		}

		return result;
	} // updateUserInfo(dto) end.

	// 관리자에게 유저들의 리스트를 넘겨주는 매서드.
	public List<UserDTO> getUserList() {

		List<UserDTO> list = new ArrayList<>();

		try {
			openConn();

			sql = "select * from user order by user_no";

			pstmt = con.prepareStatement(sql);

			rs = pstmt.executeQuery();

			while (rs.next()) {

				UserDTO dto = new UserDTO();

				dto.setUser_no(rs.getString("user_no"));
				dto.setUser_id(rs.getString("user_id"));
				dto.setUser_name(rs.getString("user_name"));
				dto.setUser_phone(rs.getString("user_phone"));
				dto.setUser_email(rs.getString("user_email"));
				dto.setUser_mileage(rs.getInt("user_mileage"));
				dto.setUser_grade(rs.getString("user_grade"));
				dto.setUser_regdate(rs.getString("user_regdate"));

				list.add(dto);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}
		return list;
	}

	// 전체 Qna 리스트를 카운트하는 매서드.
	public int countQnaList() {
		int cnt = 0;

		try {

			openConn();

			sql = "select count(*) from qna_board";

			pstmt = con.prepareStatement(sql);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				cnt = rs.getInt(1);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}
		return cnt;
	} // countQnaList() end.

	// 사용자가 등록 한 Qna 리스트를 카운트하는 매서드.
	public int countQnaUserList(String user_id) {
		int cnt = 0;

		try {

			openConn();

			sql = "select count(*) from qna_board where qna_board_writer_id = ?";

			pstmt = con.prepareStatement(sql);

			pstmt.setString(1, user_id);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				cnt = rs.getInt(1);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}
		return cnt;
	} // countQnaUserList() end.

	// 관리자에게 QnA 리스트를 넘겨주는 매서드.
	public List<QnaBoardDTO> getQnaBoardList() {

		List<QnaBoardDTO> list = new ArrayList<QnaBoardDTO>();

		try {

			openConn();

			sql = "select * from qna_board order by qna_board_no";

			pstmt = con.prepareStatement(sql);

			rs = pstmt.executeQuery();

			while (rs.next()) {

				QnaBoardDTO dto = new QnaBoardDTO();

				dto.setQna_board_no(rs.getInt("qna_board_no"));
				dto.setQna_board_type(rs.getString("qna_board_type"));
				dto.setQna_board_writer_id(rs.getString("qna_board_writer_id"));
				dto.setQna_board_title(rs.getString("qna_board_title"));
				dto.setQna_board_cont(rs.getString("qna_board_cont"));
				dto.setQna_board_date(rs.getString("qna_board_date"));
				dto.setQna_board_update(rs.getString("qna_board_update"));
				dto.setQna_board_file(rs.getString("qna_board_file"));
				dto.setQna_board_status(rs.getBoolean("qna_board_status"));

				list.add(dto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}
		return list;
	} // getQnaBoardList() end.

	// 사용자가 등록한 QnA 리스트를 넘겨주는 매서드.
	public List<QnaBoardDTO> getQnaBoardUserList(String user_id) {

		List<QnaBoardDTO> list = new ArrayList<QnaBoardDTO>();

		try {

			openConn();

			sql = "select * from qna_board where qna_board_writer_id = ? order by qna_board_no";

			pstmt = con.prepareStatement(sql);

			pstmt.setString(1, user_id);

			rs = pstmt.executeQuery();

			while (rs.next()) {

				QnaBoardDTO dto = new QnaBoardDTO();

				dto.setQna_board_no(rs.getInt("qna_board_no"));
				dto.setQna_board_type(rs.getString("qna_board_type"));
				dto.setQna_board_writer_id(rs.getString("qna_board_writer_id"));
				dto.setQna_board_title(rs.getString("qna_board_title"));
				dto.setQna_board_cont(rs.getString("qna_board_cont"));
				dto.setQna_board_date(rs.getString("qna_board_date"));
				dto.setQna_board_update(rs.getString("qna_board_update"));
				dto.setQna_board_file(rs.getString("qna_board_file"));
				dto.setQna_board_status(rs.getBoolean("qna_board_status"));

				list.add(dto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}
		return list;
	} // getQnaBoardUserList() end.

	// 관리자 페이지에서 DB에 등록된 회사 정보를 가져오는 매서드.
	public CompanyDTO getCompanyInfo() {

		CompanyDTO dto = null;

		try {
			openConn();

			sql = "select * from company";

			pstmt = con.prepareStatement(sql);

			rs = pstmt.executeQuery();

			if (rs.next()) {

				dto = new CompanyDTO();

				dto.setCompany_business_no(rs.getString("company_business_no"));
				dto.setCompany_name(rs.getString("company_name"));
				dto.setCompany_ceo_name(rs.getString("company_ceo_name"));
				dto.setCompany_addr(rs.getString("company_addr"));
				dto.setCompany_found_date(rs.getString("company_found_date"));
				dto.setCompany_phone(rs.getString("company_phone"));
				dto.setCompany_account(rs.getString("company_account"));
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}

		return dto;
	} // getCompanyInfo() end.

	public ReqSellBoardDTO getReqSellBoardContent(int board_no) {

		ReqSellBoardDTO dto = null;

		try {
			openConn();

			sql = "select * from req_sell_board where req_sell_board_no = ?";

			pstmt = con.prepareStatement(sql);

			pstmt.setInt(1, board_no);

			rs = pstmt.executeQuery();

			if (rs.next()) {

				dto = new ReqSellBoardDTO();

				dto.setReq_sell_board_no(rs.getInt("req_sell_board_no"));
				dto.setReq_sell_board_user_no(rs.getString("req_sell_board_user_no"));
				dto.setReq_sell_board_car_std_no(rs.getString("req_sell_board_car_std_no"));
				dto.setReq_sell_board_car_no(rs.getString("req_sell_board_car_no"));
				dto.setReq_sell_board_car_distance(rs.getInt("req_sell_board_car_distance"));
				dto.setReq_sell_board_car_price(rs.getInt("req_sell_board_car_price"));
				dto.setReq_sell_board_car_detail(rs.getString("req_sell_board_car_detail"));
				dto.setReq_sell_board_date(rs.getString("req_sell_board_date"));
				dto.setReq_sell_board_update(rs.getString("req_sell_board_update"));
				dto.setReq_sell_board_car_file(rs.getString("req_sell_board_car_file"));
				dto.setReq_sell_board_status(rs.getBoolean("req_sell_board_status"));
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}

		return dto;
	}

	// 관리자가 수정한 회사 정보를 DB에 업데이트 하는 매서드.
	public int updateCompanyInfo(CompanyDTO dto) {

		int result = 0;

		try {

			openConn();

			sql = "update company set company_name = ?, company_ceo_name = ?, company_addr = ?, "
					+ "company_phone = ?, company_found_date = ?, company_account = ? where company_business_no = ?";

			pstmt = con.prepareStatement(sql);

			pstmt.setString(1, dto.getCompany_name());
			pstmt.setString(2, dto.getCompany_ceo_name());
			pstmt.setString(3, dto.getCompany_addr());
			pstmt.setString(4, dto.getCompany_phone());
			pstmt.setString(5, dto.getCompany_found_date());
			pstmt.setString(6, dto.getCompany_account());
			pstmt.setString(7, dto.getCompany_business_no());

			result = pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConn(pstmt, con);
		}
		return result;
	} // updateCompanyInfo(dto) end.

	// 판매 요청 승인하여 판매 게시판에 글 추가하는 메서드
	public int insertSellBoard(SellBoardDTO dto) {

		int result = 0, count = 0;

		try {
			openConn();

			sql = "select max(sell_board_no) from sell_board";

			pstmt = con.prepareStatement(sql);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				count = rs.getInt(1) + 1;
			}

			sql = "insert into sell_board values(?, ?, ?, ?, ?, ?, ?, ?, default, null, ?, default, default)";

			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, count);
			pstmt.setString(2, dto.getSell_board_admin_no());
			pstmt.setString(3, dto.getSell_board_seller_no());
			pstmt.setString(4, dto.getSell_board_car_std_no());
			pstmt.setString(5, dto.getSell_board_car_no());
			pstmt.setInt(6, dto.getSell_board_car_distance());
			pstmt.setInt(7, dto.getSell_board_car_price());
			pstmt.setString(8, dto.getSell_board_car_detail());
			pstmt.setString(9, dto.getSell_board_car_file());

			result = pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}

		return result;
	} // insertSellBoard(dto) end.

	// 판매 요청 게시판 상태를 변경하는 메서드
	public void changeReqSellBoardStatus(int board_no) {

		try {
			openConn();

			sql = "update req_sell_board set req_sell_board_status = true where req_sell_board_no = ?";

			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, board_no);

			pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConn(pstmt, con);
		}

	} // changeReqSellBoardStatus() end.

	// qna 글 삽입 메서드
	public int insertQna(QnaBoardDTO dto) {

		int result = 0, cnt = 0;

		try {
			openConn();

			sql = "select max(qna_board_no) from qna_board";

			pstmt = con.prepareStatement(sql);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				cnt = rs.getInt(1) + 1;
			}

			sql = "insert into qna_board values(?, ?, ?, ?, ?, default, null, ?, default)";

			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, cnt);
			pstmt.setString(2, dto.getQna_board_type());
			pstmt.setString(3, dto.getQna_board_writer_id());
			pstmt.setString(4, dto.getQna_board_title());
			pstmt.setString(5, dto.getQna_board_cont());
			pstmt.setString(6, dto.getQna_board_file());

			result = pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}

		return result;
	} // insertQna(dto) end.

	public void fkCheckOn() { // 무결성검사 시 외래키 검사하는 설정 켜기

		try {

			sql = "set foreign_key_checks = 1";

			pstmt = con.prepareStatement(sql);

			pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void fkCheckOff() { // 무결성검사 시 외래키 검사하는 설정 끄기

		try {

			sql = "set foreign_key_checks = 0";

			pstmt = con.prepareStatement(sql);

			pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// 유저 회원탈퇴를 하는 매서드.
	public int deleteUser(String user_no) {

		int result = 0;

		try {

			openConn();

			fkCheckOff();

			sql = "delete from user where user_no = ?";

			pstmt = con.prepareStatement(sql);

			pstmt.setString(1, user_no);

			result = pstmt.executeUpdate();

			fkCheckOn();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConn(pstmt, con);
		}

		return result;
	} // deleteUser(user_no) end.

	// 관리자가 공지사항을 등록하는 매서드.
	public int insertNotice(NoticeBoardDTO dto) {

		int result = 0, cnt = 0;

		try {

			openConn();

			sql = "select max(notice_board_no) from notice_board";

			pstmt = con.prepareStatement(sql);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				cnt = rs.getInt(1) + 1;
			}

			sql = "insert into notice_board values(?, ?, ?, ?, default, null, default, ?)";

			pstmt = con.prepareStatement(sql);

			pstmt.setInt(1, cnt);
			pstmt.setString(2, dto.getNotice_board_writer_id());
			pstmt.setString(3, dto.getNotice_board_title());
			pstmt.setString(4, dto.getNotice_board_cont());
			pstmt.setString(5, dto.getNotice_board_file());

			result = pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}

		return result;
	} // insertNotice(dto) end.

	// 공지사항의 갯수를 카운트하는 매서드.
	public int countNoticeList() {

		int cnt = 0;

		try {

			openConn();

			sql = "select count(*) from notice_board";

			pstmt = con.prepareStatement(sql);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				cnt = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}

		return cnt;
	} // countNoticeList() end.

	// 공지사항의 모든 글을 읽어오는 매서드.
	public List<NoticeBoardDTO> getNoticeList() {

		List<NoticeBoardDTO> list = new ArrayList<NoticeBoardDTO>();

		try {

			NoticeBoardDTO dto = null;

			openConn();

			sql = "select * from notice_board order by notice_board_no desc";

			pstmt = con.prepareStatement(sql);

			rs = pstmt.executeQuery();

			while (rs.next()) {

				dto = new NoticeBoardDTO();

				dto.setNotice_board_no(rs.getInt("notice_board_no"));
				dto.setNotice_board_writer_id(rs.getString("notice_board_writer_id"));
				dto.setNotice_board_title(rs.getString("notice_board_title"));
				dto.setNotice_board_cont(rs.getString("notice_board_cont"));
				dto.setNotice_board_date(rs.getString("notice_board_date"));
				dto.setNotice_board_update(rs.getString("notice_board_update"));
				dto.setNotice_board_hit(rs.getInt("notice_board_hit"));
				dto.setNotice_board_file(rs.getString("notice_board_file"));

				list.add(dto);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}

		return list;
	} // getNoticeList() end.

	// 페이징처리를 위한 공지사항의 글을 읽어오는 매서드.
	public List<NoticeBoardDTO> getNoticeList(int page, int rowsize) {

		List<NoticeBoardDTO> list = new ArrayList<NoticeBoardDTO>();

		// 해당 페이지에서 시작 번호
		int startNo = (page * rowsize) - (rowsize - 1);

		// 해당 페이지에서 끝 번호
		int endNo = (page * rowsize);

		try {

			NoticeBoardDTO dto = null;

			openConn();

			sql = "select * from (select @ROWN:=@ROWN+1 AS RN, nb.* " + "from notice_board nb, "
					+ "(select @ROWN:=0) as clear_RN " + "order by nb.notice_board_no desc) as subquery "
					+ "where RN >= ? and RN <= ?";

			pstmt = con.prepareStatement(sql);

			pstmt.setInt(1, startNo);
			pstmt.setInt(2, endNo);

			rs = pstmt.executeQuery();

			while (rs.next()) {

				dto = new NoticeBoardDTO();

				dto.setNotice_board_no(rs.getInt("notice_board_no"));
				dto.setNotice_board_writer_id(rs.getString("notice_board_writer_id"));
				dto.setNotice_board_title(rs.getString("notice_board_title"));
				dto.setNotice_board_cont(rs.getString("notice_board_cont"));
				dto.setNotice_board_date(rs.getString("notice_board_date"));
				dto.setNotice_board_update(rs.getString("notice_board_update"));
				dto.setNotice_board_hit(rs.getInt("notice_board_hit"));
				dto.setNotice_board_file(rs.getString("notice_board_file"));

				list.add(dto);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}

		return list;
	} // getNoticeList() end.

	// 관리자가 이벤트를 등록하는 매서드.
	public int insertEvent(EventBoardDTO dto) {
		int result = 0, cnt = 0;

		try {

			openConn();

			sql = "select max(event_board_no) from event_board";

			pstmt = con.prepareStatement(sql);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				cnt = rs.getInt(1) + 1;
			}

			sql = "insert into event_board values(?, ?, ?, ?, default, null, default, ?)";

			pstmt = con.prepareStatement(sql);

			pstmt.setInt(1, cnt);
			pstmt.setString(2, dto.getEvent_board_writer_id());
			pstmt.setString(3, dto.getEvent_board_title());
			pstmt.setString(4, dto.getEvent_board_cont());
			pstmt.setString(5, dto.getEvent_board_file());

			result = pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}

		return result;
	} // insertEvent(dto) end.

	// 이벤트의 갯수를 카운트하는 매서드.
	public int countEventList() {

		int cnt = 0;

		try {

			openConn();

			sql = "select count(*) from event_board";

			pstmt = con.prepareStatement(sql);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				cnt = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}

		return cnt;
	} // countEventList() end.

	// 이벤트의 모든 글을 읽어오는 매서드.
	public List<EventBoardDTO> getEventList() {

		List<EventBoardDTO> list = new ArrayList<EventBoardDTO>();

		try {

			EventBoardDTO dto = null;

			openConn();

			sql = "select * from event_board order by event_board_no desc";

			pstmt = con.prepareStatement(sql);

			rs = pstmt.executeQuery();

			while (rs.next()) {

				dto = new EventBoardDTO();

				dto.setEvent_board_no(rs.getInt("event_board_no"));
				dto.setEvent_board_writer_id(rs.getString("event_board_writer_id"));
				dto.setEvent_board_title(rs.getString("event_board_title"));
				dto.setEvent_board_cont(rs.getString("event_board_cont"));
				dto.setEvent_board_date(rs.getString("event_board_date"));
				dto.setEvent_board_update(rs.getString("event_board_update"));
				dto.setEvent_board_hit(rs.getInt("event_board_hit"));
				dto.setEvent_board_file(rs.getString("event_board_file"));

				list.add(dto);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}

		return list;
	} // getEventList() end.

	// 이벤트의 모든 글을 읽어오는 페이징 처리를 위한 매서드.
	public List<EventBoardDTO> getEventList(int page, int rowsize) {

		List<EventBoardDTO> list = new ArrayList<EventBoardDTO>();
		EventBoardDTO dto = null;

		// 해당 페이지에서 시작 번호
		int startNo = (page * rowsize) - (rowsize - 1);

		// 해당 페이지에서 끝 번호
		int endNo = (page * rowsize);

		try {

			openConn();

			sql = "select * from (select @ROWN:=@ROWN+1 AS RN, eb.* " + "from event_board eb, "
					+ "(select @ROWN:=0) as clear_RN " + "order by eb.event_board_no desc) as subquery "
					+ "where RN >= ? and RN <= ?";

			pstmt = con.prepareStatement(sql);

			pstmt.setInt(1, startNo);
			pstmt.setInt(2, endNo);

			rs = pstmt.executeQuery();

			while (rs.next()) {

				dto = new EventBoardDTO();

				dto.setEvent_board_no(rs.getInt("event_board_no"));
				dto.setEvent_board_writer_id(rs.getString("event_board_writer_id"));
				dto.setEvent_board_title(rs.getString("event_board_title"));
				dto.setEvent_board_cont(rs.getString("event_board_cont"));
				dto.setEvent_board_date(rs.getString("event_board_date"));
				dto.setEvent_board_update(rs.getString("event_board_update"));
				dto.setEvent_board_hit(rs.getInt("event_board_hit"));
				dto.setEvent_board_file(rs.getString("event_board_file"));

				list.add(dto);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}

		return list;
	} // getEventList(int page, int rowsize) end.

	// 메인페이지에서 최근 공지사항부터 5개 글까지 가져오는 매서드.
	public List<NoticeBoardDTO> printNoticeMain() {

		List<NoticeBoardDTO> list = new ArrayList<NoticeBoardDTO>();

		try {

			openConn();

			sql = "select * from notice_board order by notice_board_no desc limit 5";

			pstmt = con.prepareStatement(sql);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				NoticeBoardDTO dto = new NoticeBoardDTO();

				dto.setNotice_board_no(rs.getInt("notice_board_no"));
				dto.setNotice_board_writer_id(rs.getString("notice_board_writer_id"));
				dto.setNotice_board_title(rs.getString("notice_board_title"));
				dto.setNotice_board_cont(rs.getString("notice_board_cont"));
				dto.setNotice_board_date(rs.getString("notice_board_date"));
				dto.setNotice_board_update(rs.getString("notice_board_update"));
				dto.setNotice_board_hit(rs.getInt("notice_board_hit"));
				dto.setNotice_board_file(rs.getString("notice_board_file"));

				list.add(dto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}
		return list;
	} // printNoticeMain() end.

	// 회원가입 마일리지 적립 메서드
	public void setMileageSignup() {

		int mileage = 10000; // 회원가입 시 적립할 마일리지
		String mileage_cont = "회원가입 적립금"; // 마일리지 적립 내용
		String mileage_type = mileage >= 0 ? "+" : "-"; // +, - 구분
		String user_no = null;
		int user_mileage = 0;
		String user_grade = null;

		try {
			openConn();

			// 회원가입과 동시에 적립할 대상의 회원번호를 가져오기
			sql = "select max(user_no) from user";

			pstmt = con.prepareStatement(sql);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				user_no = rs.getString(1);
			}

			// 회원 DB에 마일리지 적립
			sql = "update user set user_mileage = user_mileage + ? where user_no like ?";

			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, mileage);
			pstmt.setString(2, user_no);

			pstmt.executeUpdate();

			// 마일리지 적립 후 회원의 마일리지 상태 가져오기
			sql = "select user_mileage from user where user_no like ?";

			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, user_no);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				user_mileage = rs.getInt(1);
			}

			// 마일리지 상태에 따라 등급을 나눠주는 메서드
			user_grade = StaticArea.getUserGrade(user_mileage);

			// 마일리지 상태에 맞게 등급을 user 테이블에 적용시켜주는 메서드
			sql = "update user set user_grade = ? where user_no like ?";

			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, user_grade);
			pstmt.setString(2, user_no);

			pstmt.executeUpdate();

			// 마일리지 테이블에 마일리지 적립 내역 저장
			sql = "insert into mileage values(?, default, ?, ?, ?, ?, ?)";

			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, user_no);
			pstmt.setString(2, mileage_cont);
			pstmt.setString(3, mileage_type);
			pstmt.setInt(4, mileage);
			pstmt.setInt(5, user_mileage);
			pstmt.setString(6, user_grade);

			pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}
	}

	// 회원번호로 마일리지 내역 조회하는 메서드
	public List<MileageDTO> getUserMileage(String user_no) {

		List<MileageDTO> list = new ArrayList<>();

		try {
			openConn();

			sql = "select * from mileage where mileage_user_no like ? order by mileage_date";

			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, user_no);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				MileageDTO dto = new MileageDTO();

				dto.setMileage_user_no(rs.getString("mileage_user_no"));
				dto.setMileage_date(rs.getString("mileage_date"));
				dto.setMileage_cont(rs.getString("mileage_cont"));
				dto.setMileage_type(rs.getString("mileage_type"));
				dto.setMileage_amount(rs.getInt("mileage_amount"));
				dto.setMileage_status(rs.getInt("mileage_status"));
				dto.setMileage_grade(rs.getString("mileage_grade"));

				list.add(dto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}

		return list;
	}

	// 로그인 마일리지 적립 메서드
	public void setMileageLogin(String user_id) {

		int mileage = 100; // 로그인 시 적립할 마일리지
		String mileage_cont = "로그인 적립금"; // 적립 내용
		String mileage_type = mileage >= 0 ? "+" : "-"; // +, - 구분
		String user_no = null;
		int user_mileage = 0;
		String user_grade = null;

		try {
			openConn();

			// 로그인 시 입력받은 아이디로 마일리지 적립
			sql = "update user set user_mileage = user_mileage + ? where user_id like ?";

			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, mileage);
			pstmt.setString(2, user_id);

			pstmt.executeUpdate();

			// 회원 아이디로 회원번호, 적립 후 마일리지 조회
			sql = "select user_no, user_mileage from user where user_id like ?";

			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, user_id);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				user_no = rs.getString("user_no");
				user_mileage = rs.getInt("user_mileage");
			}

			// 마일리지에 따라 등급을 나누는 메서드
			user_grade = StaticArea.getUserGrade(user_mileage);

			// 등급 변경 후 user 테이블의 등급 변경
			sql = "update user set user_grade = ? where user_no like ?";

			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, user_grade);
			pstmt.setString(2, user_no);

			pstmt.executeUpdate();

			// mileage 테이블에 마일리지 적립 내역 저장
			sql = "insert into mileage values(?, default, ?, ?, ?, ?, ?)";

			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, user_no);
			pstmt.setString(2, mileage_cont);
			pstmt.setString(3, mileage_type);
			pstmt.setInt(4, mileage);
			pstmt.setInt(5, user_mileage);
			pstmt.setString(6, user_grade);

			pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}
	}

	// 로그인 시 로그 남기는 메서드
	public void remainLogin(String user_id) {

		String user_no = null;
		String log_type = "login";

		try {
			openConn();

			sql = "select user_no from user where user_id like ?";

			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, user_id);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				user_no = rs.getString(1);
			}

			sql = "insert into log values(default, ?, ?, ?)";

			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, user_no);
			pstmt.setString(2, user_id);
			pstmt.setString(3, log_type);

			pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}
	}

	// 로그인 시 로그 남기는 메서드
	public void remainAdminLogin(String id) {

		String admin_no = null;
		String log_type = "login";

		try {
			openConn();

			sql = "select admin_no from admin where admin_id like ?";

			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				admin_no = rs.getString(1);
			}

			sql = "insert into log values(default, ?, ?, ?)";

			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, admin_no);
			pstmt.setString(2, id);
			pstmt.setString(3, log_type);

			pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}
	}

	// 회원가입 시 로그 남기는 메서드
	public void remainSignup() {

		String user_no = null;
		String user_id = null;
		String log_type = "signup";

		try {
			openConn();

			sql = "select max(user_no) from user";

			pstmt = con.prepareStatement(sql);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				user_no = rs.getString(1);
			}

			sql = "select user_id from user where user_no = ?";

			pstmt = con.prepareStatement(sql);

			pstmt.setString(1, user_no);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				user_id = rs.getString(1);
			}

			sql = "insert into log values(default, ?, ?, ?)";

			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, user_no);
			pstmt.setString(2, user_id);
			pstmt.setString(3, log_type);

			pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConn(pstmt, con);
		}
	}

	// 오늘 로그인 기록이 있는지 확인하는 메서드
	public String checkLoginToday(String user_id) {

		String log_type = "login";
		String result = null;

		try {
			openConn();

			// 회원번호의 가장 최근 로그인 기록 조회
			sql = "select max(date_format(log_date, '%Y%m%d')) from log where log_user_id like ? and log_type like ?";

			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, user_id);
			pstmt.setString(2, log_type);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				result = rs.getString(1);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}

		return result;
	}

	// 사용자 로그아웃 시 log 남기는 매서드.
	public void remainLogout(String user_no) {

		String user_id = null;
		String log_type = "logout";

		try {
			openConn();

			sql = "select user_id from user where user_no like ?";

			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, user_no);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				user_id = rs.getString(1);
			}

			sql = "insert into log values(default, ?, ?, ?)";

			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, user_no);
			pstmt.setString(2, user_id);
			pstmt.setString(3, log_type);

			pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}
	}

	// 사용자 로그아웃 시 log 남기는 매서드.
	public void remainAdminLogout(String admin_no) {

		String admin_id = null;
		String log_type = "logout";

		try {
			openConn();

			sql = "select admin_id from admin where admin_no like ?";

			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, admin_no);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				admin_id = rs.getString(1);
			}

			sql = "insert into log values(default, ?, ?, ?)";

			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, admin_no);
			pstmt.setString(2, admin_id);
			pstmt.setString(3, log_type);

			pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}
	}

	// 공지사항 글 번호에 해당하는 공지사항 글 정보를 가져오는 매서드.
	public NoticeBoardDTO getNoticeBoardContent(int no) {

		NoticeBoardDTO dto = null;

		try {

			openConn();

			sql = "select * from notice_board where notice_board_no = ?";

			pstmt = con.prepareStatement(sql);

			pstmt.setInt(1, no);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				dto = new NoticeBoardDTO();

				dto.setNotice_board_no(rs.getInt("notice_board_no"));
				dto.setNotice_board_writer_id(rs.getString("notice_board_writer_id"));
				dto.setNotice_board_title(rs.getString("notice_board_title"));
				dto.setNotice_board_cont(rs.getString("notice_board_cont"));
				dto.setNotice_board_date(rs.getString("notice_board_date"));
				dto.setNotice_board_update(rs.getString("notice_board_update"));
				dto.setNotice_board_hit(rs.getInt("notice_board_hit"));
				dto.setNotice_board_file(rs.getString("notice_board_file"));

			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}

		return dto;
	} // getNoticeBoardContent() end.

	public int updateNotice(NoticeBoardDTO dto) {

		int result = 0;

		try {

			openConn();

			sql = "update notice_board set notice_board_writer_id = ?, notice_board_title = ?, "
					+ "notice_board_cont = ?, notice_board_update = now() "
					+ "where notice_board_no = ?";

			pstmt = con.prepareStatement(sql);

			pstmt.setString(1, dto.getNotice_board_writer_id());
			pstmt.setString(2, dto.getNotice_board_title());
			pstmt.setString(3, dto.getNotice_board_cont());
			pstmt.setInt(4, dto.getNotice_board_no());

			result = pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConn(pstmt, con);
		}
		return result;
	}

	// 관리자가 해당 공지사항 글을 삭제하는 매서드.
	public int deleteNoticeBoard(int no) {

		int result = 0;

		try {

			openConn();

			sql = "delete from notice_board where notice_board_no = ?";

			pstmt = con.prepareStatement(sql);

			pstmt.setInt(1, no);

			result = pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConn(pstmt, con);
		}

		return result;
	}

	// 판매후기의 갯수를 카운트 하는 메서드
	public int countSellReviewBoardList() {

		int cnt = 0;

		try {
			openConn();

			sql = "select count(*) from sell_review_board";

			pstmt = con.prepareStatement(sql);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				cnt = rs.getInt(1);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}

		return cnt;

	} // countSellReviewBoardList() end

	// 판매 후기 게시판의 모든 글을 불러오는 메서드
	public List<SellReviewBoardDTO> getSellReviewList() {

		List<SellReviewBoardDTO> list = new ArrayList<SellReviewBoardDTO>();

		try {
			SellReviewBoardDTO dto = null;

			openConn();

			sql = "select * from sell_review_board order by sell_review_board_no desc";

			pstmt = con.prepareStatement(sql);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				dto = new SellReviewBoardDTO();

				dto.setSell_review_board_no(rs.getInt("sell_review_board_no"));
				dto.setSell_review_board_writer_id(rs.getString("sell_review_board_writer_id"));
				dto.setSell_review_board_title(rs.getString("sell_review_board_title"));
				dto.setSell_review_board_cont(rs.getString("sell_review_board_cont"));
				dto.setSell_review_board_date(rs.getString("sell_review_board_date"));
				dto.setSell_review_board_update(rs.getString("sell_review_board_update"));
				dto.setSell_review_board_hit(rs.getInt("sell_review_board_hit"));
				dto.setSell_review_board_file(rs.getString("sell_review_board_file"));

				list.add(dto);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}

		return list;

	} // List<SellReviewBoardDTO> getSellReviewList() end

	// 거래내역 정보 가져오는 매서드
	public TransactionDTO getTransactionInfo(int no) {

		TransactionDTO dto = null;

		try {

			openConn();

			sql = "select * from transaction where transaction_no = ?";

			pstmt = con.prepareStatement(sql);

			pstmt.setInt(1, no);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				dto = new TransactionDTO();

				dto.setTransaction_no(rs.getInt("transaction_no"));
				dto.setTransaction_seller_no(rs.getString("transaction_seller_no"));
				dto.setTransaction_buyer_no(rs.getString("transaction_buyer_no"));
				dto.setTransaction_dealer_no(rs.getString("transaction_dealer_no"));
				dto.setTransaction_car_std_no(rs.getString("transaction_car_std_no"));
				dto.setTransaction_car_no(rs.getString("transaction_car_no"));
				dto.setTransaction_car_distance(rs.getInt("transaction_car_distance"));
				dto.setTransaction_car_price(rs.getInt("transaction_car_price"));
				dto.setTransaction_date(rs.getString("transaction_date"));
				dto.setTransaction_sell_review_status(rs.getBoolean("transaction_sell_review_status"));
				dto.setTransaction_buy_review_status(rs.getBoolean("transaction_buy_review_status"));

			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}

		return dto;
	} // TransactionDTO getTransactionInfo(int no) end

	// 판매내역 리스트 불러오는 매서드
	public List<TransactionDTO> getTransactionSellList(String no) {

		List<TransactionDTO> list = new ArrayList<TransactionDTO>();

		try {
			openConn();

			sql = "select * from transaction where transaction_seller_no = ?";

			pstmt = con.prepareStatement(sql);

			pstmt.setString(1, no);

			rs = pstmt.executeQuery();

			while (rs.next()) {

				TransactionDTO dto = new TransactionDTO();

				dto.setTransaction_no(rs.getInt("transaction_no"));
				dto.setTransaction_seller_no(rs.getString("transaction_seller_no"));
				dto.setTransaction_buyer_no(rs.getString("transaction_buyer_no"));
				dto.setTransaction_dealer_no(rs.getString("transaction_dealer_no"));
				dto.setTransaction_car_std_no(rs.getString("transaction_car_std_no"));
				dto.setTransaction_car_no(rs.getString("transaction_car_no"));
				dto.setTransaction_car_distance(rs.getInt("transaction_car_distance"));
				dto.setTransaction_car_price(rs.getInt("transaction_car_price"));
				dto.setTransaction_date(rs.getString("transaction_date"));
				dto.setTransaction_sell_review_status(rs.getBoolean("transaction_sell_review_status"));
				dto.setTransaction_buy_review_status(rs.getBoolean("transaction_buy_review_status"));

				list.add(dto);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}

		return list;

	} // List<TransactionDTO> getTransactionList() end

	// 구매내역 리스트 불러오는 매서드
	public List<TransactionDTO> getTransactionBuyList(String no) {

		List<TransactionDTO> list = new ArrayList<TransactionDTO>();

		try {
			openConn();

			sql = "select * from transaction where transaction_buyer_no = ?";

			pstmt = con.prepareStatement(sql);

			pstmt.setString(1, no);

			rs = pstmt.executeQuery();

			while (rs.next()) {

				TransactionDTO dto = new TransactionDTO();

				dto.setTransaction_no(rs.getInt("transaction_no"));
				dto.setTransaction_seller_no(rs.getString("transaction_seller_no"));
				dto.setTransaction_buyer_no(rs.getString("transaction_buyer_no"));
				dto.setTransaction_dealer_no(rs.getString("transaction_dealer_no"));
				dto.setTransaction_car_std_no(rs.getString("transaction_car_std_no"));
				dto.setTransaction_car_no(rs.getString("transaction_car_no"));
				dto.setTransaction_car_distance(rs.getInt("transaction_car_distance"));
				dto.setTransaction_car_price(rs.getInt("transaction_car_price"));
				dto.setTransaction_date(rs.getString("transaction_date"));
				dto.setTransaction_sell_review_status(rs.getBoolean("transaction_sell_review_status"));
				dto.setTransaction_buy_review_status(rs.getBoolean("transaction_buy_review_status"));

				list.add(dto);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}

		return list;

	} // List<TransactionDTO> getTransactionList() end

	// 사용자가 판매한 매물들을 카운트 하는 매서드
	public int countTransactionSell(String no) {

		int cnt = 0;

		try {

			openConn();

			sql = "select count(*) from transaction where transaction_seller_no = ?";

			pstmt = con.prepareStatement(sql);

			pstmt.setString(1, no);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				cnt = rs.getInt(1);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}
		return cnt;
	} // countTransactionSell(String no) end.

	// 사용자가 판매한 매물들을 카운트 하는 매서드
	public int countTransactionBuy(String no) {

		int cnt = 0;

		try {

			openConn();

			sql = "select count(*) from transaction where transaction_buyer_no = ?";

			pstmt = con.prepareStatement(sql);

			pstmt.setString(1, no);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				cnt = rs.getInt(1);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}
		return cnt;
	} // countTransactionBuy(String no) end.

	// 판매등록 요청 글을 삭제하는 매서드.
	public int deleteMycarInfo(int board_no) {

		int result = 0;

		try {

			openConn();

			sql = "delete from req_sell_board where req_sell_board_no = ?";

			pstmt = con.prepareStatement(sql);

			pstmt.setInt(1, board_no);

			result = pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConn(pstmt, con);
		}

		return result;
	} // deleteMycarInfo(int board_no) end.

	// 사용자의 판매 요청글 수정사항을 DB에 적용하는 매서드.
	public int updateReqSellBoard(ReqSellBoardDTO dto) {

		int result = 0;

		try {

			openConn();

			sql = "update req_sell_board set req_sell_board_car_distance = ?, "
					+ "req_sell_board_car_price = ?, req_sell_board_car_detail = ?, "
					+ "req_sell_board_update = now(), req_sell_board_car_file = ? " + "where req_sell_board_no = ?";

			pstmt = con.prepareStatement(sql);

			pstmt.setInt(1, dto.getReq_sell_board_car_distance());
			pstmt.setInt(2, dto.getReq_sell_board_car_price());
			pstmt.setString(3, dto.getReq_sell_board_car_detail());
			pstmt.setString(4, dto.getReq_sell_board_car_file());
			pstmt.setInt(5, dto.getReq_sell_board_no());

			result = pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConn(pstmt, con);
		}

		return result;
	} // updateReqSellBoard(ReqSellBoardDTO dto) end.

	// 이벤트 글번호에 해당하는 상세내용 확인하는 메서드
	public EventBoardDTO getEventBoardContent(int no) {

		EventBoardDTO dto = null;

		try {
			openConn();

			sql = "select * from event_board where event_board_no = ?";

			pstmt = con.prepareStatement(sql);

			pstmt.setInt(1, no);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				dto = new EventBoardDTO();

				dto.setEvent_board_no(rs.getInt("event_board_no"));
				dto.setEvent_board_writer_id(rs.getString("event_board_writer_id"));
				dto.setEvent_board_title(rs.getString("event_board_title"));
				dto.setEvent_board_cont(rs.getString("event_board_cont"));
				dto.setEvent_board_date(rs.getString("event_board_date"));
				dto.setEvent_board_update(rs.getString("event_board_update"));
				dto.setEvent_board_hit(rs.getInt("event_board_hit"));
				dto.setEvent_board_file(rs.getString("event_board_file"));

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}

		return dto;
	} // EventBoardDTO getEventBoardContent(int no) end

	// 관리자가 이벤트 수정하는 메서드
	public int updateEvent(EventBoardDTO dto) {

		int result = 0;

		try {
			openConn();

			sql = "update event_board set event_board_writer_id = ?, event_board_title = ?, "
					+ "event_board_cont = ?, event_board_update = now() "
					+ "where event_board_no = ?";

			pstmt = con.prepareStatement(sql);

			pstmt.setString(1, dto.getEvent_board_writer_id());
			pstmt.setString(2, dto.getEvent_board_title());
			pstmt.setString(3, dto.getEvent_board_cont());
			pstmt.setInt(4, dto.getEvent_board_no());

			result = pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConn(pstmt, con);
		}

		return result;
	} // updateEvent(EventBoardDTO dto) end

	// 이벤트 삭제하는 메서드
	public int deleteEventBoard(int no) {

		int result = 0;

		try {

			openConn();

			sql = "delete from event_board where event_board_no = ?";

			pstmt = con.prepareStatement(sql);

			pstmt.setInt(1, no);

			result = pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConn(pstmt, con);
		}

		return result;
	} // deleteEventBoard(int no) end

	// 차 제조사 카운트 하는 매서드.
	public List<Integer> getCountCompany(List<String> companyList) {

		List<Integer> resultList = new ArrayList<>();

		try {
			openConn();

			sql = "select count(*) from sell_board where sell_board_car_std_no regexp ?";

			for (String i : companyList) {
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, i);

				rs = pstmt.executeQuery();
				if (rs.next()) {
					resultList.add(rs.getInt(1));
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}

		return resultList;
	}

	public int getCarListAllCount() {

		int result = 0;

		try {
			openConn();

			sql = "select count(*) from sell_board";

			pstmt = con.prepareStatement(sql);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				result = rs.getInt(1);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}

		return result;
	}

	public int insertSellReview(SellReviewBoardDTO dto, int t_no) {

		int result = 0, cnt = 0;

		try {
			openConn();

			sql = "select max(sell_review_board_no) from sell_review_board";

			pstmt = con.prepareStatement(sql);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				cnt = rs.getInt(1) + 1;
			}

			sql = "insert into sell_review_board values(?, ?, ?, ?, default, null, default, ?, ?)";

			pstmt = con.prepareStatement(sql);

			pstmt.setInt(1, cnt);
			pstmt.setString(2, dto.getSell_review_board_writer_id());
			pstmt.setString(3, dto.getSell_review_board_title());
			pstmt.setString(4, dto.getSell_review_board_cont());
			pstmt.setString(5, dto.getSell_review_board_file());
			pstmt.setInt(6, t_no);

			result = pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}
		return result;
	} // insertSellReview(SellReviewBoardDTO dto)

	// 판매자가 판매후기를 쓰면 transaction 테이블의 sell_status를 true 로 바꾸는 매서드.
	public void changeTransactionSellStatus(int t_no) {

		try {

			openConn();

			sql = "update transaction set transaction_sell_review_status = true where transaction_no = ?";

			pstmt = con.prepareStatement(sql);

			pstmt.setInt(1, t_no);

			pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConn(pstmt, con);
		}

	} // changeTransactionSellStatus(int t_no) end.

	// 구매자가 구매후기를 쓰면 transaction 테이블의 buy_status를 true 로 바꾸는 매서드.
	public void changeTransactionBuyStatus(int t_no) {

		try {

			openConn();

			sql = "update transaction set transaction_buy_review_status = true where transaction_no = ?";

			pstmt = con.prepareStatement(sql);

			pstmt.setInt(1, t_no);

			pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConn(pstmt, con);
		}

	} // changeTransactionSellStatus(int t_no) end.

	public void changeTransactionSell_Status(int t_no) {

		try {

			openConn();

			sql = "update transaction set transaction_sell_review_status = false where transaction_no = ?";

			pstmt = con.prepareStatement(sql);

			pstmt.setInt(1, t_no);

			pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConn(pstmt, con);
		}

	} // changeTransactionSellStatus(int t_no) end.

	public void changeTransactionBuy_Status(int t_no) {

		try {

			openConn();

			sql = "update transaction set transaction_buy_review_status = false where transaction_no = ?";

			pstmt = con.prepareStatement(sql);

			pstmt.setInt(1, t_no);

			pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConn(pstmt, con);
		}

	} // changeTransactionSellStatus(int t_no) end.

	public int insertBuyReview(BuyReviewBoardDTO dto, int t_no) {

		int result = 0, cnt = 0;

		try {

			openConn();

			sql = "select max(buy_review_board_no) from buy_review_board";

			pstmt = con.prepareStatement(sql);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				cnt = rs.getInt(1) + 1;
			}

			sql = "insert into buy_review_board values(?, ?, ?, ?, default, null, ?, ?, ?)";

			pstmt = con.prepareStatement(sql);

			pstmt.setInt(1, cnt);
			pstmt.setString(2, dto.getBuy_review_board_writer_id());
			pstmt.setString(3, dto.getBuy_review_board_title());
			pstmt.setString(4, dto.getBuy_review_board_cont());
			pstmt.setInt(5, dto.getBuy_review_board_hit());
			pstmt.setString(6, dto.getBuy_review_board_file());
			pstmt.setInt(7, t_no);

			result = pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);

		}
		return result;
	}


	// 판매 후기 글 번호에 해당하는 상세 내용 확인하는 매서드
	public SellReviewBoardDTO getSellReviewContent(int no) {

		SellReviewBoardDTO dto = null;

		try {

			openConn();

			sql = "select * from sell_review_board where sell_review_board_no = ?";

			pstmt = con.prepareStatement(sql);

			pstmt.setInt(1, no);

			rs = pstmt.executeQuery();

			if (rs.next()) {

				dto = new SellReviewBoardDTO();

				dto.setSell_review_board_no(rs.getInt("sell_review_board_no"));
				dto.setSell_review_board_writer_id(rs.getString("sell_review_board_writer_id"));
				dto.setSell_review_board_title(rs.getString("sell_review_board_title"));
				dto.setSell_review_board_cont(rs.getString("sell_review_board_cont"));
				dto.setSell_review_board_date(rs.getString("sell_review_board_date"));
				dto.setSell_review_board_update(rs.getString("sell_review_board_update"));
				dto.setSell_review_board_hit(rs.getInt("sell_review_board_hit"));
				dto.setSell_review_transaction_no(rs.getInt("sell_review_transaction_no"));
				dto.setSell_review_board_file(rs.getString("sell_review_board_file")); // 추가

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}

		return dto;
	}

	// 자유게시판 글 갯수 확인하는 매서드
	public int countCommunityBoardList() {

		int cnt = 0;

		try {

			openConn();

			sql = "select count(*) from community_board";

			pstmt = con.prepareStatement(sql);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				cnt = rs.getInt(1);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}

		return cnt;
	} // int countCommunityBoardList()

	// 자유게시판 글 불러오는 매서드
	public List<CommunityBoardDTO> getCommunityBoardList() {

		List<CommunityBoardDTO> list = new ArrayList<CommunityBoardDTO>();

		try {

			CommunityBoardDTO dto = null;

			openConn();

			sql = "select * from community_board order by community_board_no desc";

			pstmt = con.prepareStatement(sql);

			rs = pstmt.executeQuery();

			while (rs.next()) {

				dto = new CommunityBoardDTO();

				dto.setCommunity_board_no(rs.getInt("community_board_no"));
				dto.setCommunity_board_writer_id(rs.getString("community_board_writer_id"));
				dto.setCommunity_board_title(rs.getString("community_board_title"));
				dto.setCommunity_board_cont(rs.getString("community_board_cont"));
				dto.setCommunity_board_date(rs.getString("community_board_date"));
				dto.setCommunity_board_update(rs.getString("community_board_update"));
				dto.setCommunity_board_hit(rs.getInt("community_board_hit"));

				list.add(dto);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}

		return list;
	} // List<CommunityBoardDTO> getCommunityBoardList() end

	// 자유게시판 상세내용 확인하는 매서드
	public CommunityBoardDTO getCommunityBoardContent(int no) {

		CommunityBoardDTO dto = null;

		try {

			openConn();

			sql = "select * from community_board where community_board_no = ?";

			pstmt = con.prepareStatement(sql);

			pstmt.setInt(1, no);

			rs = pstmt.executeQuery();

			if (rs.next()) {

				dto = new CommunityBoardDTO();

				dto.setCommunity_board_no(rs.getInt("community_board_no"));
				dto.setCommunity_board_writer_id(rs.getString("community_board_writer_id"));
				dto.setCommunity_board_title(rs.getString("community_board_title"));
				dto.setCommunity_board_cont(rs.getString("community_board_cont"));
				dto.setCommunity_board_date(rs.getString("community_board_date"));
				dto.setCommunity_board_update(rs.getString("community_board_update"));
				dto.setCommunity_board_hit(rs.getInt("community_board_hit"));

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}

		return dto;
	} // CommunityBoardDTO getCommunityBoardContent(int no) end

	// 자유게시판 글 등록하는 매서드
	public int insertCommunity(CommunityBoardDTO dto) {

		int result = 0, cnt = 0;

		try {
			openConn();

			sql = "select max(community_board_no) from community_board";

			pstmt = con.prepareStatement(sql);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				cnt = rs.getInt(1) + 1;

			}

			sql = "insert into community_board values(?,?,?,?, default, null,?)";

			pstmt = con.prepareStatement(sql);

			pstmt.setInt(1, cnt);
			pstmt.setString(2, dto.getCommunity_board_writer_id());
			pstmt.setString(3, dto.getCommunity_board_title());
			pstmt.setString(4, dto.getCommunity_board_cont());
			pstmt.setInt(5, dto.getCommunity_board_hit());

			result = pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}

		return result;
	} // insertCommunity(CommunityBoardDTO dto)

	// 자유게시판 글 수정하는 매서드
	public int updateCommunity(CommunityBoardDTO dto) {

		int result = 0;

		try {
			openConn();

			sql = "update community_board set community_board_title = ?, community_board_cont = ?, community_board_update = now() where community_board_no = ?";

			pstmt = con.prepareStatement(sql);

			pstmt.setString(1, dto.getCommunity_board_title());
			pstmt.setString(2, dto.getCommunity_board_cont());
			pstmt.setInt(3, dto.getCommunity_board_no());

			result = pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConn(pstmt, con);
		}

		return result;
	} // int updateCommunity(CommunityBoardDTO dto) end

	// 공지사항 게시판 검색 기능 매서드.
	public List<NoticeBoardDTO> searchNoticeList(String notice_field, String notice_keyword, int page, int rowsize) {

		List<NoticeBoardDTO> list = new ArrayList<NoticeBoardDTO>();

		int startNo = (page * rowsize) - (rowsize - 1);

		int endNo = (page * rowsize);

		openConn();

		if (notice_field.equals("전체")) {

			try {

				sql = "select * from (select @ROWN:=@ROWN+1 AS RN, " + "nb.* from notice_board nb, "
						+ "(select @ROWN:=0) as clear_RN where " + "notice_board_title like ? or "
						+ "notice_board_cont like ? " + "order by nb.notice_board_no desc) as subquery "
						+ "where RN >= ? and RN <= ?";

				pstmt = con.prepareStatement(sql);

				pstmt.setString(1, "%" + notice_keyword + "%");
				pstmt.setString(2, "%" + notice_keyword + "%");
				pstmt.setInt(3, startNo);
				pstmt.setInt(4, endNo);

				rs = pstmt.executeQuery();

				while (rs.next()) {

					NoticeBoardDTO dto = new NoticeBoardDTO();

					dto.setNotice_board_no(rs.getInt("notice_board_no"));
					dto.setNotice_board_writer_id(rs.getString("notice_board_writer_id"));
					dto.setNotice_board_title(rs.getString("notice_board_title"));
					dto.setNotice_board_cont(rs.getString("notice_board_cont"));
					dto.setNotice_board_date(rs.getString("notice_board_date"));
					dto.setNotice_board_update(rs.getString("notice_board_update"));
					dto.setNotice_board_hit(rs.getInt("notice_board_hit"));
					dto.setNotice_board_file(rs.getString("notice_board_file"));

					list.add(dto);

				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				closeConn(rs, pstmt, con);
			}

		} else if (notice_field.equals("제목")) {

			try {

				sql = "select * from (select @ROWN:=@ROWN+1 AS RN, " + "nb.* from notice_board nb, "
						+ "(select @ROWN:=0) as clear_RN where " + "notice_board_title like ? "
						+ "order by nb.notice_board_no desc) as subquery " + "where RN >= ? and RN <= ?";

				pstmt = con.prepareStatement(sql);

				pstmt.setString(1, "%" + notice_keyword + "%");
				pstmt.setInt(2, startNo);
				pstmt.setInt(3, endNo);

				rs = pstmt.executeQuery();

				while (rs.next()) {

					NoticeBoardDTO dto = new NoticeBoardDTO();

					dto.setNotice_board_no(rs.getInt("notice_board_no"));
					dto.setNotice_board_writer_id(rs.getString("notice_board_writer_id"));
					dto.setNotice_board_title(rs.getString("notice_board_title"));
					dto.setNotice_board_cont(rs.getString("notice_board_cont"));
					dto.setNotice_board_date(rs.getString("notice_board_date"));
					dto.setNotice_board_update(rs.getString("notice_board_update"));
					dto.setNotice_board_hit(rs.getInt("notice_board_hit"));
					dto.setNotice_board_file(rs.getString("notice_board_file"));

					list.add(dto);

				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				closeConn(rs, pstmt, con);
			}

		} else if (notice_field.equals("내용")) {

			try {

				sql = "select * from (select @ROWN:=@ROWN+1 AS RN, " + "nb.* from notice_board nb, "
						+ "(select @ROWN:=0) as clear_RN where " + "notice_board_cont like ? "
						+ "order by nb.notice_board_no desc) as subquery " + "where RN >= ? and RN <= ?";

				pstmt = con.prepareStatement(sql);

				pstmt.setString(1, "%" + notice_keyword + "%");
				pstmt.setInt(2, startNo);
				pstmt.setInt(3, endNo);

				rs = pstmt.executeQuery();

				while (rs.next()) {

					NoticeBoardDTO dto = new NoticeBoardDTO();

					dto.setNotice_board_no(rs.getInt("notice_board_no"));
					dto.setNotice_board_writer_id(rs.getString("notice_board_writer_id"));
					dto.setNotice_board_title(rs.getString("notice_board_title"));
					dto.setNotice_board_cont(rs.getString("notice_board_cont"));
					dto.setNotice_board_date(rs.getString("notice_board_date"));
					dto.setNotice_board_update(rs.getString("notice_board_update"));
					dto.setNotice_board_hit(rs.getInt("notice_board_hit"));
					dto.setNotice_board_file(rs.getString("notice_board_file"));

					list.add(dto);

				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				closeConn(rs, pstmt, con);
			}
		}

		return list;
	} // List<NoticeBoardDTO> searchNoticeList() end.

	// 검색한 리스트들을 카운트 하는 매서드.
	public int countNoticeSearchList(String notice_field, String notice_keyword) {

		int cnt = 0;

		openConn();

		if (notice_field.equals("전체")) {

			try {

				sql = "select count(*) from notice_board where notice_board_title like ? or notice_board_cont like ?";

				pstmt = con.prepareStatement(sql);

				pstmt.setString(1, "%" + notice_keyword + "%");
				pstmt.setString(2, "%" + notice_keyword + "%");

				rs = pstmt.executeQuery();

				if (rs.next()) {
					cnt = rs.getInt(1);
				}

			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				closeConn(rs, pstmt, con);
			}

		} else if (notice_field.equals("제목")) {

			try {

				sql = "select count(*) from notice_board where notice_board_title like ?";

				pstmt = con.prepareStatement(sql);

				pstmt.setString(1, "%" + notice_keyword + "%");

				rs = pstmt.executeQuery();

				if (rs.next()) {
					cnt = rs.getInt(1);
				}

			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				closeConn(rs, pstmt, con);
			}

		} else if (notice_field.equals("내용")) {

			try {

				sql = "select count(*) from notice_board where notice_board_cont like ?";

				pstmt = con.prepareStatement(sql);

				pstmt.setString(1, "%" + notice_keyword + "%");

				rs = pstmt.executeQuery();

				if (rs.next()) {
					cnt = rs.getInt(1);
				}

			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				closeConn(rs, pstmt, con);
			}
		}

		return cnt;
	} // countNoticeSearchList() end.

	// 공지사항 게시판 검색 기능 매서드.
	public List<EventBoardDTO> searchEventList(String event_field, String event_keyword, int page, int rowsize) {

		List<EventBoardDTO> list = new ArrayList<EventBoardDTO>();

		int startNo = (page * rowsize) - (rowsize - 1);

		int endNo = (page * rowsize);

		openConn();

		if (event_field.equals("전체")) {

			try {

				sql = "select * from (select @ROWN:=@ROWN+1 AS RN, " + "eb.* from event_board eb, "
						+ "(select @ROWN:=0) as clear_RN where " + "event_board_title like ? or "
						+ "event_board_cont like ? " + "order by eb.event_board_no desc) as subquery "
						+ "where RN >= ? and RN <= ?";

				pstmt = con.prepareStatement(sql);

				pstmt.setString(1, "%" + event_keyword + "%");
				pstmt.setString(2, "%" + event_keyword + "%");
				pstmt.setInt(3, startNo);
				pstmt.setInt(4, endNo);

				rs = pstmt.executeQuery();

				while (rs.next()) {

					EventBoardDTO dto = new EventBoardDTO();

					dto.setEvent_board_no(rs.getInt("event_board_no"));
					dto.setEvent_board_writer_id(rs.getString("event_board_writer_id"));
					dto.setEvent_board_title(rs.getString("event_board_title"));
					dto.setEvent_board_cont(rs.getString("event_board_cont"));
					dto.setEvent_board_date(rs.getString("event_board_date"));
					dto.setEvent_board_update(rs.getString("event_board_update"));
					dto.setEvent_board_hit(rs.getInt("event_board_hit"));
					dto.setEvent_board_file(rs.getString("event_board_file"));

					list.add(dto);

				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				closeConn(rs, pstmt, con);
			}

		} else if (event_field.equals("제목")) {

			try {

				sql = "select * from (select @ROWN:=@ROWN+1 AS RN, " + "eb.* from event_board eb, "
						+ "(select @ROWN:=0) as clear_RN where " + "event_board_title like ? "
						+ "order by eb.event_board_no desc) as subquery " + "where RN >= ? and RN <= ?";

				pstmt = con.prepareStatement(sql);

				pstmt.setString(1, "%" + event_keyword + "%");
				pstmt.setInt(2, startNo);
				pstmt.setInt(3, endNo);

				rs = pstmt.executeQuery();

				while (rs.next()) {

					EventBoardDTO dto = new EventBoardDTO();

					dto.setEvent_board_no(rs.getInt("event_board_no"));
					dto.setEvent_board_writer_id(rs.getString("event_board_writer_id"));
					dto.setEvent_board_title(rs.getString("event_board_title"));
					dto.setEvent_board_cont(rs.getString("event_board_cont"));
					dto.setEvent_board_date(rs.getString("event_board_date"));
					dto.setEvent_board_update(rs.getString("event_board_update"));
					dto.setEvent_board_hit(rs.getInt("event_board_hit"));
					dto.setEvent_board_file(rs.getString("event_board_file"));

					list.add(dto);

				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				closeConn(rs, pstmt, con);
			}

		} else if (event_field.equals("내용")) {

			try {

				sql = "select * from " + "(select @ROWN:=@ROWN+1 AS RN, " + "eb.* from event_board eb, "
						+ "(select @ROWN:=0) as clear_RN where " + "event_board_cont like ? "
						+ "order by eb.event_board_no desc) as subquery " + "where RN >= ? and RN <= ?";

				pstmt = con.prepareStatement(sql);

				pstmt.setString(1, "%" + event_keyword + "%");
				pstmt.setInt(2, startNo);
				pstmt.setInt(3, endNo);

				rs = pstmt.executeQuery();

				while (rs.next()) {

					EventBoardDTO dto = new EventBoardDTO();

					dto.setEvent_board_no(rs.getInt("event_board_no"));
					dto.setEvent_board_writer_id(rs.getString("event_board_writer_id"));
					dto.setEvent_board_title(rs.getString("event_board_title"));
					dto.setEvent_board_cont(rs.getString("event_board_cont"));
					dto.setEvent_board_date(rs.getString("event_board_date"));
					dto.setEvent_board_update(rs.getString("event_board_update"));
					dto.setEvent_board_hit(rs.getInt("event_board_hit"));
					dto.setEvent_board_file(rs.getString("event_board_file"));

					list.add(dto);

				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				closeConn(rs, pstmt, con);
			}
		}

		return list;
	} // List<EventBoardDTO> searchEventList() end.

	// 검색한 이벤트 리스트들을 카운트 하는 매서드.
	public int countEventSearchList(String event_field, String event_keyword) {

		int cnt = 0;

		openConn();

		if (event_field.equals("전체")) {

			try {

				sql = "select count(*) from event_board where event_board_title like ? or event_board_cont like ?";

				pstmt = con.prepareStatement(sql);

				pstmt.setString(1, "%" + event_keyword + "%");
				pstmt.setString(2, "%" + event_keyword + "%");

				rs = pstmt.executeQuery();

				if (rs.next()) {
					cnt = rs.getInt(1);
				}

			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				closeConn(rs, pstmt, con);
			}

		} else if (event_field.equals("제목")) {

			try {

				sql = "select count(*) from event_board where event_board_title like ?";

				pstmt = con.prepareStatement(sql);

				pstmt.setString(1, "%" + event_keyword + "%");

				rs = pstmt.executeQuery();

				if (rs.next()) {
					cnt = rs.getInt(1);
				}

			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				closeConn(rs, pstmt, con);
			}

		} else if (event_field.equals("내용")) {

			try {

				sql = "select count(*) from event_board where event_board_cont like ?";

				pstmt = con.prepareStatement(sql);

				pstmt.setString(1, "%" + event_keyword + "%");

				rs = pstmt.executeQuery();

				if (rs.next()) {
					cnt = rs.getInt(1);
				}

			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				closeConn(rs, pstmt, con);
			}
		}

		return cnt;
	} // countEventSearchList() end.

	// 판매 후기게시판의 조회수 증가하는 메서드
	public void sellReviewBoardHit(int no) {

		try {
			openConn();

			sql = "update sell_review_board set sell_review_board_hit = sell_review_board_hit + 1 where sell_review_board_no = ?";

			pstmt = con.prepareStatement(sql);

			pstmt.setInt(1, no);

			pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConn(pstmt, con);
		}

	} // sellReviewBoardHit(int no) end

	// qna문의 글 번호에 해당하는 qna문의 상세내용 확인하는 매서드
	public QnaBoardDTO getQnaBoardContent(int no) {

		QnaBoardDTO dto = null;

		try {
			openConn();

			sql = "select * from qna_board where qna_board_no = ?";

			pstmt = con.prepareStatement(sql);

			pstmt.setInt(1, no);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				dto = new QnaBoardDTO();

				dto.setQna_board_no(rs.getInt("qna_board_no"));
				dto.setQna_board_type(rs.getString("qna_board_type"));
				dto.setQna_board_writer_id(rs.getString("qna_board_writer_id"));
				dto.setQna_board_title(rs.getString("qna_board_title"));
				dto.setQna_board_cont(rs.getString("qna_board_cont"));
				dto.setQna_board_date(rs.getString("qna_board_date"));
				dto.setQna_board_update(rs.getString("qna_board_update"));
				dto.setQna_board_file(rs.getString("qna_board_file"));
				dto.setQna_board_status(rs.getBoolean("qna_board_status"));

			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}

		return dto;
	} // QnaBoardDTO getQnaBoardContent(int no) end

	// 자유게시판 글 조회수 증가하는 메서드
	public void communityBoardHit(int no) {

		try {
			openConn();

			sql = "update community_board set community_board_hit = community_board_hit + 1 where community_board_no = ?";

			pstmt = con.prepareStatement(sql);

			pstmt.setInt(1, no);

			pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConn(pstmt, con);
		}

	} // communityBoardHit(int no) end

	// 자유게시판 삭제하는 메서드
	public int deleteCommunityBoard(int no) {

		int result = 0;

		try {
			openConn();

			sql = "delete from community_board where community_board_no = ?";

			pstmt = con.prepareStatement(sql);

			pstmt.setInt(1, no);

			result = pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConn(pstmt, con);
		}

		return result;
	} // deleteCommunityBoard(int no) end

	// 차량 검색 메서드 (필터링, 정렬, 페이징 포함)
	public List<SellBoardDTO> getFilterSearch(List<String> typeList, List<String> companyList, List<String> colorList,
			List<String> fuelList, List<String> transmissionList, int min_year, int max_year, int min_distance,
			int max_distance, long min_price, long max_price, String sequence, int page, int rowsize) {

		List<SellBoardDTO> list = new ArrayList<>();
		// 선택한 조건에 따라 ?(플레이스홀더)의 개수가 동적이기 때문에 count로 증감연산자 사용
		int count = 0;
		// 페이징 코드
		int startNo = (page * rowsize) - (rowsize - 1);
		int endNo = (page * rowsize);

		try {
			openConn();
			// 선택 조건에 따라 sql문장을 더하면서 작성
			sql = "select * from (select @row_number := case"
					+ " when substring_index(substring_index(sell_board_car_std_no, '_', 3), '_', -1) >= ?"
					+ " and substring_index(substring_index(sell_board_car_std_no, '_', 3), '_', -1) <= ?"
					+ " and sell_board_car_distance >= ? and sell_board_car_distance <= ?"
					+ " and sell_board_car_price >= ? and sell_board_car_price <= ?";

			// 선택한 조건이 하나라도 있다면
			if (typeList != null || companyList != null || colorList != null || fuelList != null
					|| transmissionList != null) {
				// 선택한 차종이 있다면
				if (typeList != null) {
					sql += " and (";
					for (int i = 0; i < typeList.size(); i++) {
						sql += "substring_index(substring_index(sell_board_car_std_no, '_', 2), '_', -1) regexp ?";
						if (i != typeList.size() - 1) {
							sql += " or ";
						}
					}
					sql += ")";
				}
				// 선택한 제조사가 있다면
				if (companyList != null) {
					sql += " and (";
					for (int i = 0; i < companyList.size(); i++) {
						sql += "substring_index(substring_index(sell_board_car_std_no, '_', 1), '_', -1) regexp ?";
						if (i != companyList.size() - 1) {
							sql += " or ";
						}
					}
					sql += ")";
				}
				// 선택한 색상이 있다면
				if (colorList != null) {
					sql += " and (";
					for (int i = 0; i < colorList.size(); i++) {
						sql += "substring_index(substring_index(sell_board_car_std_no, '_', 7), '_', -1) regexp ?";
						if (i != colorList.size() - 1) {
							sql += " or ";
						}
					}
					sql += ")";
				}
				// 선택한 연료가 있다면
				if (fuelList != null) {
					sql += " and (";
					for (int i = 0; i < fuelList.size(); i++) {
						sql += "substring_index(substring_index(sell_board_car_std_no, '_', 5), '_', -1) regexp ?";
						if (i != fuelList.size() - 1) {
							sql += " or ";
						}
					}
					sql += ")";
				}
				// 선택한 변속기가 있다면
				if (transmissionList != null) {
					sql += " and (";
					for (int i = 0; i < transmissionList.size(); i++) {
						sql += "substring_index(substring_index(sell_board_car_std_no, '_', 6), '_', -1) regexp ?";
						if (i != transmissionList.size() - 1) {
							sql += " or ";
						}
					}
					sql += ")";
				}
			}

			sql += " then @row_number + 1" + " else @row_number" + " end as rnum,"
					+ " sb.* from (select distinct * from sell_board";

			// 선택한 정렬이 없다면 기본 정렬, 있다면 해당 조건에 맞게 정렬 추가
			if (sequence == null) {
				sql += " order by sell_board_no desc";
			} else if (sequence.equals("최근 연식순")) {
				sql += " order by substring_index(substring_index(sell_board_car_std_no, '_', 3), '_', -1) desc";
			} else if (sequence.equals("오래된 연식순")) {
				sql += " order by substring_index(substring_index(sell_board_car_std_no, '_', 3), '_', -1)";
			} else if (sequence.equals("적은 주행거리순")) {
				sql += " order by sell_board_car_distance";
			} else if (sequence.equals("많은 주행거리순")) {
				sql += " order by sell_board_car_distance desc";
			} else if (sequence.equals("낮은 가격순")) {
				sql += " order by sell_board_car_price";
			} else if (sequence.equals("높은 가격순")) {
				sql += " order by sell_board_car_price desc";
			}

			sql += ") sb," + " (select @row_number := 0) as clear_RN"
					+ " where substring_index(substring_index(sb.sell_board_car_std_no, '_', 3), '_', -1) >= ?"
					+ " and substring_index(substring_index(sb.sell_board_car_std_no, '_', 3), '_', -1) <= ?"
					+ " and sb.sell_board_car_distance >= ? and sb.sell_board_car_distance <= ?"
					+ " and sb.sell_board_car_price >= ? and sb.sell_board_car_price <= ?";

			// 페이징을 위한 조건과 페이징 후 출력할 조건을 둘 다 입력해야 하기 때문에
			// 선택한 조건 sql문을 한번 더 입력해야 한다.
			if (typeList != null || companyList != null || colorList != null || fuelList != null
					|| transmissionList != null) {
				if (typeList != null) {
					sql += " and (";
					for (int i = 0; i < typeList.size(); i++) {
						sql += "substring_index(substring_index(sell_board_car_std_no, '_', 2), '_', -1) regexp ?";
						if (i != typeList.size() - 1) {
							sql += " or ";
						}
					}
					sql += ")";
				}
				if (companyList != null) {
					sql += " and (";
					for (int i = 0; i < companyList.size(); i++) {
						sql += "substring_index(substring_index(sell_board_car_std_no, '_', 1), '_', -1) regexp ?";
						if (i != companyList.size() - 1) {
							sql += " or ";
						}
					}
					sql += ")";
				}
				if (colorList != null) {
					sql += " and (";
					for (int i = 0; i < colorList.size(); i++) {
						sql += "substring_index(substring_index(sell_board_car_std_no, '_', 7), '_', -1) regexp ?";
						if (i != colorList.size() - 1) {
							sql += " or ";
						}
					}
					sql += ")";
				}
				if (fuelList != null) {
					sql += " and (";
					for (int i = 0; i < fuelList.size(); i++) {
						sql += "substring_index(substring_index(sell_board_car_std_no, '_', 5), '_', -1) regexp ?";
						if (i != fuelList.size() - 1) {
							sql += " or ";
						}
					}
					sql += ")";
				}
				if (transmissionList != null) {
					sql += " and (";
					for (int i = 0; i < transmissionList.size(); i++) {
						sql += "substring_index(substring_index(sell_board_car_std_no, '_', 6), '_', -1) regexp ?";
						if (i != transmissionList.size() - 1) {
							sql += " or ";
						}
					}
					sql += ")";
				}
			}

			sql += ") as subquery" + " where rnum >= ? and rnum <= ?";

			// 페이징을 위한 조건과 페이징 후 출력할 조건을 둘 다 입력해야 하기 때문에
			// 선택한 조건 sql문을 한번 더 입력해야 한다.
			if (sequence == null) {
				sql += " order by sell_board_no desc";
			} else if (sequence.equals("최근 연식순")) {
				sql += " order by substring_index(substring_index(sell_board_car_std_no, '_', 3), '_', -1) desc";
			} else if (sequence.equals("오래된 연식순")) {
				sql += " order by substring_index(substring_index(sell_board_car_std_no, '_', 3), '_', -1)";
			} else if (sequence.equals("적은 주행거리순")) {
				sql += " order by sell_board_car_distance";
			} else if (sequence.equals("많은 주행거리순")) {
				sql += " order by sell_board_car_distance desc";
			} else if (sequence.equals("낮은 가격순")) {
				sql += " order by sell_board_car_price";
			} else if (sequence.equals("높은 가격순")) {
				sql += " order by sell_board_car_price desc";
			}

			pstmt = con.prepareStatement(sql);
			pstmt.setInt(++count, min_year);
			pstmt.setInt(++count, max_year);
			pstmt.setInt(++count, min_distance);
			pstmt.setInt(++count, max_distance);
			pstmt.setLong(++count, min_price);
			pstmt.setLong(++count, max_price);

			// 선택한 조건 개수만큼 플레이스홀더에 값 적용
			if (typeList != null) {
				for (int i = 0; i < typeList.size(); i++) {
					pstmt.setString(++count, typeList.get(i));
				}
			}
			if (companyList != null) {
				for (int i = 0; i < companyList.size(); i++) {
					pstmt.setString(++count, companyList.get(i));
				}
			}
			if (colorList != null) {
				for (int i = 0; i < colorList.size(); i++) {
					pstmt.setString(++count, colorList.get(i));
				}
			}
			if (fuelList != null) {
				for (int i = 0; i < fuelList.size(); i++) {
					pstmt.setString(++count, fuelList.get(i));
				}
			}
			if (transmissionList != null) {
				for (int i = 0; i < transmissionList.size(); i++) {
					pstmt.setString(++count, transmissionList.get(i));
				}
			}

			pstmt.setInt(++count, min_year);
			pstmt.setInt(++count, max_year);
			pstmt.setInt(++count, min_distance);
			pstmt.setInt(++count, max_distance);
			pstmt.setLong(++count, min_price);
			pstmt.setLong(++count, max_price);

			if (typeList != null) {
				for (int i = 0; i < typeList.size(); i++) {
					pstmt.setString(++count, typeList.get(i));
				}
			}
			if (companyList != null) {
				for (int i = 0; i < companyList.size(); i++) {
					pstmt.setString(++count, companyList.get(i));
				}
			}
			if (colorList != null) {
				for (int i = 0; i < colorList.size(); i++) {
					pstmt.setString(++count, colorList.get(i));
				}
			}
			if (fuelList != null) {
				for (int i = 0; i < fuelList.size(); i++) {
					pstmt.setString(++count, fuelList.get(i));
				}
			}
			if (transmissionList != null) {
				for (int i = 0; i < transmissionList.size(); i++) {
					pstmt.setString(++count, transmissionList.get(i));
				}
			}

			pstmt.setInt(++count, startNo);
			pstmt.setInt(++count, endNo);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				SellBoardDTO dto = new SellBoardDTO();

				dto.setSell_board_admin_no(rs.getString("sell_board_admin_no"));
				dto.setSell_board_car_detail(rs.getString("sell_board_car_detail"));
				dto.setSell_board_car_distance(rs.getInt("sell_board_car_distance"));
				dto.setSell_board_car_file(rs.getString("sell_board_car_file"));
				dto.setSell_board_car_no(rs.getString("sell_board_car_no"));
				dto.setSell_board_car_price(rs.getInt("sell_board_car_price"));
				dto.setSell_board_car_std_no(rs.getString("sell_board_car_std_no"));
				dto.setSell_board_date(rs.getString("sell_board_date"));
				dto.setSell_board_no(rs.getInt("sell_board_no"));
				dto.setSell_board_seller_no(rs.getString("sell_board_seller_no"));
				dto.setSell_board_status(rs.getBoolean("sell_board_status"));
				dto.setSell_board_update(rs.getString("sell_board_update"));
				dto.setSell_board_hit(rs.getInt("sell_board_hit"));

				list.add(dto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}

		return list;
	}

	// 필터링된 리스트의 전체 개수를 가져오는 메서드
	public int getFilterSearchCount(List<String> typeList, List<String> companyList, List<String> colorList,
			List<String> fuelList, List<String> transmissionList, int min_year, int max_year, int min_distance,
			int max_distance, long min_price, long max_price) {

		int count = 0;
		int result = 0;

		try {
			openConn();

			sql = "select count(*) from sell_board"
					+ " where substring_index(substring_index(sell_board_car_std_no, '_', 3), '_', -1) >= ?"
					+ " and substring_index(substring_index(sell_board_car_std_no, '_', 3), '_', -1) <= ?"
					+ " and sell_board_car_distance >= ? and sell_board_car_distance <= ?"
					+ " and sell_board_car_price >= ? and sell_board_car_price <= ?";

			if (typeList != null || companyList != null || colorList != null || fuelList != null
					|| transmissionList != null) {
				if (typeList != null) {
					sql += " and (";
					for (int i = 0; i < typeList.size(); i++) {
						sql += "substring_index(substring_index(sell_board_car_std_no, '_', 2), '_', -1) regexp ?";
						if (i != typeList.size() - 1) {
							sql += " or ";
						}
					}
					sql += ") ";
				}
				if (companyList != null) {
					sql += " and (";
					for (int i = 0; i < companyList.size(); i++) {
						sql += "substring_index(substring_index(sell_board_car_std_no, '_', 1), '_', -1) regexp ?";
						if (i != companyList.size() - 1) {
							sql += " or ";
						}
					}
					sql += ") ";
				}
				if (colorList != null) {
					sql += " and (";
					for (int i = 0; i < colorList.size(); i++) {
						sql += "substring_index(substring_index(sell_board_car_std_no, '_', 7), '_', -1) regexp ?";
						if (i != colorList.size() - 1) {
							sql += " or ";
						}
					}
					sql += ") ";
				}
				if (fuelList != null) {
					sql += " and (";
					for (int i = 0; i < fuelList.size(); i++) {
						sql += "substring_index(substring_index(sell_board_car_std_no, '_', 5), '_', -1) regexp ?";
						if (i != fuelList.size() - 1) {
							sql += " or ";
						}
					}
					sql += ") ";
				}
				if (transmissionList != null) {
					sql += " and (";
					for (int i = 0; i < transmissionList.size(); i++) {
						sql += "substring_index(substring_index(sell_board_car_std_no, '_', 6), '_', -1) regexp ?";
						if (i != transmissionList.size() - 1) {
							sql += " or ";
						}
					}
					sql += ") ";
				}
			}

			pstmt = con.prepareStatement(sql);
			pstmt.setInt(++count, min_year);
			pstmt.setInt(++count, max_year);
			pstmt.setInt(++count, min_distance);
			pstmt.setInt(++count, max_distance);
			pstmt.setLong(++count, min_price);
			pstmt.setLong(++count, max_price);

			if (typeList != null) {
				for (int i = 0; i < typeList.size(); i++) {
					pstmt.setString(++count, typeList.get(i));
				}
			}
			if (companyList != null) {
				for (int i = 0; i < companyList.size(); i++) {
					pstmt.setString(++count, companyList.get(i));
				}
			}
			if (colorList != null) {
				for (int i = 0; i < colorList.size(); i++) {
					pstmt.setString(++count, colorList.get(i));
				}
			}
			if (fuelList != null) {
				for (int i = 0; i < fuelList.size(); i++) {
					pstmt.setString(++count, fuelList.get(i));
				}
			}
			if (transmissionList != null) {
				for (int i = 0; i < transmissionList.size(); i++) {
					pstmt.setString(++count, transmissionList.get(i));
				}
			}

			rs = pstmt.executeQuery();

			if (rs.next()) {
				result = rs.getInt(1);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}

		return result;
	}

	// 자유게시판 테이블에서 현재 페이지에 해당하는 게시물을 조회하는 메서드
	public List<CommunityBoardDTO> getCommunityBoardList(int community_page, int community_rowsize) {

		List<CommunityBoardDTO> list = new ArrayList<CommunityBoardDTO>();

		int community_startNo = (community_page * community_rowsize) - (community_rowsize - 1);

		int community_endNo = community_page * community_rowsize;

		try {
			openConn();

			sql = "select * from (" + "select @ROWN:=@ROWN+1 AS RC, cb.* " + "from community_board cb, "
					+ "(select @ROWN:=0) as clear_RC " + "order by cb.community_board_no desc) as subquery "
					+ "where RC >= ? and RC <= ?";

			pstmt = con.prepareStatement(sql);

			pstmt.setInt(1, community_startNo);
			pstmt.setInt(2, community_endNo);

			rs = pstmt.executeQuery();

			while (rs.next()) {

				CommunityBoardDTO dto = new CommunityBoardDTO();

				dto.setCommunity_board_no(rs.getInt("community_board_no"));
				dto.setCommunity_board_writer_id(rs.getString("community_board_writer_id"));
				dto.setCommunity_board_title(rs.getString("community_board_title"));
				dto.setCommunity_board_cont(rs.getString("community_board_cont"));
				dto.setCommunity_board_date(rs.getString("community_board_date"));
				dto.setCommunity_board_update(rs.getString("community_board_update"));
				dto.setCommunity_board_hit(rs.getInt("community_board_hit"));

				list.add(dto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}

		return list;

	} // List<CommunityBoardDTO> getCommunityBoardList(int community_page, int
		// community_rowsize)

	// 판매후기 게시판에서 현재 페이지에 해당하는 게시물을 조회하는 메서드
	public List<SellReviewBoardDTO> getSellReviewList(int sellReview_page, int sellReview_rowsize) {

		List<SellReviewBoardDTO> list = new ArrayList<SellReviewBoardDTO>();

		int sellReview_startNo = (sellReview_page * sellReview_rowsize) - (sellReview_rowsize - 1);

		int sellReview_endNo = sellReview_page * sellReview_rowsize;

		try {
			openConn();

			sql = "select * from (" + "select @ROWN:=@ROWN+1 AS RS, srb.* " + "from sell_review_board srb, "
					+ "(select @ROWN:=0) as clear_RS " + "order by srb.sell_review_board_no desc) as subquery "
					+ "where RS >= ? and RS <= ?";

			pstmt = con.prepareStatement(sql);

			pstmt.setInt(1, sellReview_startNo);
			pstmt.setInt(2, sellReview_endNo);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				SellReviewBoardDTO dto = new SellReviewBoardDTO();

				dto.setSell_review_board_no(rs.getInt("sell_review_board_no"));
				dto.setSell_review_board_writer_id(rs.getString("sell_review_board_writer_id"));
				dto.setSell_review_board_title(rs.getString("sell_review_board_title"));
				dto.setSell_review_board_cont(rs.getString("sell_review_board_cont"));
				dto.setSell_review_board_date(rs.getString("sell_review_board_date"));
				dto.setSell_review_board_update(rs.getString("sell_review_board_update"));
				dto.setSell_review_board_hit(rs.getInt("sell_review_board_hit"));
				dto.setSell_review_board_file(rs.getString("sell_review_board_file"));

				list.add(dto);
			}

		} catch (SQLException e) {
			e.printStackTrace();

		} finally {
			closeConn(rs, pstmt, con);
		}

		return list;
	} // List<SellReviewBoardDTO> getSellReviewList(int sellReview_page, int
		// sellReview_rowsize) end

	// 내차 사기 리스트의 각 Content 데이터를 가져오는 매서드.
	public SellBoardDTO getSellBoardContent(int board_no) {

		SellBoardDTO dto = null;

		try {

			openConn();

			sql = "select * from sell_board where sell_board_no = ?";

			pstmt = con.prepareStatement(sql);

			pstmt.setInt(1, board_no);

			rs = pstmt.executeQuery();

			if (rs.next()) {

				dto = new SellBoardDTO();

				dto.setSell_board_no(rs.getInt("sell_board_no"));
				dto.setSell_board_admin_no(rs.getString("sell_board_admin_no"));
				dto.setSell_board_seller_no(rs.getString("sell_board_seller_no"));
				dto.setSell_board_car_std_no(rs.getString("sell_board_car_std_no"));
				dto.setSell_board_car_no(rs.getString("sell_board_car_no"));
				dto.setSell_board_car_distance(rs.getInt("sell_board_car_distance"));
				dto.setSell_board_car_price(rs.getInt("sell_board_car_price"));
				dto.setSell_board_car_detail(rs.getString("sell_board_car_detail"));
				dto.setSell_board_date(rs.getString("sell_board_date"));
				dto.setSell_board_update(rs.getString("sell_board_update"));
				dto.setSell_board_car_file(rs.getString("sell_board_car_file"));
				dto.setSell_board_status(rs.getBoolean("sell_board_status"));
				dto.setSell_board_hit(rs.getInt("sell_board_hit"));

			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}

		return dto;
	} // getSellBoardContent() end.

	// 구매 정보를 DB에 저장하는 매서드.
	public int insertTransactionData(SellBoardDTO dto, String no) {

		int result = 0, cnt = 0;

		try {

			openConn();

			sql = "select max(transaction_no) from transaction";

			pstmt = con.prepareStatement(sql);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				cnt = rs.getInt(1) + 1;
			}

			sql = "insert into transaction values(?, ?, ?, ?, ?, ?, ? ,?, default, default, default)";

			pstmt = con.prepareStatement(sql);

			pstmt.setInt(1, cnt);
			pstmt.setString(2, dto.getSell_board_seller_no());
			pstmt.setString(3, no);
			pstmt.setString(4, dto.getSell_board_admin_no());
			pstmt.setString(5, dto.getSell_board_car_std_no());
			pstmt.setString(6, dto.getSell_board_car_no());
			pstmt.setInt(7, dto.getSell_board_car_distance());
			pstmt.setInt(8, dto.getSell_board_car_price());

			result = pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}
		return result;
	} // insertTransactionData() end.

	// 판매 된 매물의 status 를 바꾸는 매서드.
	public void updateSellBoardStatus(int board_no) {

		try {

			openConn();

			sql = "update sell_board set sell_board_status = true where sell_board_no = ?";

			pstmt = con.prepareStatement(sql);

			pstmt.setInt(1, board_no);

			pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConn(pstmt, con);
		}

	} // updateSellBoardStatus() end.

	public BuyReviewBoardDTO getBuyReviewBoardContent(int review_board_no) {

		BuyReviewBoardDTO dto = null;

		try {

			openConn();
			sql = "select * from buy_review_board where buy_review_board_no = ?";

			pstmt = con.prepareStatement(sql);

			pstmt.setInt(1, review_board_no);

			rs = pstmt.executeQuery();

			if (rs.next()) {

				dto = new BuyReviewBoardDTO();

				dto.setBuy_review_board_cont(rs.getString("buy_review_board_cont"));
				dto.setBuy_review_board_date(rs.getString("buy_review_board_date"));
				dto.setBuy_review_board_file(rs.getString("buy_review_board_file"));
				dto.setBuy_review_board_hit(rs.getInt("buy_review_board_hit"));
				dto.setBuy_review_board_no(rs.getInt("buy_review_board_no"));
				dto.setBuy_review_board_title(rs.getString("buy_review_board_title"));
				dto.setBuy_review_board_writer_id(rs.getString("buy_review_board_writer_id"));
				dto.setBuy_review_board_update(rs.getString("buy_review_board_update"));
				dto.setBuy_review_transaction_no(rs.getInt("buy_review_transaction_no"));

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {

			closeConn(rs, pstmt, con);
		}

		return dto;
	}

	public List<SellReviewBoardDTO> getSellReviewBoardList(String user_id) {

		List<SellReviewBoardDTO> sell_list = new ArrayList<SellReviewBoardDTO>();

		try {

			openConn();

			sql = "select * from sell_review_board where sell_review_board_writer_id = ? order by sell_review_board_no";

			pstmt = con.prepareStatement(sql);

			pstmt.setString(1, user_id);

			rs = pstmt.executeQuery();

			while (rs.next()) {

				SellReviewBoardDTO dto = new SellReviewBoardDTO();

				dto.setSell_review_board_cont(rs.getString("sell_review_board_cont"));
				dto.setSell_review_board_date(rs.getString("sell_review_board_date"));
				dto.setSell_review_board_file(rs.getString("sell_review_board_file"));
				dto.setSell_review_board_hit(rs.getInt("sell_review_board_hit"));
				dto.setSell_review_board_no(rs.getInt("sell_review_board_no"));
				dto.setSell_review_board_title(rs.getString("sell_review_board_title"));
				dto.setSell_review_board_update(rs.getString("sell_review_board_update"));
				dto.setSell_review_board_writer_id(rs.getString("sell_review_board_writer_id"));

				sell_list.add(dto);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return sell_list;
	}

	public int countSellReviewBoardList(String user_id) {
		int sell_review_cnt = 0;

		try {

			openConn();

			sql = "select count(*) from sell_review_board where sell_review_board_writer_id = ?";

			pstmt = con.prepareStatement(sql);

			pstmt.setString(1, user_id);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				sell_review_cnt = rs.getInt(1);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}
		return sell_review_cnt;
	} // countQnaUserList() end.

	public SellReviewBoardDTO getSellReviewBoardContent(int review_board_no) {

		SellReviewBoardDTO dto = null;

		try {

			openConn();
			sql = "select * from sell_review_board where sell_review_board_no = ?";

			pstmt = con.prepareStatement(sql);

			pstmt.setInt(1, review_board_no);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				dto = new SellReviewBoardDTO();

				dto.setSell_review_board_cont(rs.getString("sell_review_board_cont"));
				dto.setSell_review_board_date(rs.getString("sell_review_board_date"));
				dto.setSell_review_board_file(rs.getString("sell_review_board_file"));
				dto.setSell_review_board_hit(rs.getInt("sell_review_board_hit"));
				dto.setSell_review_board_no(rs.getInt("sell_review_board_no"));
				dto.setSell_review_board_title(rs.getString("sell_review_board_title"));
				dto.setSell_review_board_update(rs.getString("sell_review_board_update"));
				dto.setSell_review_board_writer_id(rs.getString("sell_review_board_writer_id"));

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {

			closeConn(rs, pstmt, con);
		}

		return dto;
	}

	// buyreviewboard의 모든 정보를 가져오는 메서드

	public List<BuyReviewBoardDTO> getBuyReviewBoardList(String user_id) {

		List<BuyReviewBoardDTO> buylist = new ArrayList<BuyReviewBoardDTO>();
		BuyReviewBoardDTO dto = null;
		try {
			openConn();

			sql = "select * from buy_review_board where buy_review_board_writer_id = ? order by buy_review_board_no";

			pstmt = con.prepareStatement(sql);

			pstmt.setString(1, user_id);

			rs = pstmt.executeQuery();

			while (rs.next()) {

				dto = new BuyReviewBoardDTO();

				dto.setBuy_review_board_no(rs.getInt("buy_review_board_no"));
				dto.setBuy_review_board_writer_id(rs.getString("buy_review_board_writer_id"));
				dto.setBuy_review_board_title(rs.getString("buy_review_board_title"));
				dto.setBuy_review_board_cont(rs.getString("buy_review_board_cont"));
				dto.setBuy_review_board_date(rs.getString("buy_review_board_date"));
				dto.setBuy_review_board_update(rs.getString("buy_review_board_update"));
				dto.setBuy_review_board_hit(rs.getInt("buy_review_board_hit"));
				dto.setBuy_review_board_file(rs.getString("buy_review_board_file"));

				buylist.add(dto);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}

		return buylist;
	}

	// 사용자가 등록한 구매후기 리스트를 카운트하는 매서드.
	public int countBuyReviewBoardList(String user_id) {

		int buyreview_cnt = 0;

		try {

			openConn();

			sql = "select count(*) from buy_review_board where buy_review_board_writer_id = ?";

			pstmt = con.prepareStatement(sql);

			pstmt.setString(1, user_id);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				buyreview_cnt = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}

		return buyreview_cnt;
	} // countNoticeList() end.

	public List<BuyReviewBoardDTO> getMainBuyReviewBoardList() {

		List<BuyReviewBoardDTO> buy_review_list = new ArrayList<BuyReviewBoardDTO>();
		BuyReviewBoardDTO dto = null;
		try {
			openConn();

			sql = "select * from buy_review_board order by buy_review_board_no";

			pstmt = con.prepareStatement(sql);

			rs = pstmt.executeQuery();

			while (rs.next()) {

				dto = new BuyReviewBoardDTO();

				dto.setBuy_review_board_no(rs.getInt("buy_review_board_no"));
				dto.setBuy_review_board_writer_id(rs.getString("buy_review_board_writer_id"));
				dto.setBuy_review_board_title(rs.getString("buy_review_board_title"));
				dto.setBuy_review_board_cont(rs.getString("buy_review_board_cont"));
				dto.setBuy_review_board_date(rs.getString("buy_review_board_date"));
				dto.setBuy_review_board_update(rs.getString("buy_review_board_update"));
				dto.setBuy_review_board_hit(rs.getInt("buy_review_board_hit"));
				dto.setBuy_review_board_file(rs.getString("buy_review_board_file"));

				buy_review_list.add(dto);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}

		return buy_review_list;
	}

	public int countBuyReviewBoardList() {

		int buy_review_cnt = 0;

		try {

			openConn();

			sql = "select count(*) from buy_review_board ";

			pstmt = con.prepareStatement(sql);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				buy_review_cnt = rs.getInt(1);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}
		return buy_review_cnt;
	} // countQnaUserList() end.

	public SellReviewBoardDTO getMypageSellReviewBoardContent(int sell_reivew_board_no) {

		SellReviewBoardDTO dto = null;

		try {

			openConn();
			sql = "select * from sell_review_board where sell_review_board_no = ?";

			pstmt = con.prepareStatement(sql);

			pstmt.setInt(1, sell_reivew_board_no);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				dto = new SellReviewBoardDTO();

				dto.setSell_review_board_cont(rs.getString("sell_review_board_cont"));
				dto.setSell_review_board_date(rs.getString("sell_review_board_date"));
				dto.setSell_review_board_file(rs.getString("sell_review_board_file"));
				dto.setSell_review_board_hit(rs.getInt("sell_review_board_hit"));
				dto.setSell_review_board_no(rs.getInt("sell_review_board_no"));
				dto.setSell_review_board_title(rs.getString("sell_review_board_title"));
				dto.setSell_review_board_update(rs.getString("sell_review_board_update"));
				dto.setSell_review_board_writer_id(rs.getString("sell_review_board_writer_id"));

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {

			closeConn(rs, pstmt, con);
		}
		return dto;
	}

	public int MypageSellReviewContentModifyOk(SellReviewBoardDTO dto) {

		int result = 0;

		try {

			openConn();

			sql = "update sell_review_board set sell_review_board_title = ?, sell_review_board_cont = ?, "
					+ "sell_review_board_update = ? , sell_review_board_file = ? " + "where sell_review_board_no = ?";

			pstmt = con.prepareStatement(sql);

			pstmt.setString(1, dto.getSell_review_board_title());
			pstmt.setString(2, dto.getSell_review_board_cont());
			pstmt.setString(3, dto.getSell_review_board_update());
			pstmt.setString(4, dto.getSell_review_board_file());
			pstmt.setInt(5, dto.getSell_review_board_no());

			result = pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConn(pstmt, con);
		}

		return result;

	}

	public int delete_mypagesellreviewcontent(int sell_review_board_no) {

		int result = 0;

		try {
			openConn();

			sql = "delete from sell_review_board where sell_review_board_no = ? ";

			pstmt = con.prepareStatement(sql);

			pstmt.setInt(1, sell_review_board_no);

			result = pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConn(pstmt, con);
		}

		return result;
	}

	public int MypageBuyReviewContentModifyOk(BuyReviewBoardDTO dto) {
		int result = 0;

		try {

			openConn();

			sql = "update buy_review_board set buy_review_board_title = ?, buy_review_board_cont = ?, buy_review_board_update = ? , buy_review_board_file = ? where buy_review_board_no = ?";

			pstmt = con.prepareStatement(sql);

			pstmt.setString(1, dto.getBuy_review_board_title());
			pstmt.setString(2, dto.getBuy_review_board_cont());
			pstmt.setString(3, dto.getBuy_review_board_update());
			pstmt.setString(4, dto.getBuy_review_board_file());
			pstmt.setInt(5, dto.getBuy_review_board_no());

			result = pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConn(pstmt, con);
		}

		return result;
	}

	public BuyReviewBoardDTO getMypageBuyReviewBoardContent(int buy_review_board_no) {

		BuyReviewBoardDTO dto = null;

		try {

			openConn();

			sql = "select * from buy_review_board where buy_review_board_no = ?";

			pstmt = con.prepareStatement(sql);

			pstmt.setInt(1, buy_review_board_no);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				dto = new BuyReviewBoardDTO();

				dto.setBuy_review_board_cont(rs.getString("buy_review_board_cont"));
				dto.setBuy_review_board_date(rs.getString("buy_review_board_date"));
				dto.setBuy_review_board_file(rs.getString("buy_review_board_file"));
				dto.setBuy_review_board_hit(rs.getInt("buy_review_board_hit"));
				dto.setBuy_review_board_no(rs.getInt("buy_review_board_no"));
				dto.setBuy_review_board_title(rs.getString("buy_review_board_title"));
				dto.setBuy_review_board_update(rs.getString("buy_review_board_update"));
				dto.setBuy_review_board_writer_id(rs.getString("buy_review_board_writer_id"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {

			closeConn(rs, pstmt, con);
		}

		return dto;
	}

	public int delete_mypagebuyreviewcontent(int buy_review_board_no) {
		int result = 0;

		try {
			openConn();

			sql = "delete from buy_review_board where buy_review_board_no = ? ";

			pstmt = con.prepareStatement(sql);

			pstmt.setInt(1, buy_review_board_no);

			result = pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConn(pstmt, con);
		}

		return result;
	}

	// 중고차 구매시 구매자의 결제 정보를 저장하는 매서드.
	public int insertPaymentData(PaymentDTO dto) {

		int result = 0, cnt = 0;

		try {

			openConn();

			sql = "select count(payment_no) from payment where payment_no like ?";

			pstmt = con.prepareStatement(sql);

			pstmt.setString(1, dto.getPayment_no() + "%");

			rs = pstmt.executeQuery();

			if (rs.next()) {
				cnt = rs.getInt(1) + 1;
			}

			sql = "insert into payment values(?, ?, ?, ?, ?, ?, ? ,? ,?)";

			pstmt = con.prepareStatement(sql);

			if (cnt < 10) {
				pstmt.setString(1, dto.getPayment_no() + "00" + cnt);
			} else if (cnt < 100) {
				pstmt.setString(1, dto.getPayment_no() + "0" + cnt);
			} else {
				pstmt.setString(1, dto.getPayment_no() + cnt);
			}

			pstmt.setString(2, dto.getPayment_buyer_no());
			pstmt.setString(3, dto.getPayment_method());
			pstmt.setString(4, dto.getPayment_card_company());
			pstmt.setString(5, dto.getPayment_card_no());
			pstmt.setInt(6, dto.getPayment_card_div_month());
			pstmt.setString(7, dto.getPayment_car_pwd());
			pstmt.setInt(8, dto.getPayment_amount());
			pstmt.setInt(9, dto.getPayment_sell_board_no());

			result = pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}
		return result;
	} // insertPaymentData() end.

	public void BuyReviewBoardHit(int buy_review_board_no) {

		try {
			openConn();

			sql = "update buy_review_board set buy_review_board_hit = buy_review_board_hit + 1 where buy_review_board_no = ?";

			pstmt = con.prepareStatement(sql);

			pstmt.setInt(1, buy_review_board_no);

			pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConn(pstmt, con);
		}

	}

	public List<BuyReviewBoardDTO> getBuyReviewBoardList(int buyreview_page, int buyreview_rowsize) {

		List<BuyReviewBoardDTO> list = new ArrayList<BuyReviewBoardDTO>();

		int buyreview_startNo = (buyreview_page * buyreview_rowsize) - (buyreview_rowsize - 1);

		int buyreview_endNo = buyreview_page * buyreview_rowsize;

		try {
			openConn();

			sql = "select * from (" + "select @ROWN:=@ROWN+1 AS RC, cb.* " + "from buy_review_board cb, "
					+ "(select @ROWN:=0) as clear_RC " + "order by cb.buy_review_board_no desc) as subquery "
					+ "where RC >= ? and RC <= ?";

			pstmt = con.prepareStatement(sql);

			pstmt.setInt(1, buyreview_startNo);
			pstmt.setInt(2, buyreview_endNo);

			rs = pstmt.executeQuery();

			while (rs.next()) {

				BuyReviewBoardDTO dto = new BuyReviewBoardDTO();

				dto.setBuy_review_board_no(rs.getInt("buy_review_board_no"));
				dto.setBuy_review_board_writer_id(rs.getString("buy_review_board_writer_id"));
				dto.setBuy_review_board_title(rs.getString("buy_review_board_title"));
				dto.setBuy_review_board_cont(rs.getString("buy_review_board_cont"));
				dto.setBuy_review_board_date(rs.getString("buy_review_board_date"));
				dto.setBuy_review_board_update(rs.getString("buy_review_board_update"));
				dto.setBuy_review_board_hit(rs.getInt("buy_review_board_hit"));

				list.add(dto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}

		return list;

	} // List<CommunityBoardDTO> getCommunityBoardList(int community_page, int
		// community_rowsize)

	// 자유게시판 검색리스트들을 카운트하는 메서드
	public int countCommunityBoardSearchList(String community_field, String community_keyword) {

		int cnt = 0;

		openConn();

		if (community_field.equals("전체")) {

			try {
				sql = "select count(*) from community_board where community_board_title like ? or community_board_cont like ?";

				pstmt = con.prepareStatement(sql);

				pstmt.setString(1, "%" + community_keyword + "%");
				pstmt.setString(2, "%" + community_keyword + "%");

				rs = pstmt.executeQuery();

				if (rs.next()) {
					cnt = rs.getInt(1);
				}

			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				closeConn(rs, pstmt, con);
			}

		} else if (community_field.equals("제목")) {

			try {
				sql = "select count(*) from community_board where community_board_title like ?";

				pstmt = con.prepareStatement(sql);

				pstmt.setString(1, "%" + community_keyword + "%");

				rs = pstmt.executeQuery();

				if (rs.next()) {
					cnt = rs.getInt(1);

				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				closeConn(rs, pstmt, con);
			}

		} else if (community_field.equals("내용")) {

			try {
				sql = "select count(*) from community_board where community_board_cont like ?";

				pstmt = con.prepareStatement(sql);

				pstmt.setString(1, "%" + community_keyword + "%");

				rs = pstmt.executeQuery();

				if (rs.next()) {
					cnt = rs.getInt(1);
				}

			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				closeConn(rs, pstmt, con);
			}

		}

		return cnt;
	} // countCommunityBoardSearchList(String community_field, String
		// community_keyword) end

	// 자유게시판 검색 기능 메서드
	public List<CommunityBoardDTO> searchCommunityBoardList(String community_field, String community_keyword, int page,
			int rowsize) {

		List<CommunityBoardDTO> list = new ArrayList<CommunityBoardDTO>();

		int startNo = (page * rowsize) - (rowsize - 1);

		int endNo = page * rowsize;

		openConn();

		if (community_field.equals("전체")) {

			try {
				sql = "select * from " + "(select @ROWN:=@ROWN+1 AS CN, " + "cb.* from community_board cb, "
						+ "(select @ROWN:=0) as clear_CN where " + "community_board_title like ? or "
						+ "community_board_cont like ? " + "order by cb.community_board_no desc) as subquery "
						+ "where CN >= ? and CN <= ?";

				pstmt = con.prepareStatement(sql);

				pstmt.setString(1, "%" + community_keyword + "%");
				pstmt.setString(2, "%" + community_keyword + "%");
				pstmt.setInt(3, startNo);
				pstmt.setInt(4, endNo);

				rs = pstmt.executeQuery();

				while (rs.next()) {
					CommunityBoardDTO dto = new CommunityBoardDTO();

					dto.setCommunity_board_no(rs.getInt("community_board_no"));
					dto.setCommunity_board_writer_id(rs.getString("community_board_writer_id"));
					dto.setCommunity_board_title(rs.getString("community_board_title"));
					dto.setCommunity_board_cont(rs.getString("community_board_cont"));
					dto.setCommunity_board_date(rs.getString("community_board_date"));
					dto.setCommunity_board_update(rs.getString("community_board_update"));
					dto.setCommunity_board_hit(rs.getInt("community_board_hit"));

					list.add(dto);
				}

			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				closeConn(rs, pstmt, con);
			}

		} else if (community_field.equals("제목")) {

			try {
				sql = "select * from " + "(select @ROWN:=@ROWN+1 AS CN, " + "cb.* from community_board cb, "
						+ "(select @ROWN:=0) as clear_CN where " + "community_board_title like ?"
						+ "order by cb.community_board_no desc) as subquery " + "where CN >= ? and CN <= ?";

				pstmt = con.prepareStatement(sql);

				pstmt.setString(1, "%" + community_keyword + "%");
				pstmt.setInt(2, startNo);
				pstmt.setInt(3, endNo);

				rs = pstmt.executeQuery();

				while (rs.next()) {

					CommunityBoardDTO dto = new CommunityBoardDTO();

					dto.setCommunity_board_no(rs.getInt("community_board_no"));
					dto.setCommunity_board_writer_id(rs.getString("community_board_writer_id"));
					dto.setCommunity_board_title(rs.getString("community_board_title"));
					dto.setCommunity_board_cont(rs.getString("community_board_cont"));
					dto.setCommunity_board_date(rs.getString("community_board_date"));
					dto.setCommunity_board_update(rs.getString("community_board_update"));
					dto.setCommunity_board_hit(rs.getInt("community_board_hit"));

					list.add(dto);
				}

			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				closeConn(rs, pstmt, con);
			}

		} else if (community_field.equals("내용")) {

			try {
				sql = "select * from (select @ROWN:=@ROWN+1 AS CN, " + "cb.* from community_board cb, "
						+ "(select @ROWN:=0) as clear_CN where " + "community_board_cont like ?"
						+ "order by cb.community_board_no desc) as subquery " + "where CN >= ? and CN <= ?";

				pstmt = con.prepareStatement(sql);

				pstmt.setString(1, "%" + community_keyword + "%");
				pstmt.setInt(2, startNo);
				pstmt.setInt(3, endNo);

				rs = pstmt.executeQuery();

				while (rs.next()) {
					CommunityBoardDTO dto = new CommunityBoardDTO();

					dto.setCommunity_board_no(rs.getInt("community_board_no"));
					dto.setCommunity_board_writer_id(rs.getString("community_board_writer_id"));
					dto.setCommunity_board_title(rs.getString("community_board_title"));
					dto.setCommunity_board_cont(rs.getString("community_board_cont"));
					dto.setCommunity_board_date(rs.getString("community_board_date"));
					dto.setCommunity_board_update(rs.getString("community_board_update"));
					dto.setCommunity_board_hit(rs.getInt("community_board_hit"));

					list.add(dto);
				}

			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				closeConn(rs, pstmt, con);
			}
		}

		return list;
	} // List<CommunityBoardDTO> searchCommunityBoardList end

	// 판매 딜러의 정보를 가져오는 매서드.
	public AdminDTO getDealerInfo(String admin_no) {

		AdminDTO dto = null;

		try {

			openConn();

			sql = "select * from admin where admin_no = ?";

			pstmt = con.prepareStatement(sql);

			pstmt.setString(1, admin_no);

			rs = pstmt.executeQuery();

			if (rs.next()) {

				dto = new AdminDTO();

				dto.setAdmin_no(rs.getString("admin_no"));
				dto.setAdmin_name(rs.getString("admin_name"));
				dto.setAdmin_job(rs.getString("admin_job"));
				dto.setAdmin_phone(rs.getString("admin_phone"));

			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}

		return dto;
	} // getDealerInfo(String admin_no) end.

	// 판매후기 게시판 검색리스트들을 카운트 하는 메서드
	public int countSellReviewBoardSearchList(String sellReview_field, String sellReview_keyword) {

		int cnt = 0;

		openConn();

		if (sellReview_field.equals("전체")) {

			try {
				sql = "select count(*) from sell_review_board where sell_review_board_title like ? or sell_review_board_cont like ?";

				pstmt = con.prepareStatement(sql);

				pstmt.setString(1, "%" + sellReview_keyword + "%");
				pstmt.setString(2, "%" + sellReview_keyword + "%");

				rs = pstmt.executeQuery();

				if (rs.next()) {
					cnt = rs.getInt(1);
				}

			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				closeConn(rs, pstmt, con);
			}
		} else if (sellReview_field.equals("제목")) {

			try {
				sql = "select count(*) from sell_review_board where sell_review_board_title like ?";

				pstmt = con.prepareStatement(sql);

				pstmt.setString(1, "%" + sellReview_keyword + "%");

				rs = pstmt.executeQuery();

				if (rs.next()) {
					cnt = rs.getInt(1);
				}

			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				closeConn(rs, pstmt, con);
			}
		} else if (sellReview_field.equals("내용")) {

			try {
				sql = "select count(*) from sell_review_board where sell_review_board_cont like ?";

				pstmt = con.prepareStatement(sql);

				pstmt.setString(1, "%" + sellReview_keyword + "%");

				rs = pstmt.executeQuery();

				if (rs.next()) {
					cnt = rs.getInt(1);
				}

			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				closeConn(rs, pstmt, con);
			}
		}
		return cnt;
	} // int countSellReviewBoardSearchList(String sellReview_field, String
		// sellReview_keyword) end

	// 판매후기 게시판 검색 기능 메서드
	public List<SellReviewBoardDTO> searchSellReviewBoardList(String sellReview_field, String sellReview_keyword,
			int page, int rowsize) {

		List<SellReviewBoardDTO> list = new ArrayList<SellReviewBoardDTO>();

		int startNo = (page * rowsize) - (rowsize - 1);

		int endNo = page * rowsize;

		openConn();

		if (sellReview_field.equals("전체")) {

			try {
				sql = "select * from (select @ROWN:=@ROWN+1 AS SN, " + "sb.* from sell_review_board sb, "
						+ "(select @ROWN:=0) as clear_SN where " + "sell_review_board_title like ? or "
						+ "sell_review_board_cont like ?" + "order by sb.sell_review_board_no desc) as subquery "
						+ "where SN >= ? and SN <= ?";

				pstmt = con.prepareStatement(sql);

				pstmt.setString(1, "%" + sellReview_keyword + "%");
				pstmt.setString(2, "%" + sellReview_keyword + "%");
				pstmt.setInt(3, startNo);
				pstmt.setInt(4, endNo);

				rs = pstmt.executeQuery();

				while (rs.next()) {

					SellReviewBoardDTO dto = new SellReviewBoardDTO();

					dto.setSell_review_board_no(rs.getInt("sell_review_board_no"));
					dto.setSell_review_board_writer_id(rs.getString("sell_review_board_writer_id"));
					dto.setSell_review_board_title(rs.getString("sell_review_board_title"));
					dto.setSell_review_board_cont(rs.getString("sell_review_board_cont"));
					dto.setSell_review_board_date(rs.getString("sell_review_board_date"));
					dto.setSell_review_board_update(rs.getString("sell_review_board_update"));
					dto.setSell_review_board_hit(rs.getInt("sell_review_board_hit"));
					dto.setSell_review_board_file(rs.getString("sell_review_board_file"));
					dto.setSell_review_transaction_no(rs.getInt("sell_review_transaction_no"));

					list.add(dto);
				}

			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				closeConn(rs, pstmt, con);
			}
		} else if (sellReview_field.equals("제목")) {

			try {
				sql = "select * from (select @ROWN:=@ROWN+1 AS SN, " + "sb.* from sell_review_board sb, "
						+ "(select @ROWN:=0) as clear_SN where " + "sell_review_board_title like ?"
						+ "order by sb.sell_review_board_no desc) as subquery " + "where SN >= ? and SN <= ?";

				pstmt = con.prepareStatement(sql);

				pstmt.setString(1, "%" + sellReview_keyword + "%");
				pstmt.setInt(2, startNo);
				pstmt.setInt(3, endNo);

				rs = pstmt.executeQuery();

				while (rs.next()) {

					SellReviewBoardDTO dto = new SellReviewBoardDTO();

					dto.setSell_review_board_no(rs.getInt("sell_review_board_no"));
					dto.setSell_review_board_writer_id(rs.getString("sell_review_board_writer_id"));
					dto.setSell_review_board_title(rs.getString("sell_review_board_title"));
					dto.setSell_review_board_cont(rs.getString("sell_review_board_cont"));
					dto.setSell_review_board_date(rs.getString("sell_review_board_date"));
					dto.setSell_review_board_update(rs.getString("sell_review_board_update"));
					dto.setSell_review_board_hit(rs.getInt("sell_review_board_hit"));
					dto.setSell_review_board_file(rs.getString("sell_review_board_file"));
					dto.setSell_review_transaction_no(rs.getInt("sell_review_transaction"));

					list.add(dto);
				}

			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				closeConn(rs, pstmt, con);
			}

		} else if (sellReview_field.equals("내용")) {

			try {
				sql = "select * from (select @ROWN:=@ROWN+1 AS SN, " + "sb.* from sell_review_board sb, "
						+ "(select @ROWN:=0) as clear_SN where " + "sell_review_board_cont like ?"
						+ "order by sb.sell_review_board_no desc) as subquery " + "where SN >= ? and SN <= ?";

				pstmt = con.prepareStatement(sql);

				pstmt.setString(1, "%" + sellReview_keyword + "%");
				pstmt.setInt(2, startNo);
				pstmt.setInt(3, endNo);

				rs = pstmt.executeQuery();

				while (rs.next()) {

					SellReviewBoardDTO dto = new SellReviewBoardDTO();

					dto.setSell_review_board_no(rs.getInt("sell_review_board_no"));
					dto.setSell_review_board_writer_id(rs.getString("sell_review_board_writer_id"));
					dto.setSell_review_board_title(rs.getString("sell_review_board_title"));
					dto.setSell_review_board_cont(rs.getString("sell_review_board_cont"));
					dto.setSell_review_board_date(rs.getString("sell_review_board_date"));
					dto.setSell_review_board_update(rs.getString("sell_review_board_update"));
					dto.setSell_review_board_hit(rs.getInt("sell_review_board_hit"));
					dto.setSell_review_board_file(rs.getString("sell_review_board_file"));
					dto.setSell_review_transaction_no(rs.getInt("sell_review_transaction"));

					list.add(dto);
				}

			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				closeConn(rs, pstmt, con);
			}
		}

		return list;
	} // List<SellReviewBoardDTO> searchSellReviewBoardList(String sellReview_field,
		// String sellReview_keyword, end

	// 구매후기 게시판 검색리스트 조회하는 메서드
	public List<BuyReviewBoardDTO> searchBuyReviewBoardList(String buyreview_field, String buyreview_keyword,
			int buyreview_page, int buyreview_rowsize) {

		List<BuyReviewBoardDTO> list = new ArrayList<BuyReviewBoardDTO>();

		int startNo = (buyreview_page * buyreview_rowsize) - (buyreview_rowsize - 1);

		int endNo = buyreview_page * buyreview_rowsize;

		openConn();

		if (buyreview_field.equals("전체")) {

			try {
				sql = "select * from (select @ROWN:=@ROWN+1 AS CN, " + "cb.* from buy_review_board cb, "
						+ "(select @ROWN:=0) as clear_CN where " + "buy_review_board_title like ? or "
						+ "buy_review_board_cont like ? " + "order by cb.buy_review_board_no desc) as subquery "
						+ "where CN >= ? and CN <= ?";

				pstmt = con.prepareStatement(sql);

				pstmt.setString(1, "%" + buyreview_keyword + "%");
				pstmt.setString(2, "%" + buyreview_keyword + "%");
				pstmt.setInt(3, startNo);
				pstmt.setInt(4, endNo);

				rs = pstmt.executeQuery();

				while (rs.next()) {
					BuyReviewBoardDTO dto = new BuyReviewBoardDTO();

					dto.setBuy_review_board_no(rs.getInt("buy_review_board_no"));
					dto.setBuy_review_board_writer_id(rs.getString("buy_review_board_writer_id"));
					dto.setBuy_review_board_title(rs.getString("buy_review_board_title"));
					dto.setBuy_review_board_cont(rs.getString("buy_review_board_cont"));
					dto.setBuy_review_board_date(rs.getString("buy_review_board_date"));
					dto.setBuy_review_board_update(rs.getString("buy_review_board_update"));
					dto.setBuy_review_board_hit(rs.getInt("buy_review_board_hit"));

					list.add(dto);
				}

			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				closeConn(rs, pstmt, con);
			}

		} else if (buyreview_field.equals("제목")) {

			try {
				sql = "select * from (select @ROWN:=@ROWN+1 AS CN, " + "cb.* from buy_review_board cb, "
						+ "(select @ROWN:=0) as clear_CN where " + "buy_review_board_title like ?"
						+ "order by cb.buy_review_board_no desc) as subquery " + "where CN >= ? and CN <= ?";

				pstmt = con.prepareStatement(sql);

				pstmt.setString(1, "%" + buyreview_keyword + "%");
				pstmt.setInt(2, startNo);
				pstmt.setInt(3, endNo);

				rs = pstmt.executeQuery();

				while (rs.next()) {

					BuyReviewBoardDTO dto = new BuyReviewBoardDTO();

					dto.setBuy_review_board_no(rs.getInt("buy_review_board_no"));
					dto.setBuy_review_board_writer_id(rs.getString("buy_review_board_writer_id"));
					dto.setBuy_review_board_title(rs.getString("buy_review_board_title"));
					dto.setBuy_review_board_cont(rs.getString("buy_review_board_cont"));
					dto.setBuy_review_board_date(rs.getString("buy_review_board_date"));
					dto.setBuy_review_board_update(rs.getString("buy_review_board_update"));
					dto.setBuy_review_board_hit(rs.getInt("buy_review_board_hit"));

					list.add(dto);
				}

			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				closeConn(rs, pstmt, con);
			}

		} else if (buyreview_field.equals("내용")) {

			try {
				sql = "select * from (select @ROWN:=@ROWN+1 AS CN, " + "cb.* from buy_review_board cb, "
						+ "(select @ROWN:=0) as clear_CN where " + "buy_review_board_cont like ?"
						+ "order by cb.buy_review_board_no desc) as subquery " + "where CN >= ? and CN <= ?";

				pstmt = con.prepareStatement(sql);

				pstmt.setString(1, "%" + buyreview_keyword + "%");
				pstmt.setInt(2, startNo);
				pstmt.setInt(3, endNo);

				rs = pstmt.executeQuery();

				while (rs.next()) {
					BuyReviewBoardDTO dto = new BuyReviewBoardDTO();

					dto.setBuy_review_board_no(rs.getInt("buy_review_board_no"));
					dto.setBuy_review_board_writer_id(rs.getString("buy_review_board_writer_id"));
					dto.setBuy_review_board_title(rs.getString("buy_review_board_title"));
					dto.setBuy_review_board_cont(rs.getString("buy_review_board_cont"));
					dto.setBuy_review_board_date(rs.getString("buy_review_board_date"));
					dto.setBuy_review_board_update(rs.getString("buy_review_board_update"));
					dto.setBuy_review_board_hit(rs.getInt("buy_review_board_hit"));

					list.add(dto);
				}

			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				closeConn(rs, pstmt, con);
			}
		}

		return list;

	}

	// 구매후기 게시판 검색리스트 카운트하는 메서드
	public int countBuyReviewBoardSearchList(String buyreview_field, String buyreview_keyword) {
		int cnt = 0;

		openConn();

		if (buyreview_field.equals("전체")) {

			try {
				sql = "select count(*) from buy_review_board where buy_review_board_title like ? or buy_review_board_cont like ?";

				pstmt = con.prepareStatement(sql);

				pstmt.setString(1, "%" + buyreview_keyword + "%");
				pstmt.setString(2, "%" + buyreview_keyword + "%");

				rs = pstmt.executeQuery();

				if (rs.next()) {
					cnt = rs.getInt(1);
				}

			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				closeConn(rs, pstmt, con);
			}

		} else if (buyreview_field.equals("제목")) {

			try {
				sql = "select count(*) from buy_review_board where buy_review_board_title like ?";

				pstmt = con.prepareStatement(sql);

				pstmt.setString(1, "%" + buyreview_keyword + "%");

				rs = pstmt.executeQuery();

				if (rs.next()) {
					cnt = rs.getInt(1);

				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				closeConn(rs, pstmt, con);
			}

		} else if (buyreview_field.equals("내용")) {

			try {
				sql = "select count(*) from buy_review_board where buy_review_board_cont like ?";

				pstmt = con.prepareStatement(sql);

				pstmt.setString(1, "%" + buyreview_keyword + "%");

				rs = pstmt.executeQuery();

				if (rs.next()) {
					cnt = rs.getInt(1);
				}

			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				closeConn(rs, pstmt, con);
			}

		}

		return cnt;
	}

	// 페이징 처리를 위한 요청 게시판 총 갯수 카운트 하는 매서드.
	public int countReqSellBoardList() {

		int cnt = 0;

		try {

			openConn();

			sql = "select count(req_sell_board_no) from req_sell_board";

			pstmt = con.prepareStatement(sql);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				cnt = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}

		return cnt;
	} // countReqSellBoardList() end.

	// 검색 후 페이징 처리를 위한 판매요청 카운트 매서드.
	   public int countReqSellBoardList(String field, String keyword) {

	      int cnt = 0;

	      openConn();

	      if (field.equals("전체")) {

	         try {

	            sql = "select count(req_sell_board_no) from req_sell_board where " + "req_sell_board_user_no like ? or "
	                  + "req_sell_board_car_std_no like ? or " + "req_sell_board_car_detail like ? or "
	                  + "req_sell_board_car_no like ?";

	            pstmt = con.prepareStatement(sql);

	            pstmt.setString(1, "%" + keyword + "%");
	            pstmt.setString(2, "%" + keyword + "%");
	            pstmt.setString(3, "%" + keyword + "%");
	            pstmt.setString(4, "%" + keyword + "%");

	            rs = pstmt.executeQuery();

	            if (rs.next()) {
	               cnt = rs.getInt(1);
	            }

	         } catch (SQLException e) {
	            e.printStackTrace();
	         } finally {
	            closeConn(rs, pstmt, con);
	         }

	      } else if (field.equals("제조사") || field.equals("차종") || field.equals("연료") || field.equals("색상")
	            || field.equals("모델명")) {

	         try {

	            sql = "select count(req_sell_board_no) from req_sell_board where req_sell_board_car_std_no like ?";

	            pstmt = con.prepareStatement(sql);

	            pstmt.setString(1, "%" + keyword + "%");

	            rs = pstmt.executeQuery();

	            if (rs.next()) {
	               cnt = rs.getInt(1);
	            }
	         } catch (SQLException e) {
	            e.printStackTrace();
	         } finally {
	            closeConn(rs, pstmt, con);
	         }

	      } else if (field.equals("내용")) {

	         try {

	            sql = "select count(req_sell_board_no) from req_sell_board where req_sell_board_car_detail like ?";

	            pstmt = con.prepareStatement(sql);

	            pstmt.setString(1, "%" + keyword + "%");

	            rs = pstmt.executeQuery();

	            if (rs.next()) {
	               cnt = rs.getInt(1);
	            }

	         } catch (SQLException e) {
	            e.printStackTrace();
	         } finally {
	            closeConn(rs, pstmt, con);
	         }
	      }
	      return cnt;
	   } // countReqSellBoardList(field, keyword) end.

	// 관리자 페이지의 판매요청 리스트 페이징 처리를 위한 매서드.
	public List<ReqSellBoardDTO> getReqSellBoardList(int page, int rowsize) {

		List<ReqSellBoardDTO> list = new ArrayList<ReqSellBoardDTO>();

		int startNo = (page * rowsize) - (rowsize - 1);

		int endNo = (page * rowsize);

		try {

			openConn();

			sql = "select * from (" + "select @ROWN:=@ROWN+1 AS RN, rb.* " + "from req_sell_board rb, "
					+ "(select @ROWN:=0) as clear_RN " + "order by rb.req_sell_board_no desc) as subquery "
					+ "where RN >= ? and RN <= ?";

			pstmt = con.prepareStatement(sql);

			pstmt.setInt(1, startNo);
			pstmt.setInt(2, endNo);

			rs = pstmt.executeQuery();

			while (rs.next()) {

				ReqSellBoardDTO dto = new ReqSellBoardDTO();

				dto.setReq_sell_board_no(rs.getInt("req_sell_board_no"));
				dto.setReq_sell_board_user_no(rs.getString("req_sell_board_user_no"));
				dto.setReq_sell_board_car_std_no(rs.getString("req_sell_board_car_std_no"));
				dto.setReq_sell_board_car_no(rs.getString("req_sell_board_car_no"));
				dto.setReq_sell_board_car_distance(rs.getInt("req_sell_board_car_distance"));
				dto.setReq_sell_board_car_price(rs.getInt("req_sell_board_car_price"));
				dto.setReq_sell_board_car_detail(rs.getString("req_sell_board_car_detail"));
				dto.setReq_sell_board_date(rs.getString("req_sell_board_date"));
				dto.setReq_sell_board_update(rs.getString("req_sell_board_update"));
				dto.setReq_sell_board_car_file(rs.getString("req_sell_board_car_file"));
				dto.setReq_sell_board_status(rs.getBoolean("req_sell_board_status"));

				list.add(dto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}
		return list;
	} // getReqSellBoardList(page, rowsize) end.

	// 검색 후 페이징 처리를 위한 판매요청 리스트 매서드.
	   public List<ReqSellBoardDTO> getReqSellBoardList(String field, String keyword, int page, int rowsize) {

	      List<ReqSellBoardDTO> list = new ArrayList<ReqSellBoardDTO>();

	      ReqSellBoardDTO dto = null;

	      int startNo = (page * rowsize) - (rowsize - 1);

	      int endNo = (page * rowsize);

	      openConn();

	      if (field.equals("전체")) {

	         try {

	            sql = "select * from (select @ROWN:=@ROWN+1 AS RN, " + "rb.* from req_sell_board rb, "
	                  + "(select @ROWN:=0) as clear_RN where " + "req_sell_board_user_no like ? or "
	                  + "req_sell_board_car_std_no like ? or " + "req_sell_board_car_detail like ? or "
	                  + "req_sell_board_car_no like ? " + "order by rb.req_sell_board_no desc) as subquery "
	                  + "where RN >= ? and RN <= ?";

	            pstmt = con.prepareStatement(sql);

	            pstmt.setString(1, "%" + keyword + "%");
	            pstmt.setString(2, "%" + keyword + "%");
	            pstmt.setString(3, "%" + keyword + "%");
	            pstmt.setString(4, "%" + keyword + "%");
	            pstmt.setInt(5, startNo);
	            pstmt.setInt(6, endNo);

	            rs = pstmt.executeQuery();

	            while (rs.next()) {

	               dto = new ReqSellBoardDTO();

	               dto.setReq_sell_board_no(rs.getInt("req_sell_board_no"));
	               dto.setReq_sell_board_user_no(rs.getString("req_sell_board_user_no"));
	               dto.setReq_sell_board_car_std_no(rs.getString("req_sell_board_car_std_no"));
	               dto.setReq_sell_board_car_no(rs.getString("req_sell_board_car_no"));
	               dto.setReq_sell_board_car_distance(rs.getInt("req_sell_board_car_distance"));
	               dto.setReq_sell_board_car_price(rs.getInt("req_sell_board_car_price"));
	               dto.setReq_sell_board_car_detail(rs.getString("req_sell_board_car_detail"));
	               dto.setReq_sell_board_date(rs.getString("req_sell_board_date"));
	               dto.setReq_sell_board_update(rs.getString("req_sell_board_update"));
	               dto.setReq_sell_board_car_file(rs.getString("req_sell_board_car_file"));
	               dto.setReq_sell_board_status(rs.getBoolean("req_sell_board_status"));

	               list.add(dto);
	            }

	         } catch (SQLException e) {
	            e.printStackTrace();
	         } finally {
	            closeConn(rs, pstmt, con);
	         }

	      } else if (field.equals("제조사") || field.equals("차종") || field.equals("연료") || field.equals("색상")
	            || field.equals("모델명")) {

	         try {

	            sql = "select * from (select @ROWN:=@ROWN+1 AS RN, " + "rb.* from req_sell_board rb, "
	                  + "(select @ROWN:=0) as clear_RN where " + "req_sell_board_car_std_no like ? "
	                  + "order by rb.req_sell_board_no desc) as subquery " + "where RN >= ? and RN <= ?";

	            pstmt = con.prepareStatement(sql);

	            pstmt.setString(1, "%" + keyword + "%");
	            pstmt.setInt(2, startNo);
	            pstmt.setInt(3, endNo);

	            rs = pstmt.executeQuery();

	            while (rs.next()) {

	               dto = new ReqSellBoardDTO();

	               dto.setReq_sell_board_no(rs.getInt("req_sell_board_no"));
	               dto.setReq_sell_board_user_no(rs.getString("req_sell_board_user_no"));
	               dto.setReq_sell_board_car_std_no(rs.getString("req_sell_board_car_std_no"));
	               dto.setReq_sell_board_car_no(rs.getString("req_sell_board_car_no"));
	               dto.setReq_sell_board_car_distance(rs.getInt("req_sell_board_car_distance"));
	               dto.setReq_sell_board_car_price(rs.getInt("req_sell_board_car_price"));
	               dto.setReq_sell_board_car_detail(rs.getString("req_sell_board_car_detail"));
	               dto.setReq_sell_board_date(rs.getString("req_sell_board_date"));
	               dto.setReq_sell_board_update(rs.getString("req_sell_board_update"));
	               dto.setReq_sell_board_car_file(rs.getString("req_sell_board_car_file"));
	               dto.setReq_sell_board_status(rs.getBoolean("req_sell_board_status"));

	               list.add(dto);
	            }

	         } catch (SQLException e) {
	            e.printStackTrace();
	         } finally {
	            closeConn(rs, pstmt, con);
	         }

	      } else if (field.equals("내용")) {

	         try {

	            sql = "select * from (select @ROWN:=@ROWN+1 AS RN, " + "rb.* from req_sell_board rb, "
	                  + "(select @ROWN:=0) as clear_RN where " + "req_sell_board_car_detail like ? "
	                  + "order by rb.req_sell_board_no desc) as subquery " + "where RN >= ? and RN <= ?";

	            pstmt = con.prepareStatement(sql);

	            pstmt.setString(1, "%" + keyword + "%");
	            pstmt.setInt(2, startNo);
	            pstmt.setInt(3, endNo);

	            rs = pstmt.executeQuery();

	            while (rs.next()) {

	               dto = new ReqSellBoardDTO();

	               dto.setReq_sell_board_no(rs.getInt("req_sell_board_no"));
	               dto.setReq_sell_board_user_no(rs.getString("req_sell_board_user_no"));
	               dto.setReq_sell_board_car_std_no(rs.getString("req_sell_board_car_std_no"));
	               dto.setReq_sell_board_car_no(rs.getString("req_sell_board_car_no"));
	               dto.setReq_sell_board_car_distance(rs.getInt("req_sell_board_car_distance"));
	               dto.setReq_sell_board_car_price(rs.getInt("req_sell_board_car_price"));
	               dto.setReq_sell_board_car_detail(rs.getString("req_sell_board_car_detail"));
	               dto.setReq_sell_board_date(rs.getString("req_sell_board_date"));
	               dto.setReq_sell_board_update(rs.getString("req_sell_board_update"));
	               dto.setReq_sell_board_car_file(rs.getString("req_sell_board_car_file"));
	               dto.setReq_sell_board_status(rs.getBoolean("req_sell_board_status"));

	               list.add(dto);
	            }

	         } catch (SQLException e) {
	            e.printStackTrace();
	         } finally {
	            closeConn(rs, pstmt, con);
	         }
	      }
	      return list;
	   } // getReqSellBoardList(field, keyword, page, rowsize) end.

	// 문의함 페이지의 페이징 처리를 위한 카운트 매서드.
	public int countQnaBoardList() {

		int cnt = 0;

		try {

			openConn();

			sql = "select count(qna_board_no) from qna_board";

			pstmt = con.prepareStatement(sql);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				cnt = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}

		return cnt;
	} // countQnaBoardList() end.

	// 문의 리스트 페이징 처리 매서드.
	public List<QnaBoardDTO> getQnaBoardList(int page, int rowsize) {

		List<QnaBoardDTO> list = new ArrayList<QnaBoardDTO>();

		int startNo = (page * rowsize) - (rowsize - 1);

		int endNo = (page * rowsize);

		try {

			openConn();

			sql = "select * from (" + "select @ROWN:=@ROWN+1 AS RN, qb.* " + "from qna_board qb, "
					+ "(select @ROWN:=0) as clear_RN " + "order by qb.qna_board_no desc) as subquery "
					+ "where RN >= ? and RN <= ?";

			pstmt = con.prepareStatement(sql);

			pstmt.setInt(1, startNo);
			pstmt.setInt(2, endNo);

			rs = pstmt.executeQuery();

			while (rs.next()) {

				QnaBoardDTO dto = new QnaBoardDTO();

				dto.setQna_board_no(rs.getInt("qna_board_no"));
				dto.setQna_board_type(rs.getString("qna_board_type"));
				dto.setQna_board_writer_id(rs.getString("qna_board_writer_id"));
				dto.setQna_board_title(rs.getString("qna_board_title"));
				dto.setQna_board_cont(rs.getString("qna_board_cont"));
				dto.setQna_board_date(rs.getString("qna_board_date"));
				dto.setQna_board_update(rs.getString("qna_board_update"));
				dto.setQna_board_file(rs.getString("qna_board_file"));
				dto.setQna_board_status(rs.getBoolean("qna_board_status"));

				list.add(dto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}

		return list;
	} // getQnaBoardList(page, rowsize) end.

	// 검색 후 페이징 처리를 위한 문의 리스트 카운터 매서드.
	public int countQnaBoardList(String field, String keyword) {

		int cnt = 0;

		openConn();

		if (field.equals("전체")) {

			try {

				sql = "select count(qna_board_no) from qna_board where " + "qna_board_type like ? or "
						+ "qna_board_writer_id like ? or " + "qna_board_title like ? or " + "qna_board_cont like ?";

				pstmt = con.prepareStatement(sql);

				pstmt.setString(1, "%" + keyword + "%");
				pstmt.setString(2, "%" + keyword + "%");
				pstmt.setString(3, "%" + keyword + "%");
				pstmt.setString(4, "%" + keyword + "%");

				rs = pstmt.executeQuery();

				if (rs.next()) {
					cnt = rs.getInt(1);
				}

			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				closeConn(rs, pstmt, con);
			}

		} else if (field.equals("문의유형")) {

			try {

				sql = "select count(qna_board_no) from qna_board where qna_board_type like ?";

				pstmt = con.prepareStatement(sql);

				pstmt.setString(1, "%" + keyword + "%");

				rs = pstmt.executeQuery();

				if (rs.next()) {
					cnt = rs.getInt(1);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				closeConn(rs, pstmt, con);
			}

		} else if (field.equals("작성자")) {

			try {

				sql = "select count(qna_board_no) from qna_board where qna_board_writer_id like ?";

				pstmt = con.prepareStatement(sql);

				pstmt.setString(1, "%" + keyword + "%");

				rs = pstmt.executeQuery();

				if (rs.next()) {
					cnt = rs.getInt(1);
				}

			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				closeConn(rs, pstmt, con);
			}

		} else if (field.equals("제목")) {

			try {

				sql = "select count(qna_board_no) from qna_board where qna_board_title like ?";

				pstmt = con.prepareStatement(sql);

				pstmt.setString(1, "%" + keyword + "%");

				rs = pstmt.executeQuery();

				if (rs.next()) {
					cnt = rs.getInt(1);
				}

			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				closeConn(rs, pstmt, con);
			}

		} else if (field.equals("내용")) {

			try {

				sql = "select count(qna_board_no) from qna_board where qna_board_cont like ?";

				pstmt = con.prepareStatement(sql);

				pstmt.setString(1, "%" + keyword + "%");

				rs = pstmt.executeQuery();

				if (rs.next()) {
					cnt = rs.getInt(1);
				}

			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				closeConn(rs, pstmt, con);
			}
		}

		return cnt;
	} // countQnaBoardList(field, keyword) end.

	// 검색 후 문의 리스트 페이징 처리 매서드.
	public List<QnaBoardDTO> getQnaBoardList(String field, String keyword, int page, int rowsize) {

		List<QnaBoardDTO> list = new ArrayList<QnaBoardDTO>();

		int startNo = (page * rowsize) - (rowsize - 1);

		int endNo = (page * rowsize);

		openConn();

		if (field.equals("전체")) {

			try {

				sql = "select * from (" + "select @ROWN:=@ROWN+1 AS RN, qb.* from qna_board qb, "
						+ "(select @ROWN:=0) as clear_RN where " + "qna_board_type like ? or "
						+ "qna_board_writer_id like ? or " + "qna_board_title like ? or " + "qna_board_cont like ? "
						+ "order by qb.qna_board_no desc) as subquery " + "where RN >= ? and RN <= ?";

				pstmt = con.prepareStatement(sql);

				pstmt.setString(1, "%" + keyword + "%");
				pstmt.setString(2, "%" + keyword + "%");
				pstmt.setString(3, "%" + keyword + "%");
				pstmt.setString(4, "%" + keyword + "%");
				pstmt.setInt(5, startNo);
				pstmt.setInt(6, endNo);

				rs = pstmt.executeQuery();

				while (rs.next()) {

					QnaBoardDTO dto = new QnaBoardDTO();

					dto.setQna_board_no(rs.getInt("qna_board_no"));
					dto.setQna_board_type(rs.getString("qna_board_type"));
					dto.setQna_board_writer_id(rs.getString("qna_board_writer_id"));
					dto.setQna_board_title(rs.getString("qna_board_title"));
					dto.setQna_board_cont(rs.getString("qna_board_cont"));
					dto.setQna_board_date(rs.getString("qna_board_date"));
					dto.setQna_board_update(rs.getString("qna_board_update"));
					dto.setQna_board_file(rs.getString("qna_board_file"));
					dto.setQna_board_status(rs.getBoolean("qna_board_status"));

					list.add(dto);
				}

			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				closeConn(rs, pstmt, con);
			}

		} else if (field.equals("문의유형")) {

			try {

				sql = "select * from (" + "select @ROWN:=@ROWN+1 AS RN, qb.* from qna_board qb, "
						+ "(select @ROWN:=0) as clear_RN where " + "qna_board_type like ? "
						+ "order by qb.qna_board_no desc) as subquery " + "where RN >= ? and RN <= ?";

				pstmt = con.prepareStatement(sql);

				pstmt.setString(1, "%" + keyword + "%");
				pstmt.setInt(2, startNo);
				pstmt.setInt(3, endNo);

				rs = pstmt.executeQuery();

				while (rs.next()) {

					QnaBoardDTO dto = new QnaBoardDTO();

					dto.setQna_board_no(rs.getInt("qna_board_no"));
					dto.setQna_board_type(rs.getString("qna_board_type"));
					dto.setQna_board_writer_id(rs.getString("qna_board_writer_id"));
					dto.setQna_board_title(rs.getString("qna_board_title"));
					dto.setQna_board_cont(rs.getString("qna_board_cont"));
					dto.setQna_board_date(rs.getString("qna_board_date"));
					dto.setQna_board_update(rs.getString("qna_board_update"));
					dto.setQna_board_file(rs.getString("qna_board_file"));
					dto.setQna_board_status(rs.getBoolean("qna_board_status"));

					list.add(dto);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				closeConn(rs, pstmt, con);
			}

		} else if (field.equals("작성자")) {

			try {

				sql = "select * from (" + "select @ROWN:=@ROWN+1 AS RN, qb.* from qna_board qb, "
						+ "(select @ROWN:=0) as clear_RN where " + "qna_board_writer_id like ? "
						+ "order by qb.qna_board_no desc) as subquery " + "where RN >= ? and RN <= ?";

				pstmt = con.prepareStatement(sql);

				pstmt.setString(1, "%" + keyword + "%");
				pstmt.setInt(2, startNo);
				pstmt.setInt(3, endNo);

				rs = pstmt.executeQuery();

				while (rs.next()) {

					QnaBoardDTO dto = new QnaBoardDTO();

					dto.setQna_board_no(rs.getInt("qna_board_no"));
					dto.setQna_board_type(rs.getString("qna_board_type"));
					dto.setQna_board_writer_id(rs.getString("qna_board_writer_id"));
					dto.setQna_board_title(rs.getString("qna_board_title"));
					dto.setQna_board_cont(rs.getString("qna_board_cont"));
					dto.setQna_board_date(rs.getString("qna_board_date"));
					dto.setQna_board_update(rs.getString("qna_board_update"));
					dto.setQna_board_file(rs.getString("qna_board_file"));
					dto.setQna_board_status(rs.getBoolean("qna_board_status"));

					list.add(dto);
				}

			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				closeConn(rs, pstmt, con);
			}

		} else if (field.equals("제목")) {

			try {

				sql = "select * from (" + "select @ROWN:=@ROWN+1 AS RN, qb.* from qna_board qb, "
						+ "(select @ROWN:=0) as clear_RN where " + "qna_board_title like ? "
						+ "order by qb.qna_board_no desc) as subquery " + "where RN >= ? and RN <= ?";

				pstmt = con.prepareStatement(sql);

				pstmt.setString(1, "%" + keyword + "%");
				pstmt.setInt(2, startNo);
				pstmt.setInt(3, endNo);

				rs = pstmt.executeQuery();

				while (rs.next()) {

					QnaBoardDTO dto = new QnaBoardDTO();

					dto.setQna_board_no(rs.getInt("qna_board_no"));
					dto.setQna_board_type(rs.getString("qna_board_type"));
					dto.setQna_board_writer_id(rs.getString("qna_board_writer_id"));
					dto.setQna_board_title(rs.getString("qna_board_title"));
					dto.setQna_board_cont(rs.getString("qna_board_cont"));
					dto.setQna_board_date(rs.getString("qna_board_date"));
					dto.setQna_board_update(rs.getString("qna_board_update"));
					dto.setQna_board_file(rs.getString("qna_board_file"));
					dto.setQna_board_status(rs.getBoolean("qna_board_status"));

					list.add(dto);
				}

			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				closeConn(rs, pstmt, con);
			}

		} else if (field.equals("내용")) {

			try {

				sql = "select * from (" + "select @ROWN:=@ROWN+1 AS RN, qb.* from qna_board qb, "
						+ "(select @ROWN:=0) as clear_RN where " + "qna_board_cont like ? "
						+ "order by qb.qna_board_no desc) as subquery " + "where RN >= ? and RN <= ?";

				pstmt = con.prepareStatement(sql);

				pstmt.setString(1, "%" + keyword + "%");
				pstmt.setInt(2, startNo);
				pstmt.setInt(3, endNo);

				rs = pstmt.executeQuery();

				while (rs.next()) {

					QnaBoardDTO dto = new QnaBoardDTO();

					dto.setQna_board_no(rs.getInt("qna_board_no"));
					dto.setQna_board_type(rs.getString("qna_board_type"));
					dto.setQna_board_writer_id(rs.getString("qna_board_writer_id"));
					dto.setQna_board_title(rs.getString("qna_board_title"));
					dto.setQna_board_cont(rs.getString("qna_board_cont"));
					dto.setQna_board_date(rs.getString("qna_board_date"));
					dto.setQna_board_update(rs.getString("qna_board_update"));
					dto.setQna_board_file(rs.getString("qna_board_file"));
					dto.setQna_board_status(rs.getBoolean("qna_board_status"));

					list.add(dto);
				}

			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				closeConn(rs, pstmt, con);
			}
		}

		return list;
	} // getQnaBoardList(field, keyword, page, rowsize)

	// 공지사항 조회수를 증가시키는 매서드.
	public void noticeBoardHit(int board_no) {

		try {

			openConn();

			sql = "update notice_board set notice_board_hit = notice_board_hit + 1 where notice_board_no = ?";

			pstmt = con.prepareStatement(sql);

			pstmt.setInt(1, board_no);

			pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConn(pstmt, con);
		}

	} // noticeBoardHit(board_no) end.

	// 이벤트 게시글의 조회수를 올리는 매서드.
	public void eventBoardHit(int board_no) {

		try {

			openConn();

			sql = "update event_board set event_board_hit = event_board_hit + 1 where event_board_no = ?";

			pstmt = con.prepareStatement(sql);

			pstmt.setInt(1, board_no);

			pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConn(pstmt, con);
		}

	} // eventBoardHit(board_no) end.

	// 1:1 문의 내역 삭제하는 메서드
	public int delete_mypageqnacontent(int qna_no) {

		int result = 0;

		try {
			openConn();

			sql = "delete from qna_board where qna_board_no = ?";

			pstmt = con.prepareStatement(sql);

			pstmt.setInt(1, qna_no);

			result = pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConn(pstmt, con);
		}

		return result;
	} // delete_mypageqnacontent(int qna_no)

	// 필터와 키워드로 차량을 조회하는 메서드(헤더검색창 포함)
	public List<SellBoardDTO> getKeywordSearch(String filter, String keyword, String sequence, int page, int rowsize) {

		List<SellBoardDTO> list = new ArrayList<>();
		// 선택한 조건에 따라 ?(플레이스홀더)의 개수가 동적이기 때문에 count로 증감연산자 사용
		int count = 0;
		// 페이징 코드
		int startNo = (page * rowsize) - (rowsize - 1);
		int endNo = (page * rowsize);

		try {
			openConn();
			// 키워드가 없는 경우
			if (keyword == null || keyword.equals("")) {
				sql = "select * from (select @row_number := @row_number + 1 as rnum,"
						+ " sb.* from (select distinct * from sell_board";
				// 선택한 정렬이 없다면 기본 정렬, 있다면 해당 조건에 맞게 정렬 추가
				if (sequence == null) {
					sql += " order by sell_board_no desc";
				} else if (sequence.equals("최근 연식순")) {
					sql += " order by substring_index(substring_index(sell_board_car_std_no, '_', 3), '_', -1) desc";
				} else if (sequence.equals("오래된 연식순")) {
					sql += " order by substring_index(substring_index(sell_board_car_std_no, '_', 3), '_', -1)";
				} else if (sequence.equals("적은 주행거리순")) {
					sql += " order by sell_board_car_distance";
				} else if (sequence.equals("많은 주행거리순")) {
					sql += " order by sell_board_car_distance desc";
				} else if (sequence.equals("낮은 가격순")) {
					sql += " order by sell_board_car_price";
				} else if (sequence.equals("높은 가격순")) {
					sql += " order by sell_board_car_price desc";
				}
				sql += ") sb, (select @row_number := 0) as clear_RN" + ") as subquery"
						+ " where rnum >= ? and rnum <= ?";
				// 선택한 정렬이 없다면 기본 정렬, 있다면 해당 조건에 맞게 정렬 추가
				if (sequence == null) {
					sql += " order by sell_board_no desc";
				} else if (sequence.equals("최근 연식순")) {
					sql += " order by substring_index(substring_index(sell_board_car_std_no, '_', 3), '_', -1) desc";
				} else if (sequence.equals("오래된 연식순")) {
					sql += " order by substring_index(substring_index(sell_board_car_std_no, '_', 3), '_', -1)";
				} else if (sequence.equals("적은 주행거리순")) {
					sql += " order by sell_board_car_distance";
				} else if (sequence.equals("많은 주행거리순")) {
					sql += " order by sell_board_car_distance desc";
				} else if (sequence.equals("낮은 가격순")) {
					sql += " order by sell_board_car_price";
				} else if (sequence.equals("높은 가격순")) {
					sql += " order by sell_board_car_price desc";
				}

				pstmt = con.prepareStatement(sql);
				pstmt.setInt(++count, startNo);
				pstmt.setInt(++count, endNo);

				rs = pstmt.executeQuery();
				// 키워드가 있는 경우
			} else {
				// 전체 검색
				if (filter.equals(StaticArea.getFilterList().get(0))) {
					sql = "select * from (select @row_number := case" + " when sell_board_car_std_no regexp ?"
							+ " then @row_number + 1 else @row_number end as rnum,"
							+ " sb.* from (select distinct * from sell_board";
					// 제조사로 검색
				} else if (filter.equals(StaticArea.getFilterList().get(1))) {

					sql = "select * from (select @row_number := case"
							+ " when substring_index(substring_index(sell_board_car_std_no, '_', 1), '_', -1) regexp ?"
							+ " then @row_number + 1 else @row_number end as rnum,"
							+ " sb.* from (select distinct * from sell_board";
					// 차종으로 검색
				} else if (filter.equals(StaticArea.getFilterList().get(2))) {
					sql = "select * from (select @row_number := case"
							+ " when substring_index(substring_index(sell_board_car_std_no, '_', 2), '_', -1) regexp ?"
							+ " then @row_number + 1 else @row_number end as rnum,"
							+ " sb.* from (select distinct * from sell_board";
					// 연료로 검색
				} else if (filter.equals(StaticArea.getFilterList().get(3))) {
					sql = "select * from (select @row_number := case"
							+ " when substring_index(substring_index(sell_board_car_std_no, '_', 5), '_', -1) regexp ?"
							+ " then @row_number + 1 else @row_number end as rnum,"
							+ " sb.* from (select distinct * from sell_board";
					// 색상으로 검색
				} else if (filter.equals(StaticArea.getFilterList().get(4))) {
					sql = "select * from (select @row_number := case"
							+ " when substring_index(substring_index(sell_board_car_std_no, '_', 7), '_', -1) regexp ?"
							+ " then @row_number + 1 else @row_number end as rnum,"
							+ " sb.* from (select distinct * from sell_board";
					// 모델명으로 검색
				} else if (filter.equals(StaticArea.getFilterList().get(5))) {
					sql = "select * from (select @row_number := case"
							+ " when substring_index(substring_index(sell_board_car_std_no, '_', 8), '_', -1) regexp ?"
							+ " then @row_number + 1 else @row_number end as rnum,"
							+ " sb.* from (select distinct * from sell_board";
				}

				if (sequence == null) {
					sql += " order by sell_board_no desc";
				} else if (sequence.equals("최근 연식순")) {
					sql += " order by substring_index(substring_index(sell_board_car_std_no, '_', 3), '_', -1) desc";
				} else if (sequence.equals("오래된 연식순")) {
					sql += " order by substring_index(substring_index(sell_board_car_std_no, '_', 3), '_', -1)";
				} else if (sequence.equals("적은 주행거리순")) {
					sql += " order by sell_board_car_distance";
				} else if (sequence.equals("많은 주행거리순")) {
					sql += " order by sell_board_car_distance desc";
				} else if (sequence.equals("낮은 가격순")) {
					sql += " order by sell_board_car_price";
				} else if (sequence.equals("높은 가격순")) {
					sql += " order by sell_board_car_price desc";
				}

				if (filter.equals(StaticArea.getFilterList().get(0))) {
					sql += ") sb, (select @row_number := 0) as clear_RN" + " where sell_board_car_std_no regexp ?"
							+ ") as subquery" + " where rnum >= ? and rnum <= ?";
					// 제조사로 검색
				} else if (filter.equals(StaticArea.getFilterList().get(1))) {
					sql += ") sb, (select @row_number := 0) as clear_RN"
							+ " where substring_index(substring_index(sell_board_car_std_no, '_', 1), '_', -1) regexp ?"
							+ ") as subquery" + " where rnum >= ? and rnum <= ?";
					// 차종으로 검색
				} else if (filter.equals(StaticArea.getFilterList().get(2))) {
					sql += ") sb, (select @row_number := 0) as clear_RN"
							+ " where substring_index(substring_index(sell_board_car_std_no, '_', 2), '_', -1) regexp ?"
							+ ") as subquery" + " where rnum >= ? and rnum <= ?";
					// 연료로 검색
				} else if (filter.equals(StaticArea.getFilterList().get(3))) {
					sql += ") sb, (select @row_number := 0) as clear_RN"
							+ " where substring_index(substring_index(sell_board_car_std_no, '_', 5), '_', -1) regexp ?"
							+ ") as subquery" + " where rnum >= ? and rnum <= ?";
					// 색상으로 검색
				} else if (filter.equals(StaticArea.getFilterList().get(4))) {
					sql += ") sb, (select @row_number := 0) as clear_RN"
							+ " where substring_index(substring_index(sell_board_car_std_no, '_', 7), '_', -1) regexp ?"
							+ ") as subquery" + " where rnum >= ? and rnum <= ?";
					// 모델명으로 검색
				} else if (filter.equals(StaticArea.getFilterList().get(5))) {
					sql += ") sb, (select @row_number := 0) as clear_RN"
							+ " where substring_index(substring_index(sell_board_car_std_no, '_', 8), '_', -1) regexp ?"
							+ ") as subquery" + " where rnum >= ? and rnum <= ?";
				}
				// 선택한 정렬이 없다면 기본 정렬, 있다면 해당 조건에 맞게 정렬 추가
				if (sequence == null) {
					sql += " order by sell_board_no desc";
				} else if (sequence.equals("최근 연식순")) {
					sql += " order by substring_index(substring_index(sell_board_car_std_no, '_', 3), '_', -1) desc";
				} else if (sequence.equals("오래된 연식순")) {
					sql += " order by substring_index(substring_index(sell_board_car_std_no, '_', 3), '_', -1)";
				} else if (sequence.equals("적은 주행거리순")) {
					sql += " order by sell_board_car_distance";
				} else if (sequence.equals("많은 주행거리순")) {
					sql += " order by sell_board_car_distance desc";
				} else if (sequence.equals("낮은 가격순")) {
					sql += " order by sell_board_car_price";
				} else if (sequence.equals("높은 가격순")) {
					sql += " order by sell_board_car_price desc";
				}

				pstmt = con.prepareStatement(sql);
				pstmt.setString(++count, keyword);
				pstmt.setString(++count, keyword);
				pstmt.setInt(++count, startNo);
				pstmt.setInt(++count, endNo);
			}

			rs = pstmt.executeQuery();

			while (rs.next()) {
				SellBoardDTO dto = new SellBoardDTO();

				dto.setSell_board_admin_no(rs.getString("sell_board_admin_no"));
				dto.setSell_board_car_detail(rs.getString("sell_board_car_detail"));
				dto.setSell_board_car_distance(rs.getInt("sell_board_car_distance"));
				dto.setSell_board_car_file(rs.getString("sell_board_car_file"));
				dto.setSell_board_car_no(rs.getString("sell_board_car_no"));
				dto.setSell_board_car_price(rs.getInt("sell_board_car_price"));
				dto.setSell_board_car_std_no(rs.getString("sell_board_car_std_no"));
				dto.setSell_board_date(rs.getString("sell_board_date"));
				dto.setSell_board_no(rs.getInt("sell_board_no"));
				dto.setSell_board_seller_no(rs.getString("sell_board_seller_no"));
				dto.setSell_board_status(rs.getBoolean("sell_board_status"));
				dto.setSell_board_update(rs.getString("sell_board_update"));
				dto.setSell_board_hit(rs.getInt("sell_board_hit"));

				list.add(dto);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}

		return list;
	}

	// 키워드로 검색한 차량 매물 개수를 구하는 메서드
	public int getKeywordSearchCount(String filter, String keyword) {

		int result = 0;

		try {
			openConn();

			// 키워드가 없는 경우
			if (keyword == null || keyword.equals("")) {
				sql = "select count(*) from sell_board";
				pstmt = con.prepareStatement(sql);
				// 키워드가 있는 경우
			} else {
				// 전체 검색
				if (filter.equals(StaticArea.getFilterList().get(0))) {
					sql = "select count(*) from sell_board where sell_board_car_std_no regexp ?";
					// 제조사로 검색
				} else if (filter.equals(StaticArea.getFilterList().get(1))) {
					sql = "select count(*) from sell_board where substring_index(substring_index(sell_board_car_std_no, '_', 1), '_', -1) regexp ?";
					// 차종으로 검색
				} else if (filter.equals(StaticArea.getFilterList().get(2))) {
					sql = "select count(*) from sell_board where substring_index(substring_index(sell_board_car_std_no, '_', 2), '_', -1) regexp ?";
					// 연료로 검색
				} else if (filter.equals(StaticArea.getFilterList().get(3))) {
					sql = "select count(*) from sell_board where substring_index(substring_index(sell_board_car_std_no, '_', 5), '_', -1) regexp ?";
					// 색상으로 검색
				} else if (filter.equals(StaticArea.getFilterList().get(4))) {
					sql = "select count(*) from sell_board where substring_index(substring_index(sell_board_car_std_no, '_', 7), '_', -1) regexp ?";
					// 모델명으로 검색
				} else if (filter.equals(StaticArea.getFilterList().get(5))) {
					sql = "select count(*) from sell_board where substring_index(substring_index(sell_board_car_std_no, '_', 8), '_', -1) regexp ?";
				}
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, keyword);
			}

			rs = pstmt.executeQuery();

			if (rs.next()) {
				result = rs.getInt(1);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}

		return result;
	}

	// sell_board의 상세정보를 누르면 조회수가 오르는 매서드.
	public void addSellBoardHit(int board_no) {

		try {

			openConn();

			sql = "update sell_board set sell_board_hit = sell_board_hit + 1 where sell_board_no = ?";

			pstmt = con.prepareStatement(sql);

			pstmt.setInt(1, board_no);

			pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConn(pstmt, con);
		}

	} // addSellBoardHit(board_no) end.

	// 메인페이지에서 인기매물 리스트를 가져오는 매서드.
	public List<SellBoardDTO> printSellList() {

		List<SellBoardDTO> list = new ArrayList<SellBoardDTO>();

		try {

			openConn();

			sql = "select * from sell_board order by sell_board_hit desc limit 4";

			pstmt = con.prepareStatement(sql);

			rs = pstmt.executeQuery();

			while (rs.next()) {

				SellBoardDTO dto = new SellBoardDTO();

				dto.setSell_board_car_file(rs.getString("sell_board_car_file"));
				dto.setSell_board_car_price(rs.getInt("sell_board_car_price"));
				dto.setSell_board_car_std_no(rs.getString("sell_board_car_std_no"));
				dto.setSell_board_no(rs.getInt("sell_board_no"));

				list.add(dto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}

		return list;
	} // printSellList() end.

	public int MyFavorite_insert(FavoriteDTO content) {

		int result = 0, cnt = 0;

		try {

			openConn();

			sql = "select max(favorite_no) from favorite";

			pstmt = con.prepareStatement(sql);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				cnt = rs.getInt(1) + 1;
			}

			sql = "insert into favorite values(?, ?, ?)";

			pstmt = con.prepareStatement(sql);

			pstmt.setInt(1, cnt);
			pstmt.setString(2, content.getFavorite_user_no());
			pstmt.setInt(3, content.getFavorite_sell_board_no());

			result = pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}

		return result;
	}

	public boolean check_favorite_content(String no, int board_no) {

		boolean result = false;

		try {

			openConn();

			sql = "select favorite_no from favorite where favorite_user_no like ? AND favorite_sell_board_no = ?";

			pstmt = con.prepareStatement(sql);

			pstmt.setString(1, no);
			pstmt.setInt(2, board_no);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				// 일치하는 데이터가 있을 경우
				result = true;
			} else {
				// 일치하는 데이터가 없을 경우
				result = false;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}
		return result;

	}

	public int MyFavorite_delete(FavoriteDTO content) {

		int result = 0;

		try {

			openConn();

			sql = "delete from favorite where favorite_user_no like ? AND favorite_sell_board_no = ?";

			pstmt = con.prepareStatement(sql);

			pstmt.setString(1, content.getFavorite_user_no());
			pstmt.setInt(2, content.getFavorite_sell_board_no());

			result = pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConn(pstmt, con);
		}

		return result;
	}

	public int countfavorite_list(int no) {

		int result = 0;

		try {

			openConn();

			sql = "select count(*) from favorite where favorite_sell_board_no = ?";

			pstmt = con.prepareStatement(sql);

			pstmt.setInt(1, no);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				result = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}

		return result;
	}

	public int selectFavoriteNo(FavoriteDTO content) {

		int no = 0;

		try {

			openConn();

			sql = "select favorite_no from favorite where favorite_user_no like ? AND favorite_sell_board_no = ?";

			pstmt = con.prepareStatement(sql);

			pstmt.setString(1, content.getFavorite_user_no());
			pstmt.setInt(2, content.getFavorite_sell_board_no());

			rs = pstmt.executeQuery();

			if (rs.next()) {
				no = rs.getInt(1);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}

		return no;
	}

	public int updateFavoriteNo(int no) {

		int result = 0;

		try {

			openConn();

			sql = "update favorite set favorite_no = favorite_no - 1 where favorite_no > ?";

			pstmt = con.prepareStatement(sql);

			pstmt.setInt(1, no);

			result = pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConn(pstmt, con);
		}

		return result;
	}

	public int countFavoriteNo() {

		int result = 0;

		try {
			openConn();

			sql = "select count(*) from favorite";

			pstmt = con.prepareStatement(sql);

			rs = pstmt.executeQuery();

			if (rs.next()) {

				result = rs.getInt(1);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}

		return result;
	}

	// 유저가 찜한 모든 찜목록 리스트를 페이징 처리 후 가져오는 매서드.
	public List<FavoriteDTO> getFavoriteList(String user_no, int page, int rowsize) {

		List<FavoriteDTO> list = new ArrayList<FavoriteDTO>();

		int startNo = (page * rowsize) - (rowsize - 1);

		int endNo = (page * rowsize);

		try {

			openConn();

			sql = "select * from (" + "select @ROWN:=@ROWN+1 AS RN, fb.* from favorite fb, "
					+ "(select @ROWN:=0) as clear_RN where " + "favorite_user_no like ? "
					+ "order by fb.favorite_no desc) as subquery " + "where RN >= ? and RN <= ?";

			pstmt = con.prepareStatement(sql);

			pstmt.setString(1, user_no);
			pstmt.setInt(2, startNo);
			pstmt.setInt(3, endNo);

			rs = pstmt.executeQuery();

			while (rs.next()) {

				FavoriteDTO dto = new FavoriteDTO();

				dto.setFavorite_no(rs.getInt("favorite_no"));
				dto.setFavorite_user_no(rs.getString("favorite_user_no"));
				dto.setFavorite_sell_board_no(rs.getInt("favorite_sell_board_no"));

				list.add(dto);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}

		return list;
	} // getFavoriteList(user_no) end.

	// 페이징 처리를 위한 찜목록 카운트 매서드.
	public int countFavoriteList(String no) {

		int cnt = 0;

		try {

			openConn();

			sql = "select count(favorite_no) from favorite where favorite_user_no like ?";

			pstmt = con.prepareStatement(sql);

			pstmt.setString(1, no);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				cnt = rs.getInt(1);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}

		return cnt;
	} // countFavoriteList(no) end.

	
	public List<UserDTO> getadminUserList(int userlist_page, int userlist_rowsize) {

		List<UserDTO> list = new ArrayList<UserDTO>();

		int userlist_startNo = (userlist_page * userlist_rowsize) - (userlist_rowsize - 1);

		int userlist_endNo = userlist_page * userlist_rowsize;

		try {
			openConn();

			sql = "select * from (select @ROWN:=@ROWN+1 AS RC, cb.* from user cb, "
					+ "(select @ROWN:=0) as clear_RC order by cb.user_no desc) as subquery "
					+ "where RC >= ? and RC <= ?";

			pstmt = con.prepareStatement(sql);

			pstmt.setInt(1, userlist_startNo);
			pstmt.setInt(2, userlist_endNo);

			rs = pstmt.executeQuery();

			while (rs.next()) {

				UserDTO dto = new UserDTO();

				dto.setUser_no(rs.getString("user_no"));
				dto.setUser_id(rs.getString("user_id"));
				dto.setUser_name(rs.getString("user_name"));
				dto.setUser_phone(rs.getString("user_phone"));
				dto.setUser_email(rs.getString("user_email"));
				dto.setUser_mileage(rs.getInt("user_mileage"));
				dto.setUser_grade(rs.getString("user_grade"));
				dto.setUser_regdate(rs.getString("user_regdate"));

				list.add(dto);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}
		return list;

	}	// getadminUserList() end.

	
	public int countUser() {
		int cnt = 0;

		try {

			openConn();

			sql = "select count(*) from user";

			pstmt = con.prepareStatement(sql);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				cnt = rs.getInt(1);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}

		return cnt;
	}

	
	// 관리자 페이지에서 사용자를 검색 할 때 필요한 카운트 매서드.
	public int countUserSearchList(String field, String keyword) {
		
		int cnt = 0;
		
		openConn();
		
		if (field.equals("전체")) {

			try {

				sql = "select count(*) from user where user_no like ? or user_id like ? or user_name like ?";

				pstmt = con.prepareStatement(sql);

				pstmt.setString(1, "%" + keyword + "%");
				pstmt.setString(2, "%" + keyword + "%");
				pstmt.setString(3, "%" + keyword + "%");

				rs = pstmt.executeQuery();

				if (rs.next()) {
					cnt = rs.getInt(1);
				}

			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				closeConn(rs, pstmt, con);
			}

		} else if (field.equals("회원번호")) {

			try {

				sql = "select count(*) from user where user_no like ?";

				pstmt = con.prepareStatement(sql);

				pstmt.setString(1, "%" + keyword + "%");

				rs = pstmt.executeQuery();

				if (rs.next()) {
					cnt = rs.getInt(1);
				}

			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				closeConn(rs, pstmt, con);
			}

		} else if (field.equals("아이디")) {
			
			try {

				sql = "select count(*) from user where user_id like ?";

				pstmt = con.prepareStatement(sql);

				pstmt.setString(1, "%" + keyword + "%");

				rs = pstmt.executeQuery();

				if (rs.next()) {
					cnt = rs.getInt(1);
				}

			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				closeConn(rs, pstmt, con);
			}
			
		} else if(field.equals("회원명")) {
			
			try {

				sql = "select count(*) from user where user_name like ?";

				pstmt = con.prepareStatement(sql);

				pstmt.setString(1, "%" + keyword + "%");

				rs = pstmt.executeQuery();

				if (rs.next()) {
					cnt = rs.getInt(1);
				}

			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				closeConn(rs, pstmt, con);
			}
		}
		
		return cnt;
	}	// countUserSearchList() end.

	
	
	// 관리자페이지에서 keyword에 맞게 유저 리스트를 가져오는 매서드.
	public List<UserDTO> getUserSearchList(String field, String keyword, int page, int rowsize) {
		
		List<UserDTO> list = new ArrayList<UserDTO>();
		
		int startNo = (page * rowsize) - (rowsize - 1);

		int endNo = page * rowsize;
		
		openConn();
		
		if (field.equals("전체")) {

			try {
				
				sql = "select * from (select @ROWN:=@ROWN+1 AS RC, cb.* from user cb, "
						+ "(select @ROWN:=0) as clear_RC where "
						+ "user_no like ? or user_id like ? or user_name like ? "
						+ "order by cb.user_no desc) as subquery "
						+ "where RC >= ? and RC <= ?";

				pstmt = con.prepareStatement(sql);

				pstmt.setString(1, "%" + keyword + "%");
				pstmt.setString(2, "%" + keyword + "%");
				pstmt.setString(3, "%" + keyword + "%");
				pstmt.setInt(4, startNo);
				pstmt.setInt(5, endNo);

				rs = pstmt.executeQuery();

				while (rs.next()) {
					
					UserDTO dto = new UserDTO();

					dto.setUser_no(rs.getString("user_no"));
					dto.setUser_id(rs.getString("user_id"));
					dto.setUser_name(rs.getString("user_name"));
					dto.setUser_phone(rs.getString("user_phone"));
					dto.setUser_email(rs.getString("user_email"));
					dto.setUser_mileage(rs.getInt("user_mileage"));
					dto.setUser_grade(rs.getString("user_grade"));
					dto.setUser_regdate(rs.getString("user_regdate"));

					list.add(dto);
				}

			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				closeConn(rs, pstmt, con);
			}

		} else if (field.equals("회원번호")) {

			try {

				sql = "select * from (select @ROWN:=@ROWN+1 AS RC, cb.* from user cb, "
						+ "(select @ROWN:=0) as clear_RC where "
						+ "user_no like ? "
						+ "order by cb.user_no desc) as subquery "
						+ "where RC >= ? and RC <= ?";

				pstmt = con.prepareStatement(sql);

				pstmt.setString(1, "%" + keyword + "%");
				pstmt.setInt(2, startNo);
				pstmt.setInt(3, endNo);

				rs = pstmt.executeQuery();

				while (rs.next()) {
					
					UserDTO dto = new UserDTO();

					dto.setUser_no(rs.getString("user_no"));
					dto.setUser_id(rs.getString("user_id"));
					dto.setUser_name(rs.getString("user_name"));
					dto.setUser_phone(rs.getString("user_phone"));
					dto.setUser_email(rs.getString("user_email"));
					dto.setUser_mileage(rs.getInt("user_mileage"));
					dto.setUser_grade(rs.getString("user_grade"));
					dto.setUser_regdate(rs.getString("user_regdate"));

					list.add(dto);
				}

			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				closeConn(rs, pstmt, con);
			}

		} else if (field.equals("아이디")) {
			
			try {

				sql = "select * from (select @ROWN:=@ROWN+1 AS RC, cb.* from user cb, "
						+ "(select @ROWN:=0) as clear_RC where "
						+ "user_id like ? "
						+ "order by cb.user_no desc) as subquery "
						+ "where RC >= ? and RC <= ?";

				pstmt = con.prepareStatement(sql);

				pstmt.setString(1, "%" + keyword + "%");
				pstmt.setInt(2, startNo);
				pstmt.setInt(3, endNo);

				rs = pstmt.executeQuery();

				while (rs.next()) {
					
					UserDTO dto = new UserDTO();

					dto.setUser_no(rs.getString("user_no"));
					dto.setUser_id(rs.getString("user_id"));
					dto.setUser_name(rs.getString("user_name"));
					dto.setUser_phone(rs.getString("user_phone"));
					dto.setUser_email(rs.getString("user_email"));
					dto.setUser_mileage(rs.getInt("user_mileage"));
					dto.setUser_grade(rs.getString("user_grade"));
					dto.setUser_regdate(rs.getString("user_regdate"));

					list.add(dto);
				}

			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				closeConn(rs, pstmt, con);
			}
			
		} else if(field.equals("회원명")) {
			
			try {

				sql = "select * from (select @ROWN:=@ROWN+1 AS RC, cb.* from user cb, "
						+ "(select @ROWN:=0) as clear_RC where "
						+ "user_name like ? "
						+ "order by cb.user_no desc) as subquery "
						+ "where RC >= ? and RC <= ?";

				pstmt = con.prepareStatement(sql);

				pstmt.setString(1, "%" + keyword + "%");
				pstmt.setInt(2, startNo);
				pstmt.setInt(3, endNo);

				rs = pstmt.executeQuery();

				while (rs.next()) {
					
					UserDTO dto = new UserDTO();

					dto.setUser_no(rs.getString("user_no"));
					dto.setUser_id(rs.getString("user_id"));
					dto.setUser_name(rs.getString("user_name"));
					dto.setUser_phone(rs.getString("user_phone"));
					dto.setUser_email(rs.getString("user_email"));
					dto.setUser_mileage(rs.getInt("user_mileage"));
					dto.setUser_grade(rs.getString("user_grade"));
					dto.setUser_regdate(rs.getString("user_regdate"));

					list.add(dto);
				}

			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				closeConn(rs, pstmt, con);
			}
		}
		
		return list;
	}	// getUserSearchList() end.
	
	
	// 메인 페이지에서 추천매물 리스트를 가져오는 매서드.
	public List<SellBoardDTO> printRecommendSellList() {

	      List<SellBoardDTO> list = new ArrayList<SellBoardDTO>();
	      
	      try {

	         openConn();
	         
	         sql = "select * from sell_board order by sell_board_date asc limit 4";
	         
	         pstmt = con.prepareStatement(sql);
	         
	         rs = pstmt.executeQuery();
	         
	         while(rs.next()) {
	            
	            SellBoardDTO dto = new SellBoardDTO();
	            
	            dto.setSell_board_car_file(rs.getString("sell_board_car_file"));
	            dto.setSell_board_car_price(rs.getInt("sell_board_car_price"));
	            dto.setSell_board_car_std_no(rs.getString("sell_board_car_std_no"));
	            dto.setSell_board_no(rs.getInt("sell_board_no"));
	            
	            list.add(dto);
	         }
	      } catch (SQLException e) {
	         e.printStackTrace();
	      } finally {
	         closeConn(rs, pstmt, con);
	      }
	      
	      return list;
	      
	   }	// printRecommendSellList() end.

	//	관리자 페이지에서 월 페이지 가입자 수를 가져오는 매서드.
	public List<Integer> countSignupList(int year, int month) {
		
		List<Integer> list = new ArrayList<Integer>();
		
		String temp = "";
		
		int lastYear = year -1;
		
		sql = "select count(*) from user where user_no like ?";
		
		try {
			
			openConn();
			
			for(int i = month + 1; i <= 12; i++) {
				
				if(i < 10) {
					temp = "U"+ lastYear + "0" +i+"%";
				} else {
					temp = "U"+ lastYear +i+"%";
				}
				
				pstmt = con.prepareStatement(sql);
				
				pstmt.setString(1, temp);
				
				rs = pstmt.executeQuery();
				
				if(rs.next()) {
					list.add(rs.getInt(1));
				}
			}
			
			for(int i = 1; i <= month; i++) {
				
				if(i < 10) {
					temp = "_____0"+i+"%";
				} else {
					temp = "_____"+i+"%";
				}
				
				pstmt = con.prepareStatement(sql);
				
				pstmt.setString(1, temp);
				
				rs = pstmt.executeQuery();
				
				if(rs.next()) {
					list.add(rs.getInt(1));
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}
		
		return list;
	}	// countSignupList() end.

	
	//	관리자 페이지에서 월 판매수익을 가져오는 매서드.
	public List<Integer> getCarSales(int year, int month) {
		
		List<Integer> list = new ArrayList<Integer>();
		
		try {

			openConn();
			
			sql = "SELECT transaction_car_price FROM transaction WHERE MONTH(transaction_date) = ? AND YEAR(transaction_date) = ?";
			
			pstmt = con.prepareStatement(sql);
			
			pstmt.setInt(1, month);
			pstmt.setInt(2, year);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				
				list.add(rs.getInt("transaction_car_price"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}
		
		return list;
	}	// getCarSales() end.

	
	//	관리자 페이지에서 월 판매량을 가져오는 매서드.
	public List<Integer> countSalesCarList(int year, int month) {
		
		List<Integer> list = new ArrayList<Integer>();
		
		int lastYear = year -1;
		
		sql = "SELECT count(*) FROM transaction WHERE MONTH(transaction_date) = ? AND YEAR(transaction_date) = ?";
		
		try {
			
			openConn();
			
			for(int i = month + 1; i <= 12; i++) {
				
				pstmt = con.prepareStatement(sql);
				
				pstmt.setInt(1, i);
				pstmt.setInt(2, lastYear);
				
				rs = pstmt.executeQuery();
				
				if(rs.next()) {
					list.add(rs.getInt(1));
				}
			}
			
			for(int i = 1; i <= month; i++) {
				
				pstmt = con.prepareStatement(sql);
				
				pstmt.setInt(1, i);
				pstmt.setInt(2, year);
				
				rs = pstmt.executeQuery();
				
				if(rs.next()) {
					list.add(rs.getInt(1));
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}
		
		return list;
	}	// countSalesCarList() end.

	public List<Integer> countReqCarList(int year, int month) {
		
		List<Integer> list = new ArrayList<Integer>();
		
		int lastYear = year -1;
		
		sql = "SELECT count(*) FROM sell_board WHERE MONTH(sell_board_date) = ? AND YEAR(sell_board_date) = ?";
		
		try {
			
			openConn();
			
			for(int i = month + 1; i <= 12; i++) {
				
				pstmt = con.prepareStatement(sql);
				
				pstmt.setInt(1, i);
				pstmt.setInt(2, lastYear);
				
				rs = pstmt.executeQuery();
				
				if(rs.next()) {
					list.add(rs.getInt(1));
				}
			}
			
			for(int i = 1; i <= month; i++) {
				
				pstmt = con.prepareStatement(sql);
				
				pstmt.setInt(1, i);
				pstmt.setInt(2, year);
				
				rs = pstmt.executeQuery();
				
				if(rs.next()) {
					list.add(rs.getInt(1));
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}
		
		return list;
	}

	
	//	관리자 페이지 차량 등록게시판 매물 카운팅 매서드.
	public int countSellCarBoardList(String no) {
		
		int cnt = 0;
		
		try {

			openConn();
			
			sql = "select count(*) from sell_board where sell_board_admin_no = ?";
			
			pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, no);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				cnt = rs.getInt(1);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}
		
		return cnt;
	}	// countSellCarBoardList() end.

	
	//	관리자 페이지 차량 등록 게시판 페이징 처리 List 매서드.
	public List<SellBoardDTO> getSellCarBoardList(String no, int page, int rowsize) {
		
		List<SellBoardDTO> list = new ArrayList<SellBoardDTO>();
		
		int startNo = (page * rowsize) - (rowsize - 1);

		int endNo = (page * rowsize);
		
		try {

			openConn();
			
			sql = "select * from (select @ROWN:=@ROWN+1 AS RN, sb.* " + "from sell_board sb, "
					+ "(select @ROWN:=0) as clear_RN where "
					+ "sb.sell_board_admin_no = ? "
					+ "order by sb.sell_board_no desc) as subquery "
					+ "where RN >= ? and RN <= ?";
			
			pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, no);
			pstmt.setInt(2, startNo);
			pstmt.setInt(3, endNo);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				
				SellBoardDTO dto = new SellBoardDTO();
				
				dto.setSell_board_admin_no(rs.getString("sell_board_admin_no"));
				dto.setSell_board_car_detail(rs.getString("sell_board_car_detail"));
				dto.setSell_board_car_distance(rs.getInt("sell_board_car_distance"));
				dto.setSell_board_car_file(rs.getString("sell_board_car_file"));
				dto.setSell_board_car_no(rs.getString("sell_board_car_no"));
				dto.setSell_board_car_price(rs.getInt("sell_board_car_price"));
				dto.setSell_board_car_std_no(rs.getString("sell_board_car_std_no"));
				dto.setSell_board_date(rs.getString("sell_board_date"));
				dto.setSell_board_no(rs.getInt("sell_board_no"));
				dto.setSell_board_seller_no(rs.getString("sell_board_seller_no"));
				dto.setSell_board_status(rs.getBoolean("sell_board_status"));
				dto.setSell_board_update(rs.getString("sell_board_update"));
				dto.setSell_board_hit(rs.getInt("sell_board_hit"));

				list.add(dto);
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}
		
		return list;
	}

	
	//	관리자페이지 검색기능 구현 매물 카운팅 매서드.
	public int countSellCarBoardList(String no, String field, String keyword) {
		
		int cnt = 0;

		openConn();

		if (field.equals("전체")) {

			try {

				sql = "SELECT COUNT(sell_board_no) " 
						+"FROM sell_board " 
						+"WHERE sell_board_admin_no = ? AND " 
						+"(sell_board_seller_no LIKE ? OR "
						+ "sell_board_car_std_no LIKE ? OR "
						+ "sell_board_car_detail LIKE ? OR "
						+ "sell_board_car_no LIKE ?)";

				pstmt = con.prepareStatement(sql);

				pstmt.setString(1, no);
				pstmt.setString(2, "%" + keyword + "%");
				pstmt.setString(3, "%" + keyword + "%");
				pstmt.setString(4, "%" + keyword + "%");
				pstmt.setString(5, "%" + keyword + "%");

				rs = pstmt.executeQuery();

				if (rs.next()) {
					cnt = rs.getInt(1);
				}

			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				closeConn(rs, pstmt, con);
			}

		} else if (field.equals("제조사") || field.equals("모델") || field.equals("색상") || field.equals("변속기")
				|| field.equals("연료") || field.equals("연식")) {

			try {

				sql = "SELECT COUNT(sell_board_no) " 
						+"FROM sell_board " 
						+"WHERE sell_board_admin_no = ? AND " 
						+ "sell_board_car_std_no LIKE ?";

				pstmt = con.prepareStatement(sql);

				pstmt.setString(1, no);
				pstmt.setString(2, "%" + keyword + "%");

				rs = pstmt.executeQuery();

				if (rs.next()) {
					cnt = rs.getInt(1);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				closeConn(rs, pstmt, con);
			}

		} else if (field.equals("내용")) {

			try {

				sql = "SELECT COUNT(sell_board_no) " 
						+"FROM sell_board " 
						+"WHERE sell_board_admin_no = ? AND " 
						+ "sell_board_car_detail LIKE ?";

				pstmt = con.prepareStatement(sql);

				pstmt.setString(1, no);
				pstmt.setString(2, "%" + keyword + "%");

				rs = pstmt.executeQuery();

				if (rs.next()) {
					cnt = rs.getInt(1);
				}

			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				closeConn(rs, pstmt, con);
			}
		}
		return cnt;
	}

	//	관리자 페이지 검색 기능, 페이징 처리 List 매서드.
	public List<SellBoardDTO> getSellCarBoardList(String admin_no, String field, String keyword, int page,
			int rowsize) {
		
		List<SellBoardDTO> list = new ArrayList<SellBoardDTO>();
		
		openConn();
		
		int startNo = (page * rowsize) - (rowsize - 1);

		int endNo = (page * rowsize);
		
		if (field.equals("전체")) {

			try {

				sql = "select * from (select @ROWN:=@ROWN+1 AS RN, " 
						+ "sb.* from sell_board sb, "
						+ "(select @ROWN:=0) as clear_RN where " 
						+ "sell_board_admin_no = ? AND "
						+ "(sell_board_seller_no like ? or "
						+ "sell_board_car_std_no like ? or " 
						+ "sell_board_car_detail like ? or "
						+ "sell_board_car_no like ?) " 
						+ "order by sb.sell_board_no desc) as subquery "
						+ "where RN >= ? and RN <= ?";

				pstmt = con.prepareStatement(sql);

				pstmt.setString(1, admin_no);
				pstmt.setString(2, "%" + keyword + "%");
				pstmt.setString(3, "%" + keyword + "%");
				pstmt.setString(4, "%" + keyword + "%");
				pstmt.setString(5, "%" + keyword + "%");
				pstmt.setInt(6, startNo);
				pstmt.setInt(7, endNo);

				rs = pstmt.executeQuery();

				while (rs.next()) {
					
					SellBoardDTO dto = new SellBoardDTO();

					dto.setSell_board_admin_no(rs.getString("sell_board_admin_no"));
					dto.setSell_board_car_detail(rs.getString("sell_board_car_detail"));
					dto.setSell_board_car_distance(rs.getInt("sell_board_car_distance"));
					dto.setSell_board_car_file(rs.getString("sell_board_car_file"));
					dto.setSell_board_car_no(rs.getString("sell_board_car_no"));
					dto.setSell_board_car_price(rs.getInt("sell_board_car_price"));
					dto.setSell_board_car_std_no(rs.getString("sell_board_car_std_no"));
					dto.setSell_board_date(rs.getString("sell_board_date"));
					dto.setSell_board_no(rs.getInt("sell_board_no"));
					dto.setSell_board_seller_no(rs.getString("sell_board_seller_no"));
					dto.setSell_board_status(rs.getBoolean("sell_board_status"));
					dto.setSell_board_update(rs.getString("sell_board_update"));
					dto.setSell_board_hit(rs.getInt("sell_board_hit"));

					list.add(dto);

				}

			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				closeConn(rs, pstmt, con);
			}

		} else if (field.equals("제조사") || field.equals("모델") || field.equals("색상") || field.equals("변속기")
				|| field.equals("연료") || field.equals("연식")) {

			try {

				sql = "select * from (select @ROWN:=@ROWN+1 AS RN, " 
						+ "sb.* from sell_board sb, "
						+ "(select @ROWN:=0) as clear_RN where " 
						+ "sell_board_admin_no = ? AND "
						+ "sell_board_car_std_no like ? " 
						+ "order by sb.sell_board_no desc) as subquery "
						+ "where RN >= ? and RN <= ?";

				pstmt = con.prepareStatement(sql);

				pstmt.setString(1, admin_no);
				pstmt.setString(2, "%" + keyword + "%");
				pstmt.setInt(3, startNo);
				pstmt.setInt(4, endNo);

				rs = pstmt.executeQuery();

				while (rs.next()) {

					SellBoardDTO dto = new SellBoardDTO();

					dto.setSell_board_admin_no(rs.getString("sell_board_admin_no"));
					dto.setSell_board_car_detail(rs.getString("sell_board_car_detail"));
					dto.setSell_board_car_distance(rs.getInt("sell_board_car_distance"));
					dto.setSell_board_car_file(rs.getString("sell_board_car_file"));
					dto.setSell_board_car_no(rs.getString("sell_board_car_no"));
					dto.setSell_board_car_price(rs.getInt("sell_board_car_price"));
					dto.setSell_board_car_std_no(rs.getString("sell_board_car_std_no"));
					dto.setSell_board_date(rs.getString("sell_board_date"));
					dto.setSell_board_no(rs.getInt("sell_board_no"));
					dto.setSell_board_seller_no(rs.getString("sell_board_seller_no"));
					dto.setSell_board_status(rs.getBoolean("sell_board_status"));
					dto.setSell_board_update(rs.getString("sell_board_update"));
					dto.setSell_board_hit(rs.getInt("sell_board_hit"));

					list.add(dto);
				}

			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				closeConn(rs, pstmt, con);
			}

		} else if (field.equals("내용")) {

			try {

				sql = "select * from (select @ROWN:=@ROWN+1 AS RN, " 
						+ "sb.* from sell_board sb, "
						+ "(select @ROWN:=0) as clear_RN where " 
						+ "sell_board_admin_no = ? AND "
						+ "sell_board_car_detail like ? "
						+ "order by sb.sell_board_no desc) as subquery "
						+ "where RN >= ? and RN <= ?";

				pstmt = con.prepareStatement(sql);

				pstmt.setString(1, admin_no);
				pstmt.setString(2, "%" + keyword + "%");
				pstmt.setInt(3, startNo);
				pstmt.setInt(4, endNo);

				rs = pstmt.executeQuery();

				while (rs.next()) {

					SellBoardDTO dto = new SellBoardDTO();

					dto.setSell_board_admin_no(rs.getString("sell_board_admin_no"));
					dto.setSell_board_car_detail(rs.getString("sell_board_car_detail"));
					dto.setSell_board_car_distance(rs.getInt("sell_board_car_distance"));
					dto.setSell_board_car_file(rs.getString("sell_board_car_file"));
					dto.setSell_board_car_no(rs.getString("sell_board_car_no"));
					dto.setSell_board_car_price(rs.getInt("sell_board_car_price"));
					dto.setSell_board_car_std_no(rs.getString("sell_board_car_std_no"));
					dto.setSell_board_date(rs.getString("sell_board_date"));
					dto.setSell_board_no(rs.getInt("sell_board_no"));
					dto.setSell_board_seller_no(rs.getString("sell_board_seller_no"));
					dto.setSell_board_status(rs.getBoolean("sell_board_status"));
					dto.setSell_board_update(rs.getString("sell_board_update"));
					dto.setSell_board_hit(rs.getInt("sell_board_hit"));

					list.add(dto);
				}

			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				closeConn(rs, pstmt, con);
			}
		}
		
		return list;
	}
	
	
	
} // class end.
