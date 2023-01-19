package me.nazarxexe.survival.core.chat;


import cn.nukkit.Player;
import lombok.Getter;
import me.nazarxexe.survival.core.tools.TextComponent;

@Getter
public class Message{

    TextComponent message;
    final Player sender;
    final long timestamp;

    public Message(Player sender, TextComponent message) {
        this.sender = sender;
        this.timestamp = System.currentTimeMillis();
        this.message = message;
    }

    public Message(String message) {
        this.sender = null;
        this.timestamp = System.currentTimeMillis();
        this.message = new TextComponent(message);
    }
    public Message(TextComponent message) {
        this.sender = null;
        this.timestamp = System.currentTimeMillis();
        this.message = message;
    }

    public Message(Player sender, String message) {
        this.sender = sender;
        this.timestamp = System.currentTimeMillis();
        this.message = new TextComponent(message);
    }



    public void setMessage(TextComponent message) {
        this.message = message;
    }

}
