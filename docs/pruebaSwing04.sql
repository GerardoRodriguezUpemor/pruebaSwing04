DROP TABLE IF EXISTS usuario;
DROP TABLE IF EXISTS tipo_usuario;

CREATE TABLE tipo_usuario(
    id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT
    ,nombre VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE usuario(
    id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT
    ,nombre VARCHAR(255) NOT NULL
    ,apellidos VARCHAR(255) NOT NULL
    ,email VARCHAR(255) NOT NULL
    ,pasexsword VARCHAR(255) NOT NULL
    ,fecha_de_nacimiento DATE
    ,tipo_usuario INTEGER NOT NULL
    ,FOREIGN KEY (tipo_usuario) REFERENCES tipo_usuario(id)
);

