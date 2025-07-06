package me.General.Chat;

import net.minestom.server.MinecraftServer;
import net.minestom.server.entity.Player;

public class Broadcaster {

    public static void broadcast(String message) {
        for (Player player : MinecraftServer.getConnectionManager().getOnlinePlayers()) {
            player.sendMessage(message);
        }
    }
}
