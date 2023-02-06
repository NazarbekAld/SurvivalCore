package me.nazarxexe.survival.core.network.debug;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import me.nazarxexe.survival.core.Core;
import me.nazarxexe.survival.core.network.Sound;

public class PlaySoundDebug extends Command {

    private Core plugin;

    public PlaySoundDebug(Core pl){
        super("debugplaysound");

        this.plugin = pl;
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] strings) {

        if (!(commandSender instanceof Player)) return true;

        new Sound(plugin, strings[0])
                .setVol(1)
                .setPitch(1)
                .play(commandSender.asPlayer());

        return true;
    }
}
