/*
 * Copyright (c) 2022 Fondef IDeA I+D.
 */

package cl.ucn.fondef.sata.mini.model;

import cl.ucn.fondef.sata.mini.grpc.Domain;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "equipo")
@ToString
@EqualsAndHashCode
public class Equipo {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Getter
    @Setter
    @Column(name = "id")
    private long id;

    @Getter
    @Setter
    @Column(name = "nombre")
    private String nombre;

    @Getter
    @Setter
    @Column(name = "descripcion")
    private String descripcion;

    @Getter
    @Setter
    @Column(name = "url_repositorio ")
    private String urlRepositorio;

    @Getter
    @Setter
    @Column(name = "rut_configurador")
    private String rutConfigurador;
/*
    @Getter
    @Setter
    @Column(name = "estado")
    private Domain.EstadoEquipo estado;
    */
    @Getter
    @Setter
    @Column(name = "estado")
    private String estado;
}
