package com.piersyp.dynasors.example.server;

import com.piersyp.dynasors.example.common.ExampleService;

public class ExampleServiceResource implements ExampleService {

    public static final String HELLO_WORLD = "hello world";

    public ExampleServiceResource() {
        System.out.println("constructor");
    }

    @Override
    public String helloWorld() {
        return HELLO_WORLD;
    }
}
