--добавил столбец faculty_id в таблицу кафедр
alter table department
    add column faculty_id bigint references faculty(id);

--добавил столбец department_id в таблицу направлений
alter table specialty
    add column department_id bigint references department(id);