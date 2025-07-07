package me.General;

import me.General.Commands.*;
import me.General.DataManegement.DataFunctions;
import net.minestom.server.MinecraftServer;
import net.minestom.server.entity.Player;
import net.minestom.server.event.Event;
import net.minestom.server.event.EventNode;
import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.event.player.AsyncPlayerConfigurationEvent;
import net.minestom.server.event.player.PlayerDisconnectEvent;
import net.minestom.server.event.player.PlayerSpawnEvent;
import net.minestom.server.extras.MojangAuth;
import net.minestom.server.instance.*;
import net.minestom.server.instance.block.Block;
import net.minestom.server.coordinate.Pos;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main {
    private static final Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws IOException {
        // Initialization
        MinecraftServer minecraftServer = MinecraftServer.init();

        // Create the instance
        InstanceManager instanceManager = MinecraftServer.getInstanceManager();
        InstanceContainer instanceContainer = instanceManager.createInstanceContainer();

        // Set the ChunkGenerator
        instanceContainer.setGenerator(unit -> unit.modifier().fillHeight(0, 40, Block.GRASS_BLOCK));

        //light
        instanceContainer.setChunkSupplier(LightingChunk::new);
        log.info("Instance created");

        //Skins
        MojangAuth.init();
        log.info("Mojang Auth Init");

        // Add an event callback to specify the spawning instance (and the spawn position)
        GlobalEventHandler globalEventHandler = MinecraftServer.getGlobalEventHandler();
        globalEventHandler.addListener(AsyncPlayerConfigurationEvent.class, event -> {
            final Player player = event.getPlayer();
            event.setSpawningInstance(instanceContainer);
            player.setRespawnPoint(new Pos(0, 42, 0));
        });

        //Register Events
        EventNode<Event> globalEvents = EventNode.all("global-events");

        //Disconnect Event
        globalEvents.addListener(PlayerDisconnectEvent.class, event -> {
            Player player = event.getPlayer();
            DataFunctions.getInstance().saveData(player);
        });

        //Join Event
        globalEvents.addListener(PlayerSpawnEvent.class, event -> {
            Player player = event.getPlayer();
            DataFunctions.getInstance().loadData(player);
        });

        globalEventHandler.addChild(globalEvents);
        log.info("Global events loaded");

        // Create Directories
        Path folderpath = Path.of("playerdata");
        if (Files.notExists(folderpath)) {
            Files.createDirectories(folderpath);
            log.info("{} created", folderpath);
        }

        //Register Commands
        //PermissionData permissionData = new PermissionData();
        MinecraftServer.getCommandManager().register(new ShutDown());
        MinecraftServer.getCommandManager().register(new Broadcast());
        MinecraftServer.getCommandManager().register(new Permission());
        MinecraftServer.getCommandManager().register(new SaveData());
        MinecraftServer.getCommandManager().register(new LoadData());
        log.info("Commands loaded");

        // Start the server on port 25565
        minecraftServer.start("0.0.0.0", 25565);
    }
}