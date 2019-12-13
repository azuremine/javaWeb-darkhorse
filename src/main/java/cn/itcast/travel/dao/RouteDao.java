package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Route;

import java.util.List;

public interface RouteDao {

    /**
     * 根据cid查询总记录数
     * @param cid
     * @return
     */
    int findTotalCount(int cid, String rname);

    /**
     * 根据cid,start,pageSize查询当前页的数据集合
     * @param cid
     * @param start
     * @param pageSize
     * @return
     */
    List<Route> findByPage(int cid, String rname, int start, int pageSize);

    /**
     * 根据rid查找旅游路线详情
     * @param rid
     * @return
     */
    Route findOne(int rid);
}
