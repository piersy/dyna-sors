package com.piersyp.dynasors.example.client;

import com.sun.jersey.api.client.WebResource;

import javax.ws.rs.Path;
import java.lang.reflect.AnnotatedElement;

public class WebResourceBuildingService {
    public WebResource appendPath(WebResource resource, AnnotatedElement resourceClass) {
        String pathElement = ((Path) resourceClass.getAnnotation(Path.class)).value();
        return resource.path(pathElement);
    }
}
