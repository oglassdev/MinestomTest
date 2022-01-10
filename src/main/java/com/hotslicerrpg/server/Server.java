package com.hotslicerrpg.server;

import com.hotslicerrpg.server.Files.BannedPlayers;
import com.hotslicerrpg.server.Files.DefaultConfig;
import com.hotslicerrpg.server.Files.PlayerData;
import com.hotslicerrpg.server.commands.ChangeWorld;
import com.hotslicerrpg.server.commands.GamemodeSurvival;
import com.hotslicerrpg.server.commands.SaveInstances;
import com.hotslicerrpg.server.commands.StopServer;
import com.hotslicerrpg.server.worlds.Worlds;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.minestom.server.MinecraftServer;
import net.minestom.server.command.ConsoleSender;
import net.minestom.server.entity.GameMode;
import net.minestom.server.entity.Player;
import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.event.player.*;
import net.minestom.server.extras.MojangAuth;
import net.minestom.server.permission.Permission;
import org.slf4j.LoggerFactory;

import java.util.Objects;

public class Server {
    public static void main(String[] arguments) {
        MinecraftServer server = MinecraftServer.init();
        MinecraftServer.getCommandManager().register(new ChangeWorld());
        MinecraftServer.getCommandManager().register(new GamemodeSurvival());
        MinecraftServer.getCommandManager().register(new StopServer());
        MinecraftServer.getCommandManager().register(new SaveInstances());
        MojangAuth.init();

        // Files
        DefaultConfig.init();
        BannedPlayers.init();

        // Listeners
        GlobalEventHandler globalEventHandler = MinecraftServer.getGlobalEventHandler();
        globalEventHandler.addListener(PlayerLoginEvent.class, event -> {
            final Player player = event.getPlayer();
            event.setSpawningInstance(Worlds.OneBlock.instanceContainer);
            if (BannedPlayers.isBanned(player)) {
                player.kick("You are banned from this server!\nReason: " + BannedPlayers.getReason(player));
                LoggerFactory.getLogger(Server.class).info("Player " + player.getUsername() + " tried to log in, but is banned! Reason: " + BannedPlayers.getReason(player));
                return;
            }
            player.setRespawnPoint(Worlds.OneBlock.getSpawnLocation());
            player.setGameMode(GameMode.CREATIVE);
            player.setAllowFlying(true);
            player.addPermission(new Permission("hotslicerrpg.updatemode"));
            new PlayerData(player);
            LoggerFactory.getLogger(Server.class).info(player.getUsername() + "[" + player.getPlayerConnection().getRemoteAddress() + "] logged into the server!");
        });
        globalEventHandler.addListener(PlayerDisconnectEvent.class, event -> {
            final Player player = event.getPlayer();
            if (!BannedPlayers.isBanned(player)) {
                LoggerFactory.getLogger(Server.class).info(player.getUsername() + " left the game.");
            }
        });

        globalEventHandler.addListener(PlayerChatEvent.class, event -> {
            Player player = event.getPlayer();
            for (Player p : Objects.requireNonNull(player.getInstance()).getPlayers()) {
                p.sendMessage(Component.text("[", NamedTextColor.DARK_RED)
                        .append(Component.text(player.getUsername(), NamedTextColor.RED))
                        .append(Component.text("]: ", NamedTextColor.DARK_RED))
                        .append(Component.text(event.getMessage(), NamedTextColor.WHITE)));
            }
        });
        server.start("0.0.0.0",25565);

        // MinecraftServer.getSchedulerManager().buildTask(() -> { }).repeat(Duration.ofSeconds(5)).schedule();
    }

    public static void stop(boolean save) {
        for (Player player : MinecraftServer.getConnectionManager().getOnlinePlayers()) {
            player.kick("Server shutting down.");
        }
        if (save) {
            LoggerFactory.getLogger(Server.class).info("Saving worlds!");
            saveWorlds();
            LoggerFactory.getLogger(Server.class).info("Finished saving worlds!");
        }
        MinecraftServer.stopCleanly();
    }
    public static void saveWorlds() {
        for (Worlds world : Worlds.values()) {
            world.saveWorld();
        }
    }

}
