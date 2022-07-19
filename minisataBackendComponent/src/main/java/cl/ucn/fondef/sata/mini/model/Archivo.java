package cl.ucn.fondef.sata.mini.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.File;

/**
 * The type Archivo.
 */
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "archivo")
public class Archivo {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
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
    @Column(name = "data")
    private File data;

    @Getter
    @Setter
    @Column(name = "tipo")
    private String tipo;

}
