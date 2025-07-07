package me.General;

import me.General.Chat.Broadcaster;
import me.General.DataManegement.PermissionData;
import net.minestom.server.entity.Player;

import java.util.Objects;

public class Permissions {

    private static final Permissions INSTANCE = new Permissions();

    public static Permissions getInstance() {
        return INSTANCE;
    }


    public boolean permissionChecker(Player player, String permission) {
        int counter = 0;
        for (String permission_list : PermissionData.getInstance().getPermission(player.getUuid())) {
            if (Objects.equals(permission_list, permission)) {
                counter += 1;
                Broadcaster.broadcast("1");
            }
        }
        if (counter > 0) {
            Broadcaster.broadcast("true");
            return true;
        }
        Broadcaster.broadcast("false");
        return false;
    }
}
