package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.service.impl.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@WebServlet("/user/*")
public class UserServlet extends BaseServlet {

    private UserService userService = new UserServiceImpl();
    private ResultInfo info = new ResultInfo();

    /**
     * 用户退出
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void exit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        session.removeAttribute("user");
        //request.getSession().invalidate();
        //　　销毁当前会话域中的所有属性
        //
        //request.getSession().removeAttribute("username"); //假设当前session域对象中已经有属性名为username的属性
        //　　只移除特定属性名的属性
        response.sendRedirect(request.getContextPath() + "/login.html");
    }

    /**
     * 查找用户信息
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void findUser(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        writeValue(user,response);
    }

    /**
     * 用户登录
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void login(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {

        //验证码校验，通过才能继续后面的查询
        String check = request.getParameter("check");
        HttpSession session = request.getSession();
        String checkcode_server = (String) session.getAttribute("CHECKCODE_SERVER");
        session.removeAttribute("CHECKCODE_SERVER");//为了保证验证码只能使用一次
        //比较
        if(checkcode_server == null || !checkcode_server.equalsIgnoreCase(check)){
            //验证码错误
            //注册失败
            info.setFlag(false);
            info.setErrorMsg("验证码错误");
            //将info对象序列化为json
            writeValue(info,response);
            return;
        }

        //验证码通过
        Map<String, String[]> map = request.getParameterMap();
        User user = new User();
        //封装JavaBean对象
        try {
            BeanUtils.populate(user,map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        //查询是否有此用户
        User u = userService.userLogin(user);

        //判断用户是否为空
        if(u == null){
            //用户名或密码错误
            info.setFlag(false);
            info.setErrorMsg("用户名或密码错误！");
        }
        //判断用户激活状态
        if(u != null && u.getStatus().equals("N")){
            info.setFlag(false);
            info.setErrorMsg("账号未激活，请先激活账号！");
        }
        //判断登录成功
        if(u != null && u.getStatus().equals("Y")){
            info.setFlag(true);
            //将用户信息存入session
            session.setAttribute("user",u);
        }
        writeValue(info,response);
    }

    /**
     * 用户注册
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void registerUser(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {

        //验证校验
        String check = request.getParameter("check");
        //从sesion中获取验证码
        HttpSession session = request.getSession();
        String checkcode_server = (String) session.getAttribute("CHECKCODE_SERVER");
        session.removeAttribute("CHECKCODE_SERVER");//为了保证验证码只能使用一次
        //比较
        if(checkcode_server == null || !checkcode_server.equalsIgnoreCase(check)){
            //验证码错误
            //注册失败
            info.setFlag(false);
            info.setErrorMsg("验证码错误");
            //将info对象序列化为json,并返回
            writeValue(info,response);
            return;
        }

        //1.获取数据
        Map<String, String[]> map = request.getParameterMap();

        //2.封装对象
        User user = new User();
        try {
            BeanUtils.populate(user,map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        //3.调用service完成注册
        UserService service = new UserServiceImpl();
        boolean flag = service.regist(user);
        ResultInfo info = new ResultInfo();
        //4.响应结果
        if(flag){
            //注册成功
            info.setFlag(true);
        }else{
            //注册失败
            info.setFlag(false);
            info.setErrorMsg("注册失败!");
        }
        //将info对象序列化为json
        //将json数据写回客户端
        writeValue(info,response);
    }

    /**
     * 用户激活
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void activeUser(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {

        String code = request.getParameter("code");
        String msg = null;
        if(code == null || code == ""){
            //如果激活码为空,重定向到注册页面
            response.sendRedirect(request.getContextPath() + "/register.html");
        }else {
            //激活码不为空，调用后台查询激活码
            boolean flag = userService.activeUser(code);
            if(flag){
                //激活成功，跳转登录页面
                msg = "激活成功，请<a href='" + request.getContextPath() + "/login.html'>登录</a>";
                //response.sendRedirect(request.getContextPath() + "/login.html");
            }else {
                msg = "激活失败，请与管理员联系";
            }
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write(msg);
        }
    }

}
