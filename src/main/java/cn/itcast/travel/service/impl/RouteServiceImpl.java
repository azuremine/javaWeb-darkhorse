package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.dao.impl.RouteDaoImpl;
import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.service.RouteService;

public class RouteServiceImpl implements RouteService {

    private RouteDao routeDao = new RouteDaoImpl();
    private PageBean<Route> pageBean = new PageBean<Route>();

    @Override
    public PageBean<Route> pageQuery(int cid, int currentPage, int pageSize) {
        //获取总记录条数
        int totalCount = routeDao.findTotalCount(cid);
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
        pageBean.setList(routeDao.findByPage(cid,start,pageSize));

        return pageBean;
    }
}
