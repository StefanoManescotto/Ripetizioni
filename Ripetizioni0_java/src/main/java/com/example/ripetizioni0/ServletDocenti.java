package com.example.ripetizioni0;

import com.example.ripetizioni0.DAO.Corso;
import com.example.ripetizioni0.DAO.DAO;
import com.example.ripetizioni0.DAO.Docente;
import com.google.gson.Gson;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

@WebServlet(name = "Docenti", value = "/docenti")
public class ServletDocenti extends HttpServlet {
    DAO dao;
    public void init() {
        dao = (DAO)getServletContext().getAttribute("DAO");
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ArrayList<Corso> corsi;
        ArrayList<Docente> docenti;

        corsi = dao.getCorsi();
        PrintWriter out = response.getWriter();

        docenti = dao.getAllDocenti();

        for(Corso c : corsi){
            for(Docente d : dao.getDocentiMateria(c.getTitolo())){
                docenti.get(docenti.indexOf(d)).addMateria(c.getTitolo());
            }
        }

        Gson gson = new Gson();
        String json = gson.toJson(docenti);
        out.println(json);
    }
}
