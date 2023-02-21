package me.nazarxexe.survival.core.database;

import cn.nukkit.utils.TextFormat;
import me.nazarxexe.survival.core.Core;
import me.nazarxexe.survival.core.tools.TerminalComponent;
import me.nazarxexe.survival.core.tools.text.TextComponent;
import org.jetbrains.annotations.Nullable;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.CompletableFuture;

public class MySQL implements IDatabase{

    final String url;
    final String port;
    final String username;
    final String password;
    final String database;
    final Core core;

    Connection connection;

    public MySQL(Core core, String url, String port, String username, String password, String database) {
        this.url = url;
        this.port = port;
        this.username = username;
        this.password = password;
        this.database = database;
        this.core = core;
    }


    @Override
    public @Nullable CompletableFuture<Connection> getConnection() {
        return CompletableFuture.supplyAsync(() -> {
            try {

                if (connection != null && !(connection.isClosed())){
                    return connection;
                }

                this.connection = DriverManager.getConnection(String.join("",
                        "jdbc:mysql://",
                        url,
                        ":",
                        port,
                        "/",
                        database
                ), username, password);
                return connection;
            } catch (SQLException e) {
                new TerminalComponent(core.getLogger(),
                        new TextComponent()
                                .combine(TextComponent.coloredText(TextFormat.RED, "SQL connection error!")
                                        .add(">>>" + e)
                                ))
                        .critical();
                return null;
            }
        });
    }
}
