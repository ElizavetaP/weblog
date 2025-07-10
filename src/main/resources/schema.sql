-- Таблица с постами
create table if not exists post(
  id bigserial primary key,
  title varchar(256) not null,
  textPreview varchar(256),
  likesCount integer default 0,
  image varchar(256)
  );


insert into post(title, textPreview, likesCount, image) values ('Бублик', 'Это пост про кота', 55, '1.jpg');
insert into post(title, textPreview, likesCount, image) values ('Барсик', 'Еще один пост про кота', 60, '2.jpg');
insert into post(title, textPreview, likesCount, image) values ('Снежок', 'Новый кот', 53, '3.jpg');
insert into post(title, textPreview, likesCount, image) values ('Василий', 'Кот', 55, '4.jpg');
insert into post(title, textPreview, likesCount, image) values ('Муся', 'Мой кот', 60, '5.jpg');
insert into post(title, textPreview, likesCount, image) values ('Урсула', 'Новый кот', 53, '6.jpg');

create table if not exists comment (
  id bigserial primary key,
  post_id bigint not null,
  text varchar(1024) not null,
  author varchar(128),
  created_at timestamp,
  foreign key (post_id) references post(id) on delete cascade
);

INSERT INTO comment (post_id, text, author, created_at) VALUES
(1, 'Красивый котик', 'Анна', '2024-07-01 10:15:00'),
(1, 'А я люблю собак', 'Игорь', '2024-07-01 10:20:00'),
(1, 'Хаха', 'Елена', '2024-07-01 10:35:00'),

(2, 'Чем вы его кормите?', 'Сергей', '2024-07-02 09:00:00'),
(2, 'Супер!', NULL, '2024-07-02 09:10:00'),

(3, 'Этот - лучший', 'Мария', '2024-07-03 08:30:00'),
(3, 'Похож на моего кота', 'Артем', '2024-07-03 08:45:00'),

(4, 'Красивый котик', 'Анна', '2024-07-01 10:15:00'),
(4, 'А я люблю собак', 'Игорь', '2024-07-01 10:20:00'),

(5, 'Хаха', 'Елена', '2024-07-01 10:35:00'),

(6, 'Хаха', 'Елена', '2024-07-01 10:35:00');

--Таблица с тегами
create table if not exists tag (
  id bigserial primary key,
  name varchar(64) not null unique
);

--Многие-ко-многим пост-тег
create table if not exists post_tag (
  post_id bigint not null,
  tag_id bigint not null,
  foreign key (post_id) references post(id) on delete cascade,
  foreign key (tag_id) references tag(id) on delete cascade,
  primary key (post_id, tag_id)
);

INSERT INTO tag (name) VALUES
('кот'),
('животные'),
('милота'),
('вопрос'),
('юмор');

INSERT INTO post_tag (post_id, tag_id) VALUES
(1, 1),  -- кот
(1, 2),  -- животные
(1, 3);  -- милота

INSERT INTO post_tag (post_id, tag_id) VALUES
(2, 1),  -- кот
(2, 4);  -- вопрос

INSERT INTO post_tag (post_id, tag_id) VALUES
(3, 1),  -- кот
(3, 5);  -- юмор

INSERT INTO post_tag (post_id, tag_id) VALUES
(4, 1),  -- кот
(4, 5);  -- юмор

INSERT INTO post_tag (post_id, tag_id) VALUES
(5, 1),  -- кот
(5, 2);  -- животные

INSERT INTO post_tag (post_id, tag_id) VALUES
(6, 1),  -- кот
(6, 2),  -- животные
(6, 3);  -- милота
