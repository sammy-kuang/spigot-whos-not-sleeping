package com.protonmail.theliverman.whosnotsleeping;

import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedLeaveEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public final class WhosNotSleeping extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        // Plugin startup logic

        // INITIALIZE CONFIG
        getConfig().options().copyDefaults();
        saveDefaultConfig();

        System.out.println("WhosNotSleeping has started.");
        System.out.println("[WhosNotSleeping] Do need perms to execute who is in bed: " + getConfig().getBoolean("needPerms"));


    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        System.out.println("WhosNotSleeping is shutting down.");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Boolean conf = getConfig().getBoolean("needPerms");
        System.out.println(conf);

        if(sender.hasPermission("WhosNotSleeping.canCheck") || !conf) {
            List<String> notSleeping = new ArrayList<String>();
            List<String> isSleeping = new ArrayList<String>();
            String out = "";

            Server server = sender.getServer();

            // ADD EVERYONE TO LISTS
            for (Player p : sender.getServer().getOnlinePlayers()) {
                if (!p.isSleeping())
                    notSleeping.add(p.getDisplayName());
                else
                    isSleeping.add(p.getDisplayName());
            }

            // WHO IS NOT SLEEPING
            if (command.getName().equals("notinbed")) {
                if (notSleeping.isEmpty()) {
                    server.broadcastMessage(ChatColor.GREEN + "Everyone is sleeping! Zzzzz...");
                } else {
                    for (String player : notSleeping) {
                        out += player + " ";
                    }

                    server.broadcastMessage(ChatColor.GOLD + "Not sleeping: " + ChatColor.YELLOW + out);
                }
            }

            // WHO IS SLEEPING
            if (command.getName().equals("whosinbed")) {
                if (isSleeping.isEmpty()) {
                    server.broadcastMessage(ChatColor.DARK_RED + "Nobody is sleeping.");
                } else {
                    for (String player : isSleeping) {
                        out += player + " ";
                    }

                    server.broadcastMessage(ChatColor.GREEN + "Sleeping: " + ChatColor.BLUE + out);
                }
            }

        }
        else {
            sender.sendMessage(ChatColor.RED + "You do not have sufficient permissions.");
        }

        return false;
    }
}
