package com.epam.aqa.shkliarov.server;

import com.epam.aqa.shkliarov.service.CarService;
import com.sun.net.httpserver.HttpServer;
import org.glassfish.jersey.jdkhttp.JdkHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.core.UriBuilder;
import java.io.IOException;
import java.net.InetAddress;
import java.net.URI;
import java.net.UnknownHostException;

public class ServerManager {
    private static final Class RESOURCE_CLASS = CarService.class;
    private static final int PORT = 8183;

    public static HttpServer createHttpServer() throws IOException {
        ResourceConfig resourceConfig = new ResourceConfig(RESOURCE_CLASS);
        return JdkHttpServerFactory.createHttpServer(getURI(), resourceConfig);
    }

    public static URI getURI() {
        return UriBuilder.fromUri("http://" + getHostName() + "/").port(PORT).build();
    }

    private static String getHostName() {
        String hostName = "localhost";
        try {
            hostName = InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return hostName;
    }

}
