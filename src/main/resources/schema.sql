-- Таблица с постами
create table if not exists post(
  id bigserial primary key,
  title varchar(256) not null,
  textPreview varchar(256),
  likesCount integer);


insert into post(title, textPreview, likesCount) values ('Cat 1', 'Это пост про кота', 55);
insert into post(title, textPreview, likesCount) values ('Cat 2', 'Еще один пост про кота', 60);
insert into post(title, textPreview, likesCount) values ('Cat 3', 'Новый кот', 53);

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
(3, 'Похож на моего кота', 'Артем', '2024-07-03 08:45:00');

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