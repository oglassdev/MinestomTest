package com.hotslicerrpg.server.worlds;

import com.hotslicerrpg.server.StartServer;
import net.minestom.server.MinecraftServer;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.instance.ChunkGenerator;
import net.minestom.server.instance.InstanceContainer;
import net.minestom.server.instance.InstanceManager;

public enum Worlds {
    OneBlock(new Generators.SingleBlockGenerator(), new Pos(0.5,40,0.5)),
    StoneWorld(new Generators.StoneGenerator(), new Pos(0.5,40,0.5));

    public InstanceContainer instanceContainer;
    private final Pos spawnLocation;
    Worlds(ChunkGenerator generator, Pos spawnLocation) {
        InstanceManager instanceManager = MinecraftServer.getInstanceManager();
        instanceContainer = instanceManager.createInstanceContainer();
        instanceContainer.setChunkGenerator(generator);
        this.spawnLocation = spawnLocation;
    }

    public Pos getSpawnLocation() {
        return spawnLocation;
    }
}