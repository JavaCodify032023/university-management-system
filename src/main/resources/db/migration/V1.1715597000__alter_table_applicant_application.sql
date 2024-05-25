alter table applicant_application
rename column is_activated to is_email_activated;

alter table applicant_application
alter column passport_id type varchar;

