package me.nazarxexe.survival.core.test;

import me.nazarxexe.survival.core.economy.Pocket;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;

public class EconomyUnit {


    @Test
    public void testPocket() {
        Pocket pocket = new Pocket("econ", UUID.randomUUID());
        pocket.addBalance(10000L);
        Assert.assertEquals(10000L, pocket.getBalance());
        pocket.addBalance(20000L);
        Assert.assertEquals(30000L, pocket.getBalance());
        pocket.decrementBalance(10000L);
        Assert.assertEquals(20000L, pocket.getBalance());
        Assert.assertEquals("econ", pocket.getName());
    }



}
