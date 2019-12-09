package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.Category;
import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.service.CategoryService;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.service.impl.CategoryServiceImpl;
import cn.itcast.travel.service.impl.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/category/*")
public class CategoryServlet extends BaseServlet {
    
    private CategoryService categoryService = new CategoryServiceImpl();
    private ResultInfo info = new ResultInfo();

    /**
     * 查找导航条目
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void findAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        List<Category> list = categoryService.findAll();
        if(list != null){
            info.setFlag(true);
            info.setData(list);
        }else {
            info.setFlag(false);
            info.setErrorMsg("data null !");
        }
        //返回字符串数据，并写到客户端
        writeValue(info,response);
    }


}
