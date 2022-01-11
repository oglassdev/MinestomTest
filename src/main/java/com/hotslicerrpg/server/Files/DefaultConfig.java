package com.hotslicerrpg.server.Files;

import com.hotslicerrpg.server.Utils;

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

    public static String consoleWarning() {
        if (file.getMap() != null && file.getMap().containsKey("ConsoleWarning") && file.getMap().get("ConsoleWarning") instanceof String s) return Utils.translate(s);
        else return "You may not run this as console!";
    }

    public static String noPermsWarning() {
        if (file.getMap() != null && file.getMap().containsKey("NoPermissionWarning") && file.getMap().get("NoPermissionWarning") instanceof String s) return Utils.translate(s);
        else return Utils.translate("&cYou do not have the permission to run this command!");
    }
}