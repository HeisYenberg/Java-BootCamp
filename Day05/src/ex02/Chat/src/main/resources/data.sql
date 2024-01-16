INSERT INTO users(login, password)
VALUES ('butterba', 'password1'),
       ('naboojot', 'password2'),
       ('strangem', 'password3'),
       ('jacquelp', 'password4'),
       ('omanytea', 'password5');

INSERT INTO chatrooms(name, owner)
VALUES ('Chatroom1', 1),
       ('Chatroom2', 1),
       ('Chatroom3', 1),
       ('Chatroom4', 2),
       ('Chatroom5', 2);

INSERT INTO messages(author, room, message, date_time)
VALUES (1, 1, 'Hello, im butterba', '2023-12-28 10:00:00'),
       (2, 1, 'Hi, im naboojot', '2023-12-28 10:00:10'),
       (3, 1, 'Hey guys, im strangem', '2023-12-28 10:01:10'),
       (4, 1, 'Hey-hey im jacquelp', '2023-12-28 10:01:40'),
       (5, 1, 'Nice to meet you, im omanytea', '2023-12-28 10:02:00');
