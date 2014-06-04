insert into role values ('ROLE_USER', 'User');
insert into role values ('ROLE_ADMIN', 'Administrator');
insert into role values ('ROLE_REMOTE', 'Remote User');

insert into app_user values ('clarence', 'clarence', 'Clarence Ho', 'prospring3', '2011-10-21', 'prospring3', '2011-10-21');
insert into app_user values ('admin', 'admin', 'Administrator', 'prospring3', '2011-10-21', 'prospring3', '2011-10-21');
insert into app_user values ('remote', 'remote', 'Remote User', 'prospring3', '2011-10-21', 'prospring3', '2011-10-21');
insert into app_user values ('user', 'user', 'Normal User', 'prospring3', '2011-10-21', 'prospring3', '2011-10-21');

insert into user_role_detail values ('clarence','ROLE_ADMIN');
insert into user_role_detail values ('clarence','ROLE_USER');
insert into user_role_detail values ('admin','ROLE_ADMIN');
insert into user_role_detail values ('admin','ROLE_USER');
insert into user_role_detail values ('remote','ROLE_REMOTE');
insert into user_role_detail values ('user','ROLE_USER');

insert into category values ('Java', null);
insert into category values ('Spring', null);
insert into category values ('JPA', null);
insert into category values ('Spring Batch', 'Spring');
insert into category values ('Spring Integration', 'Spring');
insert into category values ('Spring Webflow', 'Spring');
insert into category values ('Spring Roo', 'Spring');
insert into category values ('Hibernate', 'JPA');
insert into category values ('Eclipse Link', 'JPA');
insert into category values ('Collections', 'Java');
insert into category values ('JSR-303', 'Java');

INSERT INTO `ENTRY` (`ID`, `SUBJECT`, `BODY`, `POST_DATE`, `CATEGORY_ID`, `SUB_CATEGORY_ID`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`, `LOCALE`, `IMPRESSIONS`, `LIKES`, `VERSION`) VALUES
  (1, 'sjfsdkljl', 'dsfasdfsadfsdfasdfsdafsd', '2014-05-14 00:00:00', 'Java', NULL, 'admin', '2014-05-14 00:00:00', '', '2014-05-14 00:00:00', 'ru_RU,uk_UA', 0, 0, 0),
  (2, 'sjfsdkljl', 'dsfasdfsadfsdfasdfsdafsd', '2014-05-14 00:00:00', 'Java', NULL, 'admin', '2014-05-14 00:00:00', '', '2014-05-14 00:00:00', 'ru_RU,uk_UA', 0, 0, 0),
  (3, 'sjfsdkljl', 'dsfasdfsadfsdfasdfsdafsd', '2014-05-14 00:00:00', 'Java', NULL, '', '2014-05-14 00:00:00', '', '2014-05-14 00:00:00', 'ru_RU,uk_UA', 0, 0, 0),
  (4, 'sjfsdkljl', 'dsfasdfsadfsdfasdfsdafsd', '2014-05-14 00:00:00', 'Java', NULL, '', '2014-05-14 00:00:00', '', '2014-05-14 00:00:00', 'ru_RU,uk_UA', 0, 0, 0),
  (5, 'sjfsdkljl', 'dsfasdfsadfsdfasdfsdafsd', '2014-05-14 00:00:00', 'Java', NULL, 'admin', '2014-05-14 00:00:00', '', '2014-05-14 00:00:00', 'ru_RU,uk_UA', 0, 0, 0),
  (6, 'sjfsdkljl', 'dsfasdfsadfsdfasdfsdafsd', '2014-05-14 00:00:00', 'Java', NULL, 'admin', '2014-05-14 00:00:00', '', '2014-05-14 00:00:00', 'ru_RU,uk_UA', 0, 0, 0),
  (7, 'sjfsdkljl', 'dsfasdfsadfsdfasdfsdafsd', '2014-05-14 00:00:00', 'Java', NULL, 'admin', '2014-05-14 00:00:00', '', '2014-05-14 00:00:00', 'ru_RU,uk_UA', 0, 0, 0),
  (8, 'sjfsdkljl', 'dsfasdfsadfsdfasdfsdafsd', '2014-05-14 00:00:00', 'Java', NULL, 'admin', '2014-05-14 00:00:00', '', '2014-05-14 00:00:00', 'ru_RU,uk_UA,en_US', 0, 0, 0),
  (9, 'sjfsdkljl', 'dsfasdfsadfsdfasdfsdafsd', '2014-05-14 00:00:00', 'Java', NULL, 'admin', '2014-05-14 00:00:00', '', '2014-05-14 00:00:00', 'ru_RU,uk_UA', 0, 0, 0),
  (10, 'sjfsdkljl', 'dsfasdfsadfsdfasdfsdafsd', '2014-05-14 00:00:00', 'Java', NULL, 'admin', '2014-05-14 00:00:00', '', '2014-05-14 00:00:00', 'ru_RU,uk_UA', 0, 0, 0),
  (11, 'sjfsdkljl', 'dsfasdfsadfsdfasdfsdafsd', '2014-05-13 00:00:00', 'Java', NULL, '', '2014-05-13 00:00:00', '', '2014-05-16 00:00:00', 'ru_RU,uk_UA', 0, 0, 0);
