package com.hivetech.servletjsp.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JDBC_Helper {

    private static final Logger LOGGER = Logger.getLogger(JDBC_Helper.class.getName());

    public static void closeStatement(Statement stm) {
        try {
            stm.close();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "ERROR: " + e.getMessage());
        }

    }

    public static void closeResultSet(ResultSet rs) {
        try {
            rs.close();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "ERROR: " + e.getMessage());
        }
    }

    public static void closeConnect(Connection connection) {

        try {
            if (connection != null || !connection.isClosed()) {
                connection.close();
                connection = null;
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "ERROR: " + e.getMessage());
        }
    }
}
