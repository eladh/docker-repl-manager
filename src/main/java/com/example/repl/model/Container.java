package com.example.repl.model;

import com.google.common.collect.Sets;

import java.time.LocalDateTime;
import java.util.Set;

public class Container {
    private String id;
    private Machine machine;
    private Set<String> activeConnections;
    private LocalDateTime lastActiveTime;
    private ContainerState state;

    public Container(String id, Machine machine) {
        this.id = id;
        this.machine = machine;
        this.activeConnections = Sets.newHashSet();
        this.state = ContainerState.CREATED;
    }

    public Set<String> getActiveConnections() {
        return activeConnections;
    }

    public LocalDateTime getLastActiveTime() {
        return lastActiveTime;
    }

    public ContainerState getState() {
        return state;
    }

    public void setState(ContainerState state) {
        this.state = state;
    }
}
