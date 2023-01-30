package me.nazarxexe.survival.core.command;

import cn.nukkit.command.Command;
import cn.nukkit.command.CommandMap;
import cn.nukkit.command.CommandSender;
import cn.nukkit.plugin.Plugin;
import cn.nukkit.utils.TextFormat;
import io.netty.util.internal.EmptyArrays;
import me.nazarxexe.survival.core.tools.DataObject;
import me.nazarxexe.survival.core.tools.TextComponent;

public class EzCommand {

    String name;
    String desc;
    String[] alts;
    String usage;


    CommandExecutable executable;


    /**
     *
     * <h3>EZCOMMAND</h3>
     * Create command, <b>write less code</b>!
     * <br />
     * Here some example:
     * <pre>
     * {@code
     *  new EzCommand(new Object() {
     *      String name = "hello;" // Name of command (REQUIRED)
     *      String desc = "say Hello"; // command description
     *      String usage = "/hello"; // command usage
     *      String[] alts = { "hi" }; // command aliases
     *      CommandExecutable executable = new CommandExecutable {
     *          boolean execute(CommandSender sender, String commandLabel, String[] args){
     *              // some code!!
     *          }
     *      };
     *  })
     * }
     * </pre>
     *
     * @param o Command proprieties
     */

    public EzCommand (Object o) {

        DataObject dO = new DataObject(o);

        // Command name
        if (dO.getFields().get("name") == null && !(dO.getFields().get("name") instanceof String) ){
            throw new NullPointerException("Failed to create command. Because name field is null!");
        }

        this.name = (String) dO.getFields().get("name");


        // Command description
        if (!(dO.getFields().get("desc") == null) && dO.getFields().get("desc") instanceof String){
            this.desc = (String) dO.getFields().get("desc");
        }else {
            this.desc = "";
        }

        // Command usage
        if (!(dO.getFields().get("usage") == null) && dO.getFields().get("usage") instanceof String){
            this.usage = (String) dO.getFields().get("usage");
        }else {
            this.usage = "";
        }

        // Command aliases
        if (!(dO.getFields().get("alts") == null) && dO.getFields().get("alts") instanceof String[]){
            this.alts = (String[]) dO.getFields().get("alts");
        }else {
            this.alts = EmptyArrays.EMPTY_STRINGS;
        }


        // Command Executable
        if (!(dO.getFields().get("executable") == null) && dO.getFields().get("executable") instanceof CommandExecutable){
            this.executable = (CommandExecutable) dO.getFields().get("executable");
        }else {
            this.executable = new CommandExecutable() {
                @Override
                public boolean execute(CommandSender sender, String commandLabel, String[] args) {
                    sender.sendMessage(new TextComponent()
                            .combine(TextFormat.GREEN)
                            .combine("This is default executable!").getText());
                    return true;
                }
            };
        }

    }

    public EzCommand register(Plugin plugin) {

        plugin.getServer().getCommandMap().getCommands().keySet().removeIf(name -> name.equals(name));
        plugin.getServer().getCommandMap().getCommands().values().removeIf(command -> command.getName().equals(name));


        plugin.getServer().getCommandMap().register(name, new Command(name, desc, usage, alts) {
            @Override
            public boolean execute(CommandSender sender, String commandLabel, String[] args) {
                return executable.execute(sender, commandLabel, args);
            }
        });

        return this;
    }

}
