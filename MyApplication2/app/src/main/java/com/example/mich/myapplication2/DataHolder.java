package com.example.mich.myapplication2;

import java.util.HashMap;
import java.util.Map;

public class DataHolder {
    private static DataHolder ourInstance = new DataHolder();

    private final Map<String, Object> extras = new HashMap<>();

    private DataHolder() {
    }

    public static DataHolder getInstance() {
        return ourInstance;
    }

    public void putExtra(String name, Object object) {
        extras.put(name, object);
    }

    public Object getExtra(String name) {
        return extras.get(name);
    }

    public boolean hasExtra(String name) {
        return extras.containsKey(name);
    }

    public void clear() {
        extras.clear();
    }
}
