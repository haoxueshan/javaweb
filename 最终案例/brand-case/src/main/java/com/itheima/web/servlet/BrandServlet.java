package com.itheima.web.servlet;

import com.alibaba.fastjson.JSON;
import com.itheima.pojo.Brand;
import com.itheima.pojo.PageBean;
import com.itheima.pojo.User;
import com.itheima.service.BrandServlce;
import com.itheima.service.UserServer;
import com.itheima.service.impl.BrandServletImpl;
import com.itheima.service.impl.UserServletImpl;
import com.itheima.util.CheckCodeUtil;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/brand/*")
public class BrandServlet extends BaseServlet{

    private BrandServlce brandServlce=new BrandServletImpl();
    private UserServer   userServer=new UserServletImpl();
    public void selectAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("selectAll.....");
        List<Brand> brands = brandServlce.selectAll();
        String jsonString = JSON.toJSONString(brands);

        response.setContentType("text/json;charset=utf-8");
        response.getWriter().write(jsonString);
    }
    public void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException  {
        BufferedReader reader = request.getReader();
        String params = reader.readLine();
        Brand brand = JSON.parseObject(params, Brand.class);

        brandServlce.add(brand);
        response.getWriter().write("success");
    }

    public void deleteByids(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException  {
        BufferedReader reader = request.getReader();
        String params = reader.readLine();
        int[] ids = JSON.parseObject(params, int[].class);

        brandServlce.deleteById(ids);
        response.getWriter().write("success");
    }

    public void selectBypage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        System.out.println("...........");
        //1. 接收 当前页码 和 每页展示条数    url?currentPage=1&pageSize=5
        String _currentPage = request.getParameter("currentPage");
        String _pageSize = request.getParameter("pageSize");

        int currentPage = Integer.parseInt(_currentPage);
        int pageSize = Integer.parseInt(_pageSize);

        //2. 调用service查询
        PageBean<Brand> pageBean = brandServlce.selectByPage(currentPage, pageSize);

        //2. 转为JSON
        String jsonString = JSON.toJSONString(pageBean);
        //3. 写数据
        response.setContentType("text/json;charset=utf-8");
        response.getWriter().write(jsonString);
    }

    public void selectByPageAndCondition(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        System.out.println("----");
        //1. 接收 当前页码 和 每页展示条数    url?currentPage=1&pageSize=5
        String _currentPage = request.getParameter("currentPage");
        String _pageSize = request.getParameter("pageSize");

        int currentPage = Integer.parseInt(_currentPage);
        int pageSize = Integer.parseInt(_pageSize);

        // 获取查询条件对象
        BufferedReader br = request.getReader();
        String params = br.readLine();//json字符串
        Brand brand = JSON.parseObject(params,Brand.class);

        PageBean<Brand> pageBean = brandServlce.selectByPageAndCondition(currentPage, pageSize,brand);
        String jsonString = JSON.toJSONString(pageBean);

        response.setContentType("text/json;charset=utf-8");
        response.getWriter().write(jsonString);

    }

    public void LoginServlet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BufferedReader reader = request.getReader();
        String params = reader.readLine();
        HashMap<String,String> users = JSON.parseObject(params,HashMap.class);
        System.out.println(users);
        String username = users.get("username");
        String password =users.get("password");

        //获取复选框数据
        String remember = users.get("checkbox");

        //2. 调用service查询
        User user = userServer.Login(username, password);

        //3. 判断
        if (user != null) {
            //登录成功，跳转到查询所有的BrandServlet
            System.out.println("ok");
            //判断用户是否勾选记住我
            if ("1".equals(remember)) {
                //勾选了，发送Cookie

                //1. 创建Cookie对象
                Cookie c_username = new Cookie("username", username);
                Cookie c_password = new Cookie("password", password);
                // 设置Cookie的存活时间
                c_username.setMaxAge(60 * 60 * 24 * 7);
                c_password.setMaxAge(60 * 60 * 24 * 7);
                //2. 发送
                response.addCookie(c_username);
                response.addCookie(c_password);
            }

            //将登陆成功后的user对象，存储到session
            HttpSession session = request.getSession();
            session.setAttribute("user", user);

//            String contextPath = request.getContextPath();
//            System.out.println(contextPath);
            //response.sendRedirect( "/brand.html");
            //request.getRequestDispatcher("/brand.html");
            response.setContentType("text/json;charset=utf-8");
            response.getWriter().write("http://localhost/brand-case/brand.html");
        } else {
            // 登录失败,
            System.out.println("no");
            // 存储错误信息到request
            response.setContentType("text/json;charset=utf-8");
            response.getWriter().write("err0");


            // 跳转到login.jsp
            //request.getRequestDispatcher("/login.jsp").forward(request, response);
        }
    }
    //验证码
    public void checkCodeServlet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 生成验证码
        ServletOutputStream os = response.getOutputStream();
        String checkCode = CheckCodeUtil.outputVerifyImage(100, 50, os, 4);


        System.out.println(checkCode);
        // 存入Session
        HttpSession session = request.getSession();
        session.setAttribute("checkCodeGen",checkCode);
    }

    //注册
    public void registerServlet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1. 获取用户名和密码数据
        BufferedReader reader = request.getReader();
        String params = reader.readLine();
        HashMap<String,String> users = JSON.parseObject(params,HashMap.class);
        System.out.println(users);
        String username = users.get("username");
        String password =users.get("password");
//        String username = request.getParameter("username");
//        String password = request.getParameter("password");


        User user = new User();
        user.setUsername(username);
        user.setPassword(password);

        // 获取用户输入的验证码
        String checkCode = users.get("checkCode");
//        String checkCode = request.getParameter("checkCode");

        // 程序生成的验证码，从Session获取
        HttpSession session = request.getSession();
        String checkCodeGen = (String) session.getAttribute("checkCodeGen");
        System.out.println(checkCodeGen);

        // 比对
        if(!checkCodeGen.equalsIgnoreCase(checkCode)){
            HashMap<String,String> messige=new HashMap<>();
            messige.put("register_msg","验证码错误");
            messige.put("reg","http://localhost/brand-case/register.html");
            String jsonString = JSON.toJSONString(messige);
            response.setContentType("text/json;charset=utf-8");
            response.getWriter().write(jsonString);
            // 不允许注册
            return;
        }

        //2. 调用service 注册
        boolean flag = userServer.register(user);
        //3. 判断注册成功与否
        HashMap<String,String> messige=new HashMap<>();
        if(flag){
            //注册功能，跳转登陆页面
            messige.put("register_msg","注册成功，请登录");
            messige.put("reg","http://localhost/brand-case/login.html");
            String jsonString = JSON.toJSONString(messige);
            response.setContentType("text/json;charset=utf-8");
            response.getWriter().write(jsonString);
        }else {
            //注册失败，跳转到注册页面
            messige.put("register_msg","用户名已存在");
            messige.put("reg","http://localhost/brand-case/login.html");
            String jsonString = JSON.toJSONString(messige);
            response.setContentType("text/json;charset=utf-8");
            response.getWriter().write(jsonString);
//            request.setAttribute("register_msg","用户名已存在");
//            request.getRequestDispatcher("/register.html").forward(request,response);
        }
    }
}
