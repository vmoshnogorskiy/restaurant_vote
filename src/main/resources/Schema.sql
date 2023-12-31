DROP TABLE IF EXISTS vote;
DROP TABLE IF EXISTS menu_item;
DROP TABLE IF EXISTS restaurant;
DROP TABLE IF EXISTS user_role;
DROP TABLE IF EXISTS users;


CREATE TABLE users
(
    id               INTEGER AUTO_INCREMENT PRIMARY KEY,
    name             VARCHAR                           NOT NULL,
    email            VARCHAR                           NOT NULL,
    password         VARCHAR                           NOT NULL,
    registered       TIMESTAMP           DEFAULT now() NOT NULL,
    enabled          BOOL                DEFAULT TRUE  NOT NULL
);
CREATE UNIQUE INDEX users_unique_email_idx ON users (email);

CREATE TABLE user_role
(
    user_id INTEGER NOT NULL,
    role    VARCHAR NOT NULL,
    CONSTRAINT user_roles_idx UNIQUE (user_id, role),
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);

CREATE TABLE restaurant
(
    id              INTEGER AUTO_INCREMENT PRIMARY KEY,
    name            VARCHAR                 NOT NULL,
    address         VARCHAR                 NOT NULL,
    created         TIMESTAMP DEFAULT now() NOT NULL,
    CONSTRAINT restaurant_name_address_idx UNIQUE (name, address)
);

CREATE TABLE menu_item
(
    id              INTEGER AUTO_INCREMENT PRIMARY KEY,
    name            VARCHAR        NOT NULL,
    price           NUMERIC(9, 2)  NOT NULL,
    actual_date     DATE DEFAULT   NOT NULL,
    restaurant_id   INTEGER        NOT NULL,
    FOREIGN KEY (restaurant_id) REFERENCES restaurant (id) ON DELETE CASCADE
);

CREATE TABLE vote
(
    id              INTEGER AUTO_INCREMENT PRIMARY KEY,
    actual_date     Date           NOT NULL,
    restaurant_id   INTEGER        NOT NULL,
    user_id         INTEGER        NOT NULL,
    CONSTRAINT vote_user_actual_date_idx UNIQUE (user_id, actual_date),
    FOREIGN KEY (restaurant_id) REFERENCES restaurant (id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);