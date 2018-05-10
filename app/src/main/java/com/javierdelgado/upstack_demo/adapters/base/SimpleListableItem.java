package com.javierdelgado.upstack_demo.adapters.base;

public interface SimpleListableItem {
    String getText();
    boolean enabled();
    int compareTo(SimpleListableItem item);
}
