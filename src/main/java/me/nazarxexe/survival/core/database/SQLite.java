package me.nazarxexe.survival.core.database;

import cn.nukkit.utils.TextFormat;
import lombok.Getter;
import me.nazarxexe.survival.core.Core;
import me.nazarxexe.survival.core.tools.TerminalComponent;
import me.nazarxexe.survival.core.tools.TextComponent;

import javax.annotation.Nullable;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.CompletableFuture;

@Getter
public class SQLite implements IDatabase {

    private final File databaseFile;
    private final Core core;



    public SQLite(File databaseFile, Core core) {
        this.databaseFile = databaseFile;
        if (!(databaseFile.exists())) {
            try {
                databaseFile.createNewFile();
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
                return DriverManager.getConnection(String.join("",
                        "jdbc:sqlite:",
                        databaseFile.getAbsolutePath()
                ));
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
