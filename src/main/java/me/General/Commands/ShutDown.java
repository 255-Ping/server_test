package me.General.Commands;

import me.General.Permissions;
import net.minestom.server.MinecraftServer;
import net.minestom.server.command.CommandSender;
import net.minestom.server.command.builder.Command;
import net.minestom.server.command.builder.CommandContext;
import net.minestom.server.entity.Player;
import org.jetbrains.annotations.NotNull;

/**
 * A simple shutdown command.
 */
public class ShutDown extends Command {

    public ShutDown() {
        super("shutdown");
        addSyntax(this::execute);
    }

    private void execute(@NotNull CommandSender commandSender, @NotNull CommandContext commandContext) {
        Player player = (Player) commandSender;
        if (!Permissions.getInstance().permissionChecker(player, "shutdown.admin")) {
            player.sendMessage("No Permission!");
            return;
        }
        MinecraftServer.stopCleanly();
    }
}