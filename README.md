### Hexlet tests and linter status:
[![Actions Status](https://github.com/ppeter777/java-project-99/actions/workflows/hexlet-check.yml/badge.svg)](https://github.com/ppeter777/java-project-99/actions)

[![Maintainability](https://api.codeclimate.com/v1/badges/7cc1a2a9643b54722760/maintainability)](https://codeclimate.com/github/ppeter777/java-project-99/maintainability)

[![Test Coverage](https://api.codeclimate.com/v1/badges/7cc1a2a9643b54722760/test_coverage)](https://codeclimate.com/github/ppeter777/java-project-99/test_coverage)

[![Java CI](https://github.com/ppeter777/java-project-99/actions/workflows/my_workflow.yml/badge.svg?branch=main)](https://github.com/ppeter777/java-project-99/actions/workflows/my_workflow.yml)


### Описание
Менеджер задач – веб-сайт предназначенный для управления задачами. Система позволяет ставить задачи, назначать исполнителей и устанавливать статусы задач.
Веб-сайт разработан на языке Java с использованием фреймворка Spring Boot. Деплой произведен на хостинге render.com, также возможен локальный запуск веб-приложения.
В качестве базы данных в продакшен среде использована БД PostgreSQL, при локальном запуске - база данных H2 в памяти. 

### Использование
Адрес веб-сайта: https://java-project-99-9qtj.onrender.com

Для работы с системой требуется регистрация и аутентификация:

- логин: hexlet@example.com
- пароль: qwerty

### Локальная установка

    git clone git@github.com:ppeter777/java-project-99.git
    cd java-project-99
    make install

### Локальный запуск

    make run-dist

в браузере перейти на http://localhost:8080/
