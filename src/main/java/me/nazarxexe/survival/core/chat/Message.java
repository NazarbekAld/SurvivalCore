package me.nazarxexe.survival.core.chat;


import cn.nukkit.Player;
import lombok.Getter;
import me.nazarxexe.survival.core.tools.text.TextComponent;
import org.jetbrains.annotations.NotNull;

@Getter
@SuppressWarnings({ "unused" })
public class Message{

    TextComponent message;
    final Player sender;
    final long timestamp;

    public Message(@NotNull Player sender, @NotNull TextComponent message) {
        this.sender = sender;
        this.timestamp = System.currentTimeMillis();
        this.message = message;
    }

    public Message(@NotNull String message) {
        this.sender = null;
        this.timestamp = System.currentTimeMillis();
        this.message = new TextComponent(message);
    }
    public Message(@NotNull TextComponent message) {
        this.sender = null;
        this.timestamp = System.currentTimeMillis();
        this.message = message;
    }

    public Message(@NotNull Player sender, @NotNull String message) {
        this.sender = sender;
        this.timestamp = System.currentTimeMillis();
        this.message = new TextComponent(message);
    }



    public void setMessage(@NotNull TextComponent message) {
        this.message = message;
    }

}
