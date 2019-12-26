package com.hivetech.servletjsp.service;

import com.hivetech.servletjsp.model.Customer;

import java.time.LocalDate;
import java.util.List;

public interface CustomerService {
    List<Customer> customers();
    Customer getCustomerById(int customerId);
    boolean add(Customer customer);
    boolean update(Customer customer);
    boolean delete(int id);
}
