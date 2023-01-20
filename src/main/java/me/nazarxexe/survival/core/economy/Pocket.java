package me.nazarxexe.survival.core.economy;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@Getter
public class Pocket {


    private final String name;
    private final UUID owner;
    private final List<UUID> accessMembers;



    long balance;


    public Pocket(@NotNull String name, @NotNull UUID owner) {
        this.name = name;
        this.owner = owner;
        this.accessMembers = new LinkedList<>();
        this.balance = 0;
    }
    public Pocket(@NotNull String name, @NotNull UUID owner, @NotNull List<UUID> accessMembers) {
        this.name = name;
        this.owner = owner;
        this.accessMembers = accessMembers;
        this.balance = 0;
    }
    public Pocket(@NotNull String name, @NotNull UUID owner, long balance) {
        this.name = name;
        this.owner = owner;
        this.accessMembers = new LinkedList<>();
        this.balance = balance;
    }
    public Pocket(@NotNull String name, @NotNull UUID owner, @NotNull List<UUID> accessMembers, long balance) {
        this.name = name;
        this.owner = owner;
        this.accessMembers = accessMembers;
        this.balance = balance;
    }

    public void setBalance(long balance){
        this.balance = balance;
    }
    public void addBalance(long balance){
        this.balance += balance;
    }
    public void decrementBalance(long balance){
        this.balance -= balance;
    }

}
