package com.hotslicerrpg.server.Listeners;

import net.minestom.server.entity.Player;
import net.minestom.server.event.player.PlayerBlockPlaceEvent;
import net.minestom.server.instance.block.Block;

import java.util.Objects;

public class PlayerBlockPlace {
    public PlayerBlockPlace(PlayerBlockPlaceEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();
        if (block.equals(Block.REDSTONE_WIRE)) {
            if (event.getInstance().getBlock(event.getBlockPosition().add(0,-1,0)).equals(Block.REDSTONE_WIRE)) {
                event.setCancelled(true);
            }
        }
    }
}
