package com.piersyp.dynasors.example.server;

import com.codahale.metrics.health.HealthCheck;
import com.google.inject.*;
import com.piersyp.dynasors.example.common.ExampleService;
import io.dropwizard.Application;
import io.dropwizard.Configuration;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class ExampleServerApplication extends Application<Configuration> {

    private Bootstrap<Configuration> bootstrap;
    private Injector injector;

    //Needs to be public for the app rules in testing
        public static void main(String[] args) throws Exception {
            new ExampleServerApplication().run(args);
        }


        @Override
        public String getName() {
            return "hello-world";
        }

        @Override
        public void initialize(Bootstrap<Configuration> bootstrap) {
        }

        @Override
        public void run(Configuration configuration, Environment environment) {


            environment.healthChecks().register("simple", new HealthCheck() {
                @Override
                protected Result check() throws Exception {
                    return Result.healthy();
                }
            });

            environment.jersey().register(createInjector(configuration).getInstance(ExampleService.class));

        }

    private Injector createInjector(final Configuration conf) {
        return Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {
                bind(ExampleService.class).to(ExampleServiceResource.class).in(Scopes.SINGLETON);
            }
        });

    }
    }
