package com.example.repl.manager;

import com.example.repl.model.Config;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;

@Service
public class ConfigService {

    private Config config;

    @PostConstruct
    public void init() throws IOException {
        config = loadConfig();
    }

    private Config loadConfig() throws IOException {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory()).findAndRegisterModules();
        return mapper.readValue(new File("src/main/resources/machineTypes.yaml"), Config.class);
    }

    public Config getConfig() {
        return config;
    }
}
