/*
 * Copyright (c) 2022 Fondef IDeA I+D.
 */

package cl.ucn.fondef.sata.mini.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "login")
@ToString @EqualsAndHashCode
public class Login {

    @Id
    @Getter
    @Setter
    @Column(name = "correo")
    private String correo;

    @Getter
    @Setter
    @Column(name = "contrasena")
    private String contrasena;

    @Getter
    @Setter
    @Column(name = "rut_usuario")
    private String rutUsuario;
}