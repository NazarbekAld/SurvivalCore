package me.nazarxexe.survival.core.command;

import cn.nukkit.command.CommandSender;

public interface CommandExecutable {

    boolean execute(CommandSender sender, String commandLabel, String[] args);

}
