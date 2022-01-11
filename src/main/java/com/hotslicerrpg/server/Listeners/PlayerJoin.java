package com.hotslicerrpg.server.Listeners;

import com.hotslicerrpg.server.Files.BannedPlayers;
import com.hotslicerrpg.server.Files.PlayerData;
import com.hotslicerrpg.server.Server;
import com.hotslicerrpg.server.worlds.Worlds;
import net.minestom.server.entity.GameMode;
import net.minestom.server.entity.Player;
import net.minestom.server.event.player.PlayerLoginEvent;
import org.slf4j.LoggerFactory;

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
        new PlayerData(player);
        LoggerFactory.getLogger(Server.class).info(player.getUsername() + "[" + player.getPlayerConnection().getRemoteAddress() + "] logged into the server!");
    }
}
