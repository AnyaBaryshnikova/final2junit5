package org.example.steps;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.example.managers.InitManager;

public class Hooks {


    @Before
    public void before() {
        InitManager.initFramework();
    }


    @After
    public void after() {

        InitManager.quitFramework();
    }
}