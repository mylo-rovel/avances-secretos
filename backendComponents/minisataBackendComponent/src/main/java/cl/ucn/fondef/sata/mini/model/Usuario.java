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
@Table(name = "usuario")
@ToString
@EqualsAndHashCode
public class Usuario {

    @Id
    @Getter
    @Setter
    @Column(name = "rut")
    private String rut;

    @Getter
    @Setter
    @Column(name = "nombre")
    private String nombre;

    @Getter
    @Setter
    @Column(name = "apellido")
    private String apellido;

    @Getter
    @Setter
    @Column(name = "correo")
    private String correo;

    @Getter
    @Setter
    @Column(name = "rol")
    private String rol;

    @Getter
    @Setter
    @Column(name = "estado")
    private boolean estado;
}
