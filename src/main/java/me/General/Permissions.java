package me.General;

import me.General.Chat.Broadcaster;
import me.General.DataManegement.PermissionData;
import net.minestom.server.entity.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

public class Permissions {

    private static final Permissions INSTANCE = new Permissions();
    private static final Logger log = LoggerFactory.getLogger(Permissions.class);

    public static Permissions getInstance() {
        return INSTANCE;
    }


    public boolean permissionChecker(Player player, String permission) {
        int counter = 0;
        for (String permission_list : PermissionData.getInstance().getPermission(player.getUuid())) {
            if (Objects.equals(permission_list, permission)) {
                counter += 1;
            }
        }
        if (counter > 0) {
            log.info("Check for {} on {} successful.", permission, player.getUsername());
            return true;
        }
        log.info("Check for {} on {} unsuccessful.", permission, player.getUsername());
        return false;
    }
}
