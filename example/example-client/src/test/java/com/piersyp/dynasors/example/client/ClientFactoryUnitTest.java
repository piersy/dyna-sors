package com.piersyp.dynasors.example.client;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.hamcrest.Matchers.*;

@RunWith(MockitoJUnitRunner.class)
public class ClientFactoryUnitTest {


    public static final String HOST = "HOST";
    public static final String RESULT = "RESULT";
    public static final String ROOT = "/";

    @Mock
    private Client clientMock;
    @Mock
    private WebResource hostWebResourceMock;
    @Mock
    private WebResource rootWebResourceMock;

    @Path(ROOT)
    private static interface TestResource{
        @GET
        String get();
    }

    private ClientFactory clientFactory;

    @Before
    public void setUp() throws Exception {
        when(clientMock.resource(HOST)).thenReturn(hostWebResourceMock);
        when(hostWebResourceMock.path(ROOT)).thenReturn(rootWebResourceMock);

        when(rootWebResourceMock.get(String.class)).thenReturn(RESULT);

        clientFactory = new ClientFactory();
    }

    @Test
    public void givenClientConstructedForResourceWithGetMethod_whenClientMethodCalled_thenExpectedValueReturned() throws Exception {
        TestResource testResource = clientFactory.buildClient(TestResource.class, clientMock, HOST);

        assertThat(testResource.get(), equalTo(RESULT));

    }


}