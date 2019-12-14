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
     * 根据rid查询旅游线路收藏次数
     * @param rid
     * @return
     */
    int findCount(String rid);

}
