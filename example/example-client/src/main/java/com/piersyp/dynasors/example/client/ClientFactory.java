package com.piersyp.dynasors.example.client;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ClientFactory {

    private final ClientFunctionFactory clientFunctionFactory;

    public ClientFactory(ClientFunctionFactory clientFunctionFactory) {

        this.clientFunctionFactory = clientFunctionFactory;
    }

    public <T> T createClient(Class<T> resourceClass, Client client, String hostAddress) {
        ClassLoader classLoader = this.getClass().getClassLoader();
        Class<?>[] interfaces = {resourceClass};
        WebResource resource = client.resource(hostAddress);
        Map<Method, Function<Object[], Object>> methodFunctionMap = Arrays.asList(resourceClass.getMethods()).stream().collect(
                Collectors.toMap(m -> m, m -> clientFunctionFactory.createFunction(resourceClass, m)));


        return (T) Proxy.newProxyInstance(classLoader, interfaces, new MethodFunctionMapInvocationHandler(methodFunctionMap));
    }

//    private static class Web

    private static class ClientInvocationHandler implements InvocationHandler {

        private WebResource webResource;
        private MediaType consumes;
        private Map<Method, Function<Object[], Object>> methodFunctionMap;

        private ClientInvocationHandler(WebResource resource, Class resourceClass, WebResourceBuildingService webResourcePathService,
                                        WebResourceTypeService webResourceTypeService, WebResourceAcceptService webResourceAcceptService) {
            resource = webResourcePathService.appendPath(resource, resourceClass);
            this.webResource = resource;
            WebResource.Builder webResourceBuilder = resource.type(MediaType.APPLICATION_OCTET_STREAM_TYPE);
            webResourceBuilder = webResourceTypeService.setWebResourceType(webResourceBuilder, resourceClass);
            webResourceBuilder = webResourceAcceptService.addAcceptsTypes(webResourceBuilder, resourceClass);
            /*We can try to handle subresource locators but in the case that
              there is more than one compatible class that have non matching annotations on them then we will fail.
             */


        }


        @Override
        public Object invoke(Object proxy, final Method method, Object[] args) throws Throwable {
            Function<WebResource, Object> f = null;
            for (Annotation annotation : method.getAnnotations()) {
                if (annotation instanceof Path) {
                    webResource = webResource.path(((Path) annotation).value());
                }
                if (annotation instanceof GET) {
                    f = new Function<WebResource, Object>() {
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


