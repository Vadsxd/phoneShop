# phoneShop
## Перед запуском проекта:

+ git clone https://github.com/Vadsxd/phoneShop.git
+ cd phoneShop
  
## Запуск:

Установить переменные окружения через терминал или IDEA:

*PostgreSQL:*
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
