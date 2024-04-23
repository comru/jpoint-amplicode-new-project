## Создание проекта с нуля до RESTfull API с тестами и локальной средой для развертывания.

### Steps:

1. Создание и конфигурация проекта
    1. Перейти на https://start.spring.io/
    2. Setup
        1. В основных настройках ни чего не меням (Gradle - Groovy, Java 17, ...)
        2. Dependencies: Validation, Spring Boot DevTools, Lombok, Spring Data JPA, PostgreSQL Driver, Spring Web
    3. Скачиваем, распаковываем, открываем.
    4. Enable Amplicode
    5. Осматриваем панели Explorer/Designer
    6. Показываем валидацию в Spring Data JPA, от нее настраиваем Spring Data Source (выбираем postgres, остальное все
       оставляем как есть).
    7. Выбираем опцию "Create DB Connection"
    8. В application.properties file, добавляем частые настройки для JPA (hibernate): отключаем Open in view, show sql, format sql, auto ddl - validate
2. Настройка локального окружения
    1. Создаем docker-compose file из explorer
    2. Из suggest добавляем postgres, но также показываем возможности по добавлению из палитры, code generation, auto-completion.
    3. Показываем что все заполнилось само из существующего `spring.datasource`. Смотрим все проперти, восхищаемся
    4. Далее, замечаем что suggest ни куда не исчез. Еще и pgAdmin добавляется, тоже все само подставилось. Да что ж за
       чудо.
    5. Смотрим настройки, особое внимание уделяем Configure DB server connections
    6. Запускаем через explorer. _почему там написано deploy?_ Не знаю на сколько это хорошая идея. Может стоит запустить через gutter icon?
    7. Переходим через inlay hint возле pgAdmin в браузер, находим наш созданный бд сервер
3. Создание модели (DB First)
    1. Поскольку мы уже находимся в клиенте БД, давайте от сюда и начнем создавать нашу модель. Возьмем за предметную область всем известную petclinic и начнем создавать c таблицы owners
    2. Раскроемся до tables и вызовем действие create table
    3. Заполняем колонки -
        - id bigint NOT NULL PRIMARY KEY,
        - first_name text NOT NULL,
        - last_name text NOT NULL,
        - address text,
        - city text,
        - telephone character varying(10) NOT NULL
    4. Создаем index для last_name
    5. Возвращаемся в проект
    6. Вызываем действие reverse engineering
    7. В поле DB Connection уже должен быть выбран созданный ранее из data source
    8. Меняем package на ...model
    9. Выбираем таблицу owners
    10. Смотрим на поля, смотрим на имя класса (оно в единственном числе)
    11. Выбираем опцию, миграция indexes and constraints
    12. Создаем сущность, проверяем код
    13. Можно поменять validation, над telephone на @Digits(fraction = 0, integer = 10)
    14. Можно вызвать show ddl, убедиться что DDL сгенерировался в соответствии с тем, что мы создали  
    15. Запускаем приложение, все корректно запустилось
4. Создание модели (Code First)
    1. Создаем сущность Pet
        - id: Long, sequence
        - name: String, nullable = false
        - birthDate: LocalDate
        - owner: Owner, nullable = false
    2. Создание атрибута type, через диалог создания атрибута создаем сущность PetType.
    3. В сущности Pet создаем index на name
    4. Переходим в PetType,
    5. Создаем атрибут name: String, nullable=false
    6. Создаем уникальный индекс на name
5. Запуск приложение и обзор ошибки от spring.jpa.hibernate.ddl-auto=validate
    1. Запускаем приложение, из-за изменений модели и установленного свойства ddl-auto=validate получаем ошибку
    2. Смотрим в логи, видим наше действие по генерации diff скриптов
    3. Обозреваем его
    4. Говорим, что не хотим им в данный момент пользоваться, а хотим по нормальному подключить версиоонирование БД
6. Подключение версиоонирование БД (liquibase)
    1. В конфигурациях выбираем DB versioning, выбираем liquibase
    2. Настраиваем все что надо, особенно Create init db scripts и run changelog-sync
    3. Рассказываем про changelog-sync
    4. Обозреваем сгенерированные ченджлоги
    5. Рассказываем про liquibaase designer
    6. Генерируем liquibase diff для изменной модели, обозреваем preview
    7. Запускаем приложение, можно рассказать что изменения будут применены за счет liquibase starter
    8. Приложение успешно запушено.
