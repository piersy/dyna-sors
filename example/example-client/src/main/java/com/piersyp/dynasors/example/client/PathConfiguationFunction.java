package com.piersyp.dynasors.example.client;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;

import java.lang.reflect.Method;
import java.util.function.Function;

public class PathConfiguationFunction implements Function<Class<?>, Function<Method, Function<Client, WebResource>>> {
    @Override
    public Function<Method, Function<Client, WebResource>> apply(Class<?> aClass) {
        return method -> client -> {
            /*
            Do work here . . . could i instead inject functions into functions function
            The problem of multiple parameters still persists
            so inject results into functions? is this any better than what we have
            well at present we cannot use this function on its own it must come wrapped
            all the logic is going in this inner function, its not so nice, instead we should
            provide functions to find annotations on class and method and then construct
            other functions based around those, really i want to compose the functions to
            allow a list of annotations to be passed to the end functions, that is much cleaner
            how does that work with other stuff?
             */
                aClass.getMethods();
                method.getDeclaredAnnotations();
                client.resource("fff");
            return null;
        };
    }
}
