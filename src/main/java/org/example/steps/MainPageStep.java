package org.example.steps;

import io.cucumber.java.ru.И;
import org.example.managers.PageManager;

public class MainPageStep {

    private final PageManager pageManager = PageManager.getPageManager();

    @И("^Ищем товар \"(.+)\"$")
    public void searchForItem(String itemName) {
        pageManager.getMainPage().searchFotItem(itemName);
    }

}