package me.General.Commands;

import me.General.Chat.Broadcaster;
import me.General.DataManegement.PermissionData;
import net.minestom.server.command.CommandSender;
import net.minestom.server.command.builder.Command;
import net.minestom.server.command.builder.CommandContext;
import net.minestom.server.command.builder.arguments.ArgumentString;
import net.minestom.server.command.builder.arguments.ArgumentType;
import net.minestom.server.command.builder.arguments.minecraft.ArgumentEntity;
import net.minestom.server.entity.Player;
import net.minestom.server.utils.entity.EntityFinder;
import org.jetbrains.annotations.NotNull;

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
        String selector = commandContext.get("selector");
        String permission = commandContext.get("permission");
        String[] permissionParts = permission.split("\\|");
        if (selector.equals("set")) {
            PermissionData.getInstance().addPermissions(player.getUuid(),permissionParts);
            player.sendMessage("Permission " + permission + " set!");
        } else {
            player.sendMessage("Invalid first argument!");
            //for (String permission_list : PermissionData.getInstance().getPermission(player.getUuid())) {
            //    Broadcaster.broadcast(permission_list);
            //}
        }
    }

    private void executeTarget(@NotNull CommandSender commandSender, @NotNull CommandContext commandContext) {
        Player player = (Player) commandSender;
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
        } else {
            player.sendMessage("Invalid first argument!");
            //for (String permission_list : PermissionData.getInstance().getPermission(player.getUuid())) {
            //    Broadcaster.broadcast(permission_list);
            //}
        }
    }

    private void executeList(@NotNull CommandSender commandSender, @NotNull CommandContext commandContext) {
        Player player = (Player) commandSender;
        String selector = commandContext.get("selector");
        //String permission = commandContext.get("permission");
        //String[] permissionParts = permission.split("\\|");
        if (selector.equals("list")) {
            for (String permission_list : PermissionData.getInstance().getPermission(player.getUuid())) {
                player.sendMessage(permission_list);
            }
        } else {
            player.sendMessage("Invalid first argument!");
        }
    }
}
