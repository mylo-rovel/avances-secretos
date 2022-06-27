package cl.ucn.fondef.sata.mini.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

/**
 * The type Pin.
 */
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "componentefisico")
public class Pin {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Getter
    @Setter
    @Column(name = "id")
    private long id;

    @Getter
    @Setter
    @Column(name = "id_componente")
    private long idComponente;

    @Getter
    @Setter
    @Column(name = "nombre")
    private String nombre;

    @Getter
    @Setter
    @Column(name = "numero")
    private int numero;

    @Getter
    @Setter
    @Column(name = "descripcion")
    private String descripcion;

    @Getter
    @Setter
    @Column(name = "placa")
    private String placa;

    @Getter
    @Setter
    @Column(name = "conexion")
    private String conexion;
}
