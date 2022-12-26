package com.example.ripetizioni0;

import com.example.ripetizioni0.DAO.DAO;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "Prenota", value = "/prenota")
public class ServletPrenota extends HttpServlet {
    DAO dao;
    public void init() {
        dao = (DAO)getServletContext().getAttribute("DAO");
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession s = request.getSession();
        PrintWriter out = response.getWriter();

        if(!ServletAuthenticate.isAuthenticated(request.getCookies(), s)){
            out.print("error");
            return;
        }

        //TODO: control if not null
        String corso = request.getParameter("corso");
        String nomeDocente = request.getParameter("nome");
        String cognomeDocente = request.getParameter("cognome");
        String data = request.getParameter("data");
        String[] ore = request.getParameter("ora").split("-");
//        System.out.println("userId: " + Integer.parseInt(s.getAttribute("userId").toString()));
//        System.out.println(corso);
//        System.out.println(nomeDocente);
//        System.out.println(cognomeDocente);
//        System.out.println(data);
//        System.out.println("TEST? " + ore.toString());

        for(String o : ore){
            dao.aggiungiPrenotazione(Integer.parseInt(s.getAttribute("userId").toString()), corso, nomeDocente, cognomeDocente, data, Integer.parseInt(o));
        }
        out.print("200");
    }
}
