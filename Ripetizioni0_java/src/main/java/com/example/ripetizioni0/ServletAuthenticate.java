package com.example.ripetizioni0;

import com.google.gson.Gson;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "Authenticate", value = "/authenticate")
public class ServletAuthenticate extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        isAuthenticated(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        isAuthenticated(request, response);
    }

    private void isAuthenticated(HttpServletRequest request, HttpServletResponse response) throws IOException {
        AuthData auth = getAuthData(request);
        PrintWriter out = response.getWriter();
        if(auth == null){
            out.print("401");
            return;
        }
        Gson gson = new Gson();
        String json = gson.toJson(auth);
        out.println(json);
    }

    private AuthData getAuthData(HttpServletRequest request){
        AuthData authData = null;
        HttpSession s = request.getSession();
        Cookie[] cookies = request.getCookies();

        if(isAuthenticated(cookies, s)){
            authData = new AuthData(s.getAttribute("userName").toString(), s.getAttribute("userSurname").toString(),
                    s.getAttribute("userEmail").toString(), s.getAttribute("type").toString());
        }
        return authData;
    }

    public static boolean isAuthenticated(Cookie[] cookies, HttpSession session){
        if(cookies != null) {
            for (Cookie c : cookies) {
                if ((c.getName().equals("sessionId")) && c.getValue().trim().equals(session.getId().trim())) {
                    return true;
                }
            }
        }
        return false;
    }

    class AuthData{
        public String name;
        public String surname;
        public String email;
        public String type;

        public AuthData(String name, String surname, String email, String type){
            this.name = name;
            this.surname = surname;
            this.email = email;
            this.type = type;
        }
    }
}
