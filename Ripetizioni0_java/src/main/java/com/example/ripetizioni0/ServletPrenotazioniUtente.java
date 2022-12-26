package com.example.ripetizioni0;

import com.example.ripetizioni0.DAO.DAO;
import com.google.gson.Gson;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "PrenotazioniUtente", value = "/prenotazioniUtente")
public class ServletPrenotazioniUtente extends HttpServlet {
    DAO dao;
    public void init() {
        dao = (DAO)getServletContext().getAttribute("DAO");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        HttpSession s = request.getSession();
        Cookie[] cookies = request.getCookies();

        if(!ServletAuthenticate.isAuthenticated(cookies, s)){
            out.print("401");
            return;
        }

        if(s.getAttribute("type").toString().equals("cliente")){
            Gson gson = new Gson();
            String json = gson.toJson(dao.getPrenotazioniUtente(Integer.parseInt(s.getAttribute("userId").toString())));
            out.println(json);
        }else if(s.getAttribute("type").toString().equals("amministratore")){
            Gson gson = new Gson();
            String json = gson.toJson(dao.getPrenotazioni());
            out.println(json);
        }
    }


//    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        PrintWriter out = response.getWriter();
//        HttpSession s = request.getSession();
//        Cookie[] cookies = request.getCookies();
//
//        if(!ServletAuthenticate.isAuthenticated(cookies, s)){
//            out.print("401");
//            return;
//        }
//
//        if(s.getAttribute("type").toString().equals("cliente")){
//
//        }
//    }
}
