package com.hotslicerrpg.server.commands;

import com.hotslicerrpg.server.Files.PlayerData;
import com.hotslicerrpg.server.Server;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.minestom.server.MinecraftServer;
import net.minestom.server.command.builder.Command;
import net.minestom.server.entity.Player;
import net.minestom.server.item.ItemStack;
import org.jglrxavpok.hephaistos.nbt.SNBTParser;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

public class SaveCommand extends Command {
    public SaveCommand() {
        super("save");

        setDefaultExecutor((sender, context) -> {
            LoggerFactory.getLogger(Server.class).info("Saving worlds...");
            Server.saveWorlds();
            if (sender instanceof Player p) p.sendMessage(Component.text("Worlds saved!", NamedTextColor.GRAY, TextDecoration.ITALIC));
            LoggerFactory.getLogger(Server.class).info("Saved worlds!");
            LoggerFactory.getLogger(Server.class).info("Saving player data...");
            Server.saveInventories();
            LoggerFactory.getLogger(Server.class).info("Saved player data!");
        });
    }
}