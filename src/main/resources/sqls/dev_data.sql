INSERT INTO `users` (email, first_name, last_name, password) VALUES ('root@gmail.com', 'Root', 'Root', 'root');
INSERT INTO `users` (email, first_name, last_name, password) VALUES ('serg.tanchenko@gmail.com', 'Maria ', 'Anders', 'root');
INSERT INTO `users` (email, first_name, last_name, password) VALUES ('xvxsergxvx@gmail.com', 'Ana ', 'Trujillo', 'root');

INSERT INTO `events` (`id`, `cost`, `deleted`, `description`, `endDateTime`, `location`, `name`, `startDateTime`, `currencyId`, `owner`) VALUES
  (1, 100, 0, 'Description of the event', '2016-04-11 23:38:00', 'вул. Костянтинівська, 2a, Kiev', 'Concert....', '2016-04-11 22:00:00', 1, 1),
  (2, 200, 0, 'Description of the event', '2016-04-12 14:38:00', 'вул. Костянтинівська, 2a, Kiev', 'Movie......', '2016-04-12 12:00:00', 2, 1),
  (3, 300, 0, 'Description of the event', '2016-04-13 16:38:00', 'вул. Костянтинівська, 2a, Kiev', 'Nature.....', '2016-04-13 14:00:00', 3, 1),
  (4, 400, 0, 'Description of the event', '2016-04-14 15:38:00', 'вул. Костянтинівська, 2a, Kiev', 'Pub........', '2016-04-14 15:00:00', 1, 1),
  (5, 500, 0, 'Description of the event', '2016-04-14 23:38:00', 'вул. Костянтинівська, 2a, Kiev', 'Sport......0', '2016-04-14 10:00:00', 2, 1),
  (9, 500, 0, 'Description of the event1', '2016-04-15 23:38:00', 'вул. Костянтинівська, 2a, Kiev', 'Sport......1', '2016-05-22 22:00:00', 2, 1),
  (10, 500, 0, 'Description of the event2', '2017-07-15 23:38:00', 'вул. Костянтинівська, 2a, Kiev', 'Sport......2', '2017-05-22 22:00:00', 2, 1),
  (11, 500, 0, 'Description of the event3', '2017-07-15 23:38:00', 'вул. Костянтинівська, 2a, Kiev', 'Sport......3', '2017-05-22 22:00:00', 2, 1),
  (6, 600, 0, 'Description of the event', '2017-07-22 23:38:00', 'вул. Костянтинівська, 2a, Kiev', 'Theatre....0', '2017-05-22 22:00:00', 3, 1),
  (12, 600, 0, 'Description of the event', '2017-07-22 23:38:00', 'вул. Костянтинівська, 2a, Kiev', 'Theatre....1', '2017-06-22 22:00:00', 3, 1),
  (13, 600, 0, 'Description of the event', '2017-07-22 23:38:00', 'вул. Костянтинівська, 2a, Kiev', 'Theatre....2', '2017-06-22 22:00:00', 3, 1),
  (14, 600, 0, 'Description of the event', '2017-06-22 23:38:00', 'вул. Костянтинівська, 2a, Kiev', 'Theatre....3', '2017-06-22 22:00:00', 3, 1),
  (15, 600, 0, 'Description of the event', '2017-06-22 23:38:00', 'вул. Костянтинівська, 2a, Kiev', 'Theatre....4', '2017-06-22 22:00:00', 3, 1),
  (7, 700, 0, 'Description of the event', '2017-07-22 23:38:00', 'вул. Костянтинівська, 2a, Kiev', 'Trip.......', '2017-06-22 13:38:00', 1, 1),
  (8, 800, 0, 'Description of the event', '2017-07-22 23:38:00', 'вул. Костянтинівська, 2a, Kiev', 'Other......', '2017-06-22 22:00:00', 2, 1);

INSERT INTO `events_categories` (category_id, event_id) VALUES
  (1, 1),
  (2, 2),
  (3, 3),
  (4, 4),
  (5, 5), # Sport0
  (5, 9), # Sport1
  (5, 10), # Sport2
  (5, 11), # Sport3
  (6, 6), # Theatre0
  (6, 12), # Theatre1
  (6, 13), # Theatre2
  (6, 14), # Theatre3
  (6, 15), # Theatre4
  (7, 7),
  (8, 8);