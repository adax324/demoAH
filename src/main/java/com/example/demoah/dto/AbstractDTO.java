package com.example.demoah.dto;

public abstract class AbstractDTO<KEY> {
    private KEY id;

    public KEY getId() {
        return this.id;
    }
}
