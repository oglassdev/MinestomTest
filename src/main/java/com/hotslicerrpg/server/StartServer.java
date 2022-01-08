package com.hotslicerrpg.server;

import com.hotslicerrpg.server.commands.ChangeWorld;
import com.hotslicerrpg.server.commands.GamemodeSurvival;
import com.hotslicerrpg.server.commands.StopServer;
import com.hotslicerrpg.server.worlds.Worlds;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.minestom.server.MinecraftServer;
import net.minestom.server.entity.GameMode;
import net.minestom.server.entity.Player;
import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.event.player.*;
import net.minestom.server.permission.Permission;

import java.util.Objects;

public class StartServer {

    public static void main(String[] arguments) {
        MinecraftServer server = MinecraftServer.init();
        MinecraftServer.getCommandManager().register(new ChangeWorld());
        MinecraftServer.getCommandManager().register(new GamemodeSurvival());
        MinecraftServer.getCommandManager().register(new StopServer());

        // Add an event callback to specify the spawning instance (and the spawn position)
        GlobalEventHandler globalEventHandler = MinecraftServer.getGlobalEventHandler();
        globalEventHandler.addListener(PlayerLoginEvent.class, event -> {
            final Player player = event.getPlayer();
            event.setSpawningInstance(Worlds.OneBlock.instanceContainer);
            player.setRespawnPoint(Worlds.OneBlock.getSpawnLocation());
            player.setGameMode(GameMode.CREATIVE);
            player.setAllowFlying(true);
            player.addPermission(new Permission("hotslicerrpg.updatemode"));
            System.out.println(player.getUsername() + " has joined the game!");
        });
        globalEventHandler.addListener(PlayerDisconnectEvent.class, event -> {
            final Player player = event.getPlayer();
            System.out.println(player.getUsername() + " left the game!");
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
    }
}
