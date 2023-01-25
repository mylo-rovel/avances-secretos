package cl.ucn.fondef.sata.mini.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;


/**
 * The type Ejecucion.
 */
@Entity
@Table(name = "ejecucion")
@ToString
@EqualsAndHashCode
public class Ejecucion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    @Column(name = "id")
    private long id;

    @Getter
    @Setter
    @Column(name = "id_simulacion")
    private long idSimulacion;

    @Getter
    @Setter
    @Column(name = "fecha_ejecucion")
    private String fechaEjecucion;
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
    @Column(name = "last_entities")
    private int last_entities;

    @Getter
    @Setter
    @Column(name = "mes")
    private int mes;
}
