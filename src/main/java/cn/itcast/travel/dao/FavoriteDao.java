package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Favorite;

public interface FavoriteDao {

    /**
     * 查询是否收藏线路
     * @param rid
     * @param uid
     * @return
     */
    Favorite findByRidAndUid(String rid, int uid);

    /**
     * 根据rid查询旅游线路收藏次数
     * @param rid
     * @return
     */
    int finCountByRid(int rid);

    /**
     * 根据rid和uid收藏旅游线路
     * @param rid
     * @param uid
     * @return
     */
    int saveFavorite(String rid, int uid);
}
