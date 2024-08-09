package com.car.admin.company;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.car.action.Action;
import com.car.action.ActionForward;
import com.car.action.StaticArea;
import com.car.model.CompanyDTO;
import com.car.model.TotalDAO;

public class AdminCompanyInfoModifyOkAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		StaticArea.checkAdminDTO(request, response);

		String company_no = request.getParameter("company_no").trim();
		String company_name = request.getParameter("company_name").trim();
		String company_ceo = request.getParameter("company_ceo").trim();
		String company_addr = request.getParameter("company_addr").trim();
		String company_date = request.getParameter("company_date").trim();
		String company_phone = request.getParameter("company_phone").trim();
		String company_account = request.getParameter("company_account").trim();

		CompanyDTO dto = new CompanyDTO();

		dto.setCompany_business_no(company_no);
		dto.setCompany_name(company_name);
		dto.setCompany_ceo_name(company_ceo);
		dto.setCompany_addr(company_addr);
		dto.setCompany_found_date(company_date);
		dto.setCompany_phone(company_phone);
		dto.setCompany_account(company_account);

		TotalDAO dao = TotalDAO.getInstance();

		int check = dao.updateCompanyInfo(dto);

		if (check > 0) {
			ActionForward forward = new ActionForward();

			forward.setPath("/WEB-INF/views/admin/company/company_info_modify_ok.jsp");

			return forward;
		} else {
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('정보 수정에 실패했습니다.')");
			out.println("history.back()");
			out.println("</script>");
			out.close();
		}

		return null;
	}

}
