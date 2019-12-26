package com.hivetech.servletjsp.util;

import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.InputStream;

public class ParseYaml {
    private static DB_YamlConfig dbInfo;

    public static DB_YamlConfig parse() {
        Yaml yaml = new Yaml(new Constructor(DB_YamlConfig.class));
        InputStream inYaml = DB_YamlConfig.class.getResourceAsStream("/mariadb.yaml");

        dbInfo = (DB_YamlConfig) yaml.load(inYaml);

        return dbInfo;
    }

}
