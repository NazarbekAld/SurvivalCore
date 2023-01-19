package me.nazarxexe.survival.core.tools;

import cn.nukkit.plugin.PluginLogger;
import lombok.Getter;

import java.util.List;

@Getter
public class TerminalComponent extends TextComponent{

    final PluginLogger logger;

    public TerminalComponent(PluginLogger logger, TextComponent component){
        super(component.getText());
        this.logger = logger;
    }
    public TerminalComponent(PluginLogger logger){
        super();
        this.logger = logger;
    }

    public void warn(){
        List<String> texts = List.of(getText().split("\n"));
        texts.forEach(this.logger::warning);
    }
    public void critical(){
        List<String> texts = List.of(getText().split("\n"));
        texts.forEach(this.logger::critical);
    }
    public void info(){
        List<String> texts = List.of(getText().split("\n"));
        texts.forEach(this.logger::info);
    }
    public void error(){
        List<String> texts = List.of(getText().split("\n"));
        texts.forEach(this.logger::error);
    }

}
