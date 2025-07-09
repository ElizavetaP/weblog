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