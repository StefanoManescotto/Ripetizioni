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
            out.print("401");
            return;
        }

        String corso = request.getParameter("corso");
        String nomeDocente = request.getParameter("nome");
        String cognomeDocente = request.getParameter("cognome");
        String data = request.getParameter("data");
        String[] ore = request.getParameter("ora").split("-");

        if(corso == null || nomeDocente == null || cognomeDocente == null || data == null || ore == null){
            out.print("400");
            return;
        }

        String serverResponse = "200";
        for(String o : ore){
            boolean ris = dao.aggiungiPrenotazione(Integer.parseInt(s.getAttribute("userId").toString()), corso, nomeDocente, cognomeDocente, data, Integer.parseInt(o));
            if(!ris){
                serverResponse = "400";
            }
        }
        out.print(serverResponse);
    }
}
