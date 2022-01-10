package com.hotslicerrpg.server.Files;

public class DefaultConfig {
    private static YamlFile file;

    public static void init() {
        file = new YamlFile("config","config.yml");
    }

    public static boolean canAutosave() {
        return file.getMap() != null &&
                file.getMap().containsKey("Autosave") &&
                file.getMap().get("Autosave") instanceof Boolean &&
                (Boolean) file.getMap().get("Autosave");
    }
}