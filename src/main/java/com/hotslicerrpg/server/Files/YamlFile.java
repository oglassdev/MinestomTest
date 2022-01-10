package com.hotslicerrpg.server.Files;

import com.hotslicerrpg.server.Server;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class YamlFile {
    protected Map<String, Object> map;
    public YamlFile(String name, String resourcePath) {
        if (!(new File(System.getProperty("user.dir")+"/"+ name + ".yml").exists())) {
            try { Files.copy(Server.class.getClassLoader().getResourceAsStream(resourcePath), Path.of(System.getProperty("user.dir") + "/"+ name +".yml")); }
            catch (IOException e) { e.printStackTrace(); }
        }
        try {
            InputStream inputStream = new FileInputStream(System.getProperty("user.dir") + "/"+ name + ".yml");
            Yaml yaml = new Yaml();
            map = yaml.load(inputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Map<String, Object> getMap() {
        return map;
    }
}
