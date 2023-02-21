package me.nazarxexe.survival.core.database;

import cn.nukkit.utils.TextFormat;
import lombok.Getter;
import me.nazarxexe.survival.core.Core;
import me.nazarxexe.survival.core.tools.TerminalComponent;
import me.nazarxexe.survival.core.tools.text.TextComponent;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.CompletableFuture;

@Getter
@SuppressWarnings({ "unused" })
public class SQLite implements IDatabase {

    private final File databaseFile;
    private final Core core;
    private Connection connection;


    public SQLite(@NotNull File databaseFile, @NotNull Core core) {

        this.databaseFile = databaseFile;
        if (!(databaseFile.exists())) {
            try {
                boolean success = databaseFile.createNewFile();
                new TerminalComponent(core.getLogger(), new TextComponent()
                        .combine(TextFormat.GREEN)
                        .combine("Database file ")
                        .combine(this.databaseFile.getName())
                        .combine(" created.")
                ).info();
            } catch (IOException e) {
                new TerminalComponent(core.getLogger(),
                        new TextComponent()
                                .combine(TextComponent.coloredText(TextFormat.RED, "Database creation error!")
                                        .add(">>>" + e)
                                ))
                        .critical();
            }
        }
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
                        "jdbc:sqlite:",
                        databaseFile.getAbsolutePath()
                ));
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
