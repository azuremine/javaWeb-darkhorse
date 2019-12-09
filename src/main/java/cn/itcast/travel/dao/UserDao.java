package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Category;
import cn.itcast.travel.domain.User;

import java.util.List;

public interface UserDao {

    /**
     * 根据用户名查询用户信息
     * @param username
     * @return
     */
    User findByUsername(String username);

    /**
     * 用户保存
     * @param user
     */
    int saveUser(User user);

    /**
     * 根据指定激活码查询用户
     * @param code
     * @return
     */
    User findByCode(String code);

    /**
     * 更新指定用户激活状态
     * @param user
     */
    int updateStatus(User user);

    /**
     * 根据用户名和密码查询指定用户
     * @param username
     * @param password
     * @return
     */
    User findByUsernameAndPassword(String username, String password);

}
