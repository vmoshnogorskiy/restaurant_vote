DELETE FROM user_role;
DELETE FROM vote;
DELETE FROM menu_item;
DELETE FROM restaurant;
DELETE FROM users;

INSERT INTO users (name, email, password, calories_per_day)
VALUES ('User', 'user@yandex.ru', '{noop}password', 2000),
       ('Admin', 'admin@gmail.com', '{noop}admin', 1500),
       ('Guest', 'guest@gmail.com', '{noop}guest', 1200);

INSERT INTO user_role (role, user_id)
VALUES ('USER', 1),
       ('ADMIN', 2),
       ('USER', 2);

INSERT INTO restaurant (name, address, created)
VALUES ('Река', 'ул. Набережная, д.1', '2023-01-30 10:00:00'),
       ('Фукусима', 'ул. Ленина, д.13', '2022-06-29 13:10:00'),
       ('Вкусняшка', 'ул. Мира, д.15', '2021-04-29 16:45:00');

INSERT INTO menu_item (name, price, updated, restaurant_id)
VALUES ('Суп сельский', 180.00, now(), 1),
       ('Пюре картофельное', 122.50, now(), 3),
       ('Пюре гороховое', 92.50, now(), 2),
       ('Котлета из свинины', 110.00, now(), 2),
       ('Котлета из говядины', 125.00, now(), 3),
       ('Чай черный', 70.00, now(), 2),
       ('Чай черный', 80.00, now(), 3);

INSERT INTO vote (date, restaurant_id, user_id)
VALUES ('2023-09-04', 2, 1),
       ('2023-09-04', 3, 3);
--        ('2023-03-19', 6, 100002);