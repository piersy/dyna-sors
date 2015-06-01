package com.piersyp.dynasors.example.server;

import com.piersyp.dynasors.example.common.ExampleService;

import javax.ws.rs.CookieParam;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import java.util.List;

public class ExampleServiceResource implements ExampleService {

    public static final String HELLO_WORLD = "hello world";

    public ExampleServiceResource() {
        System.out.println("constructor");
    }

    @Override
    public String helloWorld() {
        return HELLO_WORLD;
    }

    @Override
    public String DoSomething(@CookieParam("cookie") List<Runnable> runnableList) {
        return null;
    }
}
