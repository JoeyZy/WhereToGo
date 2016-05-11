INSERT INTO `users` (email, first_name, last_name, password, role) VALUES ('root@gmail.com', 'Root', 'Root', 'root', 'admin');
INSERT INTO `users` (email, first_name, last_name, password, role) VALUES ('test@gmail.com', 'Test', 'Test', 'test', 'user');
INSERT INTO `users` (email, first_name, last_name, password, role) VALUES ('serg.tanchenko@gmail.com', 'Maria ', 'Anders', 'root', 'user');
INSERT INTO `users` (email, first_name, last_name, password, role) VALUES ('xvxsergxvx@gmail.com', 'Ana ', 'Trujillo', 'root', 'user');

INSERT INTO `events` (`id`, `cost`, `deleted`, `description`, `endDateTime`, `location`, `name`, `startDateTime`, `currencyId`, `owner`) VALUES
  (1, 100, 0, 'Description of the event', concat(CURDATE(), ' 23:38:00'), 'Lepse Street, 6z, Kiev', 'Concert....',
   concat(CURDATE(), ' 22:00:00'), 1, 1),
  (2, 200, 0, 'Description of the event', concat(ADDDATE(CURDATE(), 1), ' 23:38:00'), 'Lepse Street, 6z, Kiev', 'Movie......',
   concat(ADDDATE(CURDATE(), 1), ' 22:00:00'), 2, 1),
  (3, 300, 0, 'Description of the event', concat(ADDDATE(CURDATE(), 2), ' 23:38:00'), 'Lepse Street, 6z, Kiev', 'Nature.....',
   concat(ADDDATE(CURDATE(), 2), ' 22:00:00'), 3, 1),
  (4, 400, 0, 'Description of the event', concat(ADDDATE(CURDATE(), 3), ' 23:38:00'), 'Lepse Street, 6z, Kiev', 'Pub........',
   concat(ADDDATE(CURDATE(), 3), ' 22:00:00'), 1, 2),
  (5, 500, 0, 'Description of the event', concat(ADDDATE(CURDATE(), 4), ' 23:38:00'), 'Lepse Street, 6z, Kiev', 'Sport......0',
   concat(ADDDATE(CURDATE(), 4), ' 22:00:00'), 2, 1),
  (9, 500, 0, 'Description of the event1', concat(ADDDATE(CURDATE(), 5), ' 23:38:00'), 'Lepse Street, 6z, Kiev', 'Sport......1',
   concat(ADDDATE(CURDATE(), 5), ' 22:00:00'), 2, 1),
  (10, 500, 0, 'Description of the event2', '2016-04-27 23:38:00', 'Lepse Street, 6z, Kiev', 'Sport......2', '2016-04-27 22:00:00', 2, 1),
  (11, 500, 0, 'Description of the event3', '2016-04-28 23:38:00', 'Lepse Street, 6z, Kiev', 'Sport......3', '2016-04-28 22:00:00', 2, 1),
  (6, 600, 0, 'Description of the event', '2016-04-22 23:38:00', 'Lepse Street, 6z, Kiev', 'Theatre....0', '2016-04-22 22:00:00', 3, 1),
  (12, 600, 0, 'Description of the event', '2016-04-23 23:38:00', 'Lepse Street, 6z, Kiev', 'Theatre....1', '2016-04-23 22:00:00', 3, 1),
  (13, 600, 0, 'Description of the event', '2016-04-24 23:38:00', 'Lepse Street, 6z, Kiev', 'Theatre....2', '2016-04-24 22:00:00', 3, 1),
  (14, 600, 0, 'Description of the event', '2016-04-24 23:38:00', 'Lepse Street, 6z, Kiev', 'Theatre....3', '2016-04-24 22:00:00', 3, 1),
  (15, 600, 0, 'Description of the event', '2016-04-24 23:38:00', 'Lepse Street, 6z, Kiev', 'Theatre....4', '2016-04-24 22:00:00', 3, 1),
  (7, 700, 0, 'Description of the event', '2016-04-24 23:38:00', 'Lepse Street, 6z, Kiev', 'Trip.......', '2016-04-24 13:38:00', 1, 1),
  (8, 800, 0, 'Description of the event', '2016-04-24 23:38:00', 'Lepse Street, 6z, Kiev', 'Other......', '2016-04-24 22:00:00', 2, 1);

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