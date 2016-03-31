INSERT INTO `users` (email, first_name, last_name, password) VALUES ('root@gmail.com', 'Root', 'Root', 'root');
INSERT INTO `users` (email, first_name, last_name, password) VALUES ('serg.tanchenko@gmail.com', 'Maria ', 'Anders', 'root');
INSERT INTO `users` (email, first_name, last_name, password) VALUES ('xvxsergxvx@gmail.com', 'Ana ', 'Trujillo', 'root');

INSERT INTO `events` (`id`, `cost`, `deleted`, `description`, `endDateTime`, `location`, `name`, `startDateTime`, `currencyId`, `owner`) VALUES
  (1, 100, 0, 'Description of the event', '2017-06-11 23:38:00', 'вул. Костянтинівська, 2a, Kiev', 'Concert....', '2017-02-22 22:00:00', 1, 1),
  (2, 200, 0, 'Description of the event', '2017-06-12 23:38:00', 'вул. Костянтинівська, 2a, Kiev', 'Movie......', '2017-06-22 22:00:00', 2, 1),
  (3, 300, 0, 'Description of the event', '2017-06-13 23:38:00', 'вул. Костянтинівська, 2a, Kiev', 'Nature.....', '2017-06-22 22:00:00', 3, 1),
  (4, 400, 0, 'Description of the event', '2017-06-14 23:38:00', 'вул. Костянтинівська, 2a, Kiev', 'Pub........', '2017-06-22 22:00:00', 1, 1),
  (5, 500, 0, 'Description of the event', '2017-06-15 23:38:00', 'вул. Костянтинівська, 2a, Kiev', 'Sport......', '2017-06-22 22:00:00', 2, 1),
  (6, 600, 0, 'Description of the event', '2017-06-22 23:38:00', 'вул. Костянтинівська, 2a, Kiev', 'Theatre....', '2017-06-22 22:00:00', 3, 1),
  (7, 700, 0, 'Description of the event', '2017-06-22 23:38:00', 'вул. Костянтинівська, 2a, Kiev', 'Trip.......', '2017-06-22 13:38:00', 1, 1),
  (8, 800, 0, 'Description of the event', '2017-06-22 23:38:00', 'вул. Костянтинівська, 2a, Kiev', 'Other......', '2017-06-22 22:00:00', 2, 1);

INSERT INTO `events_categories` VALUES (1, 1), (2, 2), (3, 3), (4, 4), (5, 5), (6, 6), (7, 7), (8, 8);