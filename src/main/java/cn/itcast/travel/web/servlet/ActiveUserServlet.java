package cn.itcast.travel.web.servlet;

import cn.itcast.travel.service.UserService;
import cn.itcast.travel.service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/activeUserServlet")
public class ActiveUserServlet extends HttpServlet {

    private UserService userService= new UserServiceImpl();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
                msg = "激活成功，请<a href='login.html'>登录</a>";
                //response.sendRedirect(request.getContextPath() + "/login.html");
            }else {
                msg = "激活失败，请与管理员联系";
            }
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write(msg);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
