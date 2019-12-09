package cn.itcast.travel.web.servlet;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class BaseServlet extends HttpServlet {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String uri = req.getRequestURI();
        String methodName = uri.substring(uri.lastIndexOf("/") + 1);
        try {
            Method method = this.getClass().getMethod(methodName,HttpServletRequest.class,HttpServletResponse.class);
            method.invoke(this,req,resp);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    /**
     * 直接将传入的对象序列化为json,并写回客户端
     * @param obj
     * @param response
     * @throws IOException
     */
    public void writeValue(Object obj,HttpServletResponse response) throws IOException {
        response.setContentType("application/json;charset=utf-8");
        objectMapper.writeValue(response.getOutputStream(),obj);
    }

    /**
     * 将传入的对象序列化为json，返回字符串
     * @param obj
     * @return
     * @throws JsonProcessingException
     */
    public String writeValueAsString(Object obj) throws JsonProcessingException {
        return objectMapper.writeValueAsString(obj);
    }

}
