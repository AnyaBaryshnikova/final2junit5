package org.example.utils;

import io.cucumber.plugin.event.EventHandler;
import io.cucumber.plugin.event.EventPublisher;
import io.cucumber.plugin.event.Status;
import io.cucumber.plugin.event.TestStepFinished;
import io.qameta.allure.Allure;
import io.qameta.allure.cucumber5jvm.AllureCucumber5Jvm;
import org.example.managers.DriverManager;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

public class MyAllureCucumberListner extends AllureCucumber5Jvm  {

    @Override
    public void setEventPublisher(EventPublisher publisher){
        EventHandler<TestStepFinished> handler = testStepFinished -> {
            if (testStepFinished.getResult().getStatus().equals(Status.FAILED)) {
                final byte[] screen = ((TakesScreenshot) DriverManager.getDriverManager().getDriver())
                        .getScreenshotAs(OutputType.BYTES);
                Allure.getLifecycle().addAttachment("Screen", "image/png", null, screen);

            }

        };
        publisher.registerHandlerFor(TestStepFinished.class, handler);
        super.setEventPublisher(publisher);
    }


}
