package com.hivetech.servletjsp.servlet.customer;

import com.hivetech.servletjsp.model.Customer;
import com.hivetech.servletjsp.service.CustomerService;
import com.hivetech.servletjsp.service.impl.CustomerServiceImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.logging.Logger;

@WebServlet("/customer/info")
public class InfoCustomerServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(InfoCustomerServlet.class.getName());

    private CustomerService customerService;

    public void init() {
        customerService = new CustomerServiceImpl();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("customerNumber"));
        Customer selectedCustomer = customerService.getCustomerById(id);

        String photoPath = String.format("%s://%s:%s/show/profilephoto/",
                request.getScheme(),
                request.getServerName(),
                String.valueOf(request.getServerPort()));
        request.setAttribute("photoPath", photoPath);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/views/_customerInfo.jsp");
        request.setAttribute("selectedCustomer", selectedCustomer);
        dispatcher.forward(request, response);

        LOGGER.info("action: " + request.getServletPath() + " | Customer: " + id);
    }

//    @Override
//    protected void service(HttpServletRequest req, HttpServletResponse resp)
//            throws ServletException, IOException {
//
//        OutputStream os = resp.getOutputStream();
//        File imgFile = new File("D:/pic1.png");
//        FileInputStream fis = new FileInputStream(imgFile);
//        byte[] binaryImgValues = readStream(fis);
//        os.write(binaryImgValues);
//        os.flush();
////        os.close();
//    }

    /**
     * Reading Stream Data in Pipeline
     */
    private byte[] readStream(InputStream inStream) {
        ByteArrayOutputStream bops = new ByteArrayOutputStream();
        int data = -1;
        try {
            while((data = inStream.read()) != -1){
                bops.write(data);
            }
            return bops.toByteArray();
        }catch(Exception e){
            return null;
        }
    }
}
