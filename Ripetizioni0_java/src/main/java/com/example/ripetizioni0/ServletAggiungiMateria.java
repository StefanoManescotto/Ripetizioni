package com.example.ripetizioni0;

import com.example.ripetizioni0.DAO.DAO;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "AggiungiMateria", value = "/aggiungiMateria")
public class ServletAggiungiMateria extends HttpServlet {
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

        String materia, descrizione;
        materia = request.getParameter("materia");
        descrizione = request.getParameter("descrizione");

        if(materia == null || descrizione == null){
            out.print("wrong parameters");
            return;
        }

        if(dao.addMateria(materia, descrizione)){
            out.print("202");
            return;
        }
        out.print("La materia esiste gia");
    }
}
