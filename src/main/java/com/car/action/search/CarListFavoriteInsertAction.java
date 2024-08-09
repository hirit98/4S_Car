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

public class CarListFavoriteInsertAction implements Action {

   @Override
   public ActionForward execute(HttpServletRequest request, HttpServletResponse response)
         throws ServletException, IOException {

      int favorite_no = 0;

      int favorite_sell_board_no = Integer.parseInt(request.getParameter("no"));
      
      FavoriteDTO content = new FavoriteDTO();

      UserDTO dto = null;
      
      PrintWriter out = response.getWriter();
      
      if(request.getSession(false) != null) {
         
         HttpSession session = request.getSession();
         
         String login_no = (String) session.getAttribute("no");
         
         if(login_no != null && login_no.substring(0, 1).equals("U")) {
            dto = (UserDTO) session.getAttribute("dto");
         } else {

            out.println("<script>");
            out.println("alert('로그인후 이용 가능한 서비스입니다.')");
            out.println("location.href='car_list_content?no=" + favorite_sell_board_no + "'");
            out.println("</script>");
            out.close();
         }
         
         if (dto != null ) {
            
            String user_no = dto.getUser_no();

            content.setFavorite_sell_board_no(favorite_sell_board_no);
            content.setFavorite_user_no(user_no);
            content.setFavorite_no(favorite_no);

            TotalDAO dao = TotalDAO.getInstance();

            int check = dao.MyFavorite_insert(content);

            if (check > 0) {

               out.println("<script>");
               out.println("alert('찜목록에 추가되었습니다!')");
               out.println("location.href='car_list_content?no=" + favorite_sell_board_no + "'");
               out.println("</script>");
               out.close();
            } else {

               out.println("<script>");
               out.println("alert('찜목록 추가에 실패했습니다.')");
               out.println("history.back()");
               out.println("</script>");
               out.close();
            }
         } else {

            out.println("<script>");
            out.println("alert('로그인후 이용 가능한 서비스입니다.')");
            out.println("location.href='car_list_content?no=" + favorite_sell_board_no + "'");
            out.println("</script>");
            out.close();
         }
      } 

      return null;

   }

}