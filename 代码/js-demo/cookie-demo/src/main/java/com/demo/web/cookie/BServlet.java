package com.demo.web.cookie;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.net.URLDecoder;

@WebServlet("/bServlet")
public class BServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Cookie[] cookies=request.getCookies();

        for(Cookie cookie:cookies){
            String name=cookie.getName();
            if("username".equals(name)){
                String value = cookie.getValue();
                String dvalue = URLDecoder.decode(value, "UTF-8");
                System.out.println(name+":"+dvalue);
                break;
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }
}
