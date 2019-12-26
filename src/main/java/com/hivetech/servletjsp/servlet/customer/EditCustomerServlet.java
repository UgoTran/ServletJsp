package com.hivetech.servletjsp.servlet.customer;

import com.hivetech.servletjsp.model.Customer;
import com.hivetech.servletjsp.service.CustomerService;
import com.hivetech.servletjsp.service.impl.CustomerServiceImpl;
import com.hivetech.servletjsp.util.ProcessPhoto;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.logging.Logger;

@WebServlet("/customer/edit")
@MultipartConfig
public class EditCustomerServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(InfoCustomerServlet.class.getName());

    private CustomerService customerService;

    public void init() {
        customerService = new CustomerServiceImpl();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        editCustomer(request, response);
    }

    private void editCustomer(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
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
        // handle photaData
        Part imgPart = request.getPart("imgUpload");
        String photoNameInput = Paths.get(imgPart.getSubmittedFileName()).getFileName().toString();
        String photoNameOutput = ProcessPhoto.saveImg(imgPart.getInputStream(), photoNameInput);


        // create edited customer
        Customer editionCustomer = new Customer(
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
                birthday,
                photoNameOutput
        );

        boolean updated = customerService.update(editionCustomer);
        response.sendRedirect("/customer/list");
        LOGGER.info(String.format("Method: %s | action: %s  | Updated: %b | Customer: %d  ",
                request.getMethod(), request.getRequestURI(), updated, customerNumber));
    }
}
