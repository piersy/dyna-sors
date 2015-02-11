package com.piersyp.dynasors.example.client;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;

import javax.ws.rs.GET;
import javax.ws.rs.HttpMethod;
import javax.ws.rs.Path;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.function.Function;

public class ClientFactory {
    public <T> T buildClient(Class<T> resourceClass, Client client, String hostAddress) {
        ClassLoader classLoader = this.getClass().getClassLoader();
        Class<?>[] interfaces = {resourceClass};
        WebResource resource = client.resource(hostAddress);
        MyInvocationHandler invocationHandler = new MyInvocationHandler(resource, resourceClass);
        return (T) Proxy.newProxyInstance(classLoader, interfaces, invocationHandler);
    }

    private static class MyInvocationHandler implements InvocationHandler {

        private final WebResource host;
        private final String rootPath;
        private WebResource resource;

        private MyInvocationHandler(WebResource host, Class resourceClass) {
            this.host = host;
            this.rootPath = ((Path)resourceClass.getAnnotation(Path.class)).value();
            this.resource = host.path(rootPath);
        }


        @Override
        public Object invoke(Object proxy, final Method method, Object[] args) throws Throwable {
            WebResource webResource = resource;
            Function<WebResource, Object> f = null;
            for (Annotation annotation : method.getAnnotations()) {
                if(annotation instanceof Path){
                    webResource = webResource.path(((Path) annotation).value());
                }
                if(annotation instanceof GET){
                    f = new Function<WebResource, Object>(){
                        @Override
                        public Object apply(WebResource webResource) {
                            return webResource.get(method.getReturnType());
                        }
                    };
                }
            }

            return f.apply(webResource);

        }
    }
}


