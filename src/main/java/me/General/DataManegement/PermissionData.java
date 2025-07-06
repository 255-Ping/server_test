package me.General.DataManegement;


import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import java.util.*;

public class PermissionData {

    private static final PermissionData INSTANCE = new PermissionData();

    public static PermissionData getInstance() {
        return INSTANCE;
    }

    // Storage for runtime player data
    private final Map<UUID, String[]> playerPermissions = new HashMap<>();

    // Store array
    public void setPermission(UUID uuid, String[] permissions) {
        playerPermissions.put(uuid, permissions);
        //playerPermissions.
    }

    // Retrieve array
    public String[] getPermission(UUID uuid) {
        return playerPermissions.get(uuid);
    }

    // Optional: Remove player data (e.g., on disconnect)
    public void removePermissions(UUID uuid) {
        playerPermissions.remove(uuid);
    }
}

