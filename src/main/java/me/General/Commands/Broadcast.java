package me.General.Commands;

import me.General.Chat.Broadcaster;
import me.General.Permissions;
import net.minestom.server.command.CommandSender;
import net.minestom.server.command.builder.Command;
import net.minestom.server.command.builder.CommandContext;
import net.minestom.server.command.builder.arguments.ArgumentString;
import net.minestom.server.command.builder.arguments.ArgumentType;
import net.minestom.server.entity.Player;
import org.jetbrains.annotations.NotNull;

public class Broadcast extends Command {
    public Broadcast() {
        super("broadcast");

        ArgumentString nameArg = ArgumentType.String("message");//.setQuoted(true);

        addSyntax(this::executeWithArgs, nameArg);
    }

    private void executeWithArgs(@NotNull CommandSender commandSender, @NotNull CommandContext commandContext) {
        Player player = (Player) commandSender;
        if (Permissions.getInstance().permissionChecker(player, "broadcast.admin")) {
            String message = commandContext.get("message");
            Broadcaster.broadcast(message);
        } else {
            player.sendMessage("No Permission!");
        }
    }


}

