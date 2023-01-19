package me.nazarxexe.survival.core.economy;

import lombok.Getter;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@Getter
public class Pocket {


    private final String name;
    private final UUID owner;
    private List<UUID> accessMembers;



    long balance;


    public Pocket(String name, UUID owner) {
        this.name = name;
        this.owner = owner;
        this.accessMembers = new LinkedList<>();
        this.balance = 0;
    }
    public Pocket(String name, UUID owner, List<UUID> accessMembers) {
        this.name = name;
        this.owner = owner;
        this.accessMembers = accessMembers;
        this.balance = 0;
    }
    public Pocket(String name, UUID owner, long balance) {
        this.name = name;
        this.owner = owner;
        this.accessMembers = new LinkedList<>();
        this.balance = balance;
    }
    public Pocket(String name, UUID owner, List<UUID> accessMembers, long balance) {
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
