package me.nazarxexe.survival.core.test;

import me.nazarxexe.survival.core.Core;
import org.junit.Assert;
import org.junit.Test;

public class CoreTest {

    @Test
    public void parseTest() {
        Core core = new Core();
        Assert.assertEquals(core.parse(2023), "2023");
        Assert.assertEquals(core.parse(2023.1212), "2023.1212");
    }
}
