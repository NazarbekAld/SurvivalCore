package me.nazarxexe.survival.core.economy;

import cn.nukkit.scheduler.AsyncTask;
import cn.nukkit.scheduler.TaskHandler;
import cn.nukkit.utils.TextFormat;
import me.nazarxexe.survival.core.Core;
import me.nazarxexe.survival.core.tools.TerminalComponent;
import me.nazarxexe.survival.core.tools.TextComponent;
import me.nazarxexe.survival.core.tools.tasktype.TaskType;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.CompletableFuture;

public class EconomyManager {

    private final Core core;




    public EconomyManager(@NotNull Core core)
    {
        this.core = core;
        databaseSetup();
    }


    /**
     * <h3>SAVE POCKET</h3>
     * Saves pocket to database.
     *
     * @exception SQLException saving failure
     * @param pocket Pocket your want to save on database.
     * @return {@link cn.nukkit.scheduler.TaskHandler}
     */

    public TaskHandler savePocket(@NotNull Pocket pocket){
        return core.getServer().getScheduler().scheduleAsyncTask(core, new AsyncTask() {
            @Override
            public void onRun() {
                core.getDatabase().getConnection().thenAcceptAsync((connection -> {
                    try {

                        PreparedStatement pre = connection.prepareStatement("SELECT * FROM `Pockets` WHERE `name`=? AND `owner`=?;");
                        pre.setString(1, pocket.getName());
                        pre.setString(2, pocket.getOwner().toString());
                        ResultSet exist = pre.executeQuery();
                        if (exist.next()){
                            PreparedStatement uppre = connection.prepareStatement("UPDATE `Pockets` SET `name`=?,`owner`=?,`members`=?,`balance`=? WHERE `name`=? AND `owner`=?;");
                            // SET
                            uppre.setString(1, pocket.getName());
                            uppre.setString(2, pocket.getOwner().toString());
                            if (pocket.getAccessMembers().isEmpty()){
                                List<String> members = new ArrayList<>();
                                pocket.getAccessMembers().forEach((access) -> members.add(access.toString()));
                                uppre.setString(3, String.join(" ", members));
                            }else {
                                uppre.setString(3, "");
                            }

                            uppre.setLong(4, pocket.getBalance());
                            // WHERE
                            uppre.setString(5, pocket.getName());
                            uppre.setString(6, pocket.getOwner().toString());
                            uppre.executeUpdate();
                        }else{
                            PreparedStatement inspre = connection.prepareStatement("INSERT INTO `Pockets`(`name`, `owner`, `members`, `balance`) VALUES (?,?,?,?);");
                            // INSERT INTO
                            inspre.setString(1, pocket.getName());
                            inspre.setString(2, pocket.getOwner().toString());
                            if (pocket.getAccessMembers().isEmpty()){
                                List<String> members = new ArrayList<>();
                                pocket.getAccessMembers().forEach((access) -> members.add(access.toString()));
                                inspre.setString(3, String.join(" ", members));
                            }else {
                                inspre.setString(3, "");
                            }
                            inspre.setLong(4, pocket.getBalance());
                            inspre.executeUpdate();
                        }
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

    /**
    * <h3>LOAD POCKET</h3>
     * Loads pocket from database.
     * <br />
     * @param name Name of your pocket
     * @param owner Owner of pocket.
     * @exception SQLException loading failure returns <b>null</b>
     * @return {@link me.nazarxexe.survival.core.economy.Pocket} in {@link java.util.concurrent.CompletableFuture}.
     * @see <a href="https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/CompletableFuture.html">CompletableFuture docs</a>
    *
     */
    public @Nullable CompletableFuture<Pocket> loadPocket(@NotNull String name, @NotNull UUID owner){
        return core.getDatabase().getConnection().thenApplyAsync(connection -> {
            PreparedStatement pre;
            try {
                pre = connection.prepareStatement("SELECT * FROM `Pockets` WHERE `name`=? AND `owner`=?;");
                pre.setString(1, name);
                pre.setString(2, owner.toString());
                ResultSet resultSet = pre.executeQuery();
                if (!(resultSet.next())) return null;
                List<UUID> memberUUIDs = new ArrayList<>();
                if ( !(resultSet.getString("members").equals(""))){
                    String[] members = resultSet.getString("members").split(" ");
                    Arrays.stream(members).toList().forEach((member) -> memberUUIDs.add(UUID.fromString(member)));
                }

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


    /**
     *
     * <h3>PUSH TRANSACTION</h3>
     *
     * @param transaction {@link me.nazarxexe.survival.core.economy.Transaction} object.
     * @return {@link cn.nukkit.scheduler.TaskHandler}
     */
    public TaskHandler pushTransaction(@NotNull Transaction transaction){
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

    /**
     *
     * <h3>PUSH TRANSACTION</h3>
     * @param transaction {@link me.nazarxexe.survival.core.economy.Transaction} object.
     * @param taskType {@link me.nazarxexe.survival.core.tools.tasktype.TaskType} enum.
     * @return {@link cn.nukkit.scheduler.TaskHandler}
     */
    public TaskHandler pushTransaction(@NotNull TaskType taskType, @NotNull Transaction transaction){
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

                    })).join();
                }
            });
        return pushTransaction(transaction);
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
                        new TerminalComponent(core.getLogger(), new TextComponent()
                                .combine(TextFormat.RED)
                                .combine("SQL execution error.")
                                .add(new TextComponent()
                                        .combine(TextFormat.RED)
                                        .combine("" + e))

                        ).warn();
                    }
                })).join();
            }
        });

    }



}
