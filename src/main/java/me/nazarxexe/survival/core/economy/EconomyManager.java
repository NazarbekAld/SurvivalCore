package me.nazarxexe.survival.core.economy;

import cn.nukkit.scheduler.AsyncTask;
import cn.nukkit.scheduler.TaskHandler;
import cn.nukkit.utils.TextFormat;
import me.nazarxexe.survival.core.Core;
import me.nazarxexe.survival.core.tools.TerminalComponent;
import me.nazarxexe.survival.core.tools.TextComponent;
import me.nazarxexe.survival.core.tools.tasktype.TaskType;

import javax.annotation.Nullable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.CompletableFuture;

public class EconomyManager {

    private final Core core;

    public List<Pocket> getCachedPockets() {
        return cachedPockets;
    }

    private final List<Pocket> cachedPockets;

    public EconomyManager(Core core)
    {
        this.core = core;
        this.cachedPockets = new ArrayList<>();
        databaseSetup();
    }


    public TaskHandler savePocket(Pocket pocket){
        return core.getServer().getScheduler().scheduleAsyncTask(core, new AsyncTask() {
            @Override
            public void onRun() {
                core.getDatabase().getConnection().thenAcceptAsync((connection -> {
                    try {

                        PreparedStatement pre;

                        pre = connection.prepareStatement("SELECT * FROM `Pockets` WHERE `name`=? AND `owner`=?;");
                        pre.setString(1, pocket.getName());
                        pre.setString(2, pocket.getOwner().toString());
                        if (pre.executeQuery().next()){
                            pre.close();
                            pre = connection.prepareStatement("UPDATE `Pockets` SET `name`=?,`owner`=?,`members`=?,`balance`=? WHERE `name`=? AND `owner`=?;");
                            // SET
                            pre.setString(1, pocket.getName());
                            pre.setString(2, pocket.getOwner().toString());
                            List<String> members = new ArrayList<>();
                            pocket.getAccessMembers().forEach((access) -> members.add(access.toString()));
                            pre.setString(3, String.join(" ", members));
                            pre.setLong(4, pocket.getBalance());
                            // WHERE
                            pre.setString(5, pocket.getName());
                            pre.setString(6, pocket.getOwner().toString());
                            pre.executeUpdate();
                            return;
                        }
                        pre.close();
                        pre = connection.prepareStatement("INSERT INTO `Pockets`(`name`, `owner`, `members`, `balance`) VALUES (?,?,?,?);");
                        // INSERT INTO
                        pre.setString(1, pocket.getName());
                        pre.setString(2, pocket.getOwner().toString());
                        List<String> members = new ArrayList<>();
                        pocket.getAccessMembers().forEach((access) -> members.add(access.toString()));
                        pre.setString(3, String.join(" ", members));
                        pre.setLong(4, pocket.getBalance());
                        pre.executeUpdate();
                    } catch (SQLException e) {
                        new TerminalComponent(core.getLogger(),
                                new TextComponent()
                                        .combine(TextComponent.coloredText(TextFormat.RED, "SQL query error!")
                                                .add(">>>" + e)
                                        ))
                                .warn();
                    }
                })).join();
            }
        });

    }


    public @Nullable CompletableFuture<Pocket> loadPocket(String name, UUID owner){
        return core.getDatabase().getConnection().thenApplyAsync(connection -> {
            PreparedStatement pre;
            try {
                pre = connection.prepareStatement("SELECT * FROM `Pockets` WHERE `name`=? AND `owner`=?;");
                pre.setString(1, name);
                pre.setString(2, owner.toString());
                ResultSet resultSet = pre.executeQuery();

                if (!(resultSet.next())) return null;
                String[] members = resultSet.getString("members").split(" ");
                List<UUID> memberUUIDs = new ArrayList<>();
                Arrays.stream(members).toList().forEach((member) -> memberUUIDs.add(UUID.fromString(member)));
                return new Pocket(resultSet.getString("name"),
                        UUID.fromString(resultSet.getString("owner")),
                        memberUUIDs,
                        resultSet.getLong("balance"));

            } catch (SQLException e) {
                new TerminalComponent(core.getLogger(),
                        new TextComponent()
                                .combine(TextComponent.coloredText(TextFormat.RED, "SQL query error!")
                                        .add(">>>" + e)
                                ))
                        .warn();
                return null;
            }
        });
    }

    public TaskHandler pushTransaction(Transaction transaction){
        return core.getServer().getScheduler().scheduleAsyncTask(core, new AsyncTask() {
            @Override
            public void onRun() {
                core.getDatabase().getConnection().thenAcceptAsync((connection -> {

                    transaction.getPockets().forEach((pocket) -> {
                        savePocket(pocket);
                    });

                })).join();
            }
        });
    }
    public TaskHandler pushTransaction(TaskType taskType, Transaction transaction){
        if (taskType == TaskType.ASYNC)
            return pushTransaction(transaction);
        if (taskType == TaskType.SYNC)
            return core.getServer().getScheduler().scheduleTask(core, new Runnable() {
                @Override
                public void run() {
                    core.getDatabase().getConnection().thenAcceptAsync((connection -> {

                        transaction.getPockets().forEach((pocket) -> {
                            savePocket(pocket);
                        });

                    }));
                }
            });
        return pushTransaction(transaction);
    }

    public List<Pocket> getPocket(UUID owner){
        List<Pocket> result = new LinkedList<>();
        this.cachedPockets.forEach((pocket -> {
            if (!(pocket.getOwner().equals(owner))) return;
            result.add(pocket);
        }));
        return result;
    }

    public List<Pocket> getPocket(String name){
        List<Pocket> result = new LinkedList<>();
        this.cachedPockets.forEach((pocket -> {
            if (!(pocket.getName().equals(name))) return;
            result.add(pocket);
        }));
        return result;
    }
    public List<Pocket> getPocket(long balance){
        List<Pocket> result = new LinkedList<>();
        this.cachedPockets.forEach((pocket -> {
            if (!(pocket.getBalance() == balance)) return;
            result.add(pocket);
        }));
        return result;
    }


    private void databaseSetup(){

        core.getServer().getScheduler().scheduleAsyncTask(core, new AsyncTask() {
            @Override
            public void onRun() {
                core.getDatabase().getConnection().thenAcceptAsync((connection -> {
                    try {
                        connection.createStatement().executeUpdate(String.join("",
                                "CREATE TABLE if not exists `Pockets` ",
                                "( `name` TEXT NOT NULL , `owner` TEXT NOT NULL , ",
                                "`members` TEXT NOT NULL , `balance` BIGINT NOT NULL );"
                        ));
                    } catch (SQLException e) {
                        core.getLogger().warning(String.join("\n",
                                "SQLLite execution FAILED. Please contact creator of plugin to fix the issue.",
                                "Error: " + e
                        ));
                    }
                })).join();
            }
        });

    }



}
