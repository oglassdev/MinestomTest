package com.hotslicerrpg.server;

import com.hotslicerrpg.server.Files.BannedPlayers;
import com.hotslicerrpg.server.Files.DefaultConfig;
import com.hotslicerrpg.server.Files.PlayerData;
import com.hotslicerrpg.server.Listeners.PlayerChat;
import com.hotslicerrpg.server.Listeners.PlayerDisconnect;
import com.hotslicerrpg.server.Listeners.PlayerJoin;
import com.hotslicerrpg.server.commands.ChangeWorld;
import com.hotslicerrpg.server.commands.GameModeCommand;
import com.hotslicerrpg.server.commands.SaveCommand;
import com.hotslicerrpg.server.commands.StopServer;
import com.hotslicerrpg.server.worlds.Worlds;
import net.minestom.server.MinecraftServer;
import net.minestom.server.command.ConsoleSender;
import net.minestom.server.entity.Player;
import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.event.player.*;
import net.minestom.server.extras.MojangAuth;
import net.minestom.server.extras.PlacementRules;
import net.minestom.server.instance.block.Block;
import net.minestom.server.instance.block.BlockManager;
import net.minestom.server.instance.block.rule.vanilla.StairsPlacementRule;
import net.minestom.server.item.ItemStack;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class Server {
    public static void main(String[] arguments) {
        MinecraftServer server = MinecraftServer.init();
        MinecraftServer.getCommandManager().register(new ChangeWorld());
        MinecraftServer.getCommandManager().register(new GameModeCommand());
        MinecraftServer.getCommandManager().register(new StopServer());
        MinecraftServer.getCommandManager().register(new SaveCommand());
        MojangAuth.init();
        PlacementRules.init();
        BlockManager blockManager = MinecraftServer.getBlockManager();
        for (Block b : Block.values()) {
            if (b.name().contains("stairs")) {
                LoggerFactory.getLogger(Server.class).info(b.name());
                blockManager.registerBlockPlacementRule(new StairsPlacementRule(b));
            }
        }


        // Files
        DefaultConfig.init();
        BannedPlayers.init();

        // Listeners
        GlobalEventHandler globalEventHandler = MinecraftServer.getGlobalEventHandler();
        globalEventHandler.addListener(PlayerLoginEvent.class, PlayerJoin::new);
        globalEventHandler.addListener(PlayerDisconnectEvent.class, PlayerDisconnect::new);
        globalEventHandler.addListener(PlayerChatEvent.class, PlayerChat::new);
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

    public static void saveInventories() {
        for (Player p : MinecraftServer.getConnectionManager().getOnlinePlayers()) {
            PlayerData playerData = new PlayerData(p);
            Map<String, String> map = new HashMap<>();

            for (int i = 0; i < p.getInventory().getSize(); i++) {
                if (!p.getInventory().getItemStack(i).isAir()) {
                    ItemStack item = p.getInventory().getItemStack(i);
                    map.put(String.valueOf(i), item.toItemNBT().toSNBT());
                }
            }

            playerData.put("Inventory", map);
            playerData.save();
        }
    }
    public static void savePlayerInventory(Player p) {
        PlayerData playerData = new PlayerData(p);
        Map<String, String> map = new HashMap<>();

        for (int i = 0; i < p.getInventory().getSize(); i++) {
            if (!p.getInventory().getItemStack(i).isAir()) {
                ItemStack item = p.getInventory().getItemStack(i);
                map.put(String.valueOf(i), item.toItemNBT().toSNBT());
            }
        }

        playerData.put("Inventory", map);
        playerData.save();
    }
}
