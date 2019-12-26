package com.hivetech.servletjsp.servlet.customer;

import com.hivetech.servletjsp.model.Customer;
import com.hivetech.servletjsp.service.CustomerService;
import com.hivetech.servletjsp.service.impl.CustomerServiceImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;

@WebServlet("/customer/delete")
public class DeleteCustomerServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(DeleteCustomerServlet.class.getName());

    private CustomerService customerService;

    public void init() {
        customerService = new CustomerServiceImpl();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doDelete(request, response);
    }

    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int id = Integer.parseInt(request.getParameter("customerNumber"));
        boolean isDeleted = customerService.delete(id);
        response.sendRedirect("/customer/list");

        LOGGER.info("action: " + request.getServletPath() + " | isDeleted: " + isDeleted + " | customerid: " + id );
    }


}
