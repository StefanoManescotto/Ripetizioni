package com.example.ripetizioni0;

import com.example.ripetizioni0.DAO.DAO;
import com.google.gson.Gson;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "DocenteMateria", value = "/docenteMateria")
public class ServletAssocDocenteCorso extends HttpServlet {
    DAO dao;
    public void init() {
        dao = (DAO)getServletContext().getAttribute("DAO");
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();

        String nome, cognome, corso;
        nome = request.getParameter("nome");
        cognome = request.getParameter("cognome");
        corso = request.getParameter("materia");

        if(nome == null || cognome == null || corso == null){
            out.print("wrong parameter");
            return;
        }
        dao.assocDocenteCorso(nome, cognome, corso);
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
            out.print("wrong parameters");
            return;
        }

        Gson gson = new Gson();
        String json = gson.toJson(dao.getAltriDocenti(materia));
        out.println(json);
    }
}
