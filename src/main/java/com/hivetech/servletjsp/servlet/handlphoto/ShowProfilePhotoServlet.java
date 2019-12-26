package com.hivetech.servletjsp.servlet.handlphoto;

import com.hivetech.servletjsp.util.ProcessPhoto;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/show/profilephoto/*")
public class ShowProfilePhotoServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ProcessPhoto.showProfilePhoto(req, resp, req.getPathInfo());
    }

}
