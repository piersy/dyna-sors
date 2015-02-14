package com.piersyp.dynasors.example.client;

import com.piersyp.dynasors.example.common.ExampleService;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import java.lang.Object;
import java.lang.reflect.Method;
import java.util.function.Function;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ClientFactoryUnitTest {


    public static final String HOST = "HOST";
    public static final String RESULT = "RESULT";
    public static final String ROOT = "/";
    public static final String PATH = "PATH";





//    need to look into how methods are dipspatched from jersey, that involves debugging

    private ClientFactory clientFactory;
    private Method method1;
    private Method method2;
    private Method method3;

    @Mock
    private ClientFunctionFactory clientFunctionFactoryMock;
    @Mock
    private Function<Object[], Object> function1Mock;
    @Mock
    private Function<Object[], Object> function2Mock;
    @Mock
    private Function<Object[], Object> function3Mock;
    @Mock
    private Client clientMock;
    @Mock
    private WebResource hostWebResourceMock;
    @Mock
    private WebResource rootWebResourceMock;
    @Mock
    private WebResource subWebResourceMock;


    @Before
    public void setUp() throws Exception {

        method1 = Resource.class.getMethod("method1");
        method2 = Resource.class.getMethod("method2");
        method3 = Resource.class.getMethod("method3");

        when(clientMock.resource(HOST)).thenReturn(hostWebResourceMock);
        when(hostWebResourceMock.path(ROOT)).thenReturn(rootWebResourceMock);

        clientFactory = new ClientFactory(clientFunctionFactoryMock);
    }

    @Path(ROOT)
    private static interface TestResource{

        @GET
        String get();
    }

    @Test
    public void givenClientConstructedForResourceWithGetMethod_whenClientMethodCalled_thenExpectedValueReturned() throws Exception {
//        when(rootWebResourceMock.get(String.class)).thenReturn(RESULT);
//        TestResource testResource = clientFactory.createClient(TestResource.class, clientMock, HOST, new WebResourceBuildingService());
//
//        assertThat(testResource.get(), equalTo(RESULT));
    }

    @Path(ROOT)
    private static interface TestResource1 extends TestResource{
        @Path(PATH)
        @GET
        String get();
    }


    private static interface Resource {
        void method1();
        void method2();
        void method3();
    }

    @Test
    public void whenBuildClient_thenClientBuiltWithExpectedMethodFunctionMapInvocationHandler() throws Exception {
        when(clientFunctionFactoryMock.createFunction(Resource.class, method1)).thenReturn(function1Mock);
        when(clientFunctionFactoryMock.createFunction(Resource.class, method2)).thenReturn(function2Mock);
        when(clientFunctionFactoryMock.createFunction(Resource.class, method3)).thenReturn(function3Mock);

        Resource client = clientFactory.createClient(Resource.class, clientMock, HOST);

        //We verify the correct methodFunctionMap by calling each method on the resource and ensuring the corresponding function is called
        client.method1();
        client.method2();
        client.method3();

        InOrder inOrder = inOrder(function1Mock, function2Mock, function3Mock);
        inOrder.verify(function1Mock).apply(null);
        inOrder.verify(function2Mock).apply(null);
        inOrder.verify(function3Mock).apply(null);
    }

    @Test
    public void givenClientConstructedForResourceWithGetMethodHavingSpecificPath_whenClientMethodCalled_thenExpectedValueReturned() throws Exception {

        when(rootWebResourceMock.path(PATH)).thenReturn(subWebResourceMock);
        when(subWebResourceMock.get(String.class)).thenReturn(RESULT);

//        TestResource testResource = clientFactory.createClient(TestResource1.class, clientMock, HOST);

//        assertThat(testResource.get(), equalTo(RESULT));
    }






}