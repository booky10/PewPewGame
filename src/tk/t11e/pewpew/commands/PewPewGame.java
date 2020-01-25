package tk.t11e.pewpew.commands;
// Created by booky10 in pewPewGame (11:57 25.01.20)

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import tk.t11e.pewpew.main.Main;
import tk.t11e.pewpew.manager.PewPewManager;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("NullableProblems")
public class PewPewGame implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (player.hasPermission("pewPewGame"))
                if (args.length == 1)
                    if (args[0].equalsIgnoreCase("setSpawn")) {
                        PewPewManager.setSpawn(player.getLocation());
                        player.sendMessage(Main.PREFIX+"§aSuccessfully set spawn!");
                    } else if (args[0].equalsIgnoreCase("setLoc1")) {
                        PewPewManager.setLoc1(player.getLocation());
                        player.sendMessage(Main.PREFIX+"§aSuccessfully set location 1!");
                    } else if (args[0].equalsIgnoreCase("setLoc2")) {
                        PewPewManager.setLoc2(player.getLocation());
                        player.sendMessage(Main.PREFIX+"§aSuccessfully set location 2!");
                    } else
                        return false;
                else
                    PewPewManager.respawn(player);
            else
                player.sendMessage(Main.NO_PERMISSION);
        } else
            sender.sendMessage("This command must be executed by a player!");
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        List<String> list = new ArrayList<>();
        if(args.length==1&&sender instanceof Player) {
            list.add("setSpawn");
            list.add("setLoc1");
            list.add("setLoc2");
        }
        return list;
    }
}