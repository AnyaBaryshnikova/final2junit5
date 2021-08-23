package org.example.pages;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class SearchResultsPage extends org.example.pages.BasePage {

    @FindBy(xpath = "//div[contains(text(), 'Не нашли')]")
    WebElement checkOpen;

    // фильтры
    @FindBy(xpath = "//div[@data-widget='searchResultsFiltersActive']")
    WebElement filters;

    // Максимальная цена
    @FindBy(xpath = "//div[contains(text(), 'Цена')]/..//input[@qa-id='range-to']")
    WebElement filterMaxPrice;

    // Высокий рейтинг
    @FindBy(xpath = "//div[@value='Высокий рейтинг']")
    WebElement highRate;

    //NFC
    @FindBy(xpath = "//span[contains(text(), 'NFC')]/../..//input/..")
    WebElement checkNFC;

    //bluetooth
    @FindBy(xpath = "//span[contains(text(), 'Bluetooth')]/../..//input/..")
    WebElement checkBluetooth;

    //wifi
    @FindBy(xpath = "//span[contains(text(), 'Wi-Fi')]/../..//input/..")
    WebElement checkWIFI;

    // все товары на страничке
    @FindBy(xpath = "//div[@style='grid-column-start: span 12;']")
    List<WebElement> listItems;

    // количество товаров в корзине
    @FindBy(xpath = "//a[contains(@href, '/cart')]/span[not(contains(text(), 'Корзина'))]")
    WebElement cartAmount;



    @FindBy(xpath = "//a[contains(@href, '/cart')]")
    WebElement cart;

    //Посмотреть все бренды
    @FindBy(xpath = "//div[contains(text(), 'Бренды')]/..//span[contains(text(), 'Посмотреть все')]")
    WebElement seeAllBth;

    //Поиск бренда
    @FindBy(xpath = "//div[contains(text(), 'Бренды')]/..//p/../input")
    WebElement searchBrandInput;

    //Выбрать все бренды
    @FindBy(xpath = "//div[contains(text(), 'Бренды')]/..//div[contains(text(), 'Выбрать все')]/../..")
    WebElement selectAllBrandsBtn;

    @FindBy(xpath = "//div[contains(@class, 'filter-block')]")
    List<WebElement> filterBlocks;

//    @FindBy(xpath = "//div[@data-widget='megaPaginator']//div[@qa-id='prevButton' or @qa-id='next']/../div[not(@qa-id)]//a[@href]")
//    List<WebElement> pages;


    //количество товаров в корзине
    private int itemsAmount = 0;


    /**
     * Проверяем, что открылась страничка с  результатами поиска
     *
     * @return
     */
    public SearchResultsPage checkSearchResultsOpen() {
        waitUtilElementToBeVisible(checkOpen);
        return this;
    }

    /**
     * Фильтр цены
     *
     * @param max максимальная цена
     */
    public SearchResultsPage filterPrice(int max) {
        scrollWithOffset(filterMaxPrice, 0, -200);
        filterMaxPrice.click();
        filterMaxPrice.clear();
        filterMaxPrice.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE, max + "");

        filterMaxPrice.sendKeys(Keys.ENTER);

        WebElement filter = filters.findElement(By.xpath(".//span[contains(text(), 'до')]"));
        waitUtilElementToBeVisible(filter);

        return this;
    }


    /**
     * Отметить высокий рейтинг
     *
     * @return
     */
    public SearchResultsPage checkHighRate() {
        scrollWithOffset(highRate, 0, -200);
        highRate.click();
        WebElement filter = filters.findElement(By.xpath(".//span[contains(text(), 'Высокий рейтинг')]"));
        waitUtilElementToBeVisible(filter);

        return this;
    }

    public SearchResultsPage checkInterfaces(String name, boolean flag) {
        switch (name) {
            case "NFC":
                if (flag) {
                    scrollWithOffset(checkNFC, 0, -200);
                    checkNFC.click();
                    waitUtilElementToBeVisible(filters.findElement(By.xpath(".//span[contains(text(), 'NFC')]")));
                    break;
                }
            case "bluetooth":
                if (flag) {
                    scrollWithOffset(checkBluetooth, 0, -200);
                    checkBluetooth.click();
                    waitUtilElementToBeVisible(filters.findElement(By.xpath(".//span[contains(text(), 'Bluetooth')]")));
                    break;
                }
            case "wifi":
                if (flag) {
                    scrollWithOffset(checkWIFI, 0, -200);
                    checkWIFI.click();
                    waitUtilElementToBeVisible(filters.findElement(By.xpath(".//span[contains(text(), 'Wi-Fi')]")));
                    break;
                }
            default:
                Assertions.fail("Беспроводной интерфейс с названием '" + name + "' отсутствует на странице");
        }
        return this;

    }

    /**
     * Выбираем четные товары и добавляем в корзину
     *
     * @return
     */
    public SearchResultsPage selectEvenItems() {
        scrollUp();

        int pageIndex = 1;
        while(true) {
            for (int i = 1; i < listItems.size(); i = i + 2) {

                scrollWithOffset(listItems.get(i), 0, -200);
                WebElement button = addToCart(listItems.get(i));
                if (button != null) {
                    scrollWithOffset(button, 0, -200);
                    button.click();
                    ++itemsAmount;
                    wait.until(ExpectedConditions.textToBePresentInElement(cartAmount, itemsAmount + ""));
                    addItemName(listItems.get(i));
                }
            }

            try {
                WebElement nextBtn = driverManager.getDriver().findElement(By.xpath("//div[(contains(text(), 'Дальше'))]/../../."));
                nextBtn.click();
            }
            catch(NoSuchElementException e)
            {
                break;
            }
            ++pageIndex;

            try {
                WebElement page = driverManager.getDriver()
                        .findElement(By.xpath("//div[@data-widget='megaPaginator']//div[@qa-id='prevButton' " +
                                "or @qa-id='next']/../div[not(@qa-id)]//a[@href and text()='" + pageIndex + "']"));

                wait.ignoring(StaleElementReferenceException.class);
                wait.ignoring(NoSuchElementException.class);
                wait.until(checkLengthAttribute(page, "class", 5));
            }
            catch(NoSuchElementException e)
            {}
            waitUtilElementToBeVisible(checkOpen);
        }

        return this;

    }

    /**
     * Выбираем четные товары и добавляем в корзину
     *
     * @param n количество товаров
     * @return
     */
    public SearchResultsPage selectEvenItems(int n) {

        scrollUp();

        int pageIndex = 1;
        while(true) {
            for (int i = 1; i < listItems.size(); i = i + 2) {

                scrollWithOffset(listItems.get(i), 0, -200);
                WebElement button = addToCart(listItems.get(i));
                if (button != null) {
                    scrollWithOffset(button, 0, -200);
                    button.click();
                    ++itemsAmount;
                    wait.until(ExpectedConditions.textToBePresentInElement(cartAmount, itemsAmount + ""));
                    addItemName(listItems.get(i));
                }
                if(itemNames.size() == n)
                    return this;
            }

            try {
                WebElement nextBtn = driverManager.getDriver().findElement(By.xpath("//div[(contains(text(), 'Дальше'))]/../../."));
                nextBtn.click();
            }
            catch(NoSuchElementException e)
            {
                break;
            }
            ++pageIndex;
            WebElement page = driverManager.getDriver()
                    .findElement(By.xpath("//div[@data-widget='megaPaginator']//div[@qa-id='prevButton' " +
                            "or @qa-id='next']/../div[not(@qa-id)]//a[@href and text()='" + pageIndex + "']"));

            wait.ignoring(StaleElementReferenceException.class);
            wait.ignoring(NoSuchElementException.class);
            wait.until(checkLengthAttribute(page, "class", 5));
            waitUtilElementToBeVisible(checkOpen);




        }

        return this;

    }

    public org.example.pages.CartPage redirectToCart() {
        cart.click();
        return pageManager.getCartPage().checkCartPageOpen();
    }

    public SearchResultsPage filterCheckBox(String filterName, String elementName) {
        WebElement blockFilter = getFilters(filterName);
        WebElement seeAllButton = blockFilter.findElement(By.xpath(".//span[@class='show']"));

        if(seeAllButton.isDisplayed()){
            scrollWithOffset(seeAllButton, 0, -200);
            if(seeAllButton.getText().contains("Посмотреть все"))
                seeAllButton.click();
            WebElement inputField = blockFilter.findElement(By.xpath(".//input[not(@type='checkbox')]"));
            inputField.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
            inputField.sendKeys(elementName, Keys.chord(Keys.ENTER));
        }

        List<WebElement> checkBoxesList = blockFilter.findElements(By.xpath(".//label"));
        for(WebElement checkBox : checkBoxesList){
            if(checkBox.getText().contains(elementName)) {
                scrollWithOffset(checkBox, 0, -200);
                checkBox.click();
                wait.until(ExpectedConditions.textToBePresentInElement(filters, elementName));
                return  this;
            }

        }

        Assertions.fail("Чек Бокс " + elementName + " не найден в фильтре " + filterName);
        return this;
    }

    private WebElement getFilters(String nameFilter){
        for(WebElement filter : filterBlocks){
            if(filter.findElement(By.xpath("./div")).getText().contains(nameFilter)){
                return filter;
            }
        }
        Assertions.fail("Фильтр не найден :(");
        return null;
    }

    /**
     * Проверяем что кнопка добавления элемента в корзину есть
     *
     * @param element товар
     * @return тру если товар добвлен
     */
    private WebElement addToCart(WebElement element) {
        WebElement elementBtn;
        try {
            elementBtn = element.findElement
                    (By.xpath(".//*[contains(text(), 'доставит') or contains(text(), 'Доставит')]/../..//button"));
        } catch (NoSuchElementException e) {
            return null;
        }

        return elementBtn;
    }

    private void addItemName(WebElement element) {
        WebElement elName = element.findElement(By.xpath(".//div[contains(@style, 'max-width: 50')]//a[not(contains(text(), 'отзыв'))]/span/span"));
        WebElement elPrice = element.findElement(By.xpath(".//div[3]//span[@style='color: rgb(249, 17, 85);' or @style='color: rgb(0, 26, 52);'][1]"));
        int price = Integer.parseInt(elPrice.getText().replaceAll("[^\\d.]", ""));
        itemNames.add(elName.getText() + "      цена: " + price);
    }
}
