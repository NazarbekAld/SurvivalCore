package me.nazarxexe.survival.core.test;

import me.nazarxexe.survival.core.tools.DataObject;
import me.nazarxexe.survival.core.tools.text.TextChangeEvent;
import me.nazarxexe.survival.core.tools.text.TextComponent;
import me.nazarxexe.survival.core.tools.text.TextComponentRule;
import org.junit.Assert;
import org.junit.Test;

public class UtilsTest {

    @Test
    @SuppressWarnings({ "unused" })
    public void dataobjTest() {

        long nano = System.nanoTime();
        long mili = System.currentTimeMillis();

        DataObject dobj = new DataObject(new Object() {

           String a = "HELLO WORLD";
           Long b = nano;
           Integer c = 10;

           Long d = mili;

        });

        Assert.assertEquals((String) dobj.getFields().get("a"), "HELLO WORLD");
        Assert.assertEquals((long) dobj.getFields().get("b"), nano);
        Assert.assertEquals((int) dobj.getFields().get("c"), 10);
        Assert.assertEquals((long) dobj.getFields().get("d"), mili);
    }

    @Test
    public void textComponentTest() {

        TextComponent textComponent = new TextComponent("HELLO");
        textComponent.add_RULE_NoES();
        textComponent.combine("\nWORLD!");

        Assert.assertEquals(textComponent.getText(), "HELLO WORLD!");

        textComponent = new TextComponent();
        /**
         *
         * Makes " " to ", "
         *
         */
        textComponent.getRules().add(new TextComponentRule() {
            @Override
            public void textchangeEvent(TextChangeEvent event) {
                event.setText(new TextComponent(event.getText().getText().replace(" ", ", ")));
            }
        });

        textComponent.setText("Hello World!");
        Assert.assertEquals(textComponent.getText(), "Hello, World!");


        /**
         *
         * No bad word example:
         *
         */
        textComponent = new TextComponent("Motherfucker shut your asshole");

        textComponent.getRules().add(new TextComponentRule() {
            @Override
            public void textchangeEvent(TextChangeEvent event) {

                String[] bad_words = { "Motherfucker", "asshole", "fuck", "Fuck" };

                for (String i : bad_words) {

                    event.setText(new TextComponent(event.getText().getText().replace(i, "^-^")));

                }
            }
        });

        textComponent.triggerRules();
        Assert.assertEquals(textComponent.getText(), "^-^ shut your ^-^");

        textComponent.setText("Fuck fuck fuck youuuu!");
        textComponent.triggerRules();
        Assert.assertEquals(textComponent.getText(), "^-^ ^-^ ^-^ youuuu!");


    }
}
