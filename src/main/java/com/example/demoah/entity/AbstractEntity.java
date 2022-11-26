package com.example.demoah.entity;

public abstract class AbstractEntity<KEY> {
    private KEY id;

    public KEY getId() {
        return this.id;
    }
}
