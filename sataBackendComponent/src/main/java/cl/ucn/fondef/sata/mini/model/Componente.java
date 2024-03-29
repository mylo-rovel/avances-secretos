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
 * The type Componente.
 */
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "componente")
public class Componente {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
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
    @Column(name = "nombre")
    private String nombre;

    @Getter
    @Setter
    @Column(name = "descripcion")
    private String descripcion;

    @Getter
    @Setter
    @Column(name = "url")
    private String url;

    @Getter
    @Setter
    @Column(name = "estado")
    private String estado;

    @Getter
    @Setter
    @Column(name = "tipo")
    private String tipo;

    @Getter
    @Setter
    @Column(name = "tipo_placa")
    private String tipoPlaca;


    /* ATRIBUTOS NUEVOS PARA QUE LA QUERY DE CoreDaoSimulacionImpl.java pueda compilar ----------------------------- */
    /* ¿Por qué estos atributos están en acá en lugar de en Simulación? */

}
