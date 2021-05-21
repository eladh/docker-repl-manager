package com.example.repl.driver;

import com.example.repl.manager.ConfigService;
import com.example.repl.model.Machine;
import com.example.repl.model.MachineOperations;
import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.command.ExecCreateCmdResponse;
import com.github.dockerjava.api.command.PullImageResultCallback;
import com.github.dockerjava.api.command.WaitContainerResultCallback;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientBuilder;
import com.github.dockerjava.core.command.ExecStartResultCallback;
import com.github.dockerjava.okhttp.OkHttpDockerCmdExecFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.concurrent.TimeUnit;


@Service
public class DockerDriver implements MachineOperations {

    private final ConfigService configService;
    private DockerClient dockerClient;

    public DockerDriver(ConfigService configService) {
        this.configService = configService;
    }

    @PostConstruct
    public void init() {
        dockerClient = DockerClientBuilder.getInstance(DefaultDockerClientConfig.createDefaultConfigBuilder()
                .withDockerHost(configService.getConfig().getHost())
                .build()).withDockerCmdExecFactory(new OkHttpDockerCmdExecFactory())
                .build();
    }

    @Override
    public String create(Machine machine) throws InterruptedException {
        dockerClient.pullImageCmd(machine.getImage())
                .withRegistry(configService.getConfig().getRegistry())
                .exec(new PullImageResultCallback())
                .awaitCompletion(60, TimeUnit.SECONDS);

        CreateContainerResponse container = dockerClient.createContainerCmd(machine.getImage())
                .withCmd(machine.getEntrypoint())
                .withName(machine.getImage() + "-" + new Date().getTime()).exec();

        dockerClient.startContainerCmd(container.getId()).exec();
        dockerClient.waitContainerCmd(container.getId()).exec(new WaitContainerResultCallback()).awaitStatusCode();

        return container.getId();
    }

    @Override
    public String command(String command, String containerId) throws InterruptedException {
        InputStream stdin = new ByteArrayInputStream((command + "\n").getBytes(StandardCharsets.UTF_8));
        ByteArrayOutputStream stderr = new ByteArrayOutputStream();

        ByteArrayOutputStream stdout = new ByteArrayOutputStream();

        ExecCreateCmdResponse execCreateCmdResponse = dockerClient.execCreateCmd(containerId)
                .withAttachStdout(true)
                .withAttachStderr(true)
                .withTty(true)
                .withAttachStdin(true)
                .withCmd("node")
                .exec();

        dockerClient.execStartCmd(execCreateCmdResponse.getId())
                .withDetach(false)
                .withTty(true)
                .withStdIn(stdin)
                .exec(new ExecStartResultCallback(stdout, stderr))
                .awaitCompletion(1, TimeUnit.SECONDS);

        return stdout.toString(StandardCharsets.UTF_8);
    }

    @Override
    public Boolean delete(String containerId) {
        dockerClient.removeContainerCmd(containerId).withForce(true).exec();
        return true;
    }
}
