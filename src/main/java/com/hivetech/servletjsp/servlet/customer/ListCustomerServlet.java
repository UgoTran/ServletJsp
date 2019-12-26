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
import java.time.LocalDate;
import java.util.List;
import java.util.logging.Logger;

@WebServlet("/customer/list")
public class ListCustomerServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(ListCustomerServlet.class.getSimpleName());

    private CustomerService customerService;

    public void init() {
        customerService = new CustomerServiceImpl();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Customer> list = customerService.customers();
        request.setAttribute("listCustomer", list);
        String photoPath = String.format("%s://%s:%s/show/profilephoto/",
                request.getScheme(),
                request.getServerName(),
                String.valueOf(request.getServerPort()));
        request.setAttribute("photoPath", photoPath);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/views/_listCustomer.jsp");
        dispatcher.forward(request, response);

        LOGGER.info(String.format("Method: %s; Servlet Path: %s; Num of Customer: %d"
                , request.getMethod(), request.getServletPath(), list.size()));
    }

}
