package cn.itcast.travel.web.servlet;


import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.service.RouteService;
import cn.itcast.travel.service.impl.RouteServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/route/*")
public class RouteServlet extends BaseServlet {

    private RouteService routeService = new RouteServiceImpl();

    /**
     * 分页查询
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void pageQuery(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String currentPageStr = request.getParameter("currentPage");
        String pageSizeStr = request.getParameter("pageSize");
        String cidStr = request.getParameter("cid");
        //接受线路名称
        String rname = request.getParameter("rname");
        if(rname != null && rname.length() > 0 && !rname.equals("null")){
            rname = new String(rname.getBytes("iso-8859-1"),"utf-8");
        }else {
            rname = null;
        }

        int cid = 0;
        int currentPage = 0;
        int pageSize = 0;
        //1、处理参数
        if(cidStr != null && cidStr.length() >0 && !cidStr.equals("null")){
            cid = Integer.parseInt(cidStr);
        }

        //当前页码
        if(currentPageStr != null && currentPageStr.length() >0){
            currentPage = Integer.parseInt(currentPageStr);
        }else {
            currentPage = 1;//不传值，默认为1
        }

        //每页显示条数
        if(pageSizeStr != null && pageSizeStr.length() >0){
            pageSize = Integer.parseInt(pageSizeStr);
        }else {
            pageSize = 5; //不传值，默认为5
        }

        PageBean<Route> routePageBean = routeService.pageQuery(cid, rname, currentPage, pageSize);
        writeValue(routePageBean,response);

    }

    /**
     * 查找路线详细信息
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void findOne(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        Route one = null;
        String rid = request.getParameter("rid");
        if(rid != null && rid.length() > 0){
            one = routeService.findOne(Integer.parseInt(rid));
        }
        writeValue(one,response);
    }
}
