package me.General.Commands;

import me.General.Chat.Broadcaster;
import me.General.DataManegement.DataFunctions;
import me.General.DataManegement.PermissionData;
import me.General.DataManegement.PlayerData;
import net.minestom.server.command.CommandSender;
import net.minestom.server.command.builder.Command;
import net.minestom.server.command.builder.CommandContext;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class LoadData extends Command {
    public LoadData() {
        super("loaddata");
        addSyntax(this::execute);
    }

    private void execute(@NotNull CommandSender commandSender, @NotNull CommandContext commandContext) {
        Player player = (Player) commandSender;
        DataFunctions.getInstance().loadData(player);
    }
}
