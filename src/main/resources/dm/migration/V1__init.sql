create table Person
(
    id    BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    name  TEXT,
    sName TEXT,
    age   int,
    email TEXT
);
