package com.piersyp.dynasors.example.client;

import com.sun.jersey.api.client.WebResource;

import javax.ws.rs.Consumes;
import javax.ws.rs.core.MediaType;
import java.lang.reflect.AnnotatedElement;

public class WebResourceTypeService {
    public WebResource.Builder setWebResourceType(WebResource.Builder webResourceBuilder, AnnotatedElement annotatedElement) {
        String[] consumes = ((Consumes) annotatedElement.getAnnotation(Consumes.class)).value();
        if(consumes.length > 1){
            throw new RuntimeException("Multiple consumes types please specify a preference when constructing");
        }
        else if(consumes.length == 1){
            return webResourceBuilder.type(MediaType.valueOf(consumes[0]));
        }
        else
        {
            return webResourceBuilder;
        }
    }
}
