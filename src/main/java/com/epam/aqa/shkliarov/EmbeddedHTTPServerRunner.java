package com.epam.aqa.shkliarov;


import com.epam.aqa.shkliarov.server.ServerManager;
import com.sun.net.httpserver.HttpServer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class EmbeddedHTTPServerRunner {

    final static Logger LOGGER = LogManager.getLogger(EmbeddedHTTPServerRunner.class);

    public static void main(String[] args) throws IOException {
        LOGGER.info("Starting Embedded Jersey HTTPServer...\n");
        HttpServer HTTPServer = ServerManager.createHttpServer();
        HTTPServer.start();
        LOGGER.info("Embedded Jersey HTTPServer started successfully");
    }
}
