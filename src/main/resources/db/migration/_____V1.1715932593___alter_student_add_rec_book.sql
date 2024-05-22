alter table student
add column grade_book_id bigint;

alter table applicant_application
add column gender varchar,
add column age int;

create table grade_book(
    id bigserial primary key ,
    student_id bigint references student(id),
)


alter table person_data
add column telegram varchar;