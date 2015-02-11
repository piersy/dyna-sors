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
    public static final String PATH = "PATH";

    @Mock
    private Client clientMock;
    @Mock
    private WebResource hostWebResourceMock;
    @Mock
    private WebResource rootWebResourceMock;
    @Mock
    private WebResource subWebResourceMock;

    @Path(ROOT)
    private static interface BasicGet {
        @GET
        String get();
    }



    private ClientFactory clientFactory;

    @Before
    public void setUp() throws Exception {
        when(clientMock.resource(HOST)).thenReturn(hostWebResourceMock);
        when(hostWebResourceMock.path(ROOT)).thenReturn(rootWebResourceMock);



        clientFactory = new ClientFactory();
    }

    @Test
    public void givenClientConstructedForResourceWithGetMethod_whenClientMethodCalled_thenExpectedValueReturned() throws Exception {
        when(rootWebResourceMock.get(String.class)).thenReturn(RESULT);
        BasicGet basicGet = clientFactory.buildClient(BasicGet.class, clientMock, HOST);

        assertThat(basicGet.get(), equalTo(RESULT));
    }

    @Path(ROOT)
    private static interface BasicGet1 extends BasicGet {
        @Path(PATH)
        @GET
        String get();
    }

    @Test
    public void givenClientConstructedForResourceWithGetMethodHavingSpecificPath_whenClientMethodCalled_thenExpectedValueReturned() throws Exception {

        when(rootWebResourceMock.path(PATH)).thenReturn(subWebResourceMock);
        when(subWebResourceMock.get(String.class)).thenReturn(RESULT);

        BasicGet basicGet = clientFactory.buildClient(BasicGet1.class, clientMock, HOST);

        assertThat(basicGet.get(), equalTo(RESULT));
    }


}