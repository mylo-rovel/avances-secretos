package com.backendCore.gRPCcore.models;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

//@Entity
//@Table(name = "simuladores")
@ToString
@EqualsAndHashCode
public class Simulador {
    //@Id
    //@GeneratedValue(strategy=GenerationType.IDENTITY)
    @Getter
    @Setter //@Column(name = "usuario_id")
    private int usuario_id;

    @Getter @Setter //@Column(name = "nombre")
    private String nombre;

    @Getter @Setter //@Column(name = "apellido")
    private String apellido;

    @Getter @Setter //@Column(name = "rut")
    private String rut;

    @Getter @Setter //@Column(name = "correo")
    private String correo;

    @Getter @Setter //@Column(name = "contraseña")
    private String contraseña;

    @Getter @Setter //@Column(name = "rol")
    private String rol;

    @Getter @Setter //@Column(name = "estado")
    private boolean estado;
}
