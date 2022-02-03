package com.example.patientaccount.user.servlet;

import com.example.patientaccount.user.entity.User;
import com.example.patientaccount.user.service.UserService;
import com.example.patientaccount.servlet.MimeTypes;
import com.example.patientaccount.servlet.HttpHeaders;
import com.example.patientaccount.servlet.ServletUtility;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.*;
import java.nio.file.Files;
import java.util.Objects;
import java.util.Optional;

@WebServlet(urlPatterns = PortraitServlet.Paths.PORTRAITS + "/*")
@MultipartConfig(maxFileSize = 200 * 1024)
public class PortraitServlet extends HttpServlet {

    private UserService service;

    @Inject
    public PortraitServlet(UserService service) {
        this.service = service;
    }

    public static class Paths {
        public static final String PORTRAITS = "/api/portraits";
    }

    public static class Patterns {
        public static final String PORTRAIT = "^/[0-9]+/?$";
    }

    public static class Parameters {
        public static final String PORTRAIT = "portrait";
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = ServletUtility.parseRequestPath(request);
        String servletPath = request.getServletPath();
        if (Paths.PORTRAITS.equals(servletPath)) {
            if (path.matches(Patterns.PORTRAIT)) {
                getPortrait(request, response);
                return;
            }
        }
        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = ServletUtility.parseRequestPath(request);
        String servletPath = request.getServletPath();
        if (Paths.PORTRAITS.equals(servletPath)) {
            if (path.matches(Patterns.PORTRAIT)) {
                putPortrait(request, response);
                return;
            }
        }
        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = ServletUtility.parseRequestPath(request);
        String servletPath = request.getServletPath();
        if (Paths.PORTRAITS.equals(servletPath)) {
            if (path.matches(Patterns.PORTRAIT)) {
                postPortrait(request, response);
                return;
            }
        }
        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = ServletUtility.parseRequestPath(request);
        String servletPath = request.getServletPath();
        if (Paths.PORTRAITS.equals(servletPath)) {
            if (path.matches(Patterns.PORTRAIT)) {
                deletePortrait(request, response);
                return;
            }
        }
        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    private void getPortrait(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //Parsed request path is valid with character pattern and can contain starting and ending '/'.
        Long id = Long.parseLong(ServletUtility.parseRequestPath(request).replaceAll("/", ""));
        Optional<User> user = service.find(id);

        if (user.isPresent() && !Objects.equals(user.get().getPortraitPath(), "")) {
            String path = user.get().getPortraitPath();
            File file = new File(path);
            FileInputStream inputStream = new FileInputStream(file);
            response.addHeader(HttpHeaders.CONTENT_TYPE, MimeTypes.IMAGE_PNG);
            response.setContentLength((int)file.length());
            OutputStream outputStream = response.getOutputStream();
            byte[] buffer = new byte[4096];
            int bytesRead = -1;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            inputStream.close();
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private void putPortrait(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        //Parsed request path is valid with character pattern and can contain starting and ending '/'.
        Long id = Long.parseLong(ServletUtility.parseRequestPath(request).replaceAll("/", ""));
        Optional<User> user = service.find(id);

        if (user.isPresent()) {
            Part portrait = request.getPart(Parameters.PORTRAIT);
            if (portrait != null) {

                if(!user.get().getPortraitPath().equals("")){ //modyfikacja
                    String filePath = user.get().getPortraitPath();
                    File file = new File(filePath);
                    try(FileOutputStream outputStream = new FileOutputStream(file)) {

                        byte[] buffer = new byte[4096];
                        int bytesRead = -1;
                        InputStream inputStream = portrait.getInputStream();
                        while ((bytesRead = inputStream.read(buffer)) != -1) {
                            outputStream.write(buffer, 0, bytesRead);
                        }
                    }
                }
            }
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private void postPortrait(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        //Parsed request path is valid with character pattern and can contain starting and ending '/'.
        Long id = Long.parseLong(ServletUtility.parseRequestPath(request).replaceAll("/", ""));
        Optional<User> user = service.find(id);

        if (user.isPresent()) {
            Part portrait = request.getPart(Parameters.PORTRAIT);
            if (portrait != null) {

                if(user.get().getPortraitPath().equals("")){ //dodawanie

                    File file = new File("D:/Rozalia/semestr_7/JEE/avatars/" + id + ".png");
                    try(FileOutputStream outputStream = new FileOutputStream(file)) {

                        byte[] buffer = new byte[4096];
                        int bytesRead = -1;
                        InputStream inputStream = portrait.getInputStream();
                        while ((bytesRead = inputStream.read(buffer)) != -1) {
                            outputStream.write(buffer, 0, bytesRead);
                        }
                    }
                }
                user.get().setPortraitPath("D:/Rozalia/semestr_7/JEE/avatars/" + id + ".png");
                service.update(user.get());
            }
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private void deletePortrait(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        //Parsed request path is valid with character pattern and can contain starting and ending '/'.
        Long id = Long.parseLong(ServletUtility.parseRequestPath(request).replaceAll("/", ""));
        Optional<User> user = service.find(id);

        if (user.isPresent() && !user.get().getPortraitPath().equals("")) {

            String path = user.get().getPortraitPath();
            File file = new File(path);
            file.delete();

            user.get().setPortraitPath("");
            service.update(user.get());

            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}
