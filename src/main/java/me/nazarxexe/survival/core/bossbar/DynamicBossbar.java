package me.nazarxexe.survival.core.bossbar;

import cn.nukkit.plugin.Plugin;
import cn.nukkit.scheduler.Task;
import cn.nukkit.utils.BossBarColor;
import lombok.Getter;
import me.nazarxexe.survival.core.tools.TextComponent;

import java.util.*;

/**
 *
 * <h1>DYNAMIC BOSSBAR</h1>
 * More advanced and cool bossbar!
 *
 */
public class DynamicBossbar extends StaticBossbar {


    private @Getter List<BossbarTrigger> triggers;
    public DynamicBossbar(Plugin plugin, TextComponent text) {
        super(plugin, text);
        this.triggers = new ArrayList<>();
    }

    public DynamicBossbar(Plugin plugin, TextComponent text, BossBarColor color) {
        super(plugin, text, color);
        this.triggers = new ArrayList<>();
    }


    /**
     *
     * <h1>decreasingTask</h1>
     *
     * Decreases progress of bossbar at the time.
     *
     * @param interval - Decrease interval.
     * @param decrease - Decrease amount.
     */
    public void decreasingTask(int interval, float decrease) {
        if (decrease > 100f || decrease <= 0f)
            throw new IllegalArgumentException("Decrease param cannot be more than 100 or be (less or equal) than 0.");
        plugin.getServer().getScheduler().scheduleDelayedRepeatingTask(new Task() {
            @Override
            public void onRun(int i) {
                if (getProgress()-decrease < 0f){
                    plugin.getServer()
                            .getScheduler()
                            .cancelTask(this.getTaskId());
                    return;
                }

                triggers.forEach(((bossbarTrigger) -> {
                    if (bossbarTrigger.isReached(getProgress()))
                        bossbarTrigger.trigger();
                }));

                setProgress(getProgress() - decrease);
            }
        }, 0, interval);


    }


    /**
     *
     * <h1>increasingTask</h1>
     *
     * Increases progress of bossbar at the time.
     *
     * @param interval - Increase interval.
     * @param increase - Increase amount.
     */
    public void increasingTask(int interval, float increase) {
        if (increase > 100f || increase <= 0f)
            throw new IllegalArgumentException("Decrease param cannot be more than 100 or be (less or equal) than 0.");
        plugin.getServer().getScheduler().scheduleDelayedRepeatingTask(new Task() {
            @Override
            public void onRun(int i) {
                if (getProgress()+increase < 100f){
                    plugin.getServer()
                            .getScheduler()
                            .cancelTask(this.getTaskId());
                    return;
                }

                triggers.forEach(((bossbarTrigger) -> {
                    if (bossbarTrigger.isReached(getProgress()))
                        bossbarTrigger.trigger();
                }));

                setProgress(getProgress() + increase);

            }
        }, 0, interval);
    }





}
