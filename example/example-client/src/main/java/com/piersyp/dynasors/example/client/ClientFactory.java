package com.piersyp.dynasors.example.client;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.function.Function;

public class ClientFactory {
    public <T> T buildClient(Class<T> resourceClass, Client client, String hostAddress) {
        return (T) Proxy.newProxyInstance(this.getClass().getClassLoader(), new Class<?>[]{resourceClass}, new MyInvocationHandler(client.resource(hostAddress), resourceClass));
    }

    private static class MyInvocationHandler implements InvocationHandler {

        private final WebResource host;
        private final Class resourceClass;
        private final String rootPath;

        private MyInvocationHandler(WebResource host, Class resourceClass) {
            this.host = host;
            this.resourceClass = resourceClass;
            this.rootPath = ((Path)resourceClass.getAnnotation(Path.class)).value();
        }


        @Override
        public Object invoke(Object proxy, final Method method, Object[] args) throws Throwable {
            WebResource resource = host.path(rootPath);
            Function<WebResource, Object> f = null;
            for (Annotation annotation : method.getAnnotations()) {
                if(annotation instanceof GET){
                    f = new Function<WebResource, Object>(){
                        @Override
                        public Object apply(WebResource webResource) {
                            return webResource.get(method.getReturnType());
                        }
                    };
                }
            }

            return f.apply(resource);

        }
    }
}


