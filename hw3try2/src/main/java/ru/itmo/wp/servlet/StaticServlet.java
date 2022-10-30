package ru.itmo.wp.servlet;

import ru.itmo.wp.util.ImageUtils;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.file.Files;

public class StaticServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String requestURI = request.getRequestURI();

        String[] uris = requestURI.split("[+]");
        System.out.println("InStaticServlet:" + requestURI);
        boolean allOkey = true;



        File[] files = new File[uris.length];
        for (int i = 0; i < uris.length; i++) {
            if (uris[i].equals("/forCaptcha.png")) {
                //System.out.println("urlForCaptcha" + uris[i]);

                HttpSession session = request.getSession();
                byte[] captcha = (byte[]) session.getAttribute("captcha");
                OutputStream out = response.getOutputStream();
                out.write(captcha);
            } else {
                files[i] = new File("C:\\All\\Stu\\Web\\hw3try2\\src\\main\\webapp\\static", uris[i]);
                if (!files[i].isFile()) {
                    files[i] = new File(getServletContext().getRealPath("/static"), uris[i]);
                }
                if (!files[i].isFile()) {
                    allOkey = false;
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                }
                System.err.println(files[i]);

                if (allOkey) {
                    response.setContentType(getServletContext().getMimeType(files[0].getName()));
                    for (File file : files) {
                        writeFile(response, file);
                    }
                }
            }
        }
    }

    private void writeFile(HttpServletResponse response, File file) throws IOException {
        try (OutputStream outputStream = response.getOutputStream()) {
            Files.copy(file.toPath(), outputStream);
        }
    }
}
