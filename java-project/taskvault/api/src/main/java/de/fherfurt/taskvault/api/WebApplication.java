package de.fherfurt.taskvault.api;

import de.fherfurt.taskvault.api.resources.BaseResource;
import de.fherfurt.taskvault.api.resources.CategorysResource;
import de.fherfurt.taskvault.api.resources.MainTasksResource;
import de.fherfurt.taskvault.data.core.DataController;

import org.eclipse.jetty.server.Server;
import org.glassfish.jersey.jetty.JettyHttpContainerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;

public class WebApplication {
    public static final String BASE_URI = "http://0.0.0.0:8080/";

    public static Server startServer() {
        final ResourceConfig config = new ResourceConfig(
                BaseResource.class,
                CategorysResource.class,
                MainTasksResource.class);
        Server server = JettyHttpContainerFactory.createServer(URI.create(BASE_URI), config);

        return server;
    }

    public static void main(String[] args) {
        Logger LOG = LoggerFactory.getLogger(WebApplication.class);

        try {
            DataController.getInstance();
            final Server server = startServer();

            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                try {
                    LOG.info("Shutting down the application...");
                    server.stop();
                    LOG.info("Done, exit.");
                } catch (Exception e) {
                    LOG.error(null, e);
                }
            }));

            LOG.info("Application started. -- Stop the application using CTRL+C\n");

            Thread.currentThread().join();
        } catch (InterruptedException ex) {
            LOG.error(null, ex);
        }
    }
}
