package com.example.repl.model;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.Arrays;

public enum ContainerType {

    DOCKER("docker"), LIGHTSAIL("lightsail");

    private final String id;

    ContainerType(String containerType) {
        this.id = containerType;
    }

    @JsonCreator
    public static ContainerType forValues(String value) {
        return Arrays.stream(ContainerType.values())
                .filter(containerType -> containerType.id.equals(value))
                .findFirst().get();
    }

    public String getId() {
        return id;
    }
}
