package org.example.managers;

import java.util.concurrent.TimeUnit;

import static org.example.utils.Constants.IMPLICITLY_WAIT;
import static org.example.utils.Constants.PAGE_LOAD_TIMEOUT;

public class InitManager {
    /**
     * Менеджер properties
     */
    private static final org.example.managers.TestPropManager props = org.example.managers.TestPropManager.getTestPropManager();

    /**
     * Менеджер WebDriver
     *
     * @see DriverManager#getDriverManager()
     */
    private static final DriverManager driverManager = DriverManager.getDriverManager();

    /**
     * Инициализация framework и запуск браузера со страницей приложения
     */
    public static void initFramework() {
        driverManager.getDriver().manage().window().maximize();
        driverManager.getDriver().manage().timeouts().implicitlyWait(Integer.parseInt(props.getProperty(IMPLICITLY_WAIT)), TimeUnit.SECONDS);
        driverManager.getDriver().manage().timeouts().pageLoadTimeout(Integer.parseInt(props.getProperty(PAGE_LOAD_TIMEOUT)), TimeUnit.SECONDS);
        driverManager.getDriver().get("https://www.ozon.ru/");
    }

    /**
     * Завершения работы framework - гасит драйвер и закрывает сессию с браузером
     */
    public static void quitFramework() {
        driverManager.quitDriver();
    }
}