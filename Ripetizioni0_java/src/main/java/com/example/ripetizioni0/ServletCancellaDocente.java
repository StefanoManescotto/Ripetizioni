package com.example.ripetizioni0;

import com.example.ripetizioni0.DAO.DAO;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "CancellaDocente", value = "/cancellaDocente")
public class ServletCancellaDocente extends HttpServlet {
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

        String docente;
        docente = request.getParameter("idDocente");

        if(docente == null){
            out.print("wrong parameters");
            return;
        }

        dao.rimuoviDocente(Integer.parseInt(docente));
        out.print("200");
    }
}
