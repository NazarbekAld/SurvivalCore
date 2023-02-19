package me.nazarxexe.survival.core.bossbar;

import cn.nukkit.Player;
import cn.nukkit.plugin.Plugin;
import cn.nukkit.utils.BossBarColor;
import cn.nukkit.utils.DummyBossBar;
import lombok.Getter;
import me.nazarxexe.survival.core.tools.TextComponent;

import java.util.*;

/**
 *
 * <h1>STATIC BOSSBAR</h1>
 * Default bossbar kit
 *
 */
public class StaticBossbar {


    final Plugin plugin;

    public void setText(TextComponent textComponent) {
        this.textComponent = textComponent;
        update();
    }

    public void setColor(BossBarColor color) {
        this.color = color;
        update();
    }

    public void setProgress(Float progress) {
        this.progress = progress;
        update();
    }

    private @Getter TextComponent textComponent;
    private @Getter BossBarColor color;
    private @Getter Float progress = 100F;

    private @Getter HashMap<Player, DummyBossBar> bossbarDummy;


    // AD HOC

    public StaticBossbar(Plugin plugin, TextComponent text)
    {
        this.plugin = plugin;
        this.textComponent = text;
        bossbarDummy = new HashMap<>();

    }

    public StaticBossbar(Plugin plugin, TextComponent text, BossBarColor color)
    {
        this.plugin = plugin;
        this.textComponent = text;
        this.color = color;
        bossbarDummy = new HashMap<>();

    }

    public StaticBossbar(Plugin plugin, TextComponent text, BossBarColor color, Set<Player> clients)
    {
        this.plugin = plugin;
        this.textComponent = text;
        this.color = color;
        bossbarDummy = new HashMap<>();

    }

    public void show(Player player) {
        DummyBossBar bossBar = build(player);
        bossBar.create();
    }

    public void hide(Player player) {
        player.getDummyBossBar(bossbarDummy.get(player).getBossBarId())
                .destroy();
        bossbarDummy.remove(player);
    }

    protected DummyBossBar build(Player player) {

        DummyBossBar b = new DummyBossBar.Builder(player)
                .length(100f)
                .color(color)
                .text(textComponent.requireNoES().getText())
                .build();

        bossbarDummy.put(player, b);

        return b;
    }

    protected void update() {

        bossbarDummy.forEach(((player, dummyBossBar) -> {

            dummyBossBar.setLength(progress);
            dummyBossBar.setText(textComponent.requireNoES().getText());
            dummyBossBar.setColor(color);
            dummyBossBar.reshow();

        }));

    }



}
