package com.example.ripetizioni0;

import com.example.ripetizioni0.DAO.DAO;

import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

@WebServlet(name = "ServletDao", value = "/servletDao", loadOnStartup = 1, asyncSupported = true)
public class ServletDAO extends HttpServlet {
    public void init() {
        ServletContext s = getServletContext();
        DAO dao = new DAO(s.getInitParameter("url1"), s.getInitParameter("user"), s.getInitParameter("password"));
        s.setAttribute("DAO", dao);
    }
}


//TODO: password encrypt
