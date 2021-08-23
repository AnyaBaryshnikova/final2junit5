package org.example.managers;

import org.example.pages.CartPage;
import org.example.pages.MainPage;
import org.example.pages.SearchResultsPage;

public class PageManager {
    /**
     * Менеджер страничек
     */
    private static PageManager pageManager;

    /**
     * Стартовая страничка
     */
    private MainPage mainPage;

    /**
     * Страничка корзины
     */
    private CartPage cartPage;

    /**
     * Страничка результатов поиска
     */
    private SearchResultsPage searchResultsPage;

    /**
     * Конструктор специально был объявлен как private (singleton паттерн)
     */
    private PageManager() {
    }

    /**
     * Ленивая инициализация PageManager
     *
     * @return PageManager
     */
    public static PageManager getPageManager() {
        if (pageManager == null) {
            pageManager = new PageManager();
        }
        return pageManager;
    }

    /**
     * Ленивая инициализация {@link MainPage}
     *
     * @return StartPage
     */
    public MainPage getMainPage() {
        if (mainPage == null) {
            mainPage = new MainPage();
        }
        return mainPage;
    }

    /**
     * Ленивая инициализация {@link CartPage}
     *
     * @return InsurancePage
     */
    public CartPage getCartPage() {
        if (cartPage == null) {
            cartPage = new CartPage();
        }
        return cartPage;
    }

    /**
     * Ленивая инициализация
     *
     * @return InsurancePage
     */
    public SearchResultsPage getSearchResultsPage() {
        if (searchResultsPage == null) {
            searchResultsPage = new SearchResultsPage();
        }
        return searchResultsPage;
    }
}
