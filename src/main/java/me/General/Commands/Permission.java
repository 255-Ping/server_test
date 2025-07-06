package me.General.Commands;

import me.General.Chat.Broadcaster;
import me.General.DataManegement.PermissionData;
import net.minestom.server.command.CommandSender;
import net.minestom.server.command.builder.Command;
import net.minestom.server.command.builder.CommandContext;
import net.minestom.server.command.builder.arguments.ArgumentString;
import net.minestom.server.command.builder.arguments.ArgumentType;
import net.minestom.server.entity.Player;
import org.jetbrains.annotations.NotNull;

public class Permission extends Command {

    public Permission() {
        super("perm");
        ArgumentString selectorArg = ArgumentType.String("selector");
        //Broadcaster.broadcast("" + selectorArg);
        ArgumentString permissionArg = ArgumentType.String("permission");
        addSyntax(this::execute, selectorArg, permissionArg);
    }

    //PermissionData permissionData = new PermissionData();

    private void execute(@NotNull CommandSender commandSender, @NotNull CommandContext commandContext) {
        Player player = (Player) commandSender;
        String selector = commandContext.get("selector");
        String permission = commandContext.get("permission");
        String[] permissionParts = permission.split("\\.");
        if (selector.equals("set")) {
            PermissionData.getInstance().setPermission(player.getUuid(),permissionParts);
            //permissionData.setPermission(player.getUuid(), permissionParts);
            player.sendMessage("Permission " + permission + " set!");
        } else {
            for (String permission_list : PermissionData.getInstance().getPermission(player.getUuid())) {
                Broadcaster.broadcast(permission_list);
            }
        }
    }
}
