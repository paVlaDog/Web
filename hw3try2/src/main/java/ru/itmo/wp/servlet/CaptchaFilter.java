package ru.itmo.wp.servlet;

import ru.itmo.wp.util.ImageUtils;

import javax.servlet.*;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;

public class CaptchaFilter extends HttpFilter {
    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpSession session = request.getSession();
        Boolean captchaGenerated = (Boolean) session.getAttribute("captchaGenerated");
        Boolean captchaPassed = (Boolean) session.getAttribute("captchaPassed");
        if (captchaGenerated == null || !captchaGenerated) {
            System.out.println("I in 1");
            generateCaptcha(response, session);
        } else if (captchaPassed == null || !captchaPassed) {
            System.out.println("I in 2");
            String answer = request.getParameter("answer");
            if (answer != null && answer.equals(session.getAttribute("ansOnCaptcha").toString())) {
                System.out.println("I in 3");
                session.setAttribute("captchaPassed", true);
                super.doFilter(request, response, chain);
            } else if (!request.getRequestURI().equals("/forCaptcha.png")) {
                System.out.println("I in 4");
                System.out.println("URIincaptcha:" + request.getRequestURI());
                generateCaptcha(response, session);
            }
        } else {
            System.out.println("I in 5");
            super.doFilter(request, response, chain);
        }
    }

    private void generateCaptcha(HttpServletResponse res, HttpSession session) throws IOException {
        session.setAttribute("ansOnCaptcha", Math.round(Math.random() * 900 + 100));
        session.setAttribute("captcha", ImageUtils.toPng(String.valueOf(session.getAttribute("ansOnCaptcha"))));
        //File captchaImg = new File("C:\\All\\Stu\\Web\\hw3try2\\src\\main\\webapp\\static\\forCaptcha.png");
        //FileOutputStream stream = new FileOutputStream(captchaImg);
        //stream.write(ImageUtils.toPng(String.valueOf(session.getAttribute("ansOnCaptcha"))));
        File captchaHTML = new File(getServletContext().getRealPath("static/captcha.html"));
        session.setAttribute("captchaGenerated", true);
        res.setContentType("text/html");
        writeFile(res, captchaHTML);
    }

    private void writeFile(HttpServletResponse response, File file) throws IOException {
        try (OutputStream outputStream = response.getOutputStream()) {
            Files.copy(file.toPath(), outputStream);
        }
    }
}
