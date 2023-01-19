package me.nazarxexe.survival.core.chat;
import cn.nukkit.Player;
import cn.nukkit.utils.TextFormat;
import me.nazarxexe.survival.core.Core;
import me.nazarxexe.survival.core.tools.TerminalComponent;
import me.nazarxexe.survival.core.tools.TextComponent;

import java.util.List;


public class ChatManager {

    private final Core core;

    private ChatExecutable executable;

    public ChatManager(Core core) {
        this.core = core;

        // Default chat format
        setDefaultExecutable();
    }

    public void send(Message message){ // Send message to everyone

        core.getServer().getOnlinePlayers().forEach(

                (uuid, player) -> send(player, message)

        );

    }

    public void send(List<Player> players, Message message){ // Send to group of players

        players.forEach(
                (player -> send(player, message))
        );

    }

    public void send(Player player, Message message) { // Send to player
        player.sendMessage(message.getMessage().getText());
    }

    public void setChatExecutable(ChatExecutable executable) {
        if (executable == null)
            throw new NullPointerException();
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