7. Создание Rest Controller для Pet
    1. Создаем RestController - PetResource,
    2. проверяем package, и указываем request path, например rest/pets
8. Создание метода save в контроллере 
    1. Вызываем делегацию из... 
    2. Создаем Repository для Pet, не забываем менять package      
    3. Проксируемся через сервис
    4. Выбираем метод save
    5. Говорим что не хорошо пробрасывать и принимать напрямую domain entity через rest controller. Поэтому обернем ее в DTO как на входе, так и на выходе. Обращаем внимание что mapstruct не был подключен и он будет добавлен автоматически. 
    6. Показываем preview 
    7. Генерим метод 
    8. Пробуем запустить, ни чего не получается т.к. spring.start.io не правильно добавляет lombok, точнее добавляет без annotationProcessor идем фиксим. 
       Рассказываем, что если бы мы добавляли lombok и прочие стартеры через наши конфигурации, то такого бы не было  
9. Создание метода findAllByFilter
   1. Только сохранять не достаточно нужно еще и получать. Хотим получить список петов по фильтрационому запросу.
   2. Идем в репозиторий, там начинаем набирать метод 
      findAllByNameContainsIgnoreCaseOrOwner_LastNameIgnoreCaseOrOwner_FirstNameContainsIgnoreCaseOrderByName
   3. Говорим, что это очень страшно и конечно хотелось бы то же самое, но через JPQL 
   4. Вызываем extract to JPQL and configure
   5. Даем имя метода findAllByQuery или что-то такое 
   6. Удаляем два параметра  lastName, firstName оставляем один параметр name переименовываем на q, в JPQL заменам все numerated параметры на ?1
   7. Рассказываем что это можно было сделать и через дизайнер и прям от репозитория в сервисе
   8. Делегируем получившийся метод в сервис, делаем мапинг на DTO
   9. Делегируем метод сервиса в контроллер, меняем Path Variable на RequestParam
   10. Запускаем приложение. Проверям что все рабоатет
10. Настало время проверить что наши эндпоинты работают
    1. Давайте сгенерируем тест для наших эндпоинтов
    2. Вызываем Создание тестов из контроллера
    3. Выбираем оба метода
    4. Заполняем для save  
                   "name": "Buddy",
                     "birthDate": "2020-04-01",
                     "ownerId": "",
                     "petTypeId": ""
    5. Говорим что да, надо будет создать тестовых данных
    6. Для find, в q например ставим ivanov, говорим что будет искать по имени владельца
    7. Генерим 
11. Создание тестовых данных
    1. В методе setup создаем сущность PetType
          PetType dogType = new PetType();
          dogType.setName("dog");
    2. Вызываем от dogType наш метод save, для выззова метода репозитория, но он еще не создан показываем эту фичу с созданием репозиторий. 
    3. Тоже самое делаем для owner 
      Owner owner = new Owner()
                .setId(1L)
                .setFirstName("Ivan")
                .setLastName("Ivanov")
                .setCity("Moscow")
                .setAddress("CMT")
                .setTelephone("9271110000");
    4. И также создаем репозиторий через метод save от owner    
    5. Далее от сохраненного petType и owner записываем id в поля тестового класса. Для того чтобы указать их в тестовом body 
        dogTypeId = petTypeRepository.save(dogType).getId();
        ownerId = ownerRepository.save(owner).getId();
12. Пишем метод tearDown, чтобы подчистить за собой созданные данные. Здесь демонстрируем обычную инжекицию существующего petRepository 
        petRepository.deleteAll();
        petTypeRepository.deleteAll();
        ownerRepository.deleteAll();
13. Нормализуем тестовый метод
    1. В методе save, меняем boddy на 
    String petDto = """
                 {
                     "name": "Buddy",
                     "birthDate": "2020-04-01",
                     "ownerId": %s,
                     "petTypeId": %s
                 }""".formatted(ownerId, dogTypeId)
    2.  Копируем из метода find , метод find можем удалить
     mockMvc.perform(get("/rest/pets")
                         .param("q", "ivanov"))
                 .andExpect(status().isOk())
                 .andDo(print());
    3. Запускаем тест, смотрим что все работает.
14. Дальше свободное общение. Рассказываем про REST CRUD Controller, JPA Specififcation filter, patch и прочее. 