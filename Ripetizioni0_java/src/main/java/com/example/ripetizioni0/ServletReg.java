package com.example.ripetizioni0;

import com.example.ripetizioni0.DAO.DAO;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import org.apache.commons.codec.digest.DigestUtils;

@WebServlet(name = "SignUp", value = "/signUp")
public class ServletReg extends HttpServlet {
    DAO dao;
    public void init() {
        dao = (DAO)getServletContext().getAttribute("DAO");
        System.out.println("INIT");
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();

        String nome = request.getParameter("nome");
        String cognome = request.getParameter("cognome");
        String email = request.getParameter("email");
        String psw = request.getParameter("password");

        if(nome == null || cognome == null || email == null || psw == null){
            out.print("400");
            return;
        }

        psw = DigestUtils.sha256Hex(psw).toUpperCase();

        if(dao.aggiungiUtente(nome, cognome, email, psw, "cliente")){
            out.print("200");
            return;
        }
        out.print("400");
    }
}
