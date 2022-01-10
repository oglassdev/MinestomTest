package com.hotslicerrpg.server.commands;

import com.hotslicerrpg.server.Server;
import net.minestom.server.command.builder.Command;
import org.slf4j.LoggerFactory;

public class SaveInstances extends Command {
    public SaveInstances() {
        super("saveinstances");

        setDefaultExecutor((sender, context) -> {
            LoggerFactory.getLogger(Server.class).info("Saving worlds...");
            Server.saveWorlds();
            LoggerFactory.getLogger(Server.class).info("Saved worlds!");
        });
    }
}