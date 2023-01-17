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
    private long idEquipo;

    @Getter
    @Setter
    @Column(name = "id_operador")
    private long idOperador;

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
    private String fechaCreacion;
    //
    @Getter
    @Setter
    @Column(name = "caudal")
    private String caudal;

    @Getter
    @Setter
    @Column(name = "temperatura")
    private String temperatura;

    @Getter
    @Setter
    @Column(name = "pluviometro")
    private String pluviometro;

    @Getter
    @Setter
    @Column(name = "presion")
    private String presion;

    @Getter
    @Setter
    @Column(name = "humedad")
    private String humedad;

    @Getter
    @Setter
    @Column(name = "idEjecucion")
    private int idEjecucion;

    @Getter
    @Setter
    @Column(name = "idSensor")
    private int idSensor;

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
    @Column(name = "lastSecond")
    private int lastSecond;

    @Getter
    @Setter
    @Column(name = "lastEntrities")
    private int lastEntrities;

    @Getter
    @Setter
    @Column(name = "mes")
    private int mes;
}
