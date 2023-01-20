package me.nazarxexe.survival.core.chat;

import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerChatEvent;
import me.nazarxexe.survival.core.Core;
import org.jetbrains.annotations.NotNull;

public class ChatEvent implements Listener {

    final Core plugin;
    final ChatManager manager;
    public ChatEvent(@NotNull Core plugin, @NotNull ChatManager manager) {
        this.plugin = plugin;
        this.manager = manager;
    }

    @EventHandler
    public void onChat(PlayerChatEvent e) {
        e.setCancelled(true);
        manager.getExecutable().chatEvent(new Message(e.getPlayer(), e.getMessage()));
    }
}
