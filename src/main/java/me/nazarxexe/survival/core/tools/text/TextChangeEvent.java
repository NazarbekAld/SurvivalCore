package me.nazarxexe.survival.core.tools.text;

import lombok.Getter;
import lombok.Setter;

public class TextChangeEvent {

    private @Getter @Setter TextComponent text;
    private @Getter @Setter boolean canceled = false;

    public TextChangeEvent(String text) {
        this.text = new TextComponent(text);
    }

    public TextChangeEvent(TextComponent text) {
        this.text = new TextComponent(text);
    }



}
