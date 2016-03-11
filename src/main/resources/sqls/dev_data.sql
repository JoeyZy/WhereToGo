INSERT INTO `users` (email, first_name, last_name, password) VALUES ('root@gmail.com', 'Root', 'Root', 'root');

INSERT INTO `events` VALUES
  (1, 0, 'description', '2017-02-22 23:38:00', 'Concert....', '2017-02-17 22:00:00', 1),
  (2, 1, 'description', '2017-02-23 23:43:00', 'Movie...', '2017-02-16 23:43:00', 1),
  (3, 1, 'description', '2016-02-23 23:43:00', 'Nature...', '2016-02-15 23:43:00', 1),
  (4, 0, 'description', '2016-02-23 23:43:00', 'Pub...', '2016-02-14 23:43:00', 1),
  (5, 0, 'description', '2017-02-23 23:43:00', 'Sport...', '2017-02-13 23:43:00', 1),
  (6, 0, 'description', '2017-02-23 23:43:00', 'Theatre...', '2017-02-12 23:43:00', 1),
  (7, 0, 'description', '2017-02-23 23:43:00', 'Trip...', '2017-02-11 23:43:00', 1),
  (8, 0, 'description', '2017-02-23 23:43:00', 'Other...', '2017-02-10 23:43:00', 1);


INSERT INTO `events_categories` VALUES (1, 1), (2, 2), (3, 3), (4, 4), (5, 5), (6, 6), (7, 7), (8, 8);