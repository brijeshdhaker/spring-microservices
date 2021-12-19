package sample.multimodule.service.impl;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.junit4.PowerMockRunnerDelegate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import sample.multimodule.SpringTestConfig;

import static org.junit.Assert.*;


@RunWith(PowerMockRunner.class)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = SpringTestConfig.class)
@PowerMockRunnerDelegate(SpringJUnit4ClassRunner.class)
@PrepareForTest({UserServiceImpl.class})

public class UserServiceImplTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void findOne() {
    }

    @Test
    public void saveUser() {
    }

    @Test
    public void getUsers() {
    }

    @Test
    public void deleteUserById() {
    }

    @Test
    public void toRequestDTO() {
    }
}