package com.hivetech.servletjsp.util;

public class DB_YamlConfig {

    private Welcome welcome;
    private DatabaseProperties databaseProperties;

    public Welcome getWelcome() {
        return welcome;
    }

    public void setWelcome(Welcome welcome) {
        this.welcome = welcome;
    }

    public DatabaseProperties getDatabaseProperties() {
        return databaseProperties;
    }

    public void setDatabaseProperties(DatabaseProperties databaseProperties) {
        this.databaseProperties = databaseProperties;
    }

    @Override
    public String toString() {
        return "DB_YamlConfig{" +
                "wel=" + welcome +
                ", dbProperties=" + databaseProperties +
                '}';
    }
}
