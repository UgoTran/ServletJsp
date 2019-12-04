package com.hivetech.todoapp.servlet;

import com.hivetech.todoapp.dao.LoginDao;
import com.hivetech.todoapp.model.Login;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet {
    private LoginDao loginDao;

    public void init() {
        loginDao = new LoginDao();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect("login/login.jsp");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        authenticate(request, response);
    }

    private void authenticate(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        Login Login = new Login();
        Login.setUsername(username);
        Login.setPassword(password);

        try {
            if (loginDao.validate(Login)) {
                RequestDispatcher dispatcher = request.getRequestDispatcher("todo/todo-list.jsp");
                dispatcher.forward(request, response);
            } else {
                HttpSession session = request.getSession();
                // session.setAttribute("user", username);
                // response.sendRedirect("login.jsp");
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
