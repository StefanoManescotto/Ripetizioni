package com.example.ripetizioni0;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "LogOut", value = "/logOut")
public class ServletLogOut extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        logOut(request, response);

        out.print("logged out");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        logOut(request, response);

        out.print("logged out");
    }

    private void logOut(HttpServletRequest request, HttpServletResponse response){
        HttpSession s = request.getSession();
        String jsessionID = s.getId();

        Cookie[] cookies = request.getCookies();

        for(Cookie c : cookies){
            if(c.getName().equals("sessionId") && c.getValue().equals(s.getId())){
                Cookie cookie = new Cookie("sessionId", jsessionID);
                cookie.setMaxAge(0);
                response.addCookie(cookie);
                break;
            }
        }

        s.invalidate();
    }
}
