package me.nazarxexe.survival.core.tools;

import cn.nukkit.utils.TextFormat;
import lombok.Data;

@Data
@SuppressWarnings({ "unused" })
public class TextComponent{

    private String text;

    public TextComponent(String text){
        this.text = text;
    }
    public TextComponent(TextComponent component){
        this.text = component.getText();
    }
    public TextComponent(){
        this.text = "";
    }


    public TextComponent add(String text){
        this.text = this.text + "\n" + text;
        return this;
    }
    public TextComponent add(TextComponent component){
        this.text = this.text + "\n" + component.getText();
        return this;
    }
    public TextComponent combine(String text){
        this.text = this.text + text;
        return this;
    }
    public TextComponent combine(TextComponent component){
        this.text = this.text + component.getText();
        return this;
    }
    public TextComponent combine(TextFormat color){
        this.text = this.text + color;
        return this;
    }

    public static TextComponent coloredText(TextFormat color, String text){
        return
                new TextComponent()
                .combine(color)
                .combine(text)
                .combine(TextFormat.RESET);
    }
    public static TextComponent coloredText(TextFormat color, TextFormat suffix, String text){
        return
                new TextComponent()
                        .combine(color)
                        .combine(suffix)
                        .combine(text)
                        .combine(TextFormat.RESET);
    }
    public static TextComponent coloredText(TextFormat color, TextFormat suffix, TextFormat suffix2, String text){
        return
                new TextComponent()
                        .combine(color)
                        .combine(suffix)
                        .combine(suffix2)
                        .combine(text)
                        .combine(TextFormat.RESET);
    }
    public static TextComponent coloredText(TextFormat color, TextFormat suffix, TextFormat suffix2, TextFormat suffix3, String text){
        return
                new TextComponent()
                        .combine(color)
                        .combine(suffix)
                        .combine(suffix2)
                        .combine(suffix3)
                        .combine(text)
                        .combine(TextFormat.RESET);
    }





}
