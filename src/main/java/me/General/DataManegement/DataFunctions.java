package me.General.DataManegement;

import me.General.Chat.Broadcaster;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class DataFunctions {

    private static final DataFunctions INSTANCE = new DataFunctions();
    private static final Logger LOGGER = LoggerFactory.getLogger(DataFunctions.class);

    public static DataFunctions getInstance() {
        return INSTANCE;
    }

    public void saveData(Player player) {

        PlayerData data = new PlayerData(
                player.getUuid(),
                player.getUsername(),
                player.getPosition().x(),
                player.getPosition().y(),
                player.getPosition().z(),
                PermissionData.getInstance().getPermission(player.getUuid())
        );

        try (ObjectOutputStream out = new ObjectOutputStream(
                Files.newOutputStream(Path.of("playerdata/" + player.getUuid() + ".dat")))) {
            out.writeObject(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
        PermissionData.getInstance().removePermissions(player.getUuid());
    }

    public void loadData(Player player) {
        //LOGGER.info("This is a message!");
        Path filePath = Path.of("playerdata/" + player.getUuid() + ".dat");

        if (Files.exists(filePath)) {
            try (ObjectInputStream in = new ObjectInputStream(Files.newInputStream(filePath))) {
                PlayerData data = (PlayerData) in.readObject();
                Broadcaster.broadcast(data.username + " " + data.uuid);
                for (String permission : data.permissions) {
                    Broadcaster.broadcast(permission);
                }
                Pos pos = new Pos(data.x, data.y, data.z);
                player.teleport(pos);
                PermissionData.getInstance().setPermission(player.getUuid(), data.permissions);

            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
