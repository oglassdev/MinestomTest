package com.hotslicerrpg.server;

import com.hotslicerrpg.server.Files.BannedPlayers;
import com.hotslicerrpg.server.Files.DefaultConfig;
import com.hotslicerrpg.server.Listeners.PlayerBlockPlace;
import com.hotslicerrpg.server.Listeners.PlayerChat;
import com.hotslicerrpg.server.Listeners.PlayerDisconnect;
import com.hotslicerrpg.server.Listeners.PlayerJoin;
import com.hotslicerrpg.server.commands.ChangeWorld;
import com.hotslicerrpg.server.commands.GameModeCommand;
import com.hotslicerrpg.server.commands.SaveInstances;
import com.hotslicerrpg.server.commands.StopServer;
import com.hotslicerrpg.server.worlds.Worlds;
import net.minestom.server.MinecraftServer;
import net.minestom.server.entity.Player;
import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.event.player.*;
import net.minestom.server.extras.MojangAuth;
import org.slf4j.LoggerFactory;

import java.util.Objects;

public class Server {
    public static void main(String[] arguments) {
        MinecraftServer server = MinecraftServer.init();
        MinecraftServer.getCommandManager().register(new ChangeWorld());
        MinecraftServer.getCommandManager().register(new GameModeCommand());
        MinecraftServer.getCommandManager().register(new StopServer());
        MinecraftServer.getCommandManager().register(new SaveInstances());
        MojangAuth.init();

        // Files
        DefaultConfig.init();
        BannedPlayers.init();

        // Listeners
        GlobalEventHandler globalEventHandler = MinecraftServer.getGlobalEventHandler();
        globalEventHandler.addListener(PlayerLoginEvent.class, PlayerJoin::new);
        globalEventHandler.addListener(PlayerDisconnectEvent.class, PlayerDisconnect::new);
        globalEventHandler.addListener(PlayerChatEvent.class, PlayerChat::new);
        globalEventHandler.addListener(PlayerBlockPlaceEvent.class, PlayerBlockPlace::new);
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
