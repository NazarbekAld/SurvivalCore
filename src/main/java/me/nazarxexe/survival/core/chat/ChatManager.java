package me.nazarxexe.survival.core.chat;
import cn.nukkit.Player;
import cn.nukkit.utils.TextFormat;
import me.nazarxexe.survival.core.Core;
import me.nazarxexe.survival.core.tools.TerminalComponent;
import me.nazarxexe.survival.core.tools.TextComponent;
import org.jetbrains.annotations.NotNull;

import java.util.List;


public class ChatManager {

    private final Core core;

    private ChatExecutable executable;

    public ChatManager(Core core) {
        this.core = core;

        // Default chat format
        setDefaultExecutable();
    }

    public void send(@NotNull Message message){ // Send message to everyone

        core.getServer().getOnlinePlayers().forEach(

                (uuid, player) -> send(player, message)

        );

    }

    public void send(@NotNull List<Player> players, @NotNull Message message){ // Send to group of players

        players.forEach(
                (player -> send(player, message))
        );

    }

    public void send(@NotNull Player player, @NotNull Message message) { // Send to player
        player.sendMessage(message.getMessage().getText());
    }

    public void setChatExecutable(@NotNull ChatExecutable executable) {
        this.executable = executable;
    }

    public ChatExecutable getExecutable(){
        return this.executable;
    }

    private void setDefaultExecutable() {
        this.setChatExecutable(message -> {

            if (message.getSender() == null){
                send(message);
                return;
            }
            TextComponent msg = new TextComponent()
                    .combine(TextComponent.coloredText(TextFormat.GREEN, message.getSender().getName())
                            .combine(" > ")
                            .combine(message.getMessage().getText())
                    );
            message.setMessage(msg);
            new TerminalComponent(core.getLogger(), msg)
                    .info();

            send(message);
        });
    }
}
