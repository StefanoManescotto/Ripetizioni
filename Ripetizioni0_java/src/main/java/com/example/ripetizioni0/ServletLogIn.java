package com.example.ripetizioni0;

import com.example.ripetizioni0.DAO.DAO;
import com.example.ripetizioni0.DAO.Persona;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "LogIn", value = "/logIn")
public class ServletLogIn extends HttpServlet {
    DAO dao;
    public void init() {
        dao = (DAO)getServletContext().getAttribute("DAO");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Persona utente = null;

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String userEmail = request.getParameter("email");
        String password = request.getParameter("password");
        // String sessionID = request.getParameter("sessione");
        //System.out.println("Test Authetication " + userEmail + " " + password);

        if(userEmail != null && password != null){
            utente = dao.getUtente(userEmail);

            if(utente == null || utente.getPassword().compareTo(request.getParameter("password")) != 0 ){
                System.out.println("Authentication failed");
                out.print("User Not Found");
                return;
            }
        }
        //System.out.println("Authentication successful");
        HttpSession s = request.getSession();
        String jsessionID = s.getId();

        Cookie cookie = new Cookie("sessionId", jsessionID);
        cookie.setMaxAge(60 * 30);
        response.addCookie(cookie);
//        System.out.println("JSessionID:" + jsessionID);
//        System.out.println("sessionID ricevuto:" + sessionID);
//        System.out.println("userName ricevuto:" + userEmail);

        s.setAttribute("userId", utente.getId());
        s.setAttribute("userEmail", utente.getEmail());
        s.setAttribute("type", utente.getRuolo());

        out.print("authenticated");
//        out.println("{" +
//                "'response': authenticated" +
//                "}");
//        if(sessionID != null && jsessionID.equals(sessionID)){
//            //System.out.println("sessione riconosciuta!");
//            out.print("sessione riconosciuta!");
//        }else{
//            //System.out.println(jsessionID);
//            out.print(jsessionID);
//        }
    }
}
