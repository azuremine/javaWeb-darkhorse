package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.UserDao;
import cn.itcast.travel.dao.impl.UserDaoImpl;
import cn.itcast.travel.domain.Category;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.util.MailUtils;
import cn.itcast.travel.util.UuidUtil;

import java.util.List;


public class UserServiceImpl implements UserService {

    private UserDao userDao = new UserDaoImpl();
    /**
     * 注册用户
     * @param user
     * @return
     */
    @Override
    public boolean regist(User user) {
        //1.根据用户名查询用户对象
        User u = userDao.findByUsername(user.getUsername());
        //判断u是否为null
        if(u != null){
            //用户名存在，注册失败
            return false;
        }
        //2.保存用户信息
        //2.1设置激活码，唯一字符串
        user.setCode(UuidUtil.getUuid());
        //2.2设置激活状态
        user.setStatus("N");
        int result = userDao.saveUser(user);

        if(result != 1){
            return false;
        }

        //3.激活邮件发送，邮件正文
        String content = "<a href='http://localhost:8080/travel/user/activeUser?code="+ user.getCode() + "' >点击激活【黑马旅游网】</a>";
        MailUtils.sendMail(user.getEmail(),content,"黑马旅游网激活邮件");

        return true;
    }

    /**
     * 激活用户
     * @param code
     * @return
     */
    @Override
    public boolean activeUser(String code) {
        User user = userDao.findByCode(code);
        int flag = 0;
        if(user != null){
            //说明用户存在,设置用户的激活状态
            user.setStatus("Y");
            //更新用户信息
            flag = userDao.updateStatus(user);
        }
        if(flag == 1){
            return true;
        }
        return false;
    }

    /**
     * 用户登录
     * @param user
     */
    @Override
    public User userLogin(User user) {
        return userDao.findByUsernameAndPassword(user.getUsername(),user.getPassword());
    }

}
