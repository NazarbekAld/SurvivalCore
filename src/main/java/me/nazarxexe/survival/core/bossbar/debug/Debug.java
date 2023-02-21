package me.nazarxexe.survival.core.bossbar.debug;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import me.nazarxexe.survival.core.Core;
import me.nazarxexe.survival.core.bossbar.BossbarTrigger;
import me.nazarxexe.survival.core.bossbar.DynamicBossbar;
import me.nazarxexe.survival.core.bossbar.StaticBossbar;
import me.nazarxexe.survival.core.tools.text.TextComponent;

public class Debug extends Command
{
    private Core core;
    public Debug(Core core) {
        super("dboss");
        this.core = core;
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {

        if (args[0].equals("d")){
            DynamicBossbar dyn = new DynamicBossbar(core, new TextComponent("Debug debug Debug"));
            dyn.show((Player) sender);
            dyn.decreasingTask(30, 10f);
        }

        if (args[0].equals("dd")){
            DynamicBossbar dyn = new DynamicBossbar(core, new TextComponent("Debug debug Debug"));
            dyn.show((Player) sender);
            dyn.getTriggers().add(new BossbarTrigger(50) {
                @Override
                public void trigger() {
                    dyn.setText(new TextComponent("DEBUGGGGGGGGGGG"));
                }
            });
            dyn.decreasingTask(30, 10f);
        }

        if (args[0].equals("s")){
            StaticBossbar st = new StaticBossbar(core, new TextComponent("Hello! Debuggg"));
            st.show((Player) sender);
        }

        return true;
    }
}
