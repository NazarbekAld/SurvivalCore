package me.nazarxexe.survival.core.tools.text;

import cn.nukkit.utils.TextFormat;
import lombok.Data;
import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

@Data
@SuppressWarnings({ "unused" })
public class TextComponent{

    private String text;

    private @Getter Set<TextComponentRule> rules;

    public TextComponent(String text){
        this.text = text;
        this.rules = new HashSet<>();
    }
    public TextComponent(TextComponent component){
        this.rules = new HashSet<>();
        this.text = component.getText();
    }
    public TextComponent(){
        this.text = "";
        this.rules = new HashSet<>();
    }



    public TextComponent setText(String text) {

        TextChangeEvent event = new TextChangeEvent(text);
        rules.forEach((rule) -> rule.textchangeEvent(event));
        if (event.isCanceled()) {
            return this;
        }

        this.text = event.getText().getText();
        return this;
    }

    public TextComponent setText(TextComponent text) {

        TextChangeEvent event = new TextChangeEvent(text);
        rules.forEach((rule) -> rule.textchangeEvent(event));
        if (event.isCanceled()) {
            return this;
        }

        this.text = event.getText().getText();
        return this;
    }


    public TextComponent add(String text){

        TextChangeEvent event = new TextChangeEvent(text);
        rules.forEach((rule) -> rule.textchangeEvent(event));
        if (event.isCanceled()) {
            return this;
        }

        this.text = this.text + "\n" + event.getText().getText();
        return this;
    }
    public TextComponent add(TextComponent component){

        TextChangeEvent event = new TextChangeEvent(component);
        rules.forEach((rule) -> rule.textchangeEvent(event));
        if (event.isCanceled()) {
            return this;
        }

        this.text = this.text + "\n" + event.getText().getText();
        return this;
    }
    public TextComponent combine(String text){

        TextChangeEvent event = new TextChangeEvent(text);
        rules.forEach((rule) -> rule.textchangeEvent(event));
        if (event.isCanceled()) {
            return this;
        }

        this.text = this.text + event.getText().getText();
        return this;
    }
    public TextComponent combine(TextComponent component){

        TextChangeEvent event = new TextChangeEvent(component);
        rules.forEach((rule) -> rule.textchangeEvent(event));
        if (event.isCanceled()) {
            return this;
        }

        this.text = this.text + event.getText().getText();
        return this;
    }
    public TextComponent combine(TextFormat color){

        TextChangeEvent event = new TextChangeEvent(text);
        rules.forEach((rule) -> rule.textchangeEvent(event));
        if (event.isCanceled()) {
            return this;
        }

        this.text = this.text + color;
        return this;
    }


    /**
     *
     * Set text without "escape sequences"
     *
     */

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


    public TextComponent add_RULE_NoES() {
        rules.add(new TextComponentRule() {
            @Override
            public void textchangeEvent(TextChangeEvent event) {
                if (event.getText().getText().contains("\n"))
                    event.setText(new TextComponent(event.getText().getText().replace("\n", " ")));
            }
        });
        return this;
    }

    public TextComponent add_RULE_AutoColor() {
        rules.add(new TextComponentRule() {
            @Override
            public void textchangeEvent(TextChangeEvent event) {
                event.setText(new TextComponent(TextFormat.colorize('&', event.getText().getText())));
            }
        });
        return this;
    }


    /**
     *
     * <h1>Trigger all Rules</h1>
     * <b>NOT RECOMMENDED</b> because it can break meaning of the text or cause some bugs!
     */
    public TextComponent triggerRules() {
        TextChangeEvent event = new TextChangeEvent(this.getText());
        rules.forEach((rule) -> rule.textchangeEvent(event));
        if (event.isCanceled()) {
            return this;
        }

        this.setText(event.getText());
        return this;
    }




}
