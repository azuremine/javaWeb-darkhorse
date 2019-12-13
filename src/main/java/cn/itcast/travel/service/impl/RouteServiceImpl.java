package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.dao.RouteImgDao;
import cn.itcast.travel.dao.SellerDao;
import cn.itcast.travel.dao.impl.RouteDaoImpl;
import cn.itcast.travel.dao.impl.RouteImgDaoImpl;
import cn.itcast.travel.dao.impl.SellerDaoImpl;
import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.RouteImg;
import cn.itcast.travel.domain.Seller;
import cn.itcast.travel.service.RouteService;

import java.util.List;


public class RouteServiceImpl implements RouteService {

    private RouteDao routeDao = new RouteDaoImpl();
    private SellerDao sellerDao = new SellerDaoImpl();
    private RouteImgDao routeImgDao = new RouteImgDaoImpl();
    private PageBean<Route> pageBean = new PageBean<Route>();

    @Override
    public PageBean<Route> pageQuery(int cid, String rname, int currentPage, int pageSize) {
        //获取总记录条数
        int totalCount = routeDao.findTotalCount(cid,rname);
        //设置当前页码
        pageBean.setCurrentPage(currentPage);
        //设置每页条数
        pageBean.setPageSize(pageSize);
        //设置总记录数
        pageBean.setTotalCount(totalCount);
        //设置总页数
        int totalPage = totalCount % pageSize == 0 ? totalCount/pageSize : (totalCount/pageSize) + 1;
        pageBean.setTotalPage(totalPage);
        //设置每页显示的数据
        int start = (currentPage-1) * pageSize;
        pageBean.setList(routeDao.findByPage(cid,rname,start,pageSize));

        return pageBean;
    }

    @Override
    public Route findOne(int rid) {
        //查询Route对象
        Route route = routeDao.findOne(rid);
        if (route != null){
            int sid = route.getSid();
            //查询商家Seller对象
            Seller seller = sellerDao.findBySid(sid);
            route.setSeller(seller);
            //查询对应的RouteImg对象
            int img_rid = route.getRid();
            List<RouteImg> imgList = routeImgDao.findByRid(img_rid);
            route.setRouteImgList(imgList);
        }
        return route;
    }
}
