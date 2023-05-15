# phoneShop

## Стек
SpringBoot 2.7.5, PostgreSQL 15, Docker 20.10, Java 18, Gradle 7.5.1

## Запуск:
+ git clone https://github.com/Vadsxd/phoneShop.git
+ заполняем файл .env (смотрите на мой пример, у вас он может быть другой)
+ в gradle делаем ```bootJar```
+ запускаем docker-compose

*Пару ключей (JWT_ACCESS_SECRET и JWT_REFRESH_SECRET) можно сгенерировать в com.shop.phoneshop.security.jwt.GenerateSecretKeys:*

## Swagger доступен по ссылке:
+ http://localhost:8080/swagger-ui/index.html#/
