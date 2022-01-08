package com.hotslicerrpg.server.commands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.minestom.server.MinecraftServer;
import net.minestom.server.command.ConsoleSender;
import net.minestom.server.command.builder.Command;
import net.minestom.server.command.builder.arguments.ArgumentType;
import net.minestom.server.entity.GameMode;
import net.minestom.server.entity.Player;
import net.minestom.server.utils.entity.EntityFinder;

public class StopServer extends Command {
    public StopServer() {
        super("stop");

        setDefaultExecutor((sender, context) -> {
            if (sender.hasPermission("hotslicerrpg.stopserver") || sender instanceof ConsoleSender) {
                MinecraftServer.stopCleanly();
            } else sender.sendMessage(Component.text("You do not have the permission to run this command!",NamedTextColor.RED));
        });
    }
}