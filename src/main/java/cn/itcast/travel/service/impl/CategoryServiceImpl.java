package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.CategoryDao;
import cn.itcast.travel.dao.impl.CategoryDaoImpl;
import cn.itcast.travel.domain.Category;
import cn.itcast.travel.service.CategoryService;
import cn.itcast.travel.util.JedisUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CategoryServiceImpl implements CategoryService {

    private CategoryDao categoryDao = new CategoryDaoImpl();
    private Jedis jedis = JedisUtil.getJedis();

    @Override
    public List<Category> findAll() {
        //返回是list
        List<Category> list = null;
        //使用sortedSet排序查询
//        Set<String> categorys = jedis.zrange("category",0,-1);
        //查询sortedSet中的分数（cid)和值（cname)
        Set<Tuple> categorys = jedis.zrangeWithScores("category", 0, -1);
        if(categorys == null || categorys.size() == 0){
            //说明第一次查询，缓存中没由数据
            list = categoryDao.findAll();
            //存储数据
            for (int i = 0; i < list.size(); i++) {
                jedis.zadd("category",list.get(i).getCid(),list.get(i).getCname());
            }
        }else {
            list = new ArrayList<Category>();
            //如果不为空，将set的数据存入list
            for (Tuple tuple : categorys) {
                Category category = new Category();
                category.setCid((int) tuple.getScore());
                category.setCname(tuple.getElement());
                list.add(category);
            }
        }
        return list;
    }
}
