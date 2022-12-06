package com.solvit.mobile.model;

public class NotificationGeneric<T> {
    private Class<T> clazz;

    public NotificationGeneric(Class<T> clazz) {
        this.clazz = clazz;
    }

    public T getInstance() throws InstantiationException, IllegalAccessException {
        return clazz.newInstance();
    }
}

