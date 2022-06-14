
CREATE TABLE registrousuarios
(
    rut_administrador      varchar(10),
    rut_usuario            varchar(10),
    PRIMARY KEY (rut_usuario, rut_administrador)
);

CREATE TABLE login
(
    correo              varchar(320) NOT NULL,
    contrasena          varchar(100) NOT NULL,
    rut_usuario         varchar(10)  NOT NULL UNIQUE,
    PRIMARY KEY (correo),
    FOREIGN KEY (rut_usuario) REFERENCES registrousuarios (rut_usuario)
);

CREATE TABLE usuario
(
    rut             varchar(10)  NOT NULL,
    nombre          varchar(30)  NOT NULL,
    apellido        varchar(30)  NOT NULL,
    correo          varchar(320) NOT NULL UNIQUE,
    rol             varchar(14)  NOT NULL,
    estado          tinyint      NOT NULL,
    PRIMARY KEY (rut),
    FOREIGN KEY (rut) REFERENCES registrousuarios (rut_usuario),
    FOREIGN KEY (correo) REFERENCES login (correo),
    CONSTRAINT ValidRol CHECK (rol ='Configurador' OR rol = 'Administrador' OR rol =  'Operador')
);

CREATE TABLE equipo
(
    id                  int(11)         NOT NULL AUTO_INCREMENT,
    nombre              varchar(30)     NOT NULL,
    descripcion         varchar(255)    NOT NULL,
    url_repo            varchar(100)    NOT NULL UNIQUE,
    estado              tinyint(3)      NOT NULL,
    rut_configurador    varchar(10)     NOT NULL,
    fecha_creacion      date            NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    FOREIGN KEY (rut_configurador) REFERENCES usuario (rut)
);

CREATE TABLE simulacion
(
    id                  int(11)         NOT NULL AUTO_INCREMENT,
    rut_operador        varchar(10)     NOT NULL,
    id_equipo           int(11)         NOT NULL,
    nombre              varchar(20)     NOT NULL UNIQUE,
    descripcion         varchar(100)    NOT NULL,
    fecha_creacion      date            NOT NULL DEFAULT CURRENT_TIMESTAMP,
    agua_caida          double          NOT NULL,
    fecha_ejecucion     date            NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    FOREIGN KEY (rut_operador) REFERENCES usuario (rut),
    FOREIGN KEY (id_equipo) REFERENCES equipo (id)
);

CREATE TABLE tipocomponente
(
    id                  int(11)        NOT NULL AUTO_INCREMENT,
    nombre              varchar(20)    NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE componentefisico
(
    id                  int(11)         NOT NULL AUTO_INCREMENT,
    id_tipo_compo       int(11)         NOT NULL,
    id_equipo           int(11)         NOT NULL,
    descripcion         varchar(100)    NOT NULL,
    pin                 tinyint(3)      NOT NULL,
    estado              tinyint(3)      NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (id_tipo_compo) REFERENCES tipocomponente (id),
    FOREIGN KEY (id_equipo) REFERENCES equipo (id)
);

CREATE TABLE secuencia
(
    id                  int(11)         NOT NULL AUTO_INCREMENT,
    id_tipo_compo       int(11)         NOT NULL,
    id_simulacion       int(11)         NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (id_tipo_compo) REFERENCES tipocomponente (id),
    FOREIGN KEY (id_simulacion) REFERENCES simulacion (id)
);

CREATE TABLE evento
(
    id                  int(11)         NOT NULL AUTO_INCREMENT,
    id_secuencia        int(11)         NOT NULL,
    intensidad          int(11)         NOT NULL,
    duracion            int(11)         NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (id_secuencia) REFERENCES secuencia (id)
);

CREATE TABLE imgequipo
(
    id                  int(11)         NOT NULL AUTO_INCREMENT,
    id_equipo           int(11)         NOT NULL,
    imagen              blob            NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (id_equipo) REFERENCES equipo (id)
);