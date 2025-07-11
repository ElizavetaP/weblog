-- Очистка данных
DELETE FROM comment;
DELETE FROM post_tag;
DELETE FROM post;
DELETE FROM tag;

-- Сброс автоинкремента
ALTER TABLE post ALTER COLUMN id RESTART WITH 1;
ALTER TABLE tag ALTER COLUMN id RESTART WITH 1;
ALTER TABLE comment ALTER COLUMN id RESTART WITH 1;

INSERT INTO post (title, textPreview, image, likesCount) VALUES
('Тест пост 1', 'Содержимое 1', '1.jpg', 0);
INSERT INTO post (title, textPreview, image, likesCount) VALUES
('Тест пост 2', 'Содержимое 2', '2.jpg', 2);