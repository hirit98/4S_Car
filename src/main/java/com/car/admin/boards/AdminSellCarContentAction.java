package com.car.admin.boards;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.car.action.Action;
import com.car.action.ActionForward;
import com.car.action.StaticArea;
import com.car.model.SellBoardDTO;
import com.car.model.TotalDAO;

public class AdminSellCarContentAction implements Action {

   @Override
   public ActionForward execute(HttpServletRequest request, HttpServletResponse response)
         throws ServletException, IOException {
      
      StaticArea.checkAdminDTO(request, response);

      int board_no = Integer.parseInt(request.getParameter("no").trim());
      int page = 1;
      if(request.getParameter("page") != null) {
         page = Integer.parseInt(request.getParameter("page").trim());
      }

      TotalDAO dao = TotalDAO.getInstance();
      SellBoardDTO dto = dao.getSellBoardContent(board_no);

      request.setAttribute("dto", dto);
      request.setAttribute("page", page);
      
      ActionForward forward = new ActionForward();

      forward.setPath("/WEB-INF/views/admin/boards/sell_car_content.jsp");

      return forward;
   }

}