package com.hotslicerrpg.server.commands;

import com.hotslicerrpg.server.Server;
import net.minestom.server.command.builder.Command;

public class SaveInstances extends Command {
    public SaveInstances() {
        super("saveinstances");

        setDefaultExecutor((sender, context) -> {
            System.out.print("Saving worlds...\n");
            Server.saveWorlds();
            System.out.print("Saved worlds!\n");
        });
    }
}