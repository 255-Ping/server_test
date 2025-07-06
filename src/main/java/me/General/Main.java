package me.General;

import com.google.gson.Gson;
import me.General.Chat.Broadcaster;
import me.General.Commands.*;
import me.General.DataManegement.PermissionData;
import me.General.DataManegement.PlayerData;
import net.minestom.server.MinecraftServer;
import net.minestom.server.entity.Player;
import net.minestom.server.event.Event;
import net.minestom.server.event.EventNode;
import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.event.player.AsyncPlayerConfigurationEvent;
import net.minestom.server.event.player.PlayerDisconnectEvent;
import net.minestom.server.event.player.PlayerLoadedEvent;
import net.minestom.server.extras.MojangAuth;
import net.minestom.server.instance.*;
import net.minestom.server.instance.block.Block;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.tag.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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

        //Skins
        MojangAuth.init();

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
            PlayerData data = new PlayerData(
                    player.getUuid(),
                    player.getUsername(),
                    player.getPosition().x(),
                    player.getPosition().y(),
                    player.getPosition().z(),
                    new PermissionData().getPermission(player.getUuid())
                    //player.getPosition()//

                    //Gson gson = new Gson();
                    //String json = player.getTag(Tag.String("inventory_items"));
                   // String[] loadedArray = gson.fromJson(json, String[].class);

                    //PermissionData.getPermissions(player.getUuid());
            );
            try (ObjectOutputStream out = new ObjectOutputStream(
                    Files.newOutputStream(Path.of("playerdata/" + player.getUuid() + ".dat")))) {
                out.writeObject(data);
            } catch (IOException e) {
                e.printStackTrace();
            }
            PermissionData.getInstance().removePermissions(player.getUuid());
        });

        //Join Event
        globalEvents.addListener(PlayerLoadedEvent.class, event -> {
            Player player = event.getPlayer();
            Path filePath = Path.of("playerdata/" + player.getUuid() + ".dat");
            if (Files.exists(filePath)) {
                try (ObjectInputStream in = new ObjectInputStream(Files.newInputStream(filePath))) {
                    PlayerData data = (PlayerData) in.readObject();
                    //Component text = Component.text("test");
                    //player.sendMessage(text);

                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
        // Create Directories

        Path folderpath = Path.of("playerdata");
        if (Files.notExists(folderpath)) {
            Files.createDirectories(folderpath);
        }

        //Register Commands
        //PermissionData permissionData = new PermissionData();
        MinecraftServer.getCommandManager().register(new ShutDown());
        MinecraftServer.getCommandManager().register(new Broadcast());
        MinecraftServer.getCommandManager().register(new Permission());
        MinecraftServer.getCommandManager().register(new SaveData());
        MinecraftServer.getCommandManager().register(new LoadData());

        // Start the server on port 25565
        minecraftServer.start("0.0.0.0", 25565);
    }
}