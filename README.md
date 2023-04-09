# phoneShop

## Стек
SpringBoot 2.7.5, PostgreSQL 15, Docker 20.10, Java 18, Gradle 7.5.1

## Перед запуском проекта:

+ git clone https://github.com/Vadsxd/phoneShop.git
+ cd phoneShop
  
## Запуск:

Установить переменные окружения через терминал или IDEA:

*PostgreSQL:*
+ export DB_URL=<>
+ export DB_USERNAME=<>
+ export DB_PASSWORD=<>

*Пару ключей можно сгенерировать в com.shop.phoneshop.security.jwt.GenerateSecretKeys:*
+ export JWT_ACCESS_SECRET=<>
+ export JWT_REFRESH_SECRET=<>

*Установить почту и пароль хоста для отправки писем о транзакциях:*
+ export MAIL_USERNAME=<>
+ export MAIL_PASSWORD=<>

*Сборка с помощью gradle:*
+ gradle clean bootRun

## Docker
Для Docker Compose можно использовать конфигурацию с помощью `.env` файла.

Запустить контейнер:
+ docker-compose up -d

## Подробная инструкция:
+ открываем терминал
+ git clone https://github.com/Vadsxd/phoneShop.git
+ cd phoneShop
+ запускаем Докер
+ включаем БД
+ заполняем файл .env (смотрите на мой пример, у вас он может быть другой)
+ docker-compose up -d
