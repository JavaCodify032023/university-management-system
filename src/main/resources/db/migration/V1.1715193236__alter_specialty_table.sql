--Добавил столбец shortName для таблицы specialty (например ЕПИ, ИТС и тд)

alter table specialty
    add column short_name varchar(10);