package me.General.Commands;

import me.General.Chat.Broadcaster;
import me.General.DataManegement.DataFunctions;
import me.General.DataManegement.PermissionData;
import me.General.DataManegement.PlayerData;
import net.minestom.server.command.CommandSender;
import net.minestom.server.command.builder.Command;
import net.minestom.server.command.builder.CommandContext;
import net.minestom.server.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class SaveData extends Command {
    //private final PermissionData permissionData;
    public SaveData() {
        super("savedata");
        //this.permissionData = permissionData;
        addSyntax(this::execute);
    }

    //PermissionData permissionData = new PermissionData();
    PermissionData permissionData = new PermissionData();

    private void execute(@NotNull CommandSender commandSender, @NotNull CommandContext commandContext) {
        Player player = (Player) commandSender;
        DataFunctions.getInstance().saveData(player);
    }
}
