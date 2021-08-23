package org.example.pages;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MainPage extends org.example.pages.BasePage {

    // Поиск товаров
    @FindBy(xpath = "//input[contains(@placeholder, 'Искать на Ozon')]")
    private WebElement searchInput;

    /**
     * Поисков товаров
     * @param itemName название товара
     * @return возвращаем страничку с результатами поиска
     */
    public org.example.pages.SearchResultsPage searchFotItem(String itemName){
        searchInput.click();
        searchInput.clear();
        searchInput.sendKeys(itemName);
        searchInput.sendKeys(Keys.ENTER);
        return pageManager.getSearchResultsPage().checkSearchResultsOpen();
    }
}

