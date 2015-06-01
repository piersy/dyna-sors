package com.piersyp.dynasors.example.common;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path(ExampleService.HELLO_WORLD_ENDPOINT+"{{}}")
public interface ExampleService {

    public static final String HELLO_WORLD_ENDPOINT = "hello-world";

    @GET
    public String helloWorld();


    @POST
    public String DoSomething(@CookieParam("cookie") List<Runnable> runnableList);


}
