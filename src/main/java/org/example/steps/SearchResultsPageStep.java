package org.example.steps;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.ru.И;
import org.example.managers.PageManager;

public class SearchResultsPageStep {

    private final PageManager pageManager = PageManager.getPageManager();

    @И("^Ограничеваем максимальную цену \"(.+)\"$")
    public void setMaxPrice(String maxPrice) {
        pageManager.getSearchResultsPage().filterPrice(Integer.parseInt(maxPrice));
    }

    @И("Выбираем высокий рейтинг$")
    public void highRate() {
        pageManager.getSearchResultsPage().checkHighRate();
    }

    @И("^Выбираем беспроводые интерфейсы:$")
    public void fillSliderFields(DataTable mapFieldsAndValue) {
        mapFieldsAndValue.asMap(String.class, String.class).forEach((key, value) ->
                pageManager.getSearchResultsPage().checkInterfaces((String) key, Boolean.parseBoolean((String) value)));
    }

    @И("Выбираем все четные товары$")
    public void getAllEvenItems() {
        pageManager.getSearchResultsPage().selectEvenItems();
    }

    @И("^Выбираем в фильтре \"(.+)\" чекбокс \"(.+)\"$")
    public void checkBrand(String filterName, String checkBoxName) {
        pageManager.getSearchResultsPage().filterCheckBox(filterName, checkBoxName);
    }

    @И("^Выбираем \"(.+)\" четных товаров$")
    public void getEvenItems(String amount) {
        pageManager.getSearchResultsPage().selectEvenItems(Integer.parseInt(amount));
    }

    @И("Переходим в корзину$")
    public void redirectToCart() {
        pageManager.getSearchResultsPage().redirectToCart();
    }



}



