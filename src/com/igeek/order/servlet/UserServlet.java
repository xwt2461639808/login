package com.igeek.order.servlet;

import com.igeek.order.entity.User;
import com.igeek.order.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "UserServlet",urlPatterns = "/user")
public class UserServlet extends HttpServlet {

    private UserService service = new UserService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String code = request.getParameter("code");
        String username = request.getParameter("username");
        String address = request.getParameter("address");
        if(code.equals("login")){
            User user = service.login(username, address);
            if(user!=null){
                HttpSession session = request.getSession();
                session.setAttribute("user",user);
                response.sendRedirect("main.jsp");
            }else{
                request.setAttribute("username",username);
                request.getRequestDispatcher("login.jsp").forward(request,response);
            }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request,response);
    }
}
