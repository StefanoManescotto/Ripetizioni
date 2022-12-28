package com.example.ripetizioni0;

import com.example.ripetizioni0.DAO.Corso;
import com.example.ripetizioni0.DAO.DAO;
import com.example.ripetizioni0.DAO.Docente;
import com.example.ripetizioni0.DAO.Prenotazione;
import com.google.gson.Gson;

import java.io.*;
import java.util.ArrayList;
import java.util.Calendar;

import javax.print.Doc;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "Ripetizioni", value = "/ripetizioni")
public class ServletRipetizioni extends HttpServlet {
    DAO dao;
    public void init() {
        dao = (DAO)getServletContext().getAttribute("DAO");
        System.out.println("INIT");
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ArrayList<Corso> corsi;

        corsi = dao.getCorsi();
        PrintWriter out = response.getWriter();

        for(Corso c : corsi){
            for(Docente d : dao.getDocentiMateria(c.getTitolo())){
                c.addInsegnante(d.toString());
            }
        }

        Gson gson = new Gson();
        String json = gson.toJson(corsi);
        out.println(json);
    }
}
