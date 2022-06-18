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
    nombre          varchar(10)     NOT NULL,
    PRIMARY KEY (id)
);

INSERT INTO `entidad` (`nombre`) VALUES ('USUARIO');
INSERT INTO `entidad` (`nombre`) VALUES ('EQUIPO');
INSERT INTO `entidad` (`nombre`) VALUES ('SIMULACION');

CREATE TABLE registro
(
    id                  bigint(20)      NOT NULL AUTO_INCREMENT,
    id_entidad          bigint(20)      NOT NULL,
    fecha               timestamp       NOT NULL,
    descripcion         varchar(255)    NOT NULL,
    tipo_registro       varchar(25)     NOT NULL,
    id_usuario          bigint(20)      NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (id_entidad) REFERENCES entidad (id),
    FOREIGN KEY (id_usuario) REFERENCES usuario (id)
);

CREATE TABLE equipo
(
    id                  bigint(20)      NOT NULL AUTO_INCREMENT,
    nombre              varchar(30)     NOT NULL UNIQUE,
    descripcion         varchar(255)    NOT NULL,
    url_repositorio     varchar(100)    NOT NULL,
    rut_configurador    varchar(10)     NOT NULL,
    estado              varchar(15)     NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (rut_configurador) REFERENCES usuario (rut)
);

CREATE TABLE archivoequipo
(
    id                  bigint(20)      NOT NULL AUTO_INCREMENT,
    id_equipo           bigint(20)      NOT NULL,
    data                blob            NOT NULL,
    tipo                varchar(10)     NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (id_equipo) REFERENCES equipo (id)
);

CREATE TABLE componentefisico
(
    id                  bigint(20)      NOT NULL AUTO_INCREMENT,
    id_equipo           bigint(20)      NOT NULL,
    nombre              varchar(30)     NOT NULL,
    descripcion         varchar(100)    NOT NULL,
    pin                 tinyint(3)      NOT NULL,
    url                 varchar(100)    NOT NULL,
    estado              varchar(20)     NOT NULL,
    conexion            varchar(20)     NOT NULL,
    tipo                varchar(20)     NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (id_equipo) REFERENCES equipo (id)
);

CREATE TABLE simulacion
(
    id                  bigint(20)      NOT NULL AUTO_INCREMENT,
    rut_operador        varchar(10)     NOT NULL,
    id_equipo           bigint(20)      NOT NULL,
    nombre              varchar(30)     NOT NULL UNIQUE,
    descripcion         varchar(100)    NOT NULL,
    fecha_creacion      timestamp       NOT NULL,
    agua_caida          double          NOT NULL,
    fecha_ejecucion     date            NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (rut_operador) REFERENCES usuario (rut),
    FOREIGN KEY (id_equipo) REFERENCES equipo (id)
);

CREATE TABLE secuencia
(
    id                  bigint(20)         NOT NULL AUTO_INCREMENT,
    id_simulacion       bigint(20)         NOT NULL,
    id_componente       bigint(20)         NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (id_simulacion) REFERENCES simulacion (id),
    FOREIGN KEY (id_componente) REFERENCES componentefisico (id)
);

CREATE TABLE evento
(
    id                  bigint(20)      NOT NULL AUTO_INCREMENT,
    id_secuencia        bigint(20)      NOT NULL,
    intensidad          int(11)         NOT NULL,
    duracion            int(11)         NOT NULL,
    posicion            int(11)         NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (id_secuencia) REFERENCES secuencia (id)
);

