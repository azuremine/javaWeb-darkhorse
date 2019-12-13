package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;

public class RouteDaoImpl implements RouteDao{

    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public int findTotalCount(int cid, String rname) {
        //定义sql模板
        String sql = "select count(*) from tab_route where 1=1";
        StringBuilder sb = new StringBuilder(sql);
        List<Object> params = new ArrayList<Object>();
        if (cid != 0){
            sb.append(" and cid = ?");
            params.add(cid);    //添加？对应的值
        }
        if (rname != null && rname.length() > 0){
            sb.append(" and rname like ?");
            params.add("%" + rname + "%");  //添加？对应的值
        }
        return template.queryForObject(sb.toString(),Integer.class,params.toArray());
    }

    @Override
    public List<Route> findByPage(int cid, String rname, int start, int pageSize) {
        List<Route> list = null;
        List<Object> params = new ArrayList<Object>();
        String sql = "select * from tab_route where 1=1";
        StringBuilder sb = new StringBuilder(sql);
        try {
            if(cid != 0){
                sb.append(" and cid = ?");
                params.add(cid);    //添加？对应的值
            }
            if (rname != null && rname.length() > 0){
                sb.append(" and rname like ?");
                params.add("%" + rname + "%");  //添加？对应的值
            }
            sb.append(" limit ? , ?");
            params.add(start);
            params.add(pageSize);
            list = template.query(sb.toString(), new BeanPropertyRowMapper<Route>(Route.class), params.toArray());
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return list;
    }
}