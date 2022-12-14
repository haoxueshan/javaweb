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
        //1. ?????? ???????????? ??? ??????????????????    url?currentPage=1&pageSize=5
        String _currentPage = request.getParameter("currentPage");
        String _pageSize = request.getParameter("pageSize");

        int currentPage = Integer.parseInt(_currentPage);
        int pageSize = Integer.parseInt(_pageSize);

        //2. ??????service??????
        PageBean<Brand> pageBean = brandServlce.selectByPage(currentPage, pageSize);

        //2. ??????JSON
        String jsonString = JSON.toJSONString(pageBean);
        //3. ?????????
        response.setContentType("text/json;charset=utf-8");
        response.getWriter().write(jsonString);
    }

    public void selectByPageAndCondition(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        System.out.println("----");
        //1. ?????? ???????????? ??? ??????????????????    url?currentPage=1&pageSize=5
        String _currentPage = request.getParameter("currentPage");
        String _pageSize = request.getParameter("pageSize");

        int currentPage = Integer.parseInt(_currentPage);
        int pageSize = Integer.parseInt(_pageSize);

        // ????????????????????????
        BufferedReader br = request.getReader();
        String params = br.readLine();//json?????????
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

        //?????????????????????
        String remember = users.get("checkbox");

        //2. ??????service??????
        User user = userServer.Login(username, password);

        //3. ??????
        if (user != null) {
            //???????????????????????????????????????BrandServlet
            System.out.println("ok");
            //?????????????????????????????????
            if ("1".equals(remember)) {
                //??????????????????Cookie

                //1. ??????Cookie??????
                Cookie c_username = new Cookie("username", username);
                Cookie c_password = new Cookie("password", password);
                // ??????Cookie???????????????
                c_username.setMaxAge(60 * 60 * 24 * 7);
                c_password.setMaxAge(60 * 60 * 24 * 7);
                //2. ??????
                response.addCookie(c_username);
                response.addCookie(c_password);
            }

            //?????????????????????user??????????????????session
            HttpSession session = request.getSession();
            session.setAttribute("user", user);

//            String contextPath = request.getContextPath();
//            System.out.println(contextPath);
            //response.sendRedirect( "/brand.html");
            //request.getRequestDispatcher("/brand.html");
            response.setContentType("text/json;charset=utf-8");
            response.getWriter().write("http://localhost/brand-case/brand.html");
        } else {
            // ????????????,
            System.out.println("no");
            // ?????????????????????request
            response.setContentType("text/json;charset=utf-8");
            response.getWriter().write("err0");


            // ?????????login.jsp
            //request.getRequestDispatcher("/login.jsp").forward(request, response);
        }
    }
    //?????????
    public void checkCodeServlet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // ???????????????
        ServletOutputStream os = response.getOutputStream();
        String checkCode = CheckCodeUtil.outputVerifyImage(100, 50, os, 4);


        System.out.println(checkCode);
        // ??????Session
        HttpSession session = request.getSession();
        session.setAttribute("checkCodeGen",checkCode);
    }

    //??????
    public void registerServlet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1. ??????????????????????????????
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

        // ??????????????????????????????
        String checkCode = users.get("checkCode");
//        String checkCode = request.getParameter("checkCode");

        // ??????????????????????????????Session??????
        HttpSession session = request.getSession();
        String checkCodeGen = (String) session.getAttribute("checkCodeGen");
        System.out.println(checkCodeGen);

        // ??????
        if(!checkCodeGen.equalsIgnoreCase(checkCode)){
            HashMap<String,String> messige=new HashMap<>();
            messige.put("register_msg","???????????????");
            messige.put("reg","http://localhost/brand-case/register.html");
            String jsonString = JSON.toJSONString(messige);
            response.setContentType("text/json;charset=utf-8");
            response.getWriter().write(jsonString);
            // ???????????????
            return;
        }

        //2. ??????service ??????
        boolean flag = userServer.register(user);
        //3. ????????????????????????
        HashMap<String,String> messige=new HashMap<>();
        if(flag){
            //?????????????????????????????????
            messige.put("register_msg","????????????????????????");
            messige.put("reg","http://localhost/brand-case/login.html");
            String jsonString = JSON.toJSONString(messige);
            response.setContentType("text/json;charset=utf-8");
            response.getWriter().write(jsonString);
        }else {
            //????????????????????????????????????
            messige.put("register_msg","??????????????????");
            messige.put("reg","http://localhost/brand-case/login.html");
            String jsonString = JSON.toJSONString(messige);
            response.setContentType("text/json;charset=utf-8");
            response.getWriter().write(jsonString);
//            request.setAttribute("register_msg","??????????????????");
//            request.getRequestDispatcher("/register.html").forward(request,response);
        }
    }
}
