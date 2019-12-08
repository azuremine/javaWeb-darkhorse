# javaWeb-darkhorse
java Web project

这个项目是某马中的一个java旅游网站

项目架构及使用技术
web层：
  a)	Servlet：前端控制器
  b)	html：视图
  c)	Filter：过滤器
  d)	BeanUtils：数据封装
  e)	Jackson：json序列化工具

service层：
  f)	Javamail：java发送邮件工具
  g)	Redis：nosql内存数据库 
  h)	Jedis：java的redis客户端

dao层：
  i)	Mysql：数据库
  j)	Druid：数据库连接池
  k)	JdbcTemplate：jdbc的工具

第一天 

完成了注册功能，前台jqury校验数据的正确性，发送ajax查询是否存在用户。
  说说今天的收获吧。我之前是学了SSM框架，并且做了一个项目才回来学习Java Web技术的。现在发觉框架做了很多事情，就比如Java Web中的CharchaterFilter数据编码的过滤器，在SpringMVC中直接配置一下就好；Java Web中浏览器响应json数据，需要用ObjectMapper类来转换数据的格式并设置response的contentTYpe，而SpringMVC中，导入json依赖包，可以直接以一个注解@ResponseBody解决。。。还有就是Servlet和SpringMVC中的前端控制器dispachareServlet，发出的请求都会经过Servlet,然后在doPost方法中处理业务逻辑等，而SpringMVC中的dispachareServlet（以我目前的了解），是负责分发具体的请求到Controller中，来细化业务逻辑；通过处理数据和渲染视图，可能就是为了能更好处理响应到客户端的结果。



第二天

完成了用户注册和退出，主要都是通过发送ajax请求拿到后台数据，并进行后台校验，校验通过则允许登录；退出则是将会话范围中的session移除掉，这里session有两种方法：

1、使用session.removeAttribute() 

​		只是移除session对象中的某个属性

2、使用session.invalidate()

​		销毁跟用户相关联的session对象

这里要特别注意分使用场景来做出不同的选择。今天做的功能不是特别的多，但是存在者很多的问题。比如说很多的Servlet中都有重复的可提取的代码；一个Servlet只能处理某一类的请求，不像SpringMVC中dispachareServlet可以处理多种请求



第三天

今天的Servlet代码抽取，正好解决了昨天一个Servlet只能处理某一类的请求，以及造成Servlet数量庞大的问题。通过创建一个继承自HttpServlet的BaseServlet类来解决此问题。BaseServlet中重写service方法，来处理分发同一类的请求。比如，跟User相关的请求统一为 /user/* 等。

创建继承自BaseServlet的UserServlet类来统一管理跟User相关的请求。比如用户注册，用户登录需要调用的后台方法等，统一写到UserServlet中。UserServlet处理 /user/* 请求。

SpringMVC中，也是通过@Controller和@RequestMapping来负责处理指定的由 DispatcherServlet 分发的请求。在通过@Controller标注的类中，如果方法上也有@RequestMapping注解，则请求分发到具体方法上。而且通过SpringMVC中的@RequestBody和@ResponseBody可以很便捷的获取请求的json数据和响应json数据。

