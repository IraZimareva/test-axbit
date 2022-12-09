# Тестовое задание в компанию Аксбит
**Стек технологий: Java8, Postgres, Liquibase, Hibernate, Spring Data, SpringBoot**

> Чтобы запустить приложение необходимо иметь установленную СУБД PostgreSQL и в файле application.properties изменить дефолтные креды к бд на свои. По умолчанию используется стандартный суперюзер postges, для которого необходимо создать бд library (CREATE DATABASE library WITH OWNER = postgres). После старта приложение работает, как обычный REST-сервис

Задача: Реализовать систему ввода и отображения информации об авторах книг посредством RESTfull API, включающую следующие сущности и их атрибуты:
- Автор (Имя, Фамилия, Отчество, Дата рождения, Книги)
- Книга (ISBN, Жанр)
- Жанр (Название)

Требования:
- Каждая таблица должна иметь первичный ключ типа Long, поля: дата создания, дата изменения. Общие поля из сущности должны быть вынесены в абстрактный класс. Технические поля должны быть запрещены для инициализации вручную или пользователем.
- CRUD операции + фильтры на чтение (getAllPaged, getByFilters, getById)
- Мягкое удаление сущностей по ид
- Система должна иметь защиту на уровне БД от удаления авторов с книгами. 
- Создание БД через систему контроля миграций Liquibase или Flyway


## Система имеет следующий программный интерфейс: ##
1. AuthorController <br>
GET /authors <br>
GET /authors/{id} <br>
GET /authors with different parameters (lastname, firstname, middlename, dateOfBirth). For example GET /authors?lastname=Толстой&firstname=Алексей  <br>
POST /authors with Request body as JSON: 
  {
        "lastname": "Пушкин",
        "firstname": "Александр",
        "middlename": "Сергеевич",
        "dateOfBirth": "1799-06-06"
  }<br>
DELETE /authors/{id} <br>
PATCH /authors/{id} with Request body as JSON: 
  {
        "firstname": "Александр",
        "dateOfBirth": "1799-06-06"
  }<br>
 PUT /authors/{idAuthor}/books/{idBook} <br>
 POST /authors/{idAuthor}/books with Request body as JSON: 
 {
    "isbn": "9780132350884",
    "genreId": 2     
}
 
2. BookController <br>
GET /books <br>
GET /books/{id} <br>
GET /books with different parameters (isbn, genre). For example GET /books?genre=Роман <br>
POST /books with Request body as JSON: 
{
    "isbn": "9780747532743",
    "genreId": 2
} <br>
DELETE /books/{id} <br>
PATCH /books/{id} with Request body as JSON: 
{
    "isbn": "9780439064873",
    "genre": 2
}

3. GenreController <br>
GET /genres <br>
GET /genres/{id} <br>
GET /genres with parameter (name). For example GET /genres?name=Детектив <br>
POST /genres with Request body as JSON: 
 {
     "name": "Пьеса"
 } <br>
DELETE /genres/{id} <br>
PATCH /genres/{id} with Request body as JSON: 
{
    "name": "Повесть"
}

