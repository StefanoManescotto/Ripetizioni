package com.example.ripetizioni0;

import com.example.ripetizioni0.DAO.DAO;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "CancellaMateria", value = "/cancellaMateria")
public class ServletCancellaMateria extends HttpServlet {
    DAO dao;
    public void init() {
        dao = (DAO)getServletContext().getAttribute("DAO");
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();

        HttpSession s = request.getSession();
        Cookie[] cookies = request.getCookies();

        if(!ServletAuthenticate.isAuthenticated(cookies, s) || !s.getAttribute("type").equals("amministratore")){
            out.print("401");
            return;
        }

        String materia;
        materia = request.getParameter("materia");

        if(materia == null){
            out.print("400");
            return;
        }

        dao.rimuoviMateria(materia);
        out.print("200");
    }
}
