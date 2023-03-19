package com.example.ripetizioni0;

import com.example.ripetizioni0.DAO.DAO;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "CambiaStato", value = "/cambiaStato")
public class ServletCambiaPrenStato extends HttpServlet {
    DAO dao;
    public void init() {
        dao = (DAO)getServletContext().getAttribute("DAO");
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();

        HttpSession s = request.getSession();
        Cookie[] cookies = request.getCookies();

        if(!ServletAuthenticate.isAuthenticated(cookies, s)){
            out.print("401");
            return;
        }


        if(request.getParameter("newState").equals("cancellata") || request.getParameter("newState").equals("effettuata")){
            if(dao.cambiaPrenStato(Integer.parseInt(request.getParameter("idPrenotazione")), request.getParameter("newState"), Integer.parseInt(s.getAttribute("userId").toString()),
                    s.getAttribute("type").toString())){
                out.print("200");
            }else{
                out.print("400");
            }
        }else{
            out.print("400");
        }
    }
}
