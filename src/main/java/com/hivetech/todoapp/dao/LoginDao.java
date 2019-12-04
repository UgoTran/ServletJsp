package com.hivetech.todoapp.dao;

import com.hivetech.todoapp.model.Login;
import com.hivetech.todoapp.utils.JDBC_Utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginDao {
    public boolean validate(Login Login) throws ClassNotFoundException {
        boolean status = false;

        Class.forName("com.mysql.jdbc.Driver");

        try (Connection connection = JDBC_Utils.getConnection();
             // Step 2:Create a statement using connection object
             PreparedStatement preparedStatement = connection
                     .prepareStatement("select * from users where username = ? and password = ? ")) {
            preparedStatement.setString(1, Login.getUsername());
            preparedStatement.setString(2, Login.getPassword());

            System.out.println(preparedStatement);
            ResultSet rs = preparedStatement.executeQuery();
            status = rs.next();

        } catch (SQLException e) {
            // process sql exception
            JDBC_Utils.printSQLException(e);
        }
        return status;
    }
}
