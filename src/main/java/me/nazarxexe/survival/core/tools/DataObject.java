package me.nazarxexe.survival.core.tools;

import lombok.Getter;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;

@Getter
public class DataObject {

    final Object object;

    final HashMap<String, Object> fields;

    public DataObject (Object o) {

        this.object = o;

        this.fields = new HashMap<>();
        setFields(this.fields);
    }

    private void setFields(HashMap<String, Object> map) {

        if (object.getClass().getDeclaredFields().length == 0){
            return;
        }

        for (Field f : this.object.getClass().getDeclaredFields()){
            f.setAccessible(true);
            try {
                map.put(f.getName(), f.get(this.object));
            } catch (IllegalAccessException e) {  }
        }
    }


}
