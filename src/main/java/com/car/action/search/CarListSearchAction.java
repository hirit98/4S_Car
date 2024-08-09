package com.car.action.search;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.car.action.Action;
import com.car.action.ActionForward;
import com.car.action.StaticArea;
import com.car.model.SellBoardDTO;
import com.car.model.TotalDAO;

public class CarListSearchAction implements Action {

   @Override
   public ActionForward execute(HttpServletRequest request, HttpServletResponse response)
         throws ServletException, IOException {

      String queryParameter = "";
      String action = "search";

      String sequence = null;
      if (request.getParameter("sequence") != null) {
         sequence = request.getParameter("sequence").trim();
         queryParameter += "&sequence=" + sequence;
      }

      String filter = StaticArea.getFilterList().get(0);
      if (request.getParameter("filter") != null) {
         filter = request.getParameter("filter").trim();
         queryParameter += "&filter=" + filter;
      }

      String keyword = null;
      if (request.getParameter("keyword") != null) {
         keyword = request.getParameter("keyword").trim();
         queryParameter += "&keyword=" + keyword;
      }

      TotalDAO dao = TotalDAO.getInstance();

      // 제조사/모델을 눌렀을 때 제조사별 매물의 개수를 구하는 메서드
      List<Integer> companyCountList = dao.getCountCompany(StaticArea.getCompanyList());

      // 페이징 코드 영역
      int rowsize = 20;
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

      List<SellBoardDTO> list = dao.getKeywordSearch(filter, keyword, sequence, page, rowsize);

      // 전체 매물의 개수를 표시하기 위해 값을 구하는 메서드
      int allCount = dao.getCarListAllCount();
      // 내가 검색한 조건에 해당하는 매물의 개수를 표시하기 위해 값을 구하는 메서드
      int count = dao.getKeywordSearchCount(filter, keyword);

      allPage = (int) Math.ceil(count / (double) rowsize);

      if (endBlock > allPage) {
         endBlock = allPage;
      }

      boolean is_nSearch = true;

      request.setAttribute("allCount", allCount);
      request.setAttribute("count", count);
      request.setAttribute("list", list);
      request.setAttribute("sequence", sequence);
      request.setAttribute("filter", filter);
      request.setAttribute("keyword", keyword);
      request.setAttribute("typeList", StaticArea.getTypeList());
      request.setAttribute("companyList", StaticArea.getCompanyList());
      request.setAttribute("companyCountList", companyCountList);
      request.setAttribute("colorList", StaticArea.getColorList());
      request.setAttribute("fuelList", StaticArea.getFuelList());
      request.setAttribute("transmissionList", StaticArea.getTransmissionList());
      request.setAttribute("filterList", StaticArea.getFilterList());
      request.setAttribute("sequenceList", StaticArea.getSequenceList());
      request.setAttribute("queryParameter", queryParameter);
      request.setAttribute("action", action);

      // 페이징 코드 전송 영역
      request.setAttribute("page", page);
      request.setAttribute("rowsize", rowsize);
      request.setAttribute("block", block);
      request.setAttribute("allPage", allPage);
      request.setAttribute("startNo", startNo);
      request.setAttribute("endNo", endNo);
      request.setAttribute("startBlock", startBlock);
      request.setAttribute("endBlock", endBlock);
      request.setAttribute("is_nSearch", is_nSearch);

      ActionForward forward = new ActionForward();

      forward.setPath("/WEB-INF/views/public/search/car_list.jsp");

      return forward;
   }
}