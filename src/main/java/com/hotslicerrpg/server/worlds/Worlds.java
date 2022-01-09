package com.hotslicerrpg.server.worlds;

import net.minestom.server.MinecraftServer;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.instance.*;

import java.io.File;

public enum Worlds {
    OneBlock(new Generators.SingleBlockGenerator(), new Pos(0.5,40,0.5)),
    StoneWorld(new Generators.StoneGenerator(), new Pos(0.5,40,0.5));

    public InstanceContainer instanceContainer;
    private final Pos spawnLocation;

    Worlds(ChunkGenerator generator, Pos spawnLocation) {
        InstanceManager manager = MinecraftServer.getInstanceManager();
        instanceContainer = manager.createInstanceContainer();
        try {
            AnvilLoader a = new AnvilLoader(System.getProperty("user.dir") + "/worlds/" + name());
            a.loadInstance(instanceContainer);
            instanceContainer.setChunkLoader(a);
        } catch (Exception ignored) {
            System.out.print("Creating world "+name());
        }
        instanceContainer.setChunkGenerator(generator);
        this.spawnLocation = spawnLocation;
    }
    public Pos getSpawnLocation() {
        return spawnLocation;
    }
    public void saveWorld() {
        AnvilLoader a = new AnvilLoader(System.getProperty("user.dir")+"/worlds/"+name());
        a.saveChunks(instanceContainer.getChunks());
        a.saveInstance(instanceContainer);
    }
}