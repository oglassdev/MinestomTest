package com.hotslicerrpg.server.commands;

import com.hotslicerrpg.server.Server;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.minestom.server.command.builder.Command;
import net.minestom.server.entity.Player;
import org.slf4j.LoggerFactory;

public class SaveInstances extends Command {
    public SaveInstances() {
        super("saveinstances");

        setDefaultExecutor((sender, context) -> {
            LoggerFactory.getLogger(Server.class).info("Saving worlds...");
            Server.saveWorlds();
            if (sender instanceof Player p) p.sendMessage(Component.text("Worlds saved!", NamedTextColor.GRAY, TextDecoration.ITALIC));
            LoggerFactory.getLogger(Server.class).info("Saved worlds!");
        });
    }
}