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
        ArrayList<Docente> docenti;
        String materia, from, to;

        materia = request.getParameter("materia");
        from = request.getParameter("from");
        to = request.getParameter("to");

        PrintWriter out = response.getWriter();

        if(materia == null || from == null || to == null){
            ArrayList<Corso> corsi;
            corsi = dao.getCorsi();

            docenti = dao.getAllDocenti();

            for(Corso c : corsi){
                for(Docente d : dao.getDocentiMateria(c.getTitolo())){
                    docenti.get(docenti.indexOf(d)).addMateria(c.getTitolo());
                }
            }
        }else{
            docenti = dao.getOrariDocenti(materia, from, to);
        }

        Gson gson = new Gson();
        String json = gson.toJson(docenti);
        out.println(json);
    }
}
