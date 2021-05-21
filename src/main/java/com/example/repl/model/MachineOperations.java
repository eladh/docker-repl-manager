package com.example.repl.model;

public interface MachineOperations {

    String create(Machine imageType) throws InterruptedException;

    String command(String command, String containerId) throws InterruptedException;

    Boolean delete(String containerId);
}

