package cl.ucn.fondef.sata.mini.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;


/**
 * The type Ejecucion secuencia.
 */
@Entity
@Table(name = "ejecucionsecuencia")
@ToString
@EqualsAndHashCode
public class EjecucionSecuencia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    @Column(name = "id")
    private long id;

    @Getter
    @Setter
    @Column(name = "id_ejecucion")
    private long idEjecucion;

    @Getter
    @Setter
    @Column(name = "id_secuencia")
    private long idSecuencia;
}

