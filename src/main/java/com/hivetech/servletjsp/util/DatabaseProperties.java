package com.hivetech.servletjsp.util;

public class DatabaseProperties {
    private String dbUrl;
    private String dbPw;
    private String dbUser;
    private String dbDriver;

    public String getDbUrl() {
        return dbUrl;
    }

    public void setDbUrl(String dbUrl) {
        this.dbUrl = dbUrl;
    }

    public String getDbPw() {
        return dbPw;
    }

    public void setDbPw(String dbPw) {
        this.dbPw = dbPw;
    }

    public String getDbUser() {
        return dbUser;
    }

    public void setDbUser(String dbUser) {
        this.dbUser = dbUser;
    }

    public String getDbDriver() {
        return dbDriver;
    }

    public void setDbDriver(String dbDriver) {
        this.dbDriver = dbDriver;
    }
}
