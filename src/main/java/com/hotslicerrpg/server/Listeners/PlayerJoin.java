package com.hotslicerrpg.server.Listeners;

import com.hotslicerrpg.server.Files.BannedPlayers;
import com.hotslicerrpg.server.Files.PlayerData;
import com.hotslicerrpg.server.Server;
import com.hotslicerrpg.server.worlds.Worlds;
import net.minestom.server.entity.GameMode;
import net.minestom.server.entity.Player;
import net.minestom.server.event.player.PlayerLoginEvent;
import net.minestom.server.item.*;
import org.jglrxavpok.hephaistos.nbt.NBTCompound;
import org.jglrxavpok.hephaistos.nbt.NBTException;
import org.jglrxavpok.hephaistos.nbt.SNBTParser;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.Reader;
import java.util.Arrays;
import java.util.Map;

public class PlayerJoin {
    public PlayerJoin(PlayerLoginEvent event) {
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
        PlayerData data = new PlayerData(player);
        if (data.get("Inventory") != null && data.get("Inventory") instanceof Map map) {
            for (Object o : map.keySet()) {
                if (o instanceof String s) {
                    if (map.get(s) instanceof String str) {
                        try {
                            SNBTParser parser = new SNBTParser(new InputStreamReader(new ByteArrayInputStream(str.getBytes())));
                            NBTCompound nbtc = (NBTCompound) parser.parse();
                            player.getInventory().setItemStack(Integer.valueOf(s),ItemStack.fromItemNBT(nbtc));
                        } catch(NBTException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        LoggerFactory.getLogger(Server.class).info(player.getUsername() + "[" + player.getPlayerConnection().getRemoteAddress() + "] logged into the server!");
    }
}
