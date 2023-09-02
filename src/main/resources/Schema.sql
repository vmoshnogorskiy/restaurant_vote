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
    name            VARCHAR        NOT NULL,
    address         VARCHAR        NOT NULL,
    created         TIMESTAMP      NOT NULL
);

CREATE TABLE menu_item
(
    id              INTEGER AUTO_INCREMENT PRIMARY KEY,
    name            VARCHAR        NOT NULL,
    price           NUMERIC(9, 2)  NOT NULL,
    updated         TIMESTAMP      NOT NULL,
    restaurant_id   INTEGER        NOT NULL,
    --user_id         INTEGER        NOT NULL,
    --FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (restaurant_id) REFERENCES restaurant (id) ON DELETE CASCADE
);
CREATE UNIQUE INDEX menu_unique_restaurant_updated_idx ON menu_item (restaurant_id, updated);
--CREATE INDEX menu_user_idx ON menu_item (user_id);

CREATE TABLE vote
(
    id              INTEGER AUTO_INCREMENT PRIMARY KEY,
    date            Date           NOT NULL,
    restaurant_id   INTEGER        NOT NULL,
    user_id         INTEGER        NOT NULL,
    CONSTRAINT vote_restaurant_user_date_idx UNIQUE (restaurant_id, user_id, date),
    FOREIGN KEY (restaurant_id) REFERENCES restaurant (id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);