package com.example.ripetizioni0;

import com.example.ripetizioni0.DAO.DAO;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "AggiungiDocente", value = "/aggiungiDocente")
public class ServletAggiungiDocenti extends HttpServlet {

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

        String nome, cognome;
        nome = request.getParameter("nome");
        cognome = request.getParameter("cognome");

        if(nome == null || cognome == null){
            out.print("400");
            return;
        }

        if(dao.aggiungiDocente(nome, cognome)){
            out.print("200");
            return;
        }
        out.print("400");
    }
}
