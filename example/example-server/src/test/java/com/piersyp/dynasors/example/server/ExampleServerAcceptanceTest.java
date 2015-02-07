package com.piersyp.dynasors.example.server;

import com.piersyp.dynasors.example.client.ClientFactory;
import com.piersyp.dynasors.example.common.ExampleService;
import com.sun.jersey.api.client.Client;
import io.dropwizard.Configuration;
import io.dropwizard.testing.junit.DropwizardAppRule;
import org.junit.ClassRule;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;


public class ExampleServerAcceptanceTest {
    @ClassRule
    public static final DropwizardAppRule<Configuration> ACCOUNT_SERVER_RULE =
            new DropwizardAppRule<Configuration>(ExampleServerApplication.class, "build/dropwizard_config.yml");


    @Test
    public void whenGetHelloWorldEndpoint_thenExpectedTextReturned() throws Exception {
        //String response = Client.create().resource("http://localhost:"+ACCOUNT_SERVER_RULE.getLocalPort()).path(ExampleService.HELLO_WORLD_ENDPOINT).get(String.class);
        ExampleService exampleService = new ClientFactory().buildClient(ExampleService.class, Client.create(), "http://localhost:" + ACCOUNT_SERVER_RULE.getLocalPort());

        assertThat(exampleService.helloWorld(), equalTo(ExampleServiceResource.HELLO_WORLD));
    }


}
