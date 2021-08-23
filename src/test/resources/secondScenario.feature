#language: ru

@all
Функционал: Поиск наушников

  @firstTest
  Сценарий: Поиск беспроводных наушников на сайте ozon
    * Ищем товар "беспроводные наушники"
    * Ограничеваем максимальную цену "10000"
    * Выбираем высокий рейтинг
    * Выбираем в фильтре "Бренды" чекбокс "Samsung"
    * Выбираем в фильтре "Бренды" чекбокс "Beats"
    * Выбираем в фильтре "Бренды" чекбокс "Xiaomi"

#    * Выбираем в фильтре "Бренды" чекбокс "1MORE"
#    * Выбираем в фильтре "Бренды" чекбокс "AccesStyle"

    * Выбираем все четные товары
    * Переходим в корзину
    * Прикрепляем список товаров
    * Проверяем количество товаров в корзине
    * Проверяем что в корзине те товары
    * Удаляем все товары из корзины
    * Проверяем что корзина пуста




