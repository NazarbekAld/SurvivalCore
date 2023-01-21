package me.nazarxexe.survival.core.chat;


import org.jetbrains.annotations.NotNull;

public interface ChatExecutable {

    /**
     *
     * <h3>CHAT EVENT</h3>
     * Its a callback that executes code when someone sends chat message.
     *
     */
    void chatEvent(@NotNull Message message);

}
