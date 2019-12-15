package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.FavoriteDao;
import cn.itcast.travel.dao.impl.FavoriteDaoImpl;
import cn.itcast.travel.domain.Favorite;
import cn.itcast.travel.service.FavoriteService;

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
    public boolean addFavorite(String rid, int uid) {
        int result = favoriteDao.saveFavorite(rid,uid);
        if(result == 1){
            return true;
        }
        return false;
    }
}
