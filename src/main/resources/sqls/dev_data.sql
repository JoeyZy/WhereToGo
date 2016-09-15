use wheretogo;
INSERT INTO `users` (email, first_name, last_name, password, role, active) VALUES
  ('root@gmail.com', 'Root', 'Root', '$2a$10$HbZiH.4zZp4CLY7krwzt9udnO36T23LnqgqE.WXhpArZ4nvys7T0a', 'admin', 1),
  ('test@gmail.com', 'Test', 'Test', '$2a$10$xBVIkkIXVTc.K.frxLyOM.cQSZ2GABMphVbFEfCFZTh.reKWutt6u', 'user', 1),
  ('serg.tanchenko@gmail.com', 'Maria ', 'Anders', '$2a$10$HbZiH.4zZp4CLY7krwzt9udnO36T23LnqgqE.WXhpArZ4nvys7T0a', 'user', 1),
  ('fisika80@gmail.com', 'Borys ', 'Komarov', '$2a$10$VmCbEkcT/pBx.s3MYw15GepM7zkucxAmgS7JepsQ4uBF6H6BnXAwO', 'user', 1),
  ('xvxsergxvx@gmail.com', 'Ana ', 'Trujillo', '$2a$10$HbZiH.4zZp4CLY7krwzt9udnO36T23LnqgqE.WXhpArZ4nvys7T0a', 'user', 1);

INSERT INTO `groups` (id, name, description, location, picture, owner, deleted) VALUES
  (1, 'Kyiv office', 'The workers from Kyiv office', 'Kyiv, Ukraine', NULL, 1, 'N'),
  (2, 'Odessa office', 'The workers from Odessa office', 'Odessa, Ukraine', NULL, 2, 'N'),
  (3, 'London office', 'The workers from London office', 'London, UK', NULL, 1, 'N'),
  (4, 'Tokio office', 'The workers from Tokio office', 'Tokio, Japan', NULL, 3, 'N'),
  (5, 'Berlin office', 'The workers from Berlin office', 'Berlin, Germany', NULL, 4, 'N');


INSERT INTO `events` (`id`, `cost`, `deleted`, `description`, `endDateTime`, `location`, `name`, `startDateTime`, `currencyId`, `owner`) VALUES
  (1, 100, 'N' , 'Description of the event', concat(CURDATE(), ' 23:38:00'), 'Lepse Street, 6z, Kiev', 'Concert....',
   concat(CURDATE(), ' 22:00:00'), 1, 1),
  (2, 200, 'N' , 'Description of the event', concat(ADDDATE(CURDATE(), 1), ' 23:38:00'), 'Lepse Street, 6z, Kiev', 'Movie......',
   concat(ADDDATE(CURDATE(), 1), ' 22:00:00'), 2, 1),
  (3, 300, 'N' , 'Description of the event', concat(ADDDATE(CURDATE(), 2), ' 23:38:00'), 'Lepse Street, 6z, Kiev', 'Nature.....',
   concat(ADDDATE(CURDATE(), 2), ' 22:00:00'), 3, 1),
  (4, 400, 'N' , 'Description of the event', concat(ADDDATE(CURDATE(), 3), ' 23:38:00'), 'Lepse Street, 6z, Kiev', 'Pub........',
   concat(ADDDATE(CURDATE(), 3), ' 22:00:00'), 1, 2),
  (5, 500, 'N' , 'Description of the event', concat(ADDDATE(CURDATE(), 4), ' 23:38:00'), 'Lepse Street, 6z, Kiev', 'Sport......0',
   concat(ADDDATE(CURDATE(), 4), ' 22:00:00'), 2, 1),
  (9, 500, 'N' , 'Description of the event1', concat(ADDDATE(CURDATE(), 5), ' 23:38:00'), 'Lepse Street, 6z, Kiev', 'Sport......1',
   concat(ADDDATE(CURDATE(), 5), ' 22:00:00'), 2, 1),
  (10, 500, 'N' , 'Description of the event2', '2016-04-27 23:38:00', 'Lepse Street, 6z, Kiev', 'Sport......2', '2016-04-27 22:00:00', 2, 1),
  (11, 500, 'N' , 'Description of the event3', '2016-04-28 23:38:00', 'Lepse Street, 6z, Kiev', 'Sport......3', '2016-04-28 22:00:00', 2, 1),
  (6, 600, 'N' , 'Description of the event', '2016-04-22 23:38:00', 'Lepse Street, 6z, Kiev', 'Theatre....0', '2016-04-22 22:00:00', 3, 1),
  (12, 600, 'N' , 'Description of the event', '2016-04-23 23:38:00', 'Lepse Street, 6z, Kiev', 'Theatre....1', '2016-04-23 22:00:00', 3, 1),
  (13, 600, 'N' , 'Description of the event', '2016-04-24 23:38:00', 'Lepse Street, 6z, Kiev', 'Theatre....2', '2016-04-24 22:00:00', 3, 1),
  (14, 600, 'N' , 'Description of the event', '2016-04-24 23:38:00', 'Lepse Street, 6z, Kiev', 'Theatre....3', '2016-04-24 22:00:00', 3, 1),
  (15, 600, 'N' , 'Description of the event', '2016-04-24 23:38:00', 'Lepse Street, 6z, Kiev', 'Theatre....4', '2016-04-24 22:00:00', 3, 1),
  (7, 700, 'N' , 'Description of the event', '2016-04-24 23:38:00', 'Lepse Street, 6z, Kiev', 'Trip.......', '2016-04-24 13:38:00', 1, 1),
  (8, 800, 'N' , 'Description of the event', '2016-04-24 23:38:00', 'Lepse Street, 6z, Kiev', 'Other......', '2016-04-24 22:00:00', 2, 1);


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

