package me.nazarxexe.survival.core.database;

import java.sql.Connection;
import java.util.concurrent.CompletableFuture;


public interface IDatabase {

    CompletableFuture<Connection> getConnection();


}
