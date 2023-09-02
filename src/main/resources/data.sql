DELETE FROM user_role;
DELETE FROM vote;
DELETE FROM menu_item;
DELETE FROM restaurant;
DELETE FROM users;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password'),
       ('Admin', 'admin@gmail.com', 'admin'),
       ('Guest', 'guest@gmail.com', 'guest');

INSERT INTO user_role (role, user_id)
VALUES ('USER', 1),
       ('ADMIN', 2),
       ('USER', 2);

INSERT INTO restaurant (name, address, created)
VALUES ('Река', 'ул. Набережная, д.1', '2023-01-30 10:00:00'),
       ('Фукусима', 'ул. Ленина, д.13', '2022-06-29 13:10:00'),
       ('Вкусняшка', 'ул. Мира, д.15', '2021-04-29 16:45:00');

INSERT INTO menu_item (name, price, updated, restaurant_id)
VALUES ('Суп сельский', 180.00, '2023-03-09 10:00:00', 1),
       ('Пюре картофельное', 112.50, '2023-03-09 10:03:00', 3),
       ('Пюре гороховое', 92.50, '2023-03-09 10:03:00', 2),
       ('Котлета из свинины', 110.00, '2023-03-09 10:03:33', 2),
       ('Котлета из говядины', 125.00, '2023-03-09 10:04:00', 3),
       ('Чай черный', 70.00, '2023-03-09 10:05:33', 2),
       ('Чай черный', 80.00, '2023-03-09 10:05:33', 3);

INSERT INTO vote (date, restaurant_id, user_id)
VALUES ('2023-03-19', 2, 1),
       ('2023-03-19', 3, 2);
--        ('2023-03-19', 6, 100002);