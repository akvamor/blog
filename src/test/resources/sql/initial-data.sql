insert into role values ('ROLE_USER', 'User');
insert into role values ('ROLE_ADMIN', 'Administrator');
insert into role values ('ROLE_REMOTE', 'Remote User');

insert into app_user values ('admin', 'b6ec0b8e632cd935b69216fbc3b607eeb0bef101fbb391a8bf7c80e0d2c67cc9', 'Administrator', 'admin', '2011-10-21', 'admin', '2011-10-21');
insert into app_user values ('remote', 'b6ec0b8e632cd935b69216fbc3b607eeb0bef101fbb391a8bf7c80e0d2c67cc9', 'Remote User', 'admin', '2011-10-21', 'admin', '2011-10-21');
insert into app_user values ('user', 'b6ec0b8e632cd935b69216fbc3b607eeb0bef101fbb391a8bf7c80e0d2c67cc9', 'Normal User', 'admin', '2011-10-21', 'admin', '2011-10-21');

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
