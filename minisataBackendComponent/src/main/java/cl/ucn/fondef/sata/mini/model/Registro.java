/*
 * Copyright (c) 2022 Fondef IDeA I+D.
 */

package cl.ucn.fondef.sata.mini.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "registro")
@ToString
@EqualsAndHashCode
public class Registro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    @Column(name = "id")
    private long id;

    @Getter
    @Setter
    @Column(name = "id_entidad")
    private long idEntidad;

    @Getter
    @Setter
    @Column(name = "fecha")
    private String fecha;

    @Getter
    @Setter
    @Column(name = "descripcion")
    private String descripcion;

    @Getter
    @Setter
    @Column(name = "tipo_registro")
    private String tipoRegistro;

    @Getter
    @Setter
    @Column(name = "id_usuario")
    private long idUsuario;
}
