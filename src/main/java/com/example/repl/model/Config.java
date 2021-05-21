package com.example.repl.model;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Config {
    private final Map<String, Machine> machines;
    private String registry;
    private String host;
    private int gc;

    public Config(String registry, String host, int gc, List<Machine> machines) {
        this.registry = registry;
        this.host = host;
        this.gc = gc;
        this.machines = machines.stream().collect(Collectors.toMap(Machine::getId, machine -> machine));
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getRegistry() {
        return registry;
    }

    public void setRegistry(String registry) {
        this.registry = registry;
    }

    public Map<String, Machine> getMachines() {
        return machines;
    }
}
