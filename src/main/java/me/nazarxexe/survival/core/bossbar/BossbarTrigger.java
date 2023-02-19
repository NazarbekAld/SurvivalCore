package me.nazarxexe.survival.core.bossbar;

/**
 *
 * Bossbar triggers
 *
 * Executes code when reaches start.
 *
 */
public abstract class BossbarTrigger {

    public BossbarTrigger(float triggerProgress) {
        this.start = triggerProgress;
        this.end = triggerProgress;
    }

    public BossbarTrigger(int triggerProgressStart, float triggerProgressEnd) {
        this.start = triggerProgressStart;
        this.end = triggerProgressEnd;
    }

    float start;
    float end;

    public abstract void trigger();

    public boolean isReached(Float progress) {
        return progress >= start && progress <= end;
    }

}
