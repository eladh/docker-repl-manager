package com.example.repl.manager;

import com.example.repl.driver.DockerDriver;
import com.example.repl.model.*;
import com.google.common.collect.Maps;
import org.glassfish.jersey.internal.guava.Sets;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.Set;


@Service
public class ReplManager {

    private final ConfigService configService;
    private final DockerDriver dockerDriver;
    private final Map<Machine, Set<Container>> machines = Maps.newHashMap();
    private final Map<String, Container> containers = Maps.newHashMap();

    public ReplManager(ConfigService configService, DockerDriver dockerDriver) {
        this.configService = configService;
        this.dockerDriver = dockerDriver;
    }

    @PostConstruct
    public void init() {
        for (Machine machine : configService.getConfig().getMachines().values()) {
            machines.put(machine, Sets.newHashSet());
        }
    }

    @Scheduled(fixedDelay = 1000)
    public void scheduleContainerGC() {
        for (Container container : containers.values()) {
            if (container.getState() != ContainerState.DELETED &&
                    ChronoUnit.SECONDS.between(LocalDateTime.now(), container.getLastActiveTime()) > 5) {
                container.setState(ContainerState.DELETED);
                // TODO: 07/04/2021 kill container
            }
        }
    }

    public Collection<Container> getContainerOpenTerminals() {
        return containers.values();
    }

    public Optional<Container> create(CreateContainer createContainer) {
        Machine machine = configService.getConfig().getMachines().get(createContainer.getMachineType());
        if (machine.getType() == ContainerType.DOCKER) {
            try {
                String containerId = dockerDriver.create(machine);
                Container container = new Container(containerId, machine);
                machines.get(machine).add(container);
                containers.put(containerId, container);
                return Optional.of(container);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return Optional.empty();
    }
}
