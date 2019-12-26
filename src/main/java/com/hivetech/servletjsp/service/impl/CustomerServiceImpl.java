package com.hivetech.servletjsp.service.impl;

import com.hivetech.servletjsp.model.Customer;
import com.hivetech.servletjsp.service.CustomerService;
import com.hivetech.servletjsp.service.UploadPhotoService;
import com.hivetech.servletjsp.util.Connection_Utils;
import com.hivetech.servletjsp.util.JDBC_Helper;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CustomerServiceImpl implements CustomerService, UploadPhotoService<Customer> {

    private static final Logger LOGGER = Logger.getLogger(CustomerServiceImpl.class.getName());

    private static String SELECT_CUSTOMERS = "SELECT * FROM customers LIMIT 10;";
    private static String DELETE_CUSTOMER_BY_ID = "DELETE FROM customers WHERE customerNumber=?;";
    private static String GET_CUSTOMER_BY_ID = "SELECT * FROM customers WHERE customerNumber=?;";
    private static String UPDATE_CUSTOMER =
                    "UPDATE customers " +
                    "SET customerName=?, contactLastName=?, contactFirstName=?, " +
                        "phone=?, addressLine1=?, addressLine2=?, city=?, state=?, postalCode=?, " +
                        "country=?, salesRepEmployeeNumber=?, creditLimit= ?, birthday=? " +
                    "WHERE customerNumber=?;";
    private static String UPDATE_CUSTOMER_V2 =
            "UPDATE customers " +
                    "SET customerName=?, contactLastName=?, contactFirstName=?, " +
                    "phone=?, addressLine1=?, addressLine2=?, city=?, state=?, postalCode=?, " +
                    "country=?, salesRepEmployeeNumber=?, creditLimit= ?, birthday=?, profilePhotoName=? " +
                    "WHERE customerNumber=?;";
    private static String INSERT_CUSTOMER = "INSERT INTO customers VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
    private static String GET_NEXT_MAX_ID = "SELECT MAX(customerNumber) FROM customers;";
    private static String UPDATE_CUSTOMER_PHOTO_PATH = "UPDATE customers SET profilePhotoName=? WHERE customerNumber=?;";
    private static String GET_CUSTOMER_PHOTO_PATH_BY_ID = "SELECT profilePhotoName FROM customers WHERE customerNumber=?;";

    @Override
    public List<Customer> customers() {
        List<Customer> list = new ArrayList<>();
        Statement stm = null;
        ResultSet rs = null;

        try {
            stm = Connection_Utils.connect().createStatement();
            rs = stm.executeQuery(SELECT_CUSTOMERS);

            while (rs.next()) {
                //get customer info from result set
                int id = rs.getInt(1);
                String customerName = rs.getString("customerName");
                String contactLastName = rs.getString("contactLastName");
                String contactFirstName = rs.getString("contactFirstName");
                String phone = rs.getString("phone");
                String addressLine1 = rs.getString("addressLine1");
                String addressLine2 = rs.getString("addressLine2");
                String city = rs.getString("city");
                String state = rs.getString("state");
                String postalCode = rs.getString("postalCode");
                String country = rs.getString("country");
                Integer salesRepEmployeeNumber = rs.getInt("salesRepEmployeeNumber");
                Double creditLimit = rs.getDouble("creditLimit");
                //check birthday if null
                LocalDate birthday = rs.getDate("birthday") == null ?
                        null : rs.getDate("birthday").toLocalDate();
                String profilePhotoName = rs.getString("profilePhotoName");

                Customer tempCustomer = new Customer(
                        id,
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
                        profilePhotoName);

                list.add(tempCustomer);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, e.getMessage());
        } finally {
            JDBC_Helper.closeResultSet(rs);
            JDBC_Helper.closeStatement(stm);
        }

        return list;
    }

    @Override
    public Customer getCustomerById(int customerId) {

        Customer result = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            //get connect & create statement
            stm = Connection_Utils.connect().prepareStatement(GET_CUSTOMER_BY_ID);
            stm.setInt(1, customerId);

            rs = stm.executeQuery();
            //check Result set has item
            if (rs.next()) {
                result = new Customer(
                        rs.getInt(1),
                        rs.getString("customerName"),
                        rs.getString("contactLastName"),
                        rs.getString("contactFirstName"),
                        rs.getString("phone"),
                        rs.getString("addressLine1"),
                        rs.getString("addressLine2"),
                        rs.getString("city"),
                        rs.getString("state"),
                        rs.getString("postalCode"),
                        rs.getString("country"),
                        rs.getInt("salesRepEmployeeNumber"),
                        rs.getDouble("creditLimit"),
                        //check null
                        (rs.getDate("birthday") == null ?
                                null : rs.getDate("birthay").toLocalDate()),
                        rs.getString("profilePhotoName")
                );
            }

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, e.getMessage());
        } finally {
            JDBC_Helper.closeResultSet(rs);
            JDBC_Helper.closeStatement(stm);
        }

        return result;
    }

    @Override
    public boolean add(Customer customer) {
        boolean isInserted;
        PreparedStatement stm = null;

        try {
            //get connect & create statement
            stm = Connection_Utils.connect().prepareStatement(INSERT_CUSTOMER);
            stm.setInt(1, customer.getCustomerNumber());
            stm.setString(2, customer.getCustomerName());
            stm.setString(3, customer.getContactLastName());
            stm.setString(4, customer.getContactFirstName());
            stm.setString(5, customer.getPhone());
            stm.setString(6, customer.getAddressLine1());
            stm.setString(7, customer.getAddressLine2());
            stm.setString(8, customer.getCity());
            stm.setString(9, customer.getState());
            stm.setString(10, customer.getPostalCode());
            stm.setString(11, customer.getCountry());
            stm.setInt(12, customer.getSalesRepEmployeeNumber());
            stm.setDouble(13, customer.getCreditLimit());
            //check date null
            stm.setDate(14, customer.getBirthday() == null ?
                    null : Date.valueOf(customer.getBirthday())
            );
            stm.setString(15, customer.getProfilePhotoName());

            isInserted = stm.executeUpdate() > 0;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, e.getMessage());
            return false;
        } finally {
            JDBC_Helper.closeStatement(stm);
        }

        return isInserted;
    }

    @Override
    public boolean update(Customer customer) {
        boolean isUpdated;
        PreparedStatement stm = null;

        try {
            stm = Connection_Utils.connect().prepareStatement(UPDATE_CUSTOMER_V2);
            stm.setString(1, customer.getCustomerName());
            stm.setString(2, customer.getContactLastName());
            stm.setString(3, customer.getContactFirstName());
            stm.setString(4, customer.getPhone());
            stm.setString(5, customer.getAddressLine1());
            stm.setString(6, customer.getAddressLine2());
            stm.setString(7, customer.getCity());
            stm.setString(8, customer.getState());
            stm.setString(9, customer.getPostalCode());
            stm.setString(10, customer.getCity());
            stm.setInt(11, customer.getSalesRepEmployeeNumber());
            stm.setDouble(12, customer.getCreditLimit());
            stm.setDate(13, customer.getBirthday() == null ?
                    null : Date.valueOf(customer.getBirthday())
            );
            stm.setString(14, customer.getProfilePhotoName());
            stm.setInt(15, customer.getCustomerNumber());

            isUpdated = stm.executeUpdate() > 0;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, e.getMessage());
            return false;
        } finally {
            JDBC_Helper.closeStatement(stm);
        }

        return isUpdated;
    }

    @Override
    public boolean delete(int id) {
        boolean isDeleted = false;

        PreparedStatement stm = null;

        try {
            stm = Connection_Utils.connect().prepareStatement(DELETE_CUSTOMER_BY_ID);
            stm.setInt(1, id);
            isDeleted = stm.executeUpdate() > 0;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, e.getMessage());
            return false;
        } finally {
            JDBC_Helper.closeStatement(stm);
        }

        return isDeleted;
    }

    //    Handle photoProfile
    @Override
    public boolean editPhotoPath(Customer customer) {
        boolean isUpdated;
        PreparedStatement stm = null;

        try {
            stm = Connection_Utils.connect().prepareStatement(UPDATE_CUSTOMER_PHOTO_PATH);
            stm.setString(1, customer.getProfilePhotoName());
            stm.setInt(2, customer.getCustomerNumber());

            isUpdated = stm.executeUpdate() > 0;

        } catch (SQLException e) {
            return false;

        } finally {
            JDBC_Helper.closeStatement(stm);
        }

        return isUpdated;
    }

    @Override
    public String getPhotoPath(String elementId) {
        String photoPath= "";

        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            stm = Connection_Utils.connect().prepareStatement(GET_CUSTOMER_PHOTO_PATH_BY_ID);
            stm.setString(1, elementId);
            rs = stm.executeQuery();

            if(rs.next())
                photoPath = rs.getString(1);

        }catch (SQLException e){

            e.printStackTrace();
        }finally {

            JDBC_Helper.closeStatement(stm);
        }

        return photoPath;
    }
}
