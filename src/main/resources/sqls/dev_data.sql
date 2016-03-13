INSERT INTO `users` (email, first_name, last_name, password) VALUES ('root@gmail.com', 'Root', 'Root', 'root');

INSERT INTO `events` VALUES
  (1, 100, 'Description of the event', '2016-06-22 23:38:00', 'вул. Костянтинівська, 2a, Kiev', 'Concert....', '2016-02-22 22:00:00', 1, 1),
  (2, 200, 'Description of the event', '2016-06-22 23:38:00', 'вул. Костянтинівська, 2a, Kiev', 'Movie...', '2016-06-22 23:38:00', 2, 1),
  (3, 300, 'Description of the event', '2016-06-22 23:38:00', 'вул. Костянтинівська, 2a, Kiev', 'Nature...', '2016-06-22 23:38:00', 3, 1),
  (4, 400, 'Description of the event', '2016-06-22 23:38:00', 'вул. Костянтинівська, 2a, Kiev', 'Pub...', '2016-06-22 23:38:00', 1, 1),
  (5, 500, 'Description of the event', '2016-06-22 23:38:00', 'вул. Костянтинівська, 2a, Kiev', 'Sport...', '2016-06-22 23:38:00', 2, 1),
  (6, 600, 'Description of the event', '2016-06-22 23:38:00', 'вул. Костянтинівська, 2a, Kiev', 'Theatre...', '2016-06-22 23:38:00', 3, 1),
  (7, 700, 'Description of the event', '2016-06-22 23:38:00', 'вул. Костянтинівська, 2a, Kiev', 'Trip...', '2016-06-22 23:38:00', 1, 1),
  (8, 800, 'Description of the event', '2016-06-22 23:38:00', 'вул. Костянтинівська, 2a, Kiev', 'Other...', '2016-06-22 23:38:00', 2, 1);


INSERT INTO `events_categories` VALUES (1, 1), (2, 2), (3, 3), (4, 4), (5, 5), (6, 6), (7, 7), (8, 8);