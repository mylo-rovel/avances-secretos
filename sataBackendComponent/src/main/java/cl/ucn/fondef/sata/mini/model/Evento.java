package cl.ucn.fondef.sata.mini.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

/**
 * The type Evento.
 */
@Entity
@Table(name = "evento")
@ToString
@EqualsAndHashCode
public class Evento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    @Column(name = "id")
    private long id;

    @Getter
    @Setter
    @Column(name = "id_secuencia")
    private long idSecuencia;

    @Getter
    @Setter
    @Column(name = "intensidad")
    private int intensidad;

    @Getter
    @Setter
    @Column(name = "duracion")
    private int duracion;

    @Getter
    @Setter
    @Column(name = "posicion")
    private int posicion;
}
