package com.hotslicerrpg.server.commands;

import com.hotslicerrpg.server.worlds.Worlds;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.minestom.server.MinecraftServer;
import net.minestom.server.command.CommandSender;
import net.minestom.server.command.builder.Command;
import net.minestom.server.command.builder.CommandContext;
import net.minestom.server.command.builder.arguments.ArgumentType;
import net.minestom.server.command.builder.suggestion.Suggestion;
import net.minestom.server.command.builder.suggestion.SuggestionCallback;
import net.minestom.server.entity.Entity;
import net.minestom.server.entity.GameMode;
import net.minestom.server.entity.Player;
import net.minestom.server.instance.Instance;
import net.minestom.server.utils.entity.EntityFinder;
import org.jetbrains.annotations.NotNull;

public class GamemodeSurvival extends Command {
    public GamemodeSurvival() {
        super("gms");

        setDefaultExecutor((sender, context) -> {
            if (sender instanceof Player player) {
                if (player.hasPermission("hotslicerrpg.updatemode")) {
                    player.sendMessage(Component.text("Set your gamemode to Survival!", NamedTextColor.RED));
                    player.setGameMode(GameMode.SURVIVAL);
                } else player.sendMessage(Component.text("You do not have the permission to run this command!",NamedTextColor.RED));
            } else sender.sendMessage(Component.text("You cannot execute this command as console!",NamedTextColor.RED));
        });

        var players = ArgumentType.Entity("players").onlyPlayers(true);

        addSyntax((sender, context) -> {
            final EntityFinder entity = context.get(players);
            if (sender instanceof Player player) {
            }
        }, players);
    }
}