package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.FavoriteDao;
import cn.itcast.travel.dao.impl.FavoriteDaoImpl;
import cn.itcast.travel.domain.Favorite;
import cn.itcast.travel.service.FavoriteService;
import org.apache.commons.collections.IterableMap;

public class FavoriteServiceImpl implements FavoriteService {

    private FavoriteDao favoriteDao= new FavoriteDaoImpl();

    @Override
    public boolean isFavorite(String rid, int uid) {
        Favorite favorite = favoriteDao.findByRidAndUid(rid, uid);
        if (favorite == null){
            return false;
        }
        return true;
    }

    @Override
    public int findCount(String rid) {
         int count = favoriteDao.finCount(Integer.parseInt(rid));
        return count;
    }
}
