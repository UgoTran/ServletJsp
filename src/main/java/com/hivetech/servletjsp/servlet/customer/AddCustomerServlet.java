package com.hivetech.servletjsp.servlet.customer;

import com.hivetech.servletjsp.model.Customer;
import com.hivetech.servletjsp.service.CustomerService;
import com.hivetech.servletjsp.service.impl.CustomerServiceImpl;
import org.apache.commons.lang3.StringUtils;

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

@WebServlet("/customer/add")
public class AddCustomerServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(AddCustomerServlet.class.getName());

    private CustomerService customerService;

    public void init() {
        customerService = new CustomerServiceImpl();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int customerNumber = Integer.valueOf(request.getParameter("customerNumber"));
        String customerName = request.getParameter("customerName");
        String contactLastName = request.getParameter("contactLastName");
        String contactFirstName = request.getParameter("contactFirstName");
        String phone = request.getParameter("phone");
        String addressLine1 = request.getParameter("addressLine1");
        String addressLine2 = request.getParameter("addressLine2");
        String city = request.getParameter("city");
        String state = request.getParameter("state");
        String postalCode = request.getParameter("postalCode");
        String country = request.getParameter("country");
        Integer salesRepEmployeeNumber = !StringUtils.isAnyBlank(request.getParameter("salesRepEmployeeNumber"))
                ? Integer.parseInt(request.getParameter("salesRepEmployeeNumber")) : 0;
        Double creditLimit = !StringUtils.isEmpty(request.getParameter("creditLimit")) ?
                Double.valueOf(request.getParameter("creditLimit")) : 0.0;
        LocalDate birthday = StringUtils.isAnyBlank(request.getParameter("birthday")) ?
                null : LocalDate.parse(request.getParameter("birthday"));

        Customer newCustomer = new Customer(
                customerNumber,
                customerName,
                contactLastName,
                contactFirstName,
                phone,
                addressLine1,
                addressLine2,
                city,
                state,
                postalCode,
                country,
                salesRepEmployeeNumber,
                creditLimit,
                birthday
        );

        boolean added = customerService.add(newCustomer);
        response.sendRedirect("/customer/list");
        LOGGER.info(String.format("Method: %s | action: %s  | Added: %b | Customer: %d  ",
                request.getMethod(), request.getRequestURI(), added, customerNumber));

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setAttribute("requestAction", "add");
        request.setAttribute("selectedCustomer", new Customer());
        response.setHeader("requestActionHeader", "add");
        RequestDispatcher dispatcher = request.getRequestDispatcher("/views/_customerInfo.jsp");
        dispatcher.forward(request, response);

        LOGGER.info(String.format("Method: %s | action: %s    ",
                request.getMethod(), request.getRequestURI()));
    }
}
