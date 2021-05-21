package com.example.repl.driver;

import com.example.repl.model.Machine;
import com.example.repl.model.MachineOperations;
import org.springframework.stereotype.Service;


@Service
public class LightsailDriver implements MachineOperations {

    @Override
    public String create(Machine imageType) {
        return null;
    }

    @Override
    public String command(String command, String containerId) {
        return null;
    }

    @Override
    public Boolean delete(String containerId) {
        return null;
    }
}
