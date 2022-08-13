package com.itheima.web.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/*")
public class loginFilter implements Filter {
    public void init(FilterConfig config) throws ServletException {
    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {

        String[] urls={"/login.html","/imgs","css","js","element-ui","/LoginServlet","/register.html","/registerServlet","/checkCodeServlet"};

        HttpServletRequest req= (HttpServletRequest) request;
        String URL = req.getRequestURL().toString();
        System.out.println(URL);
        for(String u:urls){
            if(URL.contains(u)){
                chain.doFilter(request, response);
                return;
            }
        }


        HttpSession session = req.getSession();
        Object user = session.getAttribute("user");

        if(user !=null){
            chain.doFilter(request, response);
        }else{
            req.setAttribute("login_msg","您尚未登录");
            req.getRequestDispatcher("login.html").forward(req,response);
        }

    }
}
