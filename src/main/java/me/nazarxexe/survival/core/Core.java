package me.nazarxexe.survival.core;

import cn.nukkit.plugin.PluginBase;
import cn.nukkit.plugin.service.ServiceManager;
import cn.nukkit.plugin.service.ServicePriority;
import cn.nukkit.utils.TextFormat;
import lombok.Getter;
import me.nazarxexe.survival.core.chat.ChatEvent;
import me.nazarxexe.survival.core.chat.ChatManager;
import me.nazarxexe.survival.core.database.IDatabase;
import me.nazarxexe.survival.core.database.SQLite;
import me.nazarxexe.survival.core.economy.EconomyManager;
import me.nazarxexe.survival.core.tools.TerminalComponent;
import me.nazarxexe.survival.core.tools.TextComponent;

import java.io.File;


@Getter
public class Core extends PluginBase {

    private EconomyManager economyManager;
    private ChatManager chatManager;

    private IDatabase database;



    // INIT plugin
    @Override
    public void onLoad() {

    }

    // Server enabled
    @Override
    public void onEnable() {
        getDataFolder().mkdir();

        economyManager = new EconomyManager(this);
        chatManager = new ChatManager(this);
        database = new SQLite(new File(getDataFolder(), "database.db"), this);


        this.getLogger().info(TextFormat.GREEN + "PLUGIN ENABLED.");

        ServiceManager servicemanager = this.getServer().getServiceManager();
        this.getLogger().info("Registering services...");
        servicemanager.register(                                        // Economy
                EconomyManager.class, // Manager Class
                economyManager, // Instance
                this, // Registry plugin
                ServicePriority.NORMAL // Priority
        );
        servicemanager.register(                                       // Chat
                ChatManager.class, // Manager Class
                chatManager, // Instance
                this, // Registry plugin
                ServicePriority.NORMAL // Priority
        );

        this.getLogger().info("Services are registered.");

        this.getLogger().info("Registering listeners...");
        this.getServer().getPluginManager().registerEvents(new ChatEvent(this, chatManager), this);
        this.getLogger().info("Listeners are registered.");


    }

    // Server disabled.
    @Override
    public void onDisable() {

    }
}
