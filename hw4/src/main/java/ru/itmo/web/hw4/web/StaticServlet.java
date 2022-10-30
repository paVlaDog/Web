//package ru.itmo.web.hw4.web;
//
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.File;
//import java.io.IOException;
//import java.io.OutputStream;
//import java.nio.file.Files;
//
//
//public class StaticServlet extends HttpServlet {
//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        File file = new File(getServletContext().getRealPath(request.getRequestURI()));
//        if (file.isFile()) {
//            response.setContentType(getServletContext().getMimeType(file.getName()));
//            Files.copy(file.toPath(), response.getOutputStream());
//        } else {
//            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
//        }
//    }
//}


package ru.itmo.web.hw4.web;

        import javax.servlet.http.HttpServlet;
        import javax.servlet.http.HttpServletRequest;
        import javax.servlet.http.HttpServletResponse;
        import java.io.File;
        import java.io.IOException;
        import java.io.OutputStream;
        import java.nio.file.Files;

public class StaticServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String requestURI = request.getRequestURI();
        String[] uris = requestURI.split("[+]");
        boolean allOkey = true;


        File[] files = new File[uris.length];
        for (int i = 0; i < uris.length; i++) {
            files[i] = new File("C:\\All\\Stu\\Web\\hw4\\src\\main\\webapp", uris[i]);
            if (!files[i].isFile()) {
                files[i] = new File(getServletContext().getRealPath(request.getRequestURI()));
            }
            if (!files[i].isFile()) {
                allOkey = false;
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        }

        if (allOkey) {
            response.setContentType(getServletContext().getMimeType(files[0].getName()));
            for (File file : files) {
                try (OutputStream outputStream = response.getOutputStream()) {
                    Files.copy(file.toPath(), outputStream);
                }
            }
        }
    }
}
