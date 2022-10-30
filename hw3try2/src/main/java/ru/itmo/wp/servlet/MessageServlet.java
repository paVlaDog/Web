package ru.itmo.wp.servlet;

import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class MessageServlet extends HttpServlet {
    private static final String UTF_8 = StandardCharsets.UTF_8.name();

    public class MyStringPair {
        private final String user;
        private final String text;

        public MyStringPair (String first, String second) {
            this.user = first;
            this.text = second;
        }
    }

    ArrayList <MyStringPair> messages = new ArrayList <>();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        response.setContentType("application/json");
        String uri = request.getRequestURI();

        //response.setCharacterEncoding("cp1251");
        //response.setContentType("text/html;charset=cp1251");

        if (uri.equals("/message/auth")) {
            if (!(request.getParameter("user") == null || request.getParameter("user").trim().isEmpty())) {
                session.setAttribute("username", request.getParameter("user"));
                printToResponse(new Gson().toJson(request.getParameter("user")), response);
            } else {
                printToResponse(new Gson().toJson(""), response);
            }
        }

        if (uri.equals("/message/add")) {
            String textMessage = request.getParameter("text");
            if (!(textMessage == null || textMessage.trim().isEmpty())) {
                messages.add(new MyStringPair(session.getAttribute("username").toString(), textMessage));
                printToResponse(new Gson().toJson(messages), response);
            } else {
                printToResponse(new Gson().toJson(""), response);
            }
        }

        if (uri.equals("/message/findAll")) {
            if (session.getAttribute("username").toString() != null) {
                printToResponse(new Gson().toJson(messages), response);
            }
        }
    }

    private void printToResponse(String messages, HttpServletResponse response) throws IOException {
        response.getOutputStream().write(messages.getBytes(UTF_8));
//        response.getWriter().print(messages);
//        response.getWriter().flush();
    }
}
