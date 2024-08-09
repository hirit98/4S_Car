package com.car.action.search;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.car.action.Action;
import com.car.action.ActionForward;
import com.car.model.FavoriteDTO;
import com.car.model.TotalDAO;
import com.car.model.UserDTO;

public class CarListFavoriteDeleteAction implements Action {

   @Override
   public ActionForward execute(HttpServletRequest request, HttpServletResponse response)
         throws ServletException, IOException {

      int favorite_sell_board_no = Integer.parseInt(request.getParameter("no"));

      FavoriteDTO content = new FavoriteDTO();

      HttpSession session = request.getSession();
      UserDTO dto = (UserDTO) session.getAttribute("dto");

      String user_no = dto.getUser_no();

      content.setFavorite_sell_board_no(favorite_sell_board_no);
      content.setFavorite_user_no(user_no);

      TotalDAO dao = TotalDAO.getInstance();

      PrintWriter out = response.getWriter();

      int favorite_no = dao.selectFavoriteNo(content);

      int check = dao.MyFavorite_delete(content);

      int check2 = dao.countFavoriteNo();

      if (check > 0) {

         if (check2 != 0) {

            dao.updateFavoriteNo(favorite_no);
         }
         out.println("<script>");
         out.println("alert('찜목록에서 삭제했습니다.')");
         out.println("location.href='car_list_content?no=" + favorite_sell_board_no + "'");
         out.println("</script>");
         out.close();

      } else {

         out.println("<script>");
         out.println("alert('찜목록 삭제에 실패했습니다.')");
         out.println("history.back()");
         out.println("</script>");
         out.close();
      }

      return null;

   }

}