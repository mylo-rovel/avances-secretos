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
    @Column(name = "id_equipo")
    private long id_equipo;

    @Getter
    @Setter
    @Column(name = "id_operador")
    private long id_operador;

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
    @Column(name = "fecha_creacion")
    private String fecha_creacion;
    //
    @Getter
    @Setter
    @Column(name = "caudal")
    private long caudal;

    @Getter
    @Setter
    @Column(name = "temperatura")
    private long temperatura;

    @Getter
    @Setter
    @Column(name = "pluviometro")
    private long pluviometro;

    @Getter
    @Setter
    @Column(name = "presion")
    private long presion;

    @Getter
    @Setter
    @Column(name = "humedad")
    private long humedad;

    @Getter
    @Setter
    @Column(name = "id_ejecucion")
    private long id_ejecucion;

    @Getter
    @Setter
    @Column(name = "id_sensor")
    private long id_sensor;

    @Getter
    @Setter
    @Column(name = "ultima_medida")
    private String UltimaMedida;

    @Getter
    @Setter
    @Column(name = "timestamp")
    private String timestamp;

    @Getter
    @Setter
    @Column(name = "last_second")
    private int last_second;

    @Getter
    @Setter
    @Column(name = "last_entrities")
    private int last_entrities;

    @Getter
    @Setter
    @Column(name = "mes")
    private int mes;
}
