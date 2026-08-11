# Sprint_7 — API-тесты Яндекс.Самокат

Автотесты API учебного сервиса [Яндекс.Самокат](https://qa-scooter.praktikum-services.ru/).

Документация API: [qa-scooter.praktikum-services.ru/docs/](https://qa-scooter.praktikum-services.ru/docs/)

## Что покрыто

- создание курьера
- логин курьера
- создание заказа (с параметризацией цвета)
- получение списка заказов

## Стек

- Java 11
- Maven
- JUnit 5
- RestAssured
- Allure
- Datafaker

## Как запустить тесты

```bash
mvn clean test
```

## Allure-отчёт

После прогона тестов:

```bash
mvn allure:serve
```

или (если установлен Allure CLI):

```bash
allure serve target/allure-results
```

Сырые результаты отчёта лежат в `target/allure-results`.
