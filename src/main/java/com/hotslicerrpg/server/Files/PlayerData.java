package com.hotslicerrpg.server.Files;

import net.minestom.server.entity.Player;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerData {
    private static final Map<UUID, Map<String,Object>> map = new HashMap<>();

    Player player;

    public PlayerData(Player player) {
        this.player = player;
        if (!map.containsKey(player.getUuid())) {
            map.put(player.getUuid(), new HashMap<>());
            File file = new File(System.getProperty("user.dir")+"/PlayerData/"+ player.getUuid() + ".yml");
            if (!file.exists()) {
                try {
                    new File(System.getProperty("user.dir")+"/PlayerData").mkdirs();
                    file.createNewFile();
                    Yaml yaml = new Yaml();
                    FileWriter writer = new FileWriter(System.getProperty("user.dir")+"/PlayerData/"+ player.getUuid() + ".yml");
                    yaml.dump(map.get(player.getUuid()), writer);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    Yaml yaml = new Yaml();
                    InputStream inputStream = new FileInputStream(System.getProperty("user.dir") + "/PlayerData/" + player.getUuid() + ".yml");
                    map.put(player.getUuid(), yaml.load(inputStream));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void put(String string, Object object) {
        map.get(player.getUuid()).put(string, object);
    }
    public Object get(String string) {
        return map.get(player.getUuid()).getOrDefault(string, null);
    }

    public void save() {
        try {
            Yaml yaml = new Yaml();
            FileWriter writer = new FileWriter(System.getProperty("user.dir") + "/PlayerData/" + player.getUuid() + ".yml");
            yaml.dump(map.get(player.getUuid()), writer);
        } catch (IOException e) { e.printStackTrace(); }
    }
}