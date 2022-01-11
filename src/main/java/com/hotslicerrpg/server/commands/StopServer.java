package com.hotslicerrpg.server.commands;

import com.hotslicerrpg.server.Files.DefaultConfig;
import com.hotslicerrpg.server.Server;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.minestom.server.command.ConsoleSender;
import net.minestom.server.command.builder.Command;

public class StopServer extends Command {
    public StopServer() {
        super("stop");

        setDefaultExecutor((sender, context) -> {
            if (sender.hasPermission("hotslicerrpg.admin.stop") || sender instanceof ConsoleSender) {
                Server.stop(true);
            } else sender.sendMessage(DefaultConfig.noPermsWarning());
        });
    }
}