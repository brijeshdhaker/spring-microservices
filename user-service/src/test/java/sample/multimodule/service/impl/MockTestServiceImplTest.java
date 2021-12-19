package sample.multimodule.service.impl;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;

import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.junit4.PowerMockRunnerDelegate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import sample.multimodule.repository.UserRepository;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(PowerMockRunner.class)
@PowerMockRunnerDelegate(SpringJUnit4ClassRunner.class)
@PrepareForTest({MockTestServiceImpl.class,ArrayList.class})
public class MockTestServiceImplTest {

    @InjectMocks
    MockTestServiceImpl mockTestService;

    @Mock
    UserRepository userRepository;

    @Before
    public void setup(){
        MockitoAnnotations.openMocks(this);
    }


    @Test
    public void callStaticMethod() {
        // mock all the static methods in a class called "Static"
        PowerMockito.mockStatic(MockTestServiceImpl.class);
        // use Mockito to set up your expectation
        Mockito.when(MockTestServiceImpl.staticMethod("i100121")).thenReturn("mockValue");
        // execute your test
        mockTestService.callStaticMethod("i100121");
        // Different from Mockito, always use PowerMockito.verifyStatic(Class) first
        // to start verifying behavior
        PowerMockito.verifyStatic(MockTestServiceImpl.class, Mockito.times(1));
        // IMPORTANT:  Call the static method you want to verify
        //MockTestServiceImpl.firstStaticMethod(param);

    }

    @Test
    public void callStaticMethodException() {
        // mock all the static methods in a class called "Static"
        PowerMockito.mockStatic(MockTestServiceImpl.class);
        // use Mockito to set up your expectation
        Mockito.when(MockTestServiceImpl.staticMethod("i100121")).thenThrow(RuntimeException.class);
        // execute your test
        mockTestService.callStaticMethod("i100121");
        // Different from Mockito, always use PowerMockito.verifyStatic(Class) first
        // to start verifying behavior
        PowerMockito.verifyStatic(MockTestServiceImpl.class, Mockito.times(1));
        // IMPORTANT:  Call the static method you want to verify
        //MockTestServiceImpl.firstStaticMethod(param);
    }


    @Test
    public void callPrivateMethod() throws Exception {

        MockTestServiceImpl classUnderTest = PowerMockito.spy(new MockTestServiceImpl());
        // use PowerMockito to set up your expectation
        PowerMockito.doReturn("mockValue").when(classUnderTest, "privateMethod", "i100121");
        // execute your test
        classUnderTest.callPrivateMethod("i100121");
        // Use PowerMockito.verify() to verify result
        PowerMockito.verifyPrivate(classUnderTest, times(1)).invoke("privateMethod", "i100121");

    }

    @Test
    public void newObjectValue() throws Exception {

        ArrayList alt = PowerMockito.spy(new ArrayList());
        alt.add("AAAA");
        PowerMockito.whenNew(ArrayList.class).withAnyArguments().thenReturn(alt);
        PowerMockito.when(alt.get(any(Integer.class))).thenReturn("mockValue");
        mockTestService.newObject("i100121");

    }

    @Test
    public void newObjectException() throws Exception {

        ArrayList alt = PowerMockito.spy(new ArrayList());
        alt.add("AAAA");

        PowerMockito.whenNew(ArrayList.class).withAnyArguments().thenReturn(alt);
        PowerMockito.when(alt.get(any(Integer.class))).thenThrow(RuntimeException.class);
        mockTestService.newObject("i100121");

    }

    @Test
    public void partialMethod() throws Exception {

        ArrayList alt = PowerMockito.spy(new ArrayList());
        alt.add("AAAA");

        //Impossible: real method is called so spy.get(0) throws IndexOutOfBoundsException (the list is yet empty)
        //PowerMockito.when(alt.get(anyInt())).thenReturn("mockValue");

        //You have to use doReturn() for stubbing
       // PowerMockito.doReturn("mockValue").when(alt).get(anyInt());

        PowerMockito.whenNew(ArrayList.class).withAnyArguments().thenReturn(alt);
        PowerMockito.when(alt.get(any(Integer.class))).thenReturn("mockValue");

        mockTestService.newObject("");

    }
}