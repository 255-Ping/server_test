package me.General.DataManegement;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import java.util.*;

public class PermissionData {

    private static final PermissionData INSTANCE = new PermissionData();
    private static final Logger log = LoggerFactory.getLogger(PermissionData.class);

    public static PermissionData getInstance() {
        return INSTANCE;
    }

    // Storage for runtime player data
    private final Map<UUID, String[]> playerPermissions = new HashMap<>();

    // Store array
    public void setPermission(UUID uuid, String[] permissions) {
        playerPermissions.put(uuid, permissions);
        for (String permission : permissions) {
            log.info("{} set on {}", permission, uuid);
        }
    }

    // Retrieve array
    public String[] getPermission(UUID uuid) {
        return playerPermissions.get(uuid);
    }

    // Optional: Remove player data (e.g., on disconnect)
    public void removePermissions(UUID uuid) {
        playerPermissions.remove(uuid);
        log.info("Permissions deleted for {}", uuid);
    }

    public void removeCertainPermissions(UUID uuid, String[] permissionsToRemove) {
        String[] currentPermissions = playerPermissions.get(uuid);
        if (currentPermissions == null) return;

        List<String> permissionList = new ArrayList<>(Arrays.asList(currentPermissions));

        for (String perm : permissionsToRemove) {
            permissionList.remove(perm);
        }

        playerPermissions.put(uuid, permissionList.toArray(new String[0]));

        for (String permission : permissionsToRemove) {
            log.info("{} removed from {}", permission, uuid);
        }
    }

    public void addPermissions(UUID uuid, String[] newPermissions) {
        String[] existingPermissions = playerPermissions.get(uuid);
        Set<String> merged = new LinkedHashSet<>();

        if (existingPermissions != null) {
            merged.addAll(Arrays.asList(existingPermissions));
        }

        merged.addAll(Arrays.asList(newPermissions));  // Merge new permissions

        playerPermissions.put(uuid, merged.toArray(new String[0]));

        for (String permission : newPermissions) {
            log.info("{} added to {}", permission, uuid);
        }
    }

}

