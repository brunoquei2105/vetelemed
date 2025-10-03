CREATE TABLE IF NOT EXISTS tb_user (
    id INT PRIMARY KEY AUTO_INCREMENT,
    login VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL
);
ALTER TABLE tb_user ALTER COLUMN id RESTART WITH 6;
INSERT INTO tb_user (id, login, password) VALUES
(1, 'admin@vetelemed.com.br', '$2a$10$wQw8Qw8Qw8Qw8Qw8Qw8QwOQw8Qw8Qw8Qw8Qw8Qw8Qw8Qw8Qw8Qw8'),
(2, 'user1@vetelemed.com.br', '$2a$10$wQw8Qw8Qw8Qw8Qw8Qw8QwOQw8Qw8Qw8Qw8Qw8Qw8Qw8Qw8Qw8Qw8'),
(3, 'user2@vetelemed.com.br', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZag0uJ8m4b5j5j5j5j5j5j5j5j5j5e'),
(4, 'user3@vetelemed.com.br', '$2a$10$7eqJtq98hPqEX7fNZaFWoOaYkz8v2X0d3b7h6c1r5H3j6K5jF6eWm'),
(5, 'user4@vetelemed.com.br', '$2a$10$EixZaYVK1fsbw1ZfbX3OXePaWxn96p36c6b8b5y5j5j5j5j5j5j5e');