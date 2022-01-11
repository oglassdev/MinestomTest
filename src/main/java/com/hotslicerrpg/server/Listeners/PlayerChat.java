package com.hotslicerrpg.server.Listeners;

import com.hotslicerrpg.server.Utils;
import net.minestom.server.entity.Player;
import net.minestom.server.event.player.PlayerChatEvent;

import java.util.Objects;

public class PlayerChat {
    public PlayerChat(PlayerChatEvent event) {
        Player player = event.getPlayer();
        for (Player p : Objects.requireNonNull(player.getInstance()).getPlayers()) {
            p.sendMessage(Utils.translate("&7" + player.getUsername() + ": &7")+event.getMessage());
        }
    }
}
