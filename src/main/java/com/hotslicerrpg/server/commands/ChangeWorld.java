package com.hotslicerrpg.server.commands;

import com.hotslicerrpg.server.worlds.Worlds;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.minestom.server.command.builder.Command;
import net.minestom.server.command.builder.arguments.ArgumentType;
import net.minestom.server.entity.Player;

public class ChangeWorld extends Command {
    public ChangeWorld() {
        super("changeworld");

        setDefaultExecutor((sender, context) -> {
            sender.sendMessage(Component.text("This world does not exist!", NamedTextColor.RED));
        });

        var enumArgument = ArgumentType.Enum("world", Worlds.class);

        addSyntax((sender, context) -> {
            final Worlds world = context.get(enumArgument);
            if (sender instanceof Player player) {
                if (world.instanceContainer.equals(player.getInstance())) {
                    player.sendMessage(Component.text("You are already here!", NamedTextColor.RED));
                }
                else {
                    world.instanceContainer.getChunkLoader().loadChunk(world.instanceContainer,world.getSpawnLocation().chunkX(),world.getSpawnLocation().chunkZ());
                    player.sendMessage(Component.text("Teleporting you to " + world.name(), NamedTextColor.GRAY));
                    player.setInstance(world.instanceContainer);
                    player.teleport(world.getSpawnLocation());
                }
            }
        }, enumArgument);
    }
}