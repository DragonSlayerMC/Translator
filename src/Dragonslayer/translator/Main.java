package Dragonslayer.translator;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements CommandExecutor {

    Main plugin;

    private static final Logger log = Logger.getLogger("Minecraft");
    private static Economy econ = null;
    private static Permission perms = null;
    private static Chat chat = null;

    Server server = Bukkit.getServer();
    PluginManager pm = server.getPluginManager();

    public void onEnable() {
        if (!setupEconomy() ) {
            log.severe(String.format("[%s] - Disabled due to no Vault dependency found!", getDescription().getName()));
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        setupPermissions();
        getLogger().info("TRANSLATOR STARTED");
        setupChat();
        saveDefaultConfig();
        plugin = this;
        pm.registerEvents(new ChatMessageListener(this), this);
        getCommand("translations").setExecutor(this);
    }

    public void onDisable() {

    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }

    private boolean setupChat() {
        RegisteredServiceProvider<Chat> rsp = getServer().getServicesManager().getRegistration(Chat.class);
        chat = rsp.getProvider();
        return chat != null;
    }

    private boolean setupPermissions() {
        RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);
        perms = rsp.getProvider();
        return perms != null;
    }
    public static Economy getEcononomy() {
        return econ;
    }

    public static Permission getPermissions() {
        return perms;
    }

    public static Chat getChat() {
        return chat;
    }

    private Plugin loadPlugin(String pluginName) {
        Plugin plugin = getServer().getPluginManager().getPlugin(pluginName);
        if (plugin == null) {
            return null;
        }
        return plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(label.equalsIgnoreCase("translations")) {
            if(args.length == 2) {
                if(args[0].equalsIgnoreCase("add")) {
                    List<String> a = new ArrayList<>();
                    if(plugin.getConfig().get("players") != null) {
                        a = (List<String>) plugin.getConfig().get("players");
                        a.add(args[1]);
                        plugin.getConfig().set("players", a);
                        plugin.saveConfig();
                        sender.sendMessage("---Player added!---");
                        return true;
                    } else {
                        a.add(args[1]);
                        plugin.getConfig().set("players", a);
                        plugin.saveConfig();
                        sender.sendMessage("---Player added!---");
                        return true;
                    }
                } else {
                    sender.sendMessage("Use /translations add <name>");
                    return true;
                }
            } else {
                sender.sendMessage("Use /translations add <name>");
                return true;
            }
        }
        return true;
    }
}
