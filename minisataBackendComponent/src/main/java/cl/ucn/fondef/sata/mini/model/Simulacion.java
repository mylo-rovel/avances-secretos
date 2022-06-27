/*
 * Copyright (c) 2022 Fondef IDeA I+D.
 */

package cl.ucn.fondef.sata.mini.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

/**
 * The type Simulacion.
 */
@Entity
@Table(name = "simulacion")
@ToString
@EqualsAndHashCode
public class Simulacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    @Column(name = "id")
    private long id;

    @Getter
    @Setter
    @Column(name = "rut_operador")
    private String rutOperador;

    @Getter
    @Setter
    @Column(name = "id_equipo")
    private Long idEquipo;

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
    @Column(name = "fecha_ejecucion")
    private String fechaEjecucion;

    @Getter
    @Setter
    @Column(name = "agua_caida")
    private Double aguaCaida;
}
