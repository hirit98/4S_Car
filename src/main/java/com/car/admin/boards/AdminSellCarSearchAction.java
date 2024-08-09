package com.car.admin.boards;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.car.action.Action;
import com.car.action.ActionForward;
import com.car.action.StaticArea;
import com.car.model.AdminDTO;
import com.car.model.SellBoardDTO;
import com.car.model.TotalDAO;

public class AdminSellCarSearchAction implements Action {

   @Override
   public ActionForward execute(HttpServletRequest request, HttpServletResponse response)
         throws ServletException, IOException {
      
      StaticArea.checkAdminDTO(request, response);

      HttpSession session = request.getSession();
      
      AdminDTO dto = (AdminDTO) session.getAttribute("dto");
      
      TotalDAO dao = TotalDAO.getInstance();

      int cnt = 0;

      int rowsize = 10;

      int block = 3;

      int allPage = 0;

      int page = 1;

      if (request.getParameter("page") != null) {
         page = Integer.parseInt(request.getParameter("page").trim());
         if (page < 1) {
            page = 1;
         }
      }

      int startNo = (page * rowsize) - (rowsize - 1);

      int endNo = (page * rowsize);

      int startBlock = (((page - 1) / block) * block) + 1;

      int endBlock = (((page - 1) / block) * block) + block;

      String field = request.getParameter("field").trim();
      String keyword = request.getParameter("keyword").trim();

      cnt = dao.countSellCarBoardList(dto.getAdmin_no() ,field, keyword);

      allPage = (int) Math.ceil(cnt / (double) rowsize);

      if (endBlock > allPage) {
         endBlock = allPage;
      }

      boolean is_Search = true;

      List<SellBoardDTO> list = dao.getSellCarBoardList(dto.getAdmin_no(), field, keyword, page, rowsize);

      request.setAttribute("cnt", cnt);
      request.setAttribute("list", list);
      request.setAttribute("page", page);
      request.setAttribute("rowsize", rowsize);
      request.setAttribute("block", block);
      request.setAttribute("allPage", allPage);
      request.setAttribute("startNo", startNo);
      request.setAttribute("endNo", endNo);
      request.setAttribute("startBlock", startBlock);
      request.setAttribute("endBlock", endBlock);
      request.setAttribute("field", field);
      request.setAttribute("keyword", keyword);
      request.setAttribute("is_Search", is_Search);
      request.setAttribute("filterList", StaticArea.getFilterList());
      
      ActionForward forward = new ActionForward();

      forward.setPath("/WEB-INF/views/admin/boards/sell_car_list.jsp");

      return forward;
   }

}