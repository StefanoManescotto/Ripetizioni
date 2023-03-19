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

        HttpSession s = request.getSession();
        Cookie[] cookies = request.getCookies();

        String op = request.getParameter("operazione");

        if(!ServletAuthenticate.isAuthenticated(cookies, s) || !s.getAttribute("type").equals("amministratore")){
            out.print("401");
            return;
        }

        String nome, cognome, corso;
        nome = request.getParameter("nome");
        cognome = request.getParameter("cognome");
        corso = request.getParameter("materia");

        if(nome == null || cognome == null || corso == null || op == null){
            out.print("400");
            return;
        }

        if(op.equals("rimuovi")){
            if(dao.removeAssocDocenteCorso(nome, cognome, corso)){
                out.println("200");
                return;
            }
        }else if(op.equals("aggiungi")){
            if(dao.addAssocDocenteCorso(nome, cognome, corso)){
                out.println("200");
                return;
            }
        }
        out.println("400");
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

        Gson gson = new Gson();
        String json = gson.toJson(dao.getAltriDocenti(materia));
        out.println(json);
    }
}
