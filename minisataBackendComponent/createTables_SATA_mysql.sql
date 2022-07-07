CREATE TABLE usuario
(
    id              bigint(20)      NOT NULL AUTO_INCREMENT,
    rut             varchar(10)     NOT NULL UNIQUE,
    email          varchar(320)     NOT NULL UNIQUE,
    password        varchar(100)    NOT NULL,
    nombre          varchar(30)     NOT NULL,
    apellido        varchar(30)     NOT NULL,
    rol             varchar(15)     NOT NULL,
    estado          varchar(15)     NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE entidad
(
    id              bigint(20)      NOT NULL AUTO_INCREMENT,
    nombre          varchar(15)     NOT NULL UNIQUE,
    PRIMARY KEY (id)
);

INSERT INTO `entidad` (`nombre`) VALUES ('USUARIO');
INSERT INTO `entidad` (`nombre`) VALUES ('EQUIPO');
INSERT INTO `entidad` (`nombre`) VALUES ('SIMULACION');

CREATE TABLE registro
(
    id                  bigint(20)      NOT NULL AUTO_INCREMENT,
    id_usuario          bigint(20)      NOT NULL,
    id_entidad          bigint(20)      NOT NULL,
    fecha               timestamp       NOT NULL,
    descripcion         varchar(255)    NOT NULL,
    tipo_registro       varchar(25)     NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (id_usuario) REFERENCES usuario (id),
    FOREIGN KEY (id_entidad) REFERENCES entidad (id)
);

CREATE TABLE equipo
(
    id                  bigint(20)      NOT NULL AUTO_INCREMENT,
    id_configurador     bigint(20)      NOT NULL,
    nombre              varchar(30)     NOT NULL UNIQUE,
    descripcion         varchar(255)    NOT NULL,
    url_repositorio     varchar(100)    NOT NULL,
    estado              varchar(15)     NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (id_configurador) REFERENCES usuario (id)
);

CREATE TABLE archivo
(
    id                  bigint(20)      NOT NULL AUTO_INCREMENT,
    id_equipo           bigint(20)      NOT NULL,
    data                blob            NOT NULL,
    tipo                varchar(10)     NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (id_equipo) REFERENCES equipo (id)
);

CREATE TABLE placa
(
    id                  bigint(20)      NOT NULL AUTO_INCREMENT,
    id_equipo           bigint(20)      NOT NULL,
    nombre              varchar(30)     NOT NULL,
    descripcion         varchar(255)    NOT NULL,
    tipo                varchar(20)     NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (id_equipo) REFERENCES equipo (id)
);


CREATE TABLE simulacion
(
    id                  bigint(20)      NOT NULL AUTO_INCREMENT,
    id_equipo           bigint(20)      NOT NULL,
    id_operador         bigint(20)      NOT NULL,
    nombre              varchar(30)     NOT NULL UNIQUE,
    descripcion         varchar(255)    NOT NULL,
    fecha_creacion      timestamp       NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (id_equipo) REFERENCES equipo (id),
    FOREIGN KEY (id_operador) REFERENCES usuario (id)
);

CREATE TABLE ejecucion
(
    id                  bigint(20)      NOT NULL AUTO_INCREMENT,
    id_simulacion       bigint(20)      NOT NULL,
    agua_caida          double          NOT NULL DEFAULT '0',
    fecha_ejecucion     timestamp       NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (id_simulacion) REFERENCES simulacion (id)
);


CREATE TABLE componente
(
    id                  bigint(20)      NOT NULL AUTO_INCREMENT,
    id_equipo           bigint(20)      NOT NULL,
    nombre              varchar(30)     NOT NULL,
    descripcion         varchar(255)    NOT NULL,
    url                 varchar(100)    NOT NULL,
    estado              varchar(20)     NOT NULL,
    tipo                varchar(20)     NOT NULL,
    tipo_placa          varchar(30)     NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (id_equipo) REFERENCES equipo (id)
);


CREATE TABLE pin
(
    id                  bigint(20)      NOT NULL AUTO_INCREMENT,
    id_placa            bigint(20)      NOT NULL,
    id_componente       bigint(20)      NOT NULL,
    nombre              varchar(30)     NOT NULL,
    numero              int(11)         NOT NULL,
    descripcion         varchar(255)    NOT NULL,
    conexion            varchar(20)     NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (id_placa) REFERENCES placa (id),
    FOREIGN KEY (id_componente) REFERENCES componente (id)
);

CREATE TABLE secuencia
(
    id                  bigint(20)         NOT NULL AUTO_INCREMENT,
    id_componente       bigint(20)         NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (id_componente) REFERENCES componente (id)
);

CREATE TABLE evento
(
    id                  bigint(20)      NOT NULL AUTO_INCREMENT,
    id_secuencia        bigint(20)      NOT NULL,
    id_ejecucion        bigint(20)      NOT NULL,
    intensidad          int(11)         NOT NULL,
    duracion            int(11)         NOT NULL,
    posicion            int(11)         NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (id_secuencia) REFERENCES secuencia (id)
);


CREATE TABLE simulacioncomponente
(
    id                  bigint(20)      NOT NULL AUTO_INCREMENT,
    id_simulacion       bigint(20)      NOT NULL,
    id_componente       bigint(20)      NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (id_simulacion) REFERENCES simulacion (id),
    FOREIGN KEY (id_componente) REFERENCES componente (id)
);

CREATE TABLE ejecucionsecuencia
(
    id                  bigint(20)      NOT NULL AUTO_INCREMENT,
    id_ejecucion        bigint(20)      NOT NULL,
    id_secuencia        bigint(20)      NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (id_ejecucion) REFERENCES ejecucion (id),
    FOREIGN KEY (id_secuencia) REFERENCES secuencia (id)
);
