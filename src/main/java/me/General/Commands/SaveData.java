package me.General.Commands;

import me.General.DataManegement.DataFunctions;
import me.General.Permissions;
import net.minestom.server.command.CommandSender;
import net.minestom.server.command.builder.Command;
import net.minestom.server.command.builder.CommandContext;
import net.minestom.server.entity.Player;
import org.jetbrains.annotations.NotNull;

public class SaveData extends Command {
    public SaveData() {
        super("savedata");
        addSyntax(this::execute);
    }

    private void execute(@NotNull CommandSender commandSender, @NotNull CommandContext commandContext) {
        Player player = (Player) commandSender;
        if (!Permissions.getInstance().permissionChecker(player, "data.save")) {
            player.sendMessage("No Permission!");
            return;
        }

        DataFunctions.getInstance().saveData(player);
    }
}
