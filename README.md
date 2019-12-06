# javaWeb-darkhorse
java Web project

项目架构及使用技术
web层：                                               service层：                                           dao层：
  a)	Servlet：前端控制器                                f)	Javamail：java发送邮件工具                        i)	Mysql：数据库
  b)	html：视图                                         g)	Redis：nosql内存数据库                           j)	Druid：数据库连接池
  c)	Filter：过滤器                                     h)	Jedis：java的redis客户端                          k)	JdbcTemplate：jdbc的工具
  d)	BeanUtils：数据封装
  e)	Jackson：json序列化工具


第一天 

完成了注册功能，前台jqury校验数据的正确性，发送ajax查询是否存在用户。
  说说今天的收获吧。我之前是学了SSM框架，并且做了一个项目才回来学习Java Web技术的。现在发觉框架做了很多事情，就比如Java Web中的CharchaterFilter数据编码的过滤器，在SpringMVC中直接配置一下就好；Java Web中浏览器响应json数据，需要用ObjectMapper类来转换数据的格式并设置response的contentTYpe，而SpringMVC中，导入json依赖包，可以直接以一个注解@ResponseBody解决。。。还有就是Servlet和SpringMVC中的前端控制器dispachareServlet，发出的请求都会经过Servlet,然后在doPost方法中处理业务逻辑等，而SpringMVC中的dispachareServlet（以我目前的了解），是负责分发具体的请求到Controller中，来细化业务逻辑；通过处理数据和渲染视图，可能就是为了能更好处理响应到客户端的结果。
