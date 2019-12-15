package cn.itcast.travel.service;

public interface FavoriteService {

    /**
     * 查询是否收藏线路
     * @param rid
     * @param uid
     * @return
     */
    boolean isFavorite(String rid, int uid);

    /**
     * 根据rid和uid收藏线路
     * @param rid
     * @param uid
     * @return
     */
    boolean addFavorite(String rid, int uid);
}
