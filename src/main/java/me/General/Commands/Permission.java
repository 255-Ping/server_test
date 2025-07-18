package me.General.Commands;

import me.General.DataManegement.PermissionData;
import me.General.Permissions;
import net.minestom.server.command.CommandSender;
import net.minestom.server.command.builder.Command;
import net.minestom.server.command.builder.CommandContext;
import net.minestom.server.command.builder.arguments.ArgumentString;
import net.minestom.server.command.builder.arguments.ArgumentType;
import net.minestom.server.command.builder.arguments.minecraft.ArgumentEntity;
import net.minestom.server.entity.Player;
import net.minestom.server.utils.entity.EntityFinder;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Permission extends Command {

    public Permission() {
        super("perm");
        ArgumentString selectorArg = ArgumentType.String("selector");
        ArgumentString permissionArg = ArgumentType.String("permission");
        ArgumentEntity targetPlayerArg = ArgumentType.Entity("target").onlyPlayers(true);
        addSyntax(this::executeTarget, selectorArg, permissionArg, targetPlayerArg);
        addSyntax(this::executeSelf, selectorArg, permissionArg);
        addSyntax(this::executeList, selectorArg);
    }

    //PermissionData permissionData = new PermissionData();

    private void executeSelf(@NotNull CommandSender commandSender, @NotNull CommandContext commandContext) {
        Player player = (Player) commandSender;
        if (!Permissions.getInstance().permissionChecker(player, "perm.admin")) {
            player.sendMessage("No Permission!");
            return;
        }

        String selector = commandContext.get("selector");
        String permission = commandContext.get("permission");
        String[] permissionParts = permission.split("\\|");
        if (selector.equals("set")) {
            PermissionData.getInstance().addPermissions(player.getUuid(),permissionParts);
            player.sendMessage("Permission " + permission + " set!");
        } else if (selector.equals("remove")) {
            PermissionData.getInstance().removeCertainPermissions(player.getUuid(), permissionParts);
            player.sendMessage("Permission " + permission + " removed!");
        } else {
            player.sendMessage("Invalid first argument!");
        }
    }

    private void executeTarget(@NotNull CommandSender commandSender, @NotNull CommandContext commandContext) {
        Player player = (Player) commandSender;
        if (!Permissions.getInstance().permissionChecker(player, "perm.admin")) {
            player.sendMessage("No Permission!");
            return;
        }

        String selector = commandContext.get("selector");
        String permission = commandContext.get("permission");
        String[] permissionParts = permission.split("\\|");
        EntityFinder finder = commandContext.get("target");
        Player target = finder.findFirstPlayer(player);
        //Player target = commandContext.get("target");
        if (target == null) {
            player.sendMessage("Target player not found.");
        }
        if (selector.equals("set")) {
            PermissionData.getInstance().addPermissions(target.getUuid(),permissionParts);
            player.sendMessage("Permission " + permission + " set for " + target.getUsername());
        } else if (selector.equals("remove")) {
            PermissionData.getInstance().removeCertainPermissions(target.getUuid(), permissionParts);
            player.sendMessage("Permission " + permission + " removed from " + target.getUsername());
        } else {
            player.sendMessage("Invalid first argument!");
        }
    }

    private void executeList(@NotNull CommandSender commandSender, @NotNull CommandContext commandContext) {
        Player player = (Player) commandSender;
        if (!Permissions.getInstance().permissionChecker(player, "perm.admin")) {
            player.sendMessage("No Permission!");
            return;
        }

        String selector = commandContext.get("selector");
        if (selector.equals("list")) {
            for (String permission_list : PermissionData.getInstance().getPermission(player.getUuid())) {
                player.sendMessage(permission_list);
            }
        } else {
            player.sendMessage("Invalid first argument!");
        }
    }
}
