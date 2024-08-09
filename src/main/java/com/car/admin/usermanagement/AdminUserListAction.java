package com.car.admin.usermanagement;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.car.action.Action;
import com.car.action.ActionForward;
import com.car.model.TotalDAO;
import com.car.model.UserDTO;

public class AdminUserListAction implements Action {

   @Override
   public ActionForward execute(HttpServletRequest request, HttpServletResponse response)
         throws ServletException, IOException {
      
      int cnt = 0;
      int rowsize = 10;
      int block = 3;
      int allPage = 0;
      int page = 0;
      
      if (request.getParameter("page") != null) {
         page = Integer.parseInt(request.getParameter("page").trim());

      } else {
         page = 1;
      }

      int startNo = (page * rowsize - 1);
      int endNo = (page * rowsize);
      int startBlock = (((page - 1) / block) * block) + 1;
      int endBlock = (((page - 1) / block) * block) + block;

      TotalDAO dao = TotalDAO.getInstance();
      
      cnt = dao.countUser();

      allPage = (int) Math.ceil(cnt / (double) rowsize);
      if (endBlock > allPage) {
         endBlock = allPage;
      }
      
      List<UserDTO> list = dao.getadminUserList(page, rowsize);
      
      boolean isSearch = false;

      request.setAttribute("cnt", cnt);
      request.setAttribute("page", page);
      request.setAttribute("rowsize", rowsize);
      request.setAttribute("block", block);
      request.setAttribute("allPage", allPage);
      request.setAttribute("startNo", startNo);
      request.setAttribute("endNo", endNo);
      request.setAttribute("startBlock", startBlock);
      request.setAttribute("endBlock", endBlock);
      request.setAttribute("list", list);
      request.setAttribute("isSearch", isSearch);

      ActionForward forward = new ActionForward();

      forward.setPath("/WEB-INF/views/admin/usermanagement/user_list.jsp");

      return forward;
   }
}