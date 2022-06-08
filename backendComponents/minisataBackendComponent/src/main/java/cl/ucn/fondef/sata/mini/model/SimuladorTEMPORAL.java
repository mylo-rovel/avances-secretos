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
@Table(name = "simulador")
@ToString @EqualsAndHashCode

public class SimuladorTEMPORAL {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Getter
    @Setter
    @Column(name = "id")
    private Long id;

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
    @Column(name = "enlace_repo")
    private String enlaceRepo;

    @Getter
    @Setter
    @Column(name = "estado")
    private String estado;

    @Getter
    @Setter
    @Column(name = "rut_configurador")
    private String rutConfigurador;

}
