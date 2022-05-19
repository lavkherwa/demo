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

        WireMockServer mockBookService = getWireMockServer();

        // Start the server
        mockBookService.start();

        // Stop the server when application context is closed
        applicationContext.addApplicationListener(applicationEvent -> {
            if (applicationEvent instanceof ContextClosedEvent) {
                mockBookService.stop();
            }
        });

        // Register our mockserver to the bean factory so that it can be autowired to our tests
        applicationContext
                .getBeanFactory()
                .registerSingleton("mockBookService", mockBookService);

        // Overwrite the base url for the external service in accordance with mock server
        TestPropertyValues
                .of(Map.of("bookdetails_service_url", mockBookService.baseUrl()))
                .applyTo(applicationContext);
    }

    private WireMockServer getWireMockServer() {
        return new WireMockServer(new WireMockConfiguration().dynamicPort());
    }


}
