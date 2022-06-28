package cl.ucn.fondef.sata.mini.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;


@Entity
@Table(name = "simulacioncomponente")
@ToString
@EqualsAndHashCode
public class SimulacionComponente {
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
    @Column(name = "id_componente")
    private long idComponente;
}
