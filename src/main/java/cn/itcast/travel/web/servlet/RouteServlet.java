package cn.itcast.travel.web.servlet;


import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.FavoriteService;
import cn.itcast.travel.service.RouteService;
import cn.itcast.travel.service.impl.FavoriteServiceImpl;
import cn.itcast.travel.service.impl.RouteServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/route/*")
public class RouteServlet extends BaseServlet {

    private RouteService routeService = new RouteServiceImpl();
    private ResultInfo info = new ResultInfo();
    private FavoriteService favoriteService = new FavoriteServiceImpl();

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

    /**
     * 判断当前用户是否了该收藏线路
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void isFavorite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        int uid;
        if(user == null){
            //用户没有登录
            uid = 0;
        }else {
            //用户已经登录
            uid = user.getUid();
        }
        String rid = request.getParameter("rid");
        boolean flag = favoriteService.isFavorite(rid,uid);
        info.setFlag(flag);
        Map<String,Integer> map = new HashMap<String,Integer>();
        map.put("uid",uid);
        info.setData(map);
        writeValue(info,response);
    }

    /**
     * 当前用户添加收藏
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void addFavorite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        int uid;
        if(user == null){
            //用户没有登录
            return;
        }else {
            //用户已经登录
            uid = user.getUid();
        }
        String rid = request.getParameter("rid");
        boolean flag = favoriteService.addFavorite(rid,uid);
        info.setFlag(flag);
        writeValue(info,response);
    }
}
