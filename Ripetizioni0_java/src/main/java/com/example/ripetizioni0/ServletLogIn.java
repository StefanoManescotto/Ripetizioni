package com.example.ripetizioni0;

import com.example.ripetizioni0.DAO.DAO;
import com.example.ripetizioni0.DAO.Persona;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;

import org.apache.commons.codec.digest.DigestUtils;

@WebServlet(name = "LogIn", value = "/logIn")
public class ServletLogIn extends HttpServlet {
    DAO dao;
    public void init() {
        dao = (DAO)getServletContext().getAttribute("DAO");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Persona utente;

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String userEmail = request.getParameter("email");
        String password = request.getParameter("password");

        if(userEmail == null || password == null){
            out.print("400");
            return;
        }

        utente = dao.getUtente(userEmail);
        System.out.println(userEmail + " - " + utente);

        if(utente == null || !utente.getPassword().equals(DigestUtils.sha256Hex(password).toUpperCase())){
            out.print("400");
            return;
        }

        HttpSession s = request.getSession();
        String jsessionID = s.getId();

        Cookie cookie = new Cookie("sessionId", jsessionID);
        System.out.println("HERE CREATING? " + jsessionID);
        cookie.setMaxAge(60 * 30);
        response.addCookie(cookie);

        s.setAttribute("userId", utente.getId());
        s.setAttribute("userName", utente.getNome());
        s.setAttribute("userSurname", utente.getCognome());
        s.setAttribute("userEmail", utente.getEmail());
        s.setAttribute("type", utente.getRuolo());

        out.print("200");
    }
}
