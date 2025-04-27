-- Create schemas
CREATE SCHEMA IF NOT EXISTS dictionary;
CREATE SCHEMA IF NOT EXISTS nuverse;

-- Dictionary schema tables
CREATE TABLE if not exists dictionary.roles
(
    id       BIGSERIAL PRIMARY KEY,
    value_kz VARCHAR,
    value_ru VARCHAR,
    value_en VARCHAR,
    exist    BOOLEAN
);

CREATE TABLE if not exists dictionary.news_types
(
    id       BIGSERIAL PRIMARY KEY,
    value_kz VARCHAR,
    value_ru VARCHAR,
    value_en VARCHAR,
    exist    BOOLEAN
);

CREATE TABLE if not exists dictionary.events_types
(
    id       BIGSERIAL PRIMARY KEY,
    value_kz VARCHAR,
    value_ru VARCHAR,
    value_en VARCHAR,
    exist    BOOLEAN
);

CREATE TABLE if not exists dictionary.weeks
(
    id       BIGSERIAL PRIMARY KEY,
    value_kz VARCHAR,
    value_ru VARCHAR,
    value_en VARCHAR,
    exist    BOOLEAN
);

CREATE TABLE if not exists dictionary.degrees
(
    id       BIGSERIAL PRIMARY KEY,
    value_kz VARCHAR,
    value_ru VARCHAR,
    value_en VARCHAR,
    exist    BOOLEAN
);

-- Nuverse schema tables
CREATE TABLE if not exists nuverse.users
(
    id        UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    role_id   BIGINT REFERENCES dictionary.roles (id),
    name      VARCHAR,
    surname   VARCHAR,
    last_name VARCHAR,
    birthday  DATE,
    email     VARCHAR,
    password  VARCHAR,
    username  VARCHAR,
    phone     VARCHAR
);

CREATE TABLE if not exists nuverse.news
(
    id        UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    type_id   BIGINT REFERENCES dictionary.news_types (id),
    user_id   UUID REFERENCES nuverse.users (id),
    header    VARCHAR,
    text      TEXT,
    image_url VARCHAR
);

CREATE TABLE if not exists nuverse.phonebooks
(
    id      UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    user_id UUID REFERENCES nuverse.users (id),
    phone   VARCHAR
);

CREATE TABLE if not exists nuverse.events
(
    id             UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    type_id        BIGINT REFERENCES dictionary.events_types (id),
    name           VARCHAR,
    location       VARCHAR,
    event_datetime TIMESTAMP
);

CREATE TABLE if not exists nuverse.schools
(
    id   UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    name VARCHAR
);

CREATE TABLE if not exists nuverse.majors
(
    id        UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    name      VARCHAR,
    school_id UUID REFERENCES nuverse.schools (id)
);

CREATE TABLE if not exists nuverse.courses
(
    id        UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    name      VARCHAR,
    school_id UUID REFERENCES nuverse.schools (id)
);

CREATE TABLE if not exists nuverse.students
(
    id              UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    user_id         UUID REFERENCES nuverse.users (id),
    enrollment_year INTEGER,
    gpa             DOUBLE PRECISION,
    major_id        UUID REFERENCES nuverse.majors (id),
    degree_id       BIGINT REFERENCES dictionary.degrees (id),
    image_url       VARCHAR
);

CREATE TABLE if not exists nuverse.students_courses
(
    id         UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    student_id UUID REFERENCES nuverse.students (id),
    course_id  UUID REFERENCES nuverse.courses (id)
);

CREATE TABLE if not exists nuverse.professors
(
    id                UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    user_id           UUID REFERENCES nuverse.users (id),
    office            VARCHAR,
    research_interest VARCHAR,
    image_url         VARCHAR
);

CREATE TABLE if not exists nuverse.universities
(
    id      UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    name    VARCHAR,
    country VARCHAR
);

CREATE TABLE if not exists nuverse.professors_degrees
(
    id            UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    professor_id  UUID REFERENCES nuverse.professors (id),
    university_id UUID REFERENCES nuverse.universities (id),
    degree_id     BIGINT REFERENCES dictionary.degrees (id),
    start_year    INT,
    end_year      INT
);

CREATE TABLE if not exists nuverse.professors_experiences
(
    id            UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    professor_id  UUID REFERENCES nuverse.professors (id),
    university_id UUID REFERENCES nuverse.universities (id),
    position      VARCHAR,
    start_year    INT,
    end_year      INT
);

CREATE TABLE if not exists nuverse.appointments
(
    id         UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    week_id    BIGINT REFERENCES dictionary.weeks (id),
    start_time TIME,
    end_time   TIME
);

CREATE TABLE if not exists nuverse.professors_appointments
(
    id             UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    professor_id   UUID REFERENCES nuverse.professors (id),
    appointment_id UUID REFERENCES nuverse.appointments (id)
);

-- Insert sample roles
INSERT INTO dictionary.roles (value_kz, value_ru, value_en, exist)
VALUES ('Студент', 'Студент', 'Student', TRUE),
       ('Оқытушы', 'Преподаватель', 'Professor', TRUE),
       ('Әкімші', 'Администратор', 'Administrator', TRUE);

-- Insert sample news types
INSERT INTO dictionary.news_types (value_kz, value_ru, value_en, exist)
VALUES ('Жаңалық', 'Новость', 'News', TRUE),
       ('Хабарлама', 'Объявление', 'Announcement', TRUE),
       ('Мақала', 'Статья', 'Article', TRUE);

-- Insert sample events types
INSERT INTO dictionary.events_types (value_kz, value_ru, value_en, exist)
VALUES ('Семинар', 'Семинар', 'Seminar', TRUE),
       ('Конференция', 'Конференция', 'Conference', TRUE),
       ('Кездесу', 'Встреча', 'Meeting', TRUE);

-- Insert sample weeks
INSERT INTO dictionary.weeks (value_kz, value_ru, value_en, exist)
VALUES ('Дүйсенбі', 'Понедельник', 'Monday', TRUE),
       ('Сейсенбі', 'Вторник', 'Tuesday', TRUE),
       ('Сәрсенбі', 'Среда', 'Wednesday', TRUE),
       ('Бейсенбі', 'Четверг', 'Thursday', TRUE),
       ('Жұма', 'Пятница', 'Friday', TRUE),
       ('Сенбі', 'Суббота', 'Saturday', TRUE),
       ('Жексенбі', 'Воскресенье', 'Sunday', TRUE);

-- Insert sample degrees
INSERT INTO dictionary.degrees (value_kz, value_ru, value_en, exist)
VALUES ('Бакалавр', 'Бакалавр', 'Bachelor', TRUE),
       ('Магистр', 'Магистр', 'Master', TRUE),
       ('PhD', 'PhD', 'PhD', TRUE);
