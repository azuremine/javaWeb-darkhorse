package cn.itcast.travel.service;

import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;

public interface RouteService {

    /**
     * 查询显示数据及分页条数据
     * @param cid
     * @param rname
     * @param currentPage
     * @param pageSize
     * @return
     */
    PageBean<Route> pageQuery(int cid, String rname, int currentPage, int pageSize);

    /**
     * 查询路线详情
     * @param rid
     * @return
     */
    Route findOne(int rid);

}
