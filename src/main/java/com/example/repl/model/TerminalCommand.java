package com.example.repl.model;

public class TerminalCommand {

    private String instruction;
    private String containerId;

    public TerminalCommand(String instruction, String containerId) {
        this.instruction = instruction;
        this.containerId = containerId;
    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    public String getContainerId() {
        return containerId;
    }

    public void setContainerId(String containerId) {
        this.containerId = containerId;
    }
}
