package com.example.ripetizioni0;

import com.example.ripetizioni0.DAO.DAO;

import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import java.util.Collection;
import java.util.Enumeration;

@WebServlet(name = "ServletDao", value = "/servletDao", loadOnStartup = 1, asyncSupported = true)
public class ServletDAO extends HttpServlet {
    public void init() {
        DAO dao = new DAO();
        ServletContext s = getServletContext();


        System.out.println("Att Names: " + s.getInitParameter("url1") + " " + s.getInitParameter("user") + " " + s.getInitParameter("password"));
        s.setAttribute("DAO", dao);
    }
}
