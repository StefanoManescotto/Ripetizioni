package com.example.ripetizioni0;

import com.example.ripetizioni0.DAO.DAO;
import com.example.ripetizioni0.DAO.Prenotazione;

import java.io.*;
import java.util.ArrayList;
import java.util.Calendar;

import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "helloServlet", value = "/ripetizioni")
public class ServletRipetizioni extends HttpServlet {
    public void init() {
        DAO.registerDriver();
        //DAO.aggiungiPrenotazione("matematica", "Mario", "Rossi", "2022-12-01", 18);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ArrayList<Prenotazione> prenotazioni = new ArrayList<>();


        prenotazioni.add(new Prenotazione("matematica", 1, 1, 17, "2022-11-03"));
        prenotazioni.add(new Prenotazione("matematica", 1, 1, 16, "2022-11-04"));
        prenotazioni.add(new Prenotazione("matematica", 1, 1, 16, "2022-12-04"));
        prenotazioni.addAll(DAO.getPrenotazioni());

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<html><body>");

        final Calendar c = Calendar.getInstance();

        c.set(c.get(Calendar.YEAR), Calendar.NOVEMBER, 28, 15,0,0);

        while(c.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY && c.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY){
            if(!isInPrenotazioni(c, prenotazioni)){
                out.println("<h1>" + c.getTime() + "</h1>");
            }

            c.add(Calendar.HOUR_OF_DAY, 1);
            if(c.get(Calendar.HOUR_OF_DAY) > 19){
                c.set(Calendar.HOUR_OF_DAY, 15);
                c.add(Calendar.DAY_OF_WEEK, 1);
            }
        }

        ArrayList<Prenotazione> ps = DAO.getPrenotazioni();
        DAO.getRipetizioni();
        for(Prenotazione p : ps){
            out.println("<h1>" + p + "</h1>");
        }
        out.println("</body></html>");
    }

    public void destroy() {
    }

    private boolean isInPrenotazioni(Calendar c, ArrayList<Prenotazione> a){
        for(Prenotazione p : a){
            if(c.get(Calendar.DAY_OF_MONTH) == getDay(p.getData()) &&
                    c.get(Calendar.MONTH) == getMonth(p.getData()) && c.get(Calendar.HOUR_OF_DAY) == p.getOra()){
                return true;
            }
        }
        return false;
    }

    private int getDay(String time){
        return Integer.valueOf(time.split("-")[2]);
    }

    private int getMonth(String time){
        return Integer.valueOf(time.split("-")[1]) - 1;
    }
}
