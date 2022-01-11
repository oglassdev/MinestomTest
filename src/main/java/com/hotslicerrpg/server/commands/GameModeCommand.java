package com.hotslicerrpg.server.commands;

import com.hotslicerrpg.server.Files.DefaultConfig;
import com.hotslicerrpg.server.Utils;
import net.minestom.server.command.builder.Command;
import net.minestom.server.command.builder.arguments.ArgumentEnum;
import net.minestom.server.command.builder.arguments.ArgumentType;
import net.minestom.server.command.builder.arguments.minecraft.ArgumentEntity;
import net.minestom.server.entity.GameMode;
import net.minestom.server.entity.Player;
import net.minestom.server.utils.entity.EntityFinder;

public class GameModeCommand extends Command {

    public GameModeCommand() {
        super("gamemode","gm");

        setDefaultExecutor((sender, context) -> {
            if (sender.hasPermission("hotslicerrpg.admin.gamemode")) {
                sender.sendMessage(Utils.translate("&cIncorrect usage!"));
                sender.sendMessage(Utils.translate("&cCorrect usage: /gamemode <gamemode> <name> OR /gamemode <gamemode>"));
            } else sender.sendMessage(DefaultConfig.noPermsWarning());
        });

        ArgumentEnum<GameMode> gamemode = ArgumentType.Enum("gamemode", GameMode.class).setFormat(ArgumentEnum.Format.LOWER_CASED);
        ArgumentEntity other = ArgumentType.Entity("player").onlyPlayers(true).singleEntity(true);

        addSyntax((sender, context) -> {
            final GameMode mode = context.get("gamemode");
            if (sender instanceof Player) {
                if (sender.hasPermission("hotslicerrpg.admin.gamemode")) {
                    ((Player) sender).setGameMode(mode);
                    sender.sendMessage(Utils.translate("&aYour gamemode has been set to " + mode.name().toLowerCase()));
                } else {
                    sender.sendMessage(DefaultConfig.noPermsWarning());
                }
            } else sender.sendMessage(DefaultConfig.consoleWarning());
        }, gamemode);

        addSyntax((sender, context) -> {
            final GameMode mode = context.get("gamemode");
            final EntityFinder targetFinder = context.get("player");
            Player player = targetFinder.findFirstPlayer(sender);

            if (!sender.hasPermission("hotslicerrpg.admin.gamemode")) {
                assert player != null;
                player.setGameMode(mode);
                sender.sendMessage(Utils.translate("&cSet " + player.getUsername() + "'s game mode to " + mode.name().toLowerCase()));
            } else {
                sender.sendMessage(DefaultConfig.noPermsWarning());
            }
        }, gamemode, other);
    }
}