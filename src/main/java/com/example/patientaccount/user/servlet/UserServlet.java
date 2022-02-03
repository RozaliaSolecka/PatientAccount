package com.example.patientaccount.user.servlet;

import com.example.patientaccount.user.dto.GetUserResponse;
import com.example.patientaccount.user.dto.GetUsersResponse;
import com.example.patientaccount.servlet.MimeTypes;
import com.example.patientaccount.servlet.ServletUtility;
import com.example.patientaccount.user.entity.User;
import com.example.patientaccount.user.service.UserService;

import javax.inject.Inject;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

/**
 * Servlet for handling HTTP requests considering characters operations. Servlet API does not allow named path
 * parameters so wildcard is used.
 */
@WebServlet(urlPatterns = {
        UserServlet.Paths.USERS + "/*",

})
public class UserServlet extends HttpServlet {
    private UserService userService;

    @Inject
    public UserServlet(UserService userService) {
        this.userService = userService;
    }

    private final Jsonb jsonb = JsonbBuilder.create();


    public static class Paths {
        public static final String USERS = "/api/users";
    }

    public static class Patterns {
        public static final String USERS = "^/?$";
        public static final String USER = "^/[0-9]+/?$";
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = ServletUtility.parseRequestPath(request);
        String servletPath = request.getServletPath();
        if (Paths.USERS.equals(servletPath)) {
            if (path.matches(Patterns.USER)) {
                getUser(request, response);
                return;
            } else if (path.matches(Patterns.USERS)) {
                getUsers(request, response);
                return;
            }
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    private void getUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //Parsed request path is valid with character pattern and can contain starting and ending '/'.
        Long personalIdNumber = Long.parseLong(ServletUtility.parseRequestPath(request).replaceAll("/", ""));
        Optional<User> user = userService.find(personalIdNumber);

        if (user.isPresent()) {
            response.setContentType(MimeTypes.APPLICATION_JSON);
            response.getWriter()
                    .write(jsonb.toJson(GetUserResponse.entityToDtoMapper().apply(user.get())));
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private void getUsers(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType(MimeTypes.APPLICATION_JSON);
        response.getWriter()
                .write(jsonb.toJson(GetUsersResponse.entityToDtoMapper().apply(userService.findAll())));
    }
}