package me.nazarxexe.survival.core.economy;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Transaction {

    final List<Pocket> pockets;
    final String transactionID;

    public Transaction(List<Pocket> pockets, String transactionID){
        this.pockets = pockets;
        this.transactionID = transactionID;
    }
    public Transaction(String transactionID){
        this.pockets = new ArrayList<>();
        this.transactionID = transactionID;
    }


}
