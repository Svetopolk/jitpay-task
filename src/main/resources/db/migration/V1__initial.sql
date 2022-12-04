create table users
(
    user_id     UUID PRIMARY KEY,
    email       VARCHAR(50) NOT NULL,
    first_name  VARCHAR(50) NOT NULL,
    second_name VARCHAR(50) NOT NULL
);


create table locations
(
    user_id    UUID,
    created_on TIMESTAMP,
    latitude   float NOT NULL,
    longitude  float NOT NULL
);

alter table locations
    add constraint locations_pk
        unique (user_id, created_on);