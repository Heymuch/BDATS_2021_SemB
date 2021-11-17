package cz.upce.bdats.gui;

import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import cz.upce.bdats.autopujcovna.IAutopujcovna;

public class MyResources extends ResourceBundle {
    private Map<String, Object> data = new HashMap<>();

    public MyResources(IAutopujcovna autopujcovna) {
        data.put("autopujcovna", autopujcovna);
    }

    @Override
    protected Object handleGetObject(String key) {
        return data.get(key);
    }

    @Override
    public Enumeration<String> getKeys() {
        return Collections.enumeration(data.keySet());
    }

}
