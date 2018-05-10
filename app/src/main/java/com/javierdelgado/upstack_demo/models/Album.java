package com.javierdelgado.upstack_demo.models;

import com.javierdelgado.upstack_demo.adapters.base.SimpleListableItem;
import com.javierdelgado.upstack_demo.customComponents.Listable;

public class Album implements SimpleListableItem{
    private int id;
    private String title;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String getText() {
        return getTitle();
    }

    @Override
    public boolean enabled() {
        return true;
    }

    @Override
    public int compareTo(SimpleListableItem item) {
        // Unused
        return 0;
    }
}
