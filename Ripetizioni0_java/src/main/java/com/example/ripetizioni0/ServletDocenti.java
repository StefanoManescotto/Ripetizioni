package com.example.ripetizioni0;

import com.example.ripetizioni0.DAO.DAO;
import com.example.ripetizioni0.DAO.Persona;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "ServletDocenti", value = "/aggiungiDocente")
public class ServletDocenti extends HttpServlet {

    DAO dao;
    public void init() {
        dao = (DAO)getServletContext().getAttribute("DAO");
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();

        Persona utente = dao.getUtente(request.getParameter("emailUtente"));
        if(utente == null){
            out.println("Utente non trovato");
        }else if(utente.getPassword().compareTo(request.getParameter("password")) == 0 && utente.getRuolo().compareTo("amministratore") == 0){
            //DAO.aggiungiDocente(request.getParameter("nomeDocente"), request.getParameter("cognomeDocente"));

            HttpSession session = request.getSession();
            if(request.getParameter("emailUtente") != null) {
                session.setAttribute("userName", request.getParameter("emailUtente"));
            }

            out.println("<html>");
            out.println("<head>");
            out.println("<title>Docente Aggiunto</title>");
            out.println("</head>");
            out.println("<body>");
            out.println(utente + "<br>");
            out.println("Docente Aggiunto <a href='./index.jsp'>Torna Indietro</a>");
            out.println("</body>");
            out.println("</html>");
        }else{
            out.println("password errata " + utente.getPassword() + " " + request.getParameter("password") + " " + utente);
        }
    }
}
