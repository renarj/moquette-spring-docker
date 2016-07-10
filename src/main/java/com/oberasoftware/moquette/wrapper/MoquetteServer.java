package com.oberasoftware.moquette.wrapper;

import io.moquette.server.Server;
import io.moquette.server.config.MemoryConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Properties;

/**
 * @author Renze de Vries
 */
@Component
public class MoquetteServer {
    private static final Logger LOG = LoggerFactory.getLogger(MoquetteServer.class);

    @Value("${mqtt.serverport:1883}")
    private int serverPort;

    @Value("${mqtt.host:0.0.0.0}")
    private String host;

    @Value("${mqtt.websocket_port:8080}")
    private int websocketPort;

    private Server server;
    private MemoryConfig config;

    public void start() throws IOException {
        LOG.info("Starting MQTT on host: {} and port: {} with websocket port: {}", host, serverPort, websocketPort);

        config = new MemoryConfig(new Properties());
        config.setProperty("port", Integer.toString(serverPort));
        config.setProperty("websocket_port", Integer.toString(websocketPort));
        config.setProperty("host", host);
        config.setProperty("authenticator_class", SpringAuthenticationWrapper.class.getName());
        config.setProperty("authorizator_class", SpringAuthorizationWrapper.class.getName());

        server = new Server();
        server.startServer(config);
        LOG.info("Moquette started successfully");
    }

    public void stop() {
        server.stopServer();
    }
}
