package org.example.steps;

import io.cucumber.java.ru.И;
import io.qameta.allure.Allure;
import org.example.managers.PageManager;

public class CartPageStep {

    private final PageManager pageManager = PageManager.getPageManager();


    @И("^Удаляем все товары из корзины$")
    public void deleteAllFromCart() {
        pageManager.getCartPage().deleteAllFromCart();
    }

    @И("^Проверяем что корзина пуста$")
    public void checkCartEmpty(){
        pageManager.getCartPage().checkCartEmpty();
    }

    @И("^Проверяем количество товаров в корзине$")
    public void searchForItem() {
        pageManager.getCartPage().checkAmountOfCart();
    }

    @И("^Прикрепляем список товаров$")
    public void getItems(){
        String str = pageManager.getCartPage().getItemList();
        Allure.addAttachment("Cписок товаров:", str);
    }

    @И("^Проверяем что в корзине те товары$")
    public void checkCartItems(){
        pageManager.getCartPage().checkItemsInCart();
    }


}