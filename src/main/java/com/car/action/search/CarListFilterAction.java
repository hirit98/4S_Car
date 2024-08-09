package com.car.action.search;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.car.action.Action;
import com.car.action.ActionForward;
import com.car.action.StaticArea;
import com.car.model.SellBoardDTO;
import com.car.model.TotalDAO;

public class CarListFilterAction implements Action {

   @Override
   public ActionForward execute(HttpServletRequest request, HttpServletResponse response)
         throws ServletException, IOException {

      // 선택 조건이 없는 상황을 위해 null로 초기화
      List<String> types = null;
      List<String> companies = null;
      List<String> colors = null;
      List<String> fuels = null;
      List<String> transmissions = null;

      String queryParameter = "";
      String action = "filter";

      // 선택한 정렬값이 없으면 null로 초기화
      String sequence = null;
      // 선택한 정렬값이 있으면 그 값을 가져온다.
      if (request.getParameter("sequence") != null) {
         sequence = request.getParameter("sequence").trim();
         queryParameter += "&sequence=" + sequence;
      }

      // 선택한 조건이 있으면 값을 모두 불러온다.
      if (request.getParameterValues("type") != null) {
         types = Arrays.asList(request.getParameterValues("type"));
         request.setAttribute("types", types);
         for (String i : types) {
            queryParameter += "&type=" + i;
         }
      }
      if (request.getParameterValues("company") != null) {
         companies = Arrays.asList(request.getParameterValues("company"));
         request.setAttribute("companies", companies);
         for (String i : companies) {
            queryParameter += "&company=" + i;
         }
      }
      if (request.getParameterValues("color") != null) {
         colors = Arrays.asList(request.getParameterValues("color"));
         request.setAttribute("colors", colors);
         for (String i : colors) {
            queryParameter += "&color=" + i;
         }
      }
      if (request.getParameterValues("fuel") != null) {
         fuels = Arrays.asList(request.getParameterValues("fuel"));
         request.setAttribute("fuels", fuels);
         for (String i : fuels) {
            queryParameter += "&fuel=" + i;
         }
      }
      if (request.getParameterValues("transmission") != null) {
         transmissions = Arrays.asList(request.getParameterValues("transmission"));
         request.setAttribute("transmissions", transmissions);
         for (String i : transmissions) {
            queryParameter += "&transmission=" + i;
         }
      }

      // 값이 비어있어도 모든 차량을 보여주기 위해 연식, 주행거리, 가격을 0과 int의 최대값으로 초기화
      int min_year = 0;
      int max_year = Integer.MAX_VALUE;
      int min_distance = 0;
      int max_distance = Integer.MAX_VALUE;
      long min_price = 0;
      long max_price = Long.MAX_VALUE;

      // 연식, 주행거리, 가격의 입력값이 존재할 때 그 값을 적용
      if (request.getParameter("min_year") != null && request.getParameter("min_year") != "") {
         min_year = Integer.parseInt(request.getParameter("min_year").trim());
         queryParameter += "&min_year=" + min_year;
      }
      if (request.getParameter("max_year") != null && request.getParameter("max_year") != "") {
         max_year = Integer.parseInt(request.getParameter("max_year").trim());
         queryParameter += "&max_year=" + max_year;
      }
      if (request.getParameter("min_distance") != null && request.getParameter("min_distance") != "") {
         min_distance = Integer.parseInt(request.getParameter("min_distance").trim());
         queryParameter += "&min_distance=" + min_distance;
      }
      if (request.getParameter("max_distance") != null && request.getParameter("max_distance") != "") {
         max_distance = Integer.parseInt(request.getParameter("max_distance").trim());
         queryParameter += "&max_distance=" + max_distance;
      }
      if (request.getParameter("min_price") != null && request.getParameter("min_price") != "") {
         min_price = Long.parseLong(request.getParameter("min_price").trim());
         queryParameter += "&min_price=" + min_price;
      }
      if (request.getParameter("max_price") != null && request.getParameter("max_price") != "") {
         max_price = Long.parseLong(request.getParameter("max_price").trim());
         queryParameter += "&max_price=" + max_price;
      }

      // 최소값이 최대값보다 크게 입력했을 때 에러 방지를 위한 초기화
      if (min_year > max_year) {
         min_year = 0;
         max_year = Integer.MAX_VALUE;
      }
      if (min_distance > max_distance) {
         min_distance = 0;
         max_distance = Integer.MAX_VALUE;
      }
      if (min_price > max_price) {
         min_price = 0;
         max_price = Long.MAX_VALUE;
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

      // 검색을 제외하고 내가 선택한 모든 조건을 가지고 검색하는 메서드(필터링, 정렬, 페이징)
      List<SellBoardDTO> list = dao.getFilterSearch(types, companies, colors, fuels, transmissions, min_year,
            max_year, min_distance, max_distance, min_price, max_price, sequence, page, rowsize);

      // 전체 매물의 개수를 표시하기 위해 값을 구하는 메서드
      int allCount = dao.getCarListAllCount();
      // 내가 선택한 조건에 해당하는 매물의 개수를 표시하기 위해 값을 구하는 메서드
      int count = dao.getFilterSearchCount(types, companies, colors, fuels, transmissions, min_year, max_year,
            min_distance, max_distance, min_price, max_price);

      allPage = (int) Math.ceil(count / (double) rowsize);

      if (endBlock > allPage) {
         endBlock = allPage;
      }

      boolean is_nSearch = false;

      // 데이터 전송 영역
      request.setAttribute("allCount", allCount);
      request.setAttribute("count", count);
      request.setAttribute("list", list);
      request.setAttribute("min_year", min_year);
      request.setAttribute("max_year", max_year);
      request.setAttribute("min_distance", min_distance);
      request.setAttribute("max_distance", max_distance);
      request.setAttribute("min_price", min_price);
      request.setAttribute("max_price", max_price);
      request.setAttribute("sequence", sequence);
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