package cn.itcast.travel.service;

public interface FavoriteService {

    /**
     * 查询是否收藏线路
     * @param rid
     * @param uid
     * @return
     */
    boolean isFavorite(String rid, int uid);

}
