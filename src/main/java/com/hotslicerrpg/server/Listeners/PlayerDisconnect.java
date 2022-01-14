package com.hotslicerrpg.server.Listeners;

import com.hotslicerrpg.server.Files.BannedPlayers;
import com.hotslicerrpg.server.Server;
import net.minestom.server.entity.Player;
import net.minestom.server.event.player.PlayerDisconnectEvent;
import org.slf4j.LoggerFactory;

public class PlayerDisconnect {
    public PlayerDisconnect(PlayerDisconnectEvent event) {
        final Player player = event.getPlayer();
        if (!BannedPlayers.isBanned(player)) {
            LoggerFactory.getLogger(Server.class).info(player.getUsername() + " left the game.");
        }
        Server.savePlayerInventory(player);
    }
}
