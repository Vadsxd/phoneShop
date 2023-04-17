# phoneShop

## Стек
SpringBoot 2.7.5, PostgreSQL 15, Docker 20.10, Java 18, Gradle 7.5.1

## Запуск:
+ git clone https://github.com/Vadsxd/phoneShop.git
+ cd phoneShop
+ заполняем файл .env (смотрите на мой пример, у вас он может быть другой)
+ docker-compose up -d

*Пару ключей можно сгенерировать в com.shop.phoneshop.security.jwt.GenerateSecretKeys:*
+ export JWT_ACCESS_SECRET=<>
+ export JWT_REFRESH_SECRET=<>
