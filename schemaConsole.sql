CREATE SCHEMA pharmacy_storage;

set SEARCH_PATH = pharmacy_storage;

CREATE TABLE user_data(
                        id SERIAL PRIMARY KEY ,
                        user_id INTEGER REFERENCES user_account (id),
                        first_name CHARACTER VARYING (16) NOT NULL ,
                        last_name CHARACTER VARYING (16) NOT NULL ,
                        date_of_birth DATE NOT NULL,
                        email CHARACTER VARYING (32)
);

CREATE TABLE payment_data(
                           id SERIAL PRIMARY KEY ,
                           user_id INTEGER REFERENCES user_account (id),
                           balance NUMERIC,
                           card_number BIGINT NOT NULL
);

CREATE TABLE user_account(
                           id SERIAL PRIMARY KEY ,
                           login CHARACTER VARYING (32) UNIQUE NOT NULL ,
                           password CHARACTER VARYING (32) NOT NULL ,
                           role INTEGER NOT NULL
);

CREATE TABLE category(
                       id SERIAL PRIMARY KEY ,
                       category_name CHARACTER VARYING (64) UNIQUE NOT NULL
);

CREATE TABLE drug(
                   id SERIAL PRIMARY KEY ,
                   name CHARACTER VARYING (128) UNIQUE NOT NULL ,
                   composition CHARACTER VARYING (256) NOT NULL ,
                   indications CHARACTER VARYING (256) NOT NULL ,
                   mode_of_application CHARACTER VARYING (256) NOT NULL ,
                   contraindications CHARACTER VARYING (256) NOT NULL ,
                   recipe_needed BOOLEAN NOT NULL ,
                   category_id INTEGER REFERENCES category (id),
                   storage_quantity INTEGER NOT NULL ,
                   price NUMERIC NOT NULL
);

CREATE TABLE drug_dosage(
                          id SERIAL PRIMARY KEY ,
                          drug_id INTEGER REFERENCES drug (id),
                          dosage CHARACTER VARYING (64) NOT NULL
);

CREATE TABLE recipe(
                     id SERIAL PRIMARY KEY ,
                     user_id INTEGER REFERENCES user_account (id),
                     dosage_id INTEGER REFERENCES drug_dosage (id),
                     expiration_date DATE NOT NULL
);

CREATE TABLE user_order(
                         id SERIAL PRIMARY KEY ,
                         user_id INTEGER REFERENCES user_account (id),
                         dosage_id INTEGER REFERENCES drug_dosage (id),
                         quantity INTEGER NOT NULL ,
                         order_time TIMESTAMP,
                         order_cost NUMERIC NOT NULL ,
                         order_status INTEGER NOT NULL
);

CREATE TABLE recipe_request(
                             id SERIAL PRIMARY KEY ,
                             user_id INTEGER REFERENCES user_account (id) NOT NULL ,
                             drug_dosage_id INTEGER REFERENCES drug_dosage (id) NOT NULL,
                             expiration_date DATE NOT NULL ,
                             request_status INTEGER NOT NULL
);