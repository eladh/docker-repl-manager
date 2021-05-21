package com.example.repl.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

public class Machine {

    @JsonIgnore
    public List<Container> containers;
    private String id;
    private String image;
    private ContainerType type;
    private List<String> entrypoint;
    private List<String> repl;

    public Machine(String id, String image, ContainerType type, List<String> entrypoint, List<String> repl, List<Container> containers) {
        this.id = id;
        this.image = image;
        this.type = type;
        this.entrypoint = entrypoint;
        this.repl = repl;
        this.containers = containers;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public ContainerType getType() {
        return type;
    }

    public void setType(ContainerType type) {
        this.type = type;
    }

    public List<String> getEntrypoint() {
        return entrypoint;
    }

    public void setEntrypoint(List<String> entrypoint) {
        this.entrypoint = entrypoint;
    }

    public List<String> getRepl() {
        return repl;
    }

    public void setRepl(List<String> repl) {
        this.repl = repl;
    }

    public List<Container> getContainers() {
        return containers;
    }

    public void setContainers(List<Container> containers) {
        this.containers = containers;
    }
}
