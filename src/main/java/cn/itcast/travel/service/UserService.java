package cn.itcast.travel.service;

import cn.itcast.travel.domain.Category;
import cn.itcast.travel.domain.User;

import java.util.List;

public interface UserService {
    /**
     * 注册用户
     *
     * @param user
     * @return
     */
    boolean regist(User user);

    /**
     * 激活用户
     * @param code
     * @return
     */
    boolean activeUser(String code);

    /**
     * 用户登录
     * @param user
     * @return
     */
    User userLogin(User user);

}
