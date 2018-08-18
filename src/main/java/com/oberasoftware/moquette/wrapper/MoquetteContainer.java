package com.oberasoftware.moquette.wrapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import java.io.IOException;

/**
 * @author Renze de Vries
 */
@SpringBootApplication
@ComponentScan
public class MoquetteContainer {
    private static final Logger LOG = LoggerFactory.getLogger(MoquetteContainer.class);

    public static void main(String[] args) throws IOException {
        SpringApplication application = new SpringApplication(MoquetteContainer.class);
        final ApplicationContext context = application.run(args);

        MoquetteServer server = context.getBean(MoquetteServer.class);
        server.start();

        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                server.stop();
                LOG.info("Moquette Server stopped");
            }
        });

    }
}
