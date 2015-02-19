package com.piersyp.dynasors.example.common;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path(ExampleService.HELLO_WORLD_ENDPOINT)
public interface ExampleService {

    public static final String HELLO_WORLD_ENDPOINT = "hello-world";

    @GET
    public String helloWorld();

}
