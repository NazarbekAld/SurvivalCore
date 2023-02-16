package me.nazarxexe.survival.core.chat;


import cn.nukkit.event.player.PlayerChatEvent;
import org.jetbrains.annotations.NotNull;

public interface ChatExecutable {

    /**
     *
     * <h3>CHAT EVENT</h3>
     * Its a callback that executes code when someone sends chat message.
     *
     */
    void chatEvent(@NotNull Message message, PlayerChatEvent event);

}
