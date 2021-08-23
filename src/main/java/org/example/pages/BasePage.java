package org.example.pages;

import org.example.managers.DriverManager;
import org.example.managers.PageManager;
import org.example.managers.TestPropManager;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;

public class BasePage {

    //имена товаров
    protected static List<String> itemNames = new ArrayList<>();


    boolean frameFlag = false;
    /**
     * Менеджер WebDriver
     */
    protected final DriverManager driverManager = DriverManager.getDriverManager();

    /**
     * Менеджер страничек
     */
    protected PageManager pageManager = PageManager.getPageManager();


    /**
     * Объект для имитации реального поведения мыши или клавиатуры
     */
    protected Actions action = new Actions(driverManager.getDriver());


    /**
     * Объект для выполнения любого js кода
     */
    protected JavascriptExecutor js = (JavascriptExecutor) driverManager.getDriver();


    /**
     * Объект явного ожидания
     * При применении будет ожидать заданного состояния 10 секунд с интервалом в 1 секунду
     */
    protected WebDriverWait wait = new WebDriverWait(driverManager.getDriver(), 10, 1000);


    /**
     * Менеджер properties
     */
    private final TestPropManager props = TestPropManager.getTestPropManager();


    /**
     * Конструктор позволяющий инициализировать все странички и их элементы помеченные аннотацией {@link FindBy}
     */
    public BasePage() {
        PageFactory.initElements(driverManager.getDriver(), this);
    }


    /**
     * Функция позволяющая производить scroll до любого элемента с помощью js
     *
     * @param element - веб-элемент странички
     */
    protected WebElement scrollToElementJs(WebElement element) {
        js.executeScript("arguments[0].scrollIntoView(true);", element);
        return element;
    }


    /**
     * Функция позволяющая производить scroll до любого элемента с помощью js со смещение
     * Смещение задается количеством пикселей по вертикали и горизонтали, т.е. смещение до точки (x, y)
     *
     * @param element - веб-элемент странички
     * @param x       - параметр координаты по горизонтали
     * @param y       - параметр координаты по вертикали
     */
    public WebElement scrollWithOffset(WebElement element, int x, int y) {
        String code = "window.scroll(" + (element.getLocation().x + x) + ","
                + (element.getLocation().y + y) + ");";
        ((JavascriptExecutor) driverManager.getDriver()).executeScript(code, element, x, y);
        return element;
    }
    
    public void scrollUp(){
        driverManager.getDriver().findElement(By.tagName("body")).sendKeys(Keys.CONTROL, Keys.HOME);
    }


    /**
     * Явное ожидание состояния clickable элемента
     *
     * @param element - веб-элемент который требует проверки clickable
     * @return WebElement - возвращаем тот же веб элемент что был передан в функцию
     */
    protected WebElement waitUtilElementToBeClickable(WebElement element) {
        return wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    /**
     * Явное ожидание того что элемент станет видемым
     *
     * @param element - веб элемент который мы ожидаем что будет  виден на странице
     */
    protected WebElement waitUtilElementToBeVisible(WebElement element) {
        return wait.until(ExpectedConditions.visibilityOf(element));
    }

    /**
     * Отмечаем поля с радиобатонами
     *
     * @param element элемент, который необходимо отметить
     * @param value   флаг, должна ли быть отмечена кнопка
     */
    protected void fillRadios(WebElement element, boolean value) {
        scrollWithOffset(element, 0, 300);
        WebElement chechStatus = element.findElement(By.xpath("./../.."));
        String status = chechStatus.getAttribute("class");

        if (status.contains("checked") & value)
            return;

        if (status.contains("checked") & !value) {
            element.click();
            return;
        }

        if (!status.contains("checked") & !value)
            return;

        if (!status.contains("checked") & value) {
            element.click();
            return;
        }
    }

    /**
     * Заполнение полей со слайдером
     *
     * @param element элемент, который неоюходимо заполнить
     * @param value   значение
     */
    protected void fillSlider(WebElement element, String value) {
        scrollWithOffset(element, 0, 300);
        element.click();
        element.sendKeys(Keys.chord(Keys.CONTROL, "a"), value);
        element.sendKeys(Keys.ENTER);
    }

    protected void checkFields(WebElement element, int value) {
        scrollWithOffset(element, 0, 200);
        String credit = element.getText().replaceAll("[^\\d.]", "");
        int sum = Integer.parseInt(credit);

        Assertions.assertTrue(sum == value, "Сумма " + sum + " не совпадает с заявленной " + value);
    }

    protected void checkFields(WebElement element, double value) {
        scrollWithOffset(element, 0, 200);
        String credit = element.getText().substring(0, element.getText().lastIndexOf('%'));
        credit = credit.replace(',', '.');
        double sum = Double.parseDouble(credit);

        Assertions.assertTrue(sum == value, "Сумма " + sum + " не совпадает с заяленной " + value);
    }





    public static ExpectedCondition<Boolean> checkLengthAttribute(final WebElement element,
                                                               final String attribute,
                                                               final int value) {
        return new ExpectedCondition<Boolean>() {
            private String currentValue = null;

            @Override
            public Boolean apply(WebDriver driver) {
                return element.getAttribute(attribute).length() > value;
            }

            @Override
            public String toString() {
                return String.format("value to contain \"%s\". Current value: \"%s\"", value, currentValue);
            }
        };
    }
}

