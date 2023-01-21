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


    /**
     *
     * <h3>SEND TO EVERYONE</h3>
     * sends message object to everyone.
     *
     * @param message {@link me.nazarxexe.survival.core.chat.Message} object.
     */

    public void send(@NotNull Message message){

        core.getServer().getOnlinePlayers().forEach(

                (uuid, player) -> send(player, message)

        );

    }

    /**
     *
     * <h3>SEND TO GROUP OF PLAYERS</h3>
     * sends message object to group of players.
     *
     * @param message {@link me.nazarxexe.survival.core.chat.Message} object.
     * @param players {@link java.util.List} list of players to send.
     */
    public void send(@NotNull List<Player> players, @NotNull Message message){ // Send to group of players

        players.forEach(
                (player -> send(player, message))
        );

    }


    /**
     *
     * <h3>SEND TO PLAYER</h3>
     * sends message object to player.
     *
     * @param message {@link me.nazarxexe.survival.core.chat.Message} object.
     * @param player {@link cn.nukkit.Player} to send.
     */
    public void send(@NotNull Player player, @NotNull Message message) { // Send to player
        player.sendMessage(message.getMessage().getText());
    }

    /**
     * <h3>EXECUTABLE SETTER</h3>
     * @param executable Reqired to be not <b>null</b>.
     */
    public void setChatExecutable(@NotNull ChatExecutable executable) {
        this.executable = executable;
    }

    /**
     * <h3>EXECUTABLE GETTER</h3>
     *
     * @return {@link me.nazarxexe.survival.core.chat.ChatExecutable}
     */
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
