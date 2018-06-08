package Dragonslayer.translator;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.permissions.PermissionAttachment;

import java.util.ArrayList;
import java.util.List;

public class ChatMessageListener implements Listener {

    Main plugin;

    public ChatMessageListener(Main plugin) {
        this.plugin = plugin;
    }


    @EventHandler(priority = EventPriority.HIGHEST)
    public void onChat(org.bukkit.event.player.AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();
        e.setCancelled(true);
        List<String> a = new ArrayList<>();
        if((a = (List<String>) plugin.getConfig().get("players")) != null) {
            if (!a.contains(p.getName())) {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    if (a.contains(player.getName())) {
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("message")
                                .replace("PLAYER", p.getDisplayName())
                                .replace("message", Translator.getWords(e.getMessage()))));
                    } else {
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("message")
                                .replace("PLAYER", p.getDisplayName())
                                .replace("message", e.getMessage())));
                    }
                }
            } else {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("message")
                            .replace("PLAYER", p.getDisplayName())
                            .replace("message", e.getMessage())));
                }
            }
        } else {
            for (Player player : Bukkit.getOnlinePlayers()) {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("message")
                        .replace("PLAYER", p.getDisplayName())
                        .replace("message", e.getMessage())));
            }
        }
    }



}
