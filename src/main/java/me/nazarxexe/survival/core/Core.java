package me.nazarxexe.survival.core;

import cn.nukkit.command.CommandSender;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.plugin.service.ServiceManager;
import cn.nukkit.plugin.service.ServicePriority;
import cn.nukkit.scheduler.AsyncTask;
import cn.nukkit.utils.Config;
import cn.nukkit.utils.ConfigSection;
import cn.nukkit.utils.TextFormat;
import lombok.Getter;
import me.nazarxexe.survival.core.chat.ChatEvent;
import me.nazarxexe.survival.core.chat.ChatManager;
import me.nazarxexe.survival.core.command.CommandExecutable;
import me.nazarxexe.survival.core.command.EzCommand;
import me.nazarxexe.survival.core.database.IDatabase;
import me.nazarxexe.survival.core.database.MySQL;
import me.nazarxexe.survival.core.database.SQLite;
import me.nazarxexe.survival.core.economy.EconomyManager;
import me.nazarxexe.survival.core.tools.TerminalComponent;
import me.nazarxexe.survival.core.tools.TextComponent;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedHashMap;


@Getter
public class Core extends PluginBase {

    private EconomyManager economyManager;
    private ChatManager chatManager;

    private IDatabase database;

    private Config config;



    // INIT plugin
    @Override
    public void onLoad() {

    }

    private String parse(Object object){
        return String.valueOf(object);
    }

    private void databaseCheck(){
        this.getServer().getScheduler().scheduleAsyncTask(this, new AsyncTask() {
            @Override
            public void onRun() {

                database.getConnection().thenAcceptAsync((connection) -> {
                    ResultSet resultSet = null;
                    new TerminalComponent(getLogger(), new TextComponent()
                            .combine(TextFormat.BLUE + "<DATABASE> Testing database connection..."))
                            .info();
                    Statement statement = null;
                    try {
                        statement = connection.createStatement();
                        statement.executeUpdate("CREATE TABLE if not exists `test502502` (`test1` INTEGER);");
                        statement.executeUpdate("INSERT INTO `test502502`(`test1`) VALUES (69420);");
                        resultSet = statement.executeQuery("SELECT * from `test502502`");
                        statement.executeUpdate("DROP TABLE `test502502`");

                    } catch (SQLException e) {
                        new TerminalComponent(getLogger(), new TextComponent()
                                .combine(TextFormat.RED + "<DATABASE> Testing execution failed!")
                                .add("" + e))
                                .warn();
                        return;
                    }

                    new TerminalComponent(getLogger(), new TextComponent()
                            .combine(TextFormat.GREEN + "<DATABASE> Everything good!"))
                            .info();

                }).join();

            }
        });
    }

    // Server enabled
    @Override
    public void onEnable() {
        getDataFolder().mkdir();

        saveDefaultConfig();

        config = new Config(
                new File(this.getDataFolder(), "config.yml"),
                Config.YAML,
                new ConfigSection(new LinkedHashMap<>(){

                    {
                        put("SQL", "SQLite");
                        put("SQLPROP", new ConfigSection() {{
                            put("url", "localhost");
                            put("port", "3306");
                            put("username", "root");
                            put("password", "");
                            put("database", "econ");
                        }});
                    }

                })
        );

        if (parse(config.get("SQL", "SQLite")).equalsIgnoreCase("SQLite")){
            new TerminalComponent(getLogger(), new TextComponent()
                    .combine(TextFormat.BLUE + "Database: SQLite")).info();
            database = new SQLite(new File(getDataFolder(), "database.db"), this);
        }else {

            ConfigSection prop = config.get("SQLPROP", new ConfigSection() {{
                put("url", "localhost");
                put("port", "3306");
                put("username", "root");
                put("password", "");
                put("database", "econ");
            }});
            new TerminalComponent(getLogger(), new TextComponent()
                    .combine(TextFormat.BLUE + "Database: MySQL")).info();
            database = new MySQL(this, prop.getString("url", "localhost"),
                    prop.getString("port", "3306"),
                    prop.getString("username", "root"),
                    prop.getString("password", ""),
                    prop.getString("database", "econ")
            );
        }

        this.databaseCheck();

        economyManager = new EconomyManager(this);
        chatManager = new ChatManager(this);



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
