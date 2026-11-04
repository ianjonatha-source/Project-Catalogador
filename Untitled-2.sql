CREATE DATABASE IF NOT EXISTS catalogador CHARACTER SET utf8mb4;
USE catalogador;

CREATE TABLE IF NOT EXISTS arquivos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    tipo VARCHAR(50),
    categoria VARCHAR(100),
    local_arquivo VARCHAR(50) NOT NULL,
    diretorio VARCHAR(500)
);