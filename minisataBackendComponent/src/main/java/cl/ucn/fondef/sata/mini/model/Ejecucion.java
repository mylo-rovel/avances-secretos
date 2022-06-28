package cl.ucn.fondef.sata.mini.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;


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
    private Long idSimulacion;

    @Getter
    @Setter
    @Column(name = "agua_caida")
    private Double aguaCaida;

    @Getter
    @Setter
    @Column(name = "fecha_ejecucion")
    private String fechaEjecucion;
}
