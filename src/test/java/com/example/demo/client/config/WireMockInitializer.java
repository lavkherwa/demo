package com.example.demo.client.config;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.event.ContextClosedEvent;

import java.util.Map;

public class WireMockInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {

        WireMockServer wireMockServer = new WireMockServer(new WireMockConfiguration().dynamicPort());

        // Start the server
        wireMockServer.start();

        // Stop the server when application context is closed
        applicationContext.addApplicationListener(applicationEvent -> {
            if (applicationEvent instanceof ContextClosedEvent) {
                wireMockServer.stop();
            }
        });

        // Register our mockserver to the bean factory so that it can be autowired to our tests
        applicationContext
                .getBeanFactory()
                .registerSingleton("wireMockServer", wireMockServer);

        // Overwrite the base url for the external service in accordance with mock server
        TestPropertyValues
                .of(Map.of("bookdetails_service_url", wireMockServer.baseUrl()))
                .applyTo(applicationContext);

    }
}
