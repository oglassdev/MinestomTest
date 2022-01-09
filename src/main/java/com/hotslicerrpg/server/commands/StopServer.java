package com.hotslicerrpg.server.commands;

import com.hotslicerrpg.server.Server;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.minestom.server.command.ConsoleSender;
import net.minestom.server.command.builder.Command;

public class StopServer extends Command {
    public StopServer() {
        super("stop");

        setDefaultExecutor((sender, context) -> {
            if (sender.hasPermission("hotslicerrpg.stopserver") || sender instanceof ConsoleSender) {
                Server.stop(true);
            } else sender.sendMessage(Component.text("You do not have the permission to run this command!",NamedTextColor.RED));
        });
    }
}