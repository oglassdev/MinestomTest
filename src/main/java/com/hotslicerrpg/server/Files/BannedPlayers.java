package com.hotslicerrpg.server.Files;

import net.minestom.server.entity.Player;

import java.util.Map;

public class BannedPlayers {
    private static YamlFile file;

    public static void init() {
        file = new YamlFile("banned-players","banned-players.yml");
        System.out.print("\n" + file.getMap() + "\n");
    }

    public static boolean isBanned(Player player) {
        return file.getMap() != null && file.getMap().containsKey(player.getUuid().toString());
    }

    public static String getReason(Player player) {
        if (file.getMap() != null &&
                file.getMap().containsKey(player.getUuid().toString()) &&
                file.getMap().get(player.getUuid().toString()) instanceof Map map) {
            if (map.containsKey("Reason") && map.get("Reason") instanceof String str) return str;
        }
        return "None";
    }
}