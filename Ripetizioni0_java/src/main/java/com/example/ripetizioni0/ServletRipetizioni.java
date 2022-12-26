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
//        System.out.println("TEST QUI");
//        Cookie[] cookies = request.getCookies();
//        if(cookies == null){
//
//        }
//        for(Cookie c : cookies){
//            System.out.println("cookie: " + c.getValue());
//        }
        // ArrayList<Prenotazione> prenotazioni = new ArrayList<>();
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



//        for(String i : dao.getDocentiMateria("matematica")){
//            out = response.getWriter();
//            out.println(i);
//        }







//        dao.aggiungiPrenotazione("matematica", "Mario", "Rossi", "2022-12-01", 16);

//        prenotazioni.add(new Prenotazione("matematica", 1, 1, 17, "2022-11-03"));
//        prenotazioni.add(new Prenotazione("matematica", 1, 1, 16, "2022-11-04"));
//        prenotazioni.add(new Prenotazione("matematica", 1, 1, 16, "2022-12-04"));
//        prenotazioni.addAll(dao.getPrenotazioni());
//
//        response.setContentType("text/html");
//        PrintWriter out = response.getWriter();
//        out.println("<html><body>");
//
//        final Calendar c = Calendar.getInstance();
//
//        c.set(c.get(Calendar.YEAR), Calendar.NOVEMBER, 28, 15,0,0);

//        while(c.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY && c.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY){
//            if(!isInPrenotazioni(c, prenotazioni)){
//                out.println("<h1>" + c.getTime() + "</h1>");
//            }
//
//            c.add(Calendar.HOUR_OF_DAY, 1);
//            if(c.get(Calendar.HOUR_OF_DAY) > 19){
//                c.set(Calendar.HOUR_OF_DAY, 15);
//                c.add(Calendar.DAY_OF_WEEK, 1);
//            }
//        }

//        ArrayList<Prenotazione> ps = dao.getPrenotazioni();
//        System.out.println("PRENOTAZIONI: " + ps.size());
        //dao.getRipetizioni();
//        for(Prenotazione p : ps){
//            out.println("<h1>" + p + "</h1>");
//        }
//        out.println("</body></html>");
    }

    private boolean isInPrenotazioni(Calendar c, ArrayList<Prenotazione> a){
        for(Prenotazione p : a){
            System.out.println(p.getYear() + " " + p.getDay() + " " + p.getMonth());
            if(c.get(Calendar.YEAR) == p.getYear() && c.get(Calendar.DAY_OF_MONTH) == p.getDay() &&
                    c.get(Calendar.MONTH) == p.getMonth() && c.get(Calendar.HOUR_OF_DAY) == p.getOra()){
                return true;
            }
        }
        return false;
    }
}
