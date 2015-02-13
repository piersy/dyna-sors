package com.piersyp.dynasors.example.client;

import com.sun.jersey.api.client.WebResource;

public class WebResourceAcceptService {
    public WebResource.Builder addAcceptsTypes(WebResource.Builder webResourceBuilder, Class resourceClass) {
        return webResourceBuilder;
    }
}
