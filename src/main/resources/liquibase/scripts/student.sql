-- liquibase formatted sql

-- changeset Goldovskaya:1
CREATE INDEX student_name_index ON student (name);

-- changeset Goldovskaya:2
CREATE INDEX faculty_cn_index ON faculty (color, name);