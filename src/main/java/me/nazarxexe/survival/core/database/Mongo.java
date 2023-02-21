package me.nazarxexe.survival.core.database;

import com.dfsek.terra.lib.commons.lang3.NotImplementedException;

import java.sql.Connection;
import java.util.concurrent.CompletableFuture;

public class Mongo implements IDatabase {
    @Override
    public CompletableFuture<Connection> getConnection() {
        throw new NotImplementedException();
    }
}
