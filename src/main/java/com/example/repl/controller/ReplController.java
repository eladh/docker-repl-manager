package com.example.repl.controller;


import com.example.repl.manager.ConfigService;
import com.example.repl.manager.ReplManager;
import com.example.repl.model.*;
import com.google.common.base.Preconditions;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Date;
import java.util.Map;

@RestController
public class ReplController {

    private final ConfigService configService;
    private final ReplManager replManager;
    private final SimpMessagingTemplate simpMessagingTemplate;

    public ReplController(ConfigService configService, ReplManager replManager, SimpMessagingTemplate simpMessagingTemplate) {
        this.configService = configService;
        this.replManager = replManager;
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @GetMapping("/machines")
    public Map<String, Machine> machines() {
        return configService.getConfig().getMachines();
    }

    @GetMapping("/containers")
    public Collection<Container> containers() {
        return replManager.getContainerOpenTerminals();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Container create(@RequestBody CreateContainer resource) {
        Preconditions.checkNotNull(resource);
        // TODO: 07/04/2021 send to web socket response about that new container just created
        // TODO: 08/04/2021 Handle container not created exception
        return replManager.create(resource).orElse(null);
    }

    @MessageMapping("/terminal/send")
    public void send(TerminalCommand command, @Header("simpSessionId") String sessionId) {
        // TODO: 07/04/2021 use session id to register new connection to the container terminal
        simpMessagingTemplate.convertAndSend("/topic/terminal-response/" + command.getContainerId(),
                new TerminalResponse(new Date().getTime(), "received - " + command.getInstruction()));
    }
}
