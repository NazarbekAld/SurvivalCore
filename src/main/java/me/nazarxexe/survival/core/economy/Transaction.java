package me.nazarxexe.survival.core.economy;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Transaction {

    final List<Pocket> pockets;
    final String transactionID;

    public Transaction(@NotNull List<Pocket> pockets, @NotNull String transactionID){
        this.pockets = pockets;
        this.transactionID = transactionID;
    }
    public Transaction(@NotNull String transactionID){
        this.pockets = new ArrayList<>();
        this.transactionID = transactionID;
    }


}
